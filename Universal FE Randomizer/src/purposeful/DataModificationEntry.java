package purposeful;

import java.util.List;

public class DataModificationEntry {
    public String id;
    public List<String> fields;
    public List<String> values;

    public DataModificationEntry(String id, List<String> fields, List<String> values)
    {
    this.id = id;
    this.fields = fields;
    this.values = values;
    }

    public String getId() {
        return id;
    }

    public List<String> getFields() {
        return fields;
    }

    public List<String> getValues() {
        return values;
    }
}
