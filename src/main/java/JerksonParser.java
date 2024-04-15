import org.apache.commons.io.IOUtils;

import java.util.Arrays;

public class JerksonParser {

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        return IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
    }


    public static void main(String[] args) throws Exception{
        String output = (new JerksonParser()).readRawDataToString();
        System.out.println(output);

    }

    public String format() {
        String separators = ":@^*%";
        return null;
    }
    public String[] separateObjects(String rawData){
        String fieldRegex = "[!@;^%*]";
        String objectRegex = "##";
        String kvRegex = "[:@^*%]";
        System.out.println(Arrays.toString(rawData.split("##")));
        return rawData.split("##");
    }
}
