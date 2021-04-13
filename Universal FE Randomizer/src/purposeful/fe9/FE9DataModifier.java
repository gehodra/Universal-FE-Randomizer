/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package purposeful.fe9;

import purposeful.AbstractDataModifier;
import random.gcnwii.fe9.loader.FE9CharacterDataLoader;
import random.gcnwii.fe9.loader.FE9ClassDataLoader;
import random.gcnwii.fe9.loader.FE9ItemDataLoader;

import java.util.stream.Collectors;

public class FE9DataModifier extends AbstractDataModifier {
    FE9CharacterDataLoader charData;
    FE9ClassDataLoader classData;
    FE9ItemDataLoader itemData;

    public FE9DataModifier(FE9CharacterDataLoader charData, FE9ClassDataLoader classData, FE9ItemDataLoader itemData) {
        super();
        this.charData = charData;
        this.classData = classData;
        this.itemData = itemData;

        db.addData(charData.allCharacters().stream().collect(Collectors.toMap(x -> charData.getPIDForCharacter(x), y -> y)));
        db.addData(classData.allClasses().stream().collect(Collectors.toMap(x -> classData.getJIDForClass(x), y -> y)));
        db.addData(itemData.allItems().stream().collect(Collectors.toMap(x -> itemData.iidOfItem(x), y -> y)));
    }

    @Override
    public void commit() {
        charData.commit();
        classData.commit();
        itemData.commit();
    }
}