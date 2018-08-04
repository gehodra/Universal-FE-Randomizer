package random;

import java.util.HashMap;
import java.util.Map;

import fedata.FEBase;
import fedata.FECharacter;
import fedata.fe7.FE7Data;
import fedata.general.Palette;
import fedata.general.PaletteInfo;
import io.FileHandler;
import util.Diff;
import util.DiffCompiler;

public class PaletteLoader {
	private FEBase.GameType gameType;
	
	private Map<Integer, Map<Integer, Palette>> characterPalettes = new HashMap<Integer, Map<Integer, Palette>>();
	private Map<Integer, Palette> templates = new HashMap<Integer, Palette>();
	
	public PaletteLoader(FEBase.GameType gameType, FileHandler handler) {
		this.gameType = gameType;
		
		switch (gameType) {
		case FE7:
			for (FE7Data.Character character : FE7Data.Character.allPlayableCharacters) {
				int charID = FE7Data.Character.canonicalIDForCharacterID(character.ID);
				Map<Integer, Palette> map = new HashMap<Integer, Palette>();
				characterPalettes.put(charID, map);
				for (PaletteInfo palette : FE7Data.Palette.palettesForCharacter(charID)) {
					int classID = palette.getClassID();
					map.put(classID, new Palette(handler, palette, 40));
					FE7Data.CharacterClass fe7class = FE7Data.CharacterClass.valueOf(classID);
					FE7Data.Character fe7char = FE7Data.Character.valueOf(charID);
					System.out.println("Initializing Character 0x" + Integer.toHexString(charID) + " (" + fe7char.toString() + ")" + " with palette at offset 0x" + Long.toHexString(palette.getOffset()) + " (Class: " + Integer.toHexString(classID) + " (" + fe7class.toString() + "))");
				}
			}
			for (FE7Data.Character boss : FE7Data.Character.allBossCharacters) {
				int charID = FE7Data.Character.canonicalIDForCharacterID(boss.ID);
				Map<Integer, Palette> map = new HashMap<Integer, Palette>();
				characterPalettes.put(charID, map);
				for (PaletteInfo palette : FE7Data.Palette.palettesForCharacter(charID)) {
					map.put(palette.getClassID(), new Palette(handler, palette, 40));
					FE7Data.Character fe7char = FE7Data.Character.valueOf(charID);
					System.out.println("Initializing Boss 0x" + Integer.toHexString(charID) + " (" + fe7char.toString() + ")" + " with palette at offset 0x" + Long.toHexString(palette.getOffset()));
				}
			}
			
			for (FE7Data.CharacterClass characterClass : FE7Data.CharacterClass.allValidClasses) {
				templates.put(characterClass.ID, new Palette(handler, FE7Data.Palette.defaultPaletteForClass(characterClass.ID), 40));
			}
			break;
		default:
			break;
		}
	}
	
	public Palette getPalette(int characterID, int classID) {
		switch (gameType) {
		case FE7:
			return characterPalettes.get(FE7Data.Character.canonicalIDForCharacterID(characterID)).get(classID);
		default:
			return null;
		}
	}
	
	public int canonicalCharacterID(int characterID) {
		switch (gameType) {
		case FE7:
			return FE7Data.Character.canonicalIDForCharacterID(characterID);
		default:
			return 0;
		}
	}
	
	public void adaptCharacterToClass(int characterID, int originalClassID, int newClassID) {
		int charID = canonicalCharacterID(characterID);
		FE7Data.Character character = FE7Data.Character.valueOf(charID);
		FE7Data.CharacterClass oldClass = FE7Data.CharacterClass.valueOf(originalClassID);
		FE7Data.CharacterClass newClass = FE7Data.CharacterClass.valueOf(newClassID);
		System.out.println("Adapting character 0x" + Integer.toHexString(charID) + " (" + character.toString() + ") " + 
		" from class 0x" + Integer.toHexString(originalClassID) + "(" + oldClass.toString() + ")" + " to 0x" + Integer.toHexString(newClassID) + " (" + newClass.toString() + ")");
		Palette originalPalette = getPalette(charID, originalClassID);
		Palette template = getTemplatePalette(newClassID);
		
		System.out.println("Original palette offset: " + Long.toHexString(originalPalette.getInfo().getOffset()));
		System.out.println("Template palette offset: " + Long.toHexString(template.getInfo().getOffset()));
		
		Palette adaptedPalette = new Palette(template, originalPalette);
		Map<Integer, Palette> paletteMap = characterPalettes.get(charID);
		
		System.out.println("Adapted palette offset: " + Long.toHexString(adaptedPalette.getInfo().getOffset()));
		
		paletteMap.put(newClassID, adaptedPalette);
		paletteMap.remove(originalClassID);
	}
	
	public void compileDiffs(DiffCompiler compiler) {
		for (Map<Integer, Palette> map : characterPalettes.values()) {
			for (Palette palette : map.values()) {
				palette.commitPalette(compiler);
			}
		}
	}
	
	public Palette getTemplatePalette(int classID) {
		switch (gameType) {
		case FE7:
			return templates.get(classID);
		default:
			return null;
		}
	}
}
