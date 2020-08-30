package io.gcn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import io.FileHandler;
import util.ByteArrayBuilder;
import util.WhyDoesJavaNotHaveThese;

// GCN Data files have a header that has some information, and all address references are offset by 0x20 to account for this.
// So any pointers used in the file have a built in +0x20 to the address they actually point to.
// The header has 4 pieces.
//
// 0x00 - 0x03 - 4 bytes - The size of the file.
// 0x04 - 0x07 - 4 bytes - The address of the start of the list of pointers (remember to add 0x20).
// 0x08 - 0x0B - 4 bytes - The number of pointers in the list.
// 0x0C - 0x0F - 4 bytes - Unknown currently. It looks like a count of items, and it seems to line up with the number of entries
// 							of something after the pointer list, with each entry being 8 bytes long. After that is more strings
//							that could theoretically be pointed at.
//
// Every pointer used in this file MUST be in this list, otherwise it will be ignored!
//
// This file handler is the same as a regular file handler, but it has the ability to manage the list of pointers as well.
public class GCNDataFileHandler extends GCNByteArrayHandler {
	
	private Set<String> stringList;
	private Set<Long> pointerOffsetList;
	private Map<Long, String> pointerToString;
	private Map<String, Long> pointerLookup;
	
	private Set<Long> addedPointersOffsets;
	private Set<String> addedStrings;
	
	private long pointerOffset;
	
	boolean needsExpansion = false;
	boolean wasExpanded = false;

	public GCNDataFileHandler(GCNFSTFileEntry file, FileHandler handler, byte[] byteArray) {
		super(file, handler, byteArray);
		
		stringList = new HashSet<String>();
		pointerOffsetList = new HashSet<Long>();
		pointerToString = new HashMap<Long, String>();
		pointerLookup = new HashMap<String, Long>();
		
		addedPointersOffsets = new HashSet<Long>();
		addedStrings = new HashSet<String>();
		
		pointerOffset = WhyDoesJavaNotHaveThese.longValueFromByteArray(readBytesAtOffset(0x04, 4), false);
		
		int numberOfPointers = (int)WhyDoesJavaNotHaveThese.longValueFromByteArray(readBytesAtOffset(0x08, 4), false);
		long currentOffset = pointerOffset + 0x20;
		for (int i = 0; i < numberOfPointers; i++) {
			// Each item is the offset of a pointer used in the data.
			long pointerOffset = WhyDoesJavaNotHaveThese.longValueFromByteArray(readBytesAtOffset(currentOffset, 4), false);
			pointerOffsetList.add(pointerOffset);
			
			// Remember to add 0x20 when dereferencing.
			long pointer = WhyDoesJavaNotHaveThese.longValueFromByteArray(readBytesAtOffset(pointerOffset + 0x20, 4), false);
			setNextReadOffset(pointer + 0x20);
			String dereferenced = WhyDoesJavaNotHaveThese.stringFromAsciiBytes(continueReadingBytesUpToNextTerminator(pointer + 0x20 + 0xFF));
			pointerToString.put(pointer + 0x20, dereferenced);
			pointerLookup.put(dereferenced, pointer + 0x20);
			
			stringList.add(dereferenced);
			currentOffset += 4;
		}
	}
	
	public void addPointerOffset(long offset) {
		if (pointerOffsetList.contains(offset) || addedPointersOffsets.contains(offset)) { return; }
		addedPointersOffsets.add(offset);
		needsExpansion = true;
	}
	
	public void addString(String string) {
		if (stringList.contains(string) || addedStrings.contains(string)) { return; }
		addedStrings.add(string);
		needsExpansion = true;
	}
	
	public List<String> allStrings() {
		return stringList.stream().sorted(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		}).collect(Collectors.toList());
	}
	
	// These pointers are already offset by the 0x20, meaning they point directly to the string. If you need to use it, subtract 0x20 before assigning it.
	public Long pointerForString(String string) {
		return pointerLookup.get(string);
	}
	
	public String stringForPointer(long pointer) {
		return pointerToString.get(pointer);
	}
	
	public void commitAdditions() {
		if (needsExpansion) {
			// Determine bytes needed for new pointers.
			ByteArrayBuilder pointerDataBuilder = new ByteArrayBuilder();
			for (long newPointer : addedPointersOffsets) {
				pointerDataBuilder.appendBytes(WhyDoesJavaNotHaveThese.byteArrayFromLongValue(newPointer, false, 4));
			}
						
			// Determine bytes needed for strings. These will be added to the very end of the file.
			// We'll also have to remember their addresses so that we can reference them later.
			ByteArrayBuilder stringDataBuilder = new ByteArrayBuilder();
			long insertAddress = pointerOffset + 0x20; 
			Map<String, Long> addedStringsToAddresses = new HashMap<String, Long>();
			for (String newString : addedStrings) {
				addedStringsToAddresses.put(newString, insertAddress);
				byte[] stringBytes = WhyDoesJavaNotHaveThese.asciiBytesFromString(newString);
				stringDataBuilder.appendBytes(stringBytes);
				stringDataBuilder.appendByte((byte)0);
				insertAddress += stringBytes.length + 1;
			}
			
			while (stringDataBuilder.getBytesWritten() % 4 != 0) {
				stringDataBuilder.appendByte((byte)0);
			}
			
			// Update header.
			int newNumberOfPointers = pointerOffsetList.size() + addedPointersOffsets.size();
			
			ByteArrayBuilder headerDataBuilder = new ByteArrayBuilder();
			// We'll fill this in later.
			headerDataBuilder.appendBytes(WhyDoesJavaNotHaveThese.byteArrayFromLongValue(0, false, 4));
			headerDataBuilder.appendBytes(WhyDoesJavaNotHaveThese.byteArrayFromLongValue(pointerOffset, false, 4)); // We'll change this later once we know where the pointers end up.
			headerDataBuilder.appendBytes(WhyDoesJavaNotHaveThese.byteArrayFromLongValue(newNumberOfPointers, false, 4));
			// There's one last value in the header that refers to the count of something else. They're 8 byte long entries, but I"m not sure what they are.
			// Nevertheless we have to restore it.
			headerDataBuilder.appendBytes(readBytesAtOffset(0x0C, 4));
			headerDataBuilder.appendBytes(new byte[] {
					0, 0, 0, 0, 
					0, 0, 0, 0,
					0, 0, 0, 0, 
					0, 0, 0, 0}); // Then there's sixteen 00 bytes separating the header and the data.
			
			// Compile header -> file data -> existing strings -> added strings -> existing pointers -> new pointers -> everything else.
			byte[] existingDataAndStrings = readBytesAtOffset((long)0x20, (int)pointerOffset);
			byte[] existingPointerOffsets = readBytesAtOffset(pointerOffset + 0x20, pointerOffsetList.size() * 4);
			long remainingOffset = pointerOffset + 0x20 + pointerOffsetList.size() * 4;
			byte[] remainingData = readBytesAtOffset(remainingOffset, (int)(byteArray.length - remainingOffset));
			
			ByteArrayBuilder expandedFileBuilder = new ByteArrayBuilder();
			expandedFileBuilder.appendBytes(headerDataBuilder.toByteArray()); // Header
			expandedFileBuilder.appendBytes(existingDataAndStrings);
			expandedFileBuilder.appendBytes(stringDataBuilder.toByteArray()); // Additional Strings
			long newPointerOffset = expandedFileBuilder.getBytesWritten() - 0x20;
			expandedFileBuilder.appendBytes(existingPointerOffsets);
			expandedFileBuilder.appendBytes(pointerDataBuilder.toByteArray()); // New pointer offsets
			expandedFileBuilder.appendBytes(remainingData);
			
			while (expandedFileBuilder.getBytesWritten() % 4 != 0) { expandedFileBuilder.appendByte((byte)0); }
			expandedFileBuilder.replaceBytes(0, WhyDoesJavaNotHaveThese.byteArrayFromLongValue(expandedFileBuilder.getBytesWritten(), false, 4));
			byteArray = expandedFileBuilder.toByteArray();

			// Write the new pointer offset (since we had to write everything before we could figure it out).
			byte[] pointerOffsetBytes = WhyDoesJavaNotHaveThese.byteArrayFromLongValue(newPointerOffset, false, 4);
			WhyDoesJavaNotHaveThese.copyBytesIntoByteArrayAtIndex(pointerOffsetBytes, byteArray, 0x4, 4);
			pointerOffset = newPointerOffset;
			
			needsExpansion = false;
			wasExpanded = true;
			
			pointerOffsetList.addAll(addedPointersOffsets);
			
			addedPointersOffsets.clear();
			
			for (String string : addedStringsToAddresses.keySet()) {
				pointerLookup.put(string, addedStringsToAddresses.get(string));
				pointerToString.put(addedStringsToAddresses.get(string), string);
			}
			
			addedStrings.clear();
		}
	}

	@Override
	public boolean hasChanges() {
		return super.hasChanges() || wasExpanded;
	}
	
	
}
