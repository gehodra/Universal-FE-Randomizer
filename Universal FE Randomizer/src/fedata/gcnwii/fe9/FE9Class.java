package fedata.gcnwii.fe9;

import java.util.Arrays;

import fedata.general.FEModifiableData;
import util.WhyDoesJavaNotHaveThese;

public class FE9Class implements FEModifiableData {
	
	public static int ClassSkill1Offset = 0x18;
	public static int ClassSkill2Offset = 0x1C;
	public static int ClassSkill3Offset = 0x20;

	private byte[] originalData;
	private byte[] data;
	
	private long originalOffset;
	
	private Boolean wasModified = false;
	private Boolean hasChanges = false;
	
	private Long cachedClassIDPointer;
	private Long cachedNameIDPointer;
	private Long cachedDescriptionIDPointer;
	
	private Long cachedPromotedIDPointer;
	private Long cachedDefaultWeaponPointer;
	private Long cachedWeaponLevelPointer;
	
	private Long cachedSkill1Pointer;
	private Long cachedSkill2Pointer;
	private Long cachedSkill3Pointer;
	
	private Long cachedRacePointer;
	private Long cachedTraitPointer;
	
	private Long cachedAnimationPointer;
	
	public FE9Class(byte[] data, long originalOffset) {
		super();
		this.originalData = data;
		this.data = data;
		this.originalOffset = originalOffset;
	}
	
	public long getClassIDPointer() {
		if (cachedClassIDPointer == null) {
			cachedClassIDPointer = readPointerAtOffset(0x0);
		}
		return cachedClassIDPointer;
	}
	
	public long getClassNamePointer() {
		if (cachedNameIDPointer == null) {
			cachedNameIDPointer = readPointerAtOffset(0x4);
		}
		return cachedNameIDPointer;
	}
	
	public long getClassDescriptionPointer() {
		if (cachedDescriptionIDPointer == null) {
			cachedDescriptionIDPointer = readPointerAtOffset(0x8);
		}
		return cachedDescriptionIDPointer;
	}
	
	public long getPromotionIDPointer() {
		if (cachedPromotedIDPointer == null) {
			cachedPromotedIDPointer = readPointerAtOffset(0xC);
		}
		return cachedPromotedIDPointer;
	}
	
	public long getDefaultWeaponPointer() {
		if (cachedDefaultWeaponPointer == null) {
			cachedDefaultWeaponPointer = readPointerAtOffset(0x10);
		}
		return cachedDefaultWeaponPointer;
	}
	
	public long getWeaponLevelPointer() {
		if (cachedWeaponLevelPointer == null) {
			cachedWeaponLevelPointer = readPointerAtOffset(0x14);
		}
		return cachedWeaponLevelPointer;
	}
	
	public void setWeaponLevelPointer(long newPtr) {
		cachedWeaponLevelPointer = newPtr;
		writePointerToOffset(newPtr, 0x14);
		wasModified = true;
	}
	
	public long getSkill1Pointer() {
		if (cachedSkill1Pointer == null) {
			cachedSkill1Pointer = readPointerAtOffset(FE9Class.ClassSkill1Offset);
		}
		return cachedSkill1Pointer;
	}
	
	public void setSkill1Pointer(long ptr) {
		cachedSkill1Pointer = ptr;
		writePointerToOffset(ptr, FE9Class.ClassSkill1Offset);
		wasModified = true;
	}
	
	public long getSkill2Pointer() {
		if (cachedSkill2Pointer == null) {
			cachedSkill2Pointer = readPointerAtOffset(FE9Class.ClassSkill2Offset);
		}
		return cachedSkill2Pointer;
	}
	
	public void setSkill2Pointer(long ptr) {
		cachedSkill2Pointer = ptr;
		writePointerToOffset(ptr, FE9Class.ClassSkill2Offset);
		wasModified = true;
	}
	
	public long getSkill3Pointer() {
		if (cachedSkill3Pointer == null) {
			cachedSkill3Pointer = readPointerAtOffset(FE9Class.ClassSkill3Offset);
		}
		return cachedSkill3Pointer;
	}
	
	public void setSkill3Pointer(long ptr) {
		cachedSkill3Pointer = ptr;
		writePointerToOffset(ptr, FE9Class.ClassSkill3Offset);
		wasModified = true;
	}
	
	public long getRacePointer() {
		if (cachedRacePointer == null) {
			cachedRacePointer = readPointerAtOffset(0x2C);
		}
		return cachedRacePointer;
	}
	
	public long getTraitPointer() {
		if (cachedTraitPointer == null) {
			cachedTraitPointer = readPointerAtOffset(0x30);
		}
		return cachedTraitPointer;
	}
	
	public long getAnimationPointer() {
		if (cachedAnimationPointer == null) {
			cachedAnimationPointer = readPointerAtOffset(0x38);
		}
		return cachedAnimationPointer;
	}
	
	public int getBaseCON() {
		return data[0x3C];
	}
	
	public int getBaseWeight() {
		return data[0x3D];
	}
	
	public int getMovementRange() {
		return data[0x3E];
	}
	
	public int getUnknownByte() {
		return data[0x3F];
	}
	
	public int getSkillCapacity() {
		return data[0x40];
	}
	
	public byte[] getUnknown3Bytes() {
		return Arrays.copyOfRange(data, 0x41, 0x41 + 3);
	}
	
	public int getBaseHP() { return data[0x44]; }
        public void setBaseHP(int newGrowth) { data[0x44] = (byte)(newGrowth & 0xFF); wasModified = true; }
	public int getBaseSTR() { return data[0x45]; }
        public void setBaseSTR(int newGrowth) { data[0x45] = (byte)(newGrowth & 0xFF); wasModified = true; }
	public int getBaseMAG() { return data[0x46]; }
        public void setBaseMAG(int newGrowth) { data[0x46] = (byte)(newGrowth & 0xFF); wasModified = true; }
	public int getBaseSKL() { return data[0x47]; }
        public void setBaseSKL(int newGrowth) { data[0x47] = (byte)(newGrowth & 0xFF); wasModified = true; }
	public int getBaseSPD() { return data[0x48]; }
        public void setBaseSPD(int newGrowth) { data[0x48] = (byte)(newGrowth & 0xFF); wasModified = true; }
	public int getBaseLCK() { return data[0x49]; }
        public void setBaseLCK(int newGrowth) { data[0x49] = (byte)(newGrowth & 0xFF); wasModified = true; }
	public int getBaseDEF() { return data[0x4A]; }
        public void setBaseDEF(int newGrowth) { data[0x4A] = (byte)(newGrowth & 0xFF); wasModified = true; }
	public int getBaseRES() { return data[0x4B]; }
        public void setBaseRES(int newGrowth) { data[0x4B] = (byte)(newGrowth & 0xFF); wasModified = true; }
	
	public int getMaxHP() { return data[0x4C]; }
        public void setMaxHP(int newGrowth) { data[0x4C] = (byte)(newGrowth & 0xFF); wasModified = true; }
	public int getMaxSTR() { return data[0x4D]; }
        public void setMaxSTR(int newGrowth) { data[0x4D] = (byte)(newGrowth & 0xFF); wasModified = true; }
	public int getMaxMAG() { return data[0x4E]; }
        public void setMaxMAG(int newGrowth) { data[0x4E] = (byte)(newGrowth & 0xFF); wasModified = true; }
	public int getMaxSKL() { return data[0x4F]; }
        public void setMaxSKL(int newGrowth) { data[0x4F] = (byte)(newGrowth & 0xFF); wasModified = true; }
	public int getMaxSPD() { return data[0x50]; }
        public void setMaxSPD(int newGrowth) { data[0x50] = (byte)(newGrowth & 0xFF); wasModified = true; }
	public int getMaxLCK() { return data[0x51]; }
        public void setMaxLCK(int newGrowth) { data[0x51] = (byte)(newGrowth & 0xFF); wasModified = true; }
	public int getMaxDEF() { return data[0x52]; }
        public void setMaxDEF(int newGrowth) { data[0x52] = (byte)(newGrowth & 0xFF); wasModified = true; }
	public int getMaxRES() { return data[0x53]; }
        public void setMaxRES(int newGrowth) { data[0x53] = (byte)(newGrowth & 0xFF); wasModified = true; }
	
	public int getHPGrowth() { return (data[0x54] & 0xFF); }
	public void setHPGrowth(int newGrowth) { data[0x54] = (byte)(newGrowth & 0xFF); wasModified = true; }
	public int getSTRGrowth() { return (data[0x55] & 0xFF); }
	public void setSTRGrowth(int newGrowth) { data[0x55] = (byte)(newGrowth & 0xFF); wasModified = true; }
	public int getMAGGrowth() { return (data[0x56] & 0xFF); }
	public void setMAGGrowth(int newGrowth) { data[0x56] = (byte)(newGrowth & 0xFF); wasModified = true; }
	public int getSKLGrowth() { return (data[0x57] & 0xFF); }
	public void setSKLGrowth(int newGrowth) { data[0x57] = (byte)(newGrowth & 0xFF); wasModified = true; }
	public int getSPDGrowth() { return (data[0x58] & 0xFF); }
	public void setSPDGrowth(int newGrowth) { data[0x58] = (byte)(newGrowth & 0xFF); wasModified = true; }
	public int getLCKGrowth() { return (data[0x59] & 0xFF); }
	public void setLCKGrowth(int newGrowth) { data[0x59] = (byte)(newGrowth & 0xFF); wasModified = true; }
	public int getDEFGrowth() { return (data[0x5A] & 0xFF); }
	public void setDEFGrowth(int newGrowth) { data[0x5A] = (byte)(newGrowth & 0xFF); wasModified = true; }
	public int getRESGrowth() { return (data[0x5B] & 0xFF); }
	public void setRESGrowth(int newGrowth) { data[0x5B] = (byte)(newGrowth & 0xFF); wasModified = true; }
        
        public int getHPAlteration() { return data[0x5C]; }
	public void setHPAlteration(int newGrowth) { data[0x5C] = (byte)(newGrowth & 0xFF); wasModified = true; }
	public int getSTRAlteration() { return data[0x5D]; }
	public void setSTRAlteration(int newGrowth) { data[0x5D] = (byte)(newGrowth & 0xFF); wasModified = true; }
	public int getMAGAlteration() { return data[0x5E]; }
	public void setMAGAlteration(int newGrowth) { data[0x5E] = (byte)(newGrowth & 0xFF); wasModified = true; }
	public int getSKLAlteration() { return data[0x5F]; }
	public void setSKLAlteration(int newGrowth) { data[0x5F] = (byte)(newGrowth & 0xFF); wasModified = true; }
	public int getSPDAlteration() { return data[0x60]; }
	public void setSPDAlteration(int newGrowth) { data[0x60] = (byte)(newGrowth & 0xFF); wasModified = true; }
	public int getLCKAlteration() { return data[0x61]; }
	public void setLCKAlteration(int newGrowth) { data[0x61] = (byte)(newGrowth & 0xFF); wasModified = true; }
	public int getDEFAlteration() { return data[0x62]; }
	public void setDEFAlteration(int newGrowth) { data[0x62] = (byte)(newGrowth & 0xFF); wasModified = true; }
	public int getRESAlteration() { return data[0x63]; }
	public void setRESAlteration(int newGrowth) { data[0x63] = (byte)(newGrowth & 0xFF); wasModified = true; }
	
	private long readPointerAtOffset(int offset) {
		byte[] ptr = Arrays.copyOfRange(data, offset, offset + 4);
		if (WhyDoesJavaNotHaveThese.byteArraysAreEqual(ptr, new byte[] {0, 0, 0, 0})) { return 0; }
		
		return WhyDoesJavaNotHaveThese.longValueFromByteArray(ptr, false) + 0x20;
	}
	
	private void writePointerToOffset(long pointer, int offset) {
		byte[] ptr = pointer == 0 ? new byte[] {0, 0, 0, 0} : WhyDoesJavaNotHaveThese.bytesFromPointer(pointer - 0x20);
		WhyDoesJavaNotHaveThese.copyBytesIntoByteArrayAtIndex(ptr, data, offset, 4);
	}
	
	public void resetData() {
		data = originalData;
		wasModified = false;
	}
	
	public void commitChanges() {
		if (wasModified) {
			hasChanges = true;
		}
		wasModified = false;
	}
	
	public Boolean hasCommittedChanges() {
		return hasChanges;
	}
	
	public byte[] getData() {
		return data;
	}
	
	public Boolean wasModified() {
		return wasModified;
	}
	
	public long getAddressOffset() {
		return originalOffset;
	}
}
