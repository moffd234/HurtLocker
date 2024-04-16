import org.apache.commons.io.IOUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JerksonParser {
    int errorCount = 0;

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
        System.out.printf("Errors\t\tseen %d times%n", jerkson.errorCount);
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
        String[] distObjs = getDistinctItems(objects);

        return null;
    }

    public String[] getDistinctItems(String[] objects) {
        String[] distinctObjects = new String[4];
        int index = 0;
        for (String object : objects) {
            String[] keyValuePairs = object.split("; ");
            if(keyValuePairs[0].split(": ").length > 1) {
                String value = keyValuePairs[0].split(": ")[1]; // Gets the name of the item if there is one
                value = handelTypos(value);
                if (!alreadyAdded(value, distinctObjects)) {
                    distinctObjects[index] = value;
                    index += 1;
                }
            }
        }
        return distinctObjects;
    }

    public String prettyFormat(String jerkSONObject, String[] objects){

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
                output += String.format("Name: %s\t\tseen: %s times\n=============\t" +
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
            if(str != null) {
                if (str.equalsIgnoreCase(strToAdd)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String handelTypos(String word){
        if(word.equalsIgnoreCase("Cookies")){
            return "Cookies";
        }
        if(word.equalsIgnoreCase("Bread")){
            return "Bread";
        }
        if(word.equalsIgnoreCase("Milk")){
            return "Milk";
        }
        if(word.equalsIgnoreCase("Apples")){
            return "Apples";
        }
        return "Cookies"; // This handles when Cookies is spelled with a 0
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
        return keyValue[1].equalsIgnoreCase(" " + input);
    }
}
