import org.apache.commons.io.IOUtils;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JerksonParser {

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        return IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
    }


    public static void main(String[] args) throws Exception{
        JerksonParser jerkson = new JerksonParser();
        String output = (new JerksonParser()).readRawDataToString();
        jerkson.format();
    }

    public String[] format() {
        String fieldRegex = "[!@;^%*]";
        String rawData;
        try {
            rawData = readRawDataToString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String[] objects = separateObjects(rawData);
        for(int i = 0; i < objects.length; i++){
            objects[i] = processFields(objects[i]);
        }
        return objects;
    }
    public String[] separateObjects(String rawData){
        String objectRegex = "##";
        return rawData.split(objectRegex);
    }

    public String processFields(String object) {

        String kvRegex = "[:@^*%;!]";
        String[] kvPairs = object.split(kvRegex); // Split the KV pairs
        StringBuilder sb = new StringBuilder();

        // Fix Jerkson so it actually looks normal and readable
        for (int i = 0; i < kvPairs.length - 1; i += 2) {
            sb.append(kvPairs[i]);
            sb.append(": ");
            if (i + 1 < kvPairs.length) {
                sb.append(kvPairs[i + 1]);
            }
            sb.append("; ");
        }
        return sb.toString();
    }

    public String getOutput() {
        return null;
    }

    public int countOccurrences(String[] objects, String input) {
        int count = 0;

        for (String entry : objects) {
            String[] keyValuePairs = entry.split("; ");
            for (String pair : keyValuePairs) {
                if(checkIfContains(pair, input)){
                    count += 1;
                }
            }
        }
        return count;
    }

    public boolean checkIfContains(String pair, String input){
        String[] keyValue = pair.split(":");
        if (keyValue[1].equalsIgnoreCase(" " + input)) {
            return true;
        }
        return false;
    }
}
