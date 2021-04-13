package purposeful;

import fedata.general.FEModifiableData;

import java.util.HashMap;
import java.util.Map;

public class GenericDataModifier {

    Map<String, FEModifiableData> idMap = new HashMap<>();

    public GenericDataModifier() {
    }

    public void addData(Map<String, FEModifiableData> data) {
        idMap.putAll(data);
    }

    public boolean containsKey(String id) {
        return idMap.keySet().stream().anyMatch(x -> x.matches(id));
    }

    public void updateData(DataModificationEntry dme) {
        idMap.entrySet().stream().filter(x -> x.getKey().matches(dme.getId())).forEach(matchingData ->
        {
            Reflector cReflect = new Reflector(matchingData.getValue());
            cReflect.reflect(dme.getFields(), dme.getValues());
        });
    }
}
