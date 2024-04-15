import org.apache.commons.io.IOUtils;

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
        return null;
    }
}
