package purposeful;

import util.DebugPrinter;

import java.util.Set;

public abstract class AbstractDataModifier {

    public GenericDataModifier db = new GenericDataModifier();

    public void applyDataUpdates(Set<String> paths) {
        for (String path : paths) {
            DebugPrinter.log(DebugPrinter.Key.FE_DATA_BALANCER, "Applying Data Update from File: " + path);
            DataModificationMap dataMap = new DataModificationMap(path);
            for (DataModificationEntry dme : dataMap.getEntries()) {
                if (db.containsKey(dme.getId())) {
                    db.updateData(dme);
                } else {
                    DebugPrinter.error(DebugPrinter.Key.FE_DATA_BALANCER, "Unused data entry: " + dme.getId());
                }
            }
        }
        commit();
    }

    protected abstract void commit();
}
