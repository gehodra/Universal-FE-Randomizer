package purposeful;

import random.gcnwii.fe9.randomizer.FE9GrowthRandomizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DataModificationMap {

    private final static String ID_DELIM = ":" ;
    private final static String COMMENT_STRING = "#" ;
    private final static String LIST_DELIM = "," ;

    List<DataModificationEntry> dataEntries = new LinkedList<>();

    public DataModificationMap(String file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            List<String> currentFields = null;
            while ((line = br.readLine()) != null) {
                String content = getUncommentedContent(line);
                if (content.isBlank()) {
                    continue;
                }
                if (isFieldLine(content)) {
                    currentFields = getFields(content);
                } else {
                    String id = getId(content);
                    List<String> currentValues = getValues(content);
                    dataEntries.add(new DataModificationEntry(id, currentFields, currentValues));
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static String getUncommentedContent(String line) {
        return line.split(COMMENT_STRING, 2)[0];
    }

    private static boolean isFieldLine(String line) {
        return !line.contains(ID_DELIM);
    }

    private static List<String> getFields(String line) {
        return Arrays.asList(line.split(LIST_DELIM));
    }

    private static List<String> getValues(String line) {
        return Arrays.asList(line.split(ID_DELIM)[1].split(LIST_DELIM));
    }

    private static String getId(String line) {
        return line.split(ID_DELIM)[0];
    }

    public List<DataModificationEntry> getEntries() {
        return dataEntries;
    }
}
