import org.apache.commons.io.IOUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JerksonParser {


    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        return IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
    }


    public static void main(String[] args) throws Exception{
        JerksonParser jerkson = new JerksonParser();
        String output = (new JerksonParser()).readRawDataToString();
        String[] objects = jerkson.format();
        for (int i = 0; i < objects.length - 1; i++) {
            String obj = objects[i];
            System.out.println(jerkson.prettyFormat(obj, objects));
        }
    }

    public String[] format() {
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

    public String prettify(String[] objects) {
        return null;
    }

    public String prettyFormat(String jerkSONObject, String[] objects){
        int errorCount = 0;
        String[] keyValuePairs = jerkSONObject.split("; ");
        String[] values = new String[4];
        String output = "";
        for (int i = 0; i < keyValuePairs.length; i++) {
            String pairs = keyValuePairs[i];
            try {
                String value = pairs.split(": ")[1];
                values[i] = value;
                String name = values[0];
                int count = countOccurrences(objects, name);
                String price = values[1];
                String type = values[2];
                String date = values[3];
                output = String.format("Name: %s\t\tseen: %s times\n=============\t" +
                        "\t=============\nprice %s\t\tseen %s times\n" +
                        "-------------\t\t-------------\n\n", name, count, price, count);
            }
            catch (ArrayIndexOutOfBoundsException e){
                errorCount++;
            }
        }
        return output;
    }

    public boolean alreadyAdded(String strToAdd, String[] strArray){
        for(String str: strArray){
            if(str.equalsIgnoreCase(strToAdd)){
                return true;
            }
        }
        return false;
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
