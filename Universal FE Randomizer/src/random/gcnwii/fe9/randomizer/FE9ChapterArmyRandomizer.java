package random.gcnwii.fe9.randomizer;

import fedata.gcnwii.fe9.FE9ChapterUnit;
import fedata.gcnwii.fe9.FE9Data;
import fedata.gcnwii.fe9.FE9Item;
import random.gcnwii.fe9.loader.FE9ChapterDataLoader;
import random.gcnwii.fe9.loader.FE9ItemDataLoader;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class FE9ChapterArmyRandomizer {

    public static void replaceItems(FE9ItemDataLoader itemData, FE9ChapterDataLoader chapterData, Random rng,
                                    List<FE9Item> replacedSet,
                                    List<FE9Item> replacingSet) {
        FE9Data.Chapter.chaptersWithAllDifficulties.stream()
                .flatMap(x -> chapterData.armiesForChapter(x).stream())
                .forEach(army ->
                        {
                            List<FE9ChapterUnit> units = army.getAllUnitIDs().stream()
                                    .map(y -> army.getUnitForUnitID(y)).collect(Collectors.toList());
                            for (FE9ChapterUnit unit : units) {
                                if (replacedSet.stream().anyMatch(x -> itemData.iidOfItem(x).equals(army.getItem1ForUnit(unit)))) {
                                    army.setItem1ForUnit(unit, itemData.iidOfItem(replacingSet.get(rng.nextInt(replacingSet.size()))));
                                }
                                if (replacedSet.stream().anyMatch(x -> itemData.iidOfItem(x).equals(army.getItem2ForUnit(unit)))) {
                                    army.setItem2ForUnit(unit, itemData.iidOfItem(replacingSet.get(rng.nextInt(replacingSet.size()))));
                                }
                                if (replacedSet.stream().anyMatch(x -> itemData.iidOfItem(x).equals(army.getItem3ForUnit(unit)))) {
                                    army.setItem3ForUnit(unit, itemData.iidOfItem(replacingSet.get(rng.nextInt(replacingSet.size()))));
                                }
                                if (replacedSet.stream().anyMatch(x -> itemData.iidOfItem(x).equals(army.getItem4ForUnit(unit)))) {
                                    army.setItem4ForUnit(unit, itemData.iidOfItem(replacingSet.get(rng.nextInt(replacingSet.size()))));
                                }
                                if (replacedSet.stream().anyMatch(x -> itemData.iidOfItem(x).equals(army.getWeapon1ForUnit(unit)))) {
                                    army.setWeapon1ForUnit(unit, itemData.iidOfItem(replacingSet.get(rng.nextInt(replacingSet.size()))));
                                }
                                if (replacedSet.stream().anyMatch(x -> itemData.iidOfItem(x).equals(army.getWeapon2ForUnit(unit)))) {
                                    army.setWeapon2ForUnit(unit, itemData.iidOfItem(replacingSet.get(rng.nextInt(replacingSet.size()))));
                                }
                                if (replacedSet.stream().anyMatch(x -> itemData.iidOfItem(x).equals(army.getWeapon3ForUnit(unit)))) {
                                    army.setWeapon3ForUnit(unit, itemData.iidOfItem(replacingSet.get(rng.nextInt(replacingSet.size()))));
                                }
                                if (replacedSet.stream().anyMatch(x -> itemData.iidOfItem(x).equals(army.getWeapon4ForUnit(unit)))) {
                                    army.setWeapon4ForUnit(unit, itemData.iidOfItem(replacingSet.get(rng.nextInt(replacingSet.size()))));
                                }
                            }
                            army.commitChanges();
                        }
                );
    }
}
