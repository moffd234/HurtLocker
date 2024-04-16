import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class JerkSONTest {
    JerksonParser jerkson;
    String rawData;

    @Before
    public void setup(){
        jerkson = new JerksonParser();
        try {
            rawData = jerkson.readRawDataToString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @Test
    public void outputTests() {
        String expected =
                "name:    Milk \t\t seen: 8 times\n"+
                "============= \t \t =============\n" +
                "Price: \t 3.23\t\t seen: 5 times\n" +
                "-------------\t\t -------------\n" +
                "Price:   1.23\t\t seen: 1 time\n" +
                "\n" +
                "name:   Bread\t\t seen: 6 times\n" +
                "=============\t\t =============\n" +
                "Price:   1.23\t\t seen: 6 times\n" +
                "-------------\t\t -------------\n" +
                "\n" +
                "name: Cookies     \t seen: 8 times\n" +
                "=============     \t =============\n" +
                "Price:   2.25        seen: 8 times\n" +
                "-------------        -------------\n" +
                "\n" +
                "name:  Apples     \t seen: 4 times\n" +
                "=============     \t =============\n" +
                "Price:   0.25     \t seen: 2 times\n" +
                "-------------     \t -------------\n" +
                "Price:   0.23  \t \t seen: 2 times\n" +
                "\n" +
                "Errors         \t \t seen: 4 times\n";

        String[] input = jerkson.format();
        String actual = jerkson.prettify(input);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void readRawInputTest() {
        String expected = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##naME:BreaD;" +
                "price:1.23;type:Food;expiration:1/02/2016##NAMe:BrEAD;price:1.23;type:Food;" +
                "expiration:2/25/2016##naMe:MiLK;price:3.23;type:Food^expiration:1/11/2016##naMe:" +
                "Cookies;price:2.25;type:Food%expiration:1/25/2016##naMe:CoOkieS;price:2.25;type:" +
                "Food*expiration:1/25/2016##naMe:COokIes;price:2.25;type:Food;expiration:3/22/2016" +
                "##naMe:COOkieS;price:2.25;type:Food;expiration:1/25/2016##NAME:MilK;price:3.23;" +
                "type:Food;expiration:1/17/2016##naMe:MilK;price:1.23;type:Food!expiration:" +
                "4/25/2016##naMe:apPles;price:0.25;type:Food;expiration:1/23/2016##naMe:apPles;" +
                "price:0.23;type:Food;expiration:5/02/2016##NAMe:BrEAD;price:1.23;type:Food;" +
                "expiration:1/25/2016##naMe:;price:3.23;type:Food;expiration:1/04/2016##naMe:" +
                "Milk;price:3.23;type:Food;expiration:1/25/2016##naME:BreaD;price:1.23;type:" +
                "Food@expiration:1/02/2016##NAMe:BrEAD;price:1.23;type:Food@expiration:2/25/2016" +
                "##naMe:MiLK;priCe:;type:Food;expiration:1/11/2016##naMe:Cookies;price:2.25;type:" +
                "Food;expiration:1/25/2016##naMe:Co0kieS;pRice:2.25;type:Food;expiration:1/25/2016" +
                "##naMe:COokIes;price:2.25;type:Food;expiration:3/22/2016##naMe:COOkieS;" +
                "Price:2.25;type:Food;expiration:1/25/2016##NAME:MilK;price:3.23;type:Food;" +
                "expiration:1/17/2016##naMe:MilK;priCe:;type:Food;expiration:4/25/2016##naMe:" +
                "apPles;prIce:0.25;type:Food;expiration:1/23/2016##naMe:apPles;pRice:0.23;type:" +
                "Food;expiration:5/02/2016##NAMe:BrEAD;price:1.23;type:Food;expiration:" +
                "1/25/2016##naMe:;price:3.23;type:Food^expiration:1/04/2016##";
        Assert.assertEquals(expected, rawData);
    }

    @Test
    public void testObjectSplit(){
        String[] expected = {"naMe:Milk;price:3.23;type:Food;expiration:1/25/2016", "naME:BreaD;price:1.23;type:Food;expiration:1/02/2016", "NAMe:BrEAD;price:1.23;type:Food;expiration:2/25/2016", "naMe:MiLK;price:3.23;type:Food^expiration:1/11/2016", "naMe:Cookies;price:2.25;type:Food%expiration:1/25/2016", "naMe:CoOkieS;price:2.25;type:Food*expiration:1/25/2016", "naMe:COokIes;price:2.25;type:Food;expiration:3/22/2016", "naMe:COOkieS;price:2.25;type:Food;expiration:1/25/2016", "NAME:MilK;price:3.23;type:Food;expiration:1/17/2016", "naMe:MilK;price:1.23;type:Food!expiration:4/25/2016", "naMe:apPles;price:0.25;type:Food;expiration:1/23/2016", "naMe:apPles;price:0.23;type:Food;expiration:5/02/2016", "NAMe:BrEAD;price:1.23;type:Food;expiration:1/25/2016", "naMe:;price:3.23;type:Food;expiration:1/04/2016", "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016", "naME:BreaD;price:1.23;type:Food@expiration:1/02/2016", "NAMe:BrEAD;price:1.23;type:Food@expiration:2/25/2016", "naMe:MiLK;priCe:;type:Food;expiration:1/11/2016", "naMe:Cookies;price:2.25;type:Food;expiration:1/25/2016", "naMe:Co0kieS;pRice:2.25;type:Food;expiration:1/25/2016", "naMe:COokIes;price:2.25;type:Food;expiration:3/22/2016", "naMe:COOkieS;Price:2.25;type:Food;expiration:1/25/2016", "NAME:MilK;price:3.23;type:Food;expiration:1/17/2016", "naMe:MilK;priCe:;type:Food;expiration:4/25/2016", "naMe:apPles;prIce:0.25;type:Food;expiration:1/23/2016", "naMe:apPles;pRice:0.23;type:Food;expiration:5/02/2016", "NAMe:BrEAD;price:1.23;type:Food;expiration:1/25/2016", "naMe:;price:3.23;type:Food^expiration:1/04/2016"};
        String[] actual = jerkson.separateObjects(rawData);
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void format() {
        String[] expected = {"naMe: Milk; price: 3.23; type: Food; expiration: 1/25/2016; ", "naME: BreaD; price: 1.23; type: Food; expiration: 1/02/2016; ", "NAMe: BrEAD; price: 1.23; type: Food; expiration: 2/25/2016; ", "naMe: MiLK; price: 3.23; type: Food; expiration: 1/11/2016; ", "naMe: Cookies; price: 2.25; type: Food; expiration: 1/25/2016; ", "naMe: CoOkieS; price: 2.25; type: Food; expiration: 1/25/2016; ","naMe: COokIes; price: 2.25; type: Food; expiration: 3/22/2016; ","naMe: COOkieS; price: 2.25; type: Food; expiration: 1/25/2016; ","NAME: MilK; price: 3.23; type: Food; expiration: 1/17/2016; ","naMe: MilK; price: 1.23; type: Food; expiration: 4/25/2016; ","naMe: apPles; price: 0.25; type: Food; expiration: 1/23/2016; ","naMe: apPles; price: 0.23; type: Food; expiration: 5/02/2016; ","NAMe: BrEAD; price: 1.23; type: Food; expiration: 1/25/2016; ","naMe: ; price: 3.23; type: Food; expiration: 1/04/2016; ","naMe: Milk; price: 3.23; type: Food; expiration: 1/25/2016; ","naME: BreaD; price: 1.23; type: Food; expiration: 1/02/2016; ","NAMe: BrEAD; price: 1.23; type: Food; expiration: 2/25/2016; ","naMe: MiLK; priCe: ; type: Food; expiration: 1/11/2016; ","naMe: Cookies; price: 2.25; type: Food; expiration: 1/25/2016; ","naMe: Co0kieS; pRice: 2.25; type: Food; expiration: 1/25/2016; ","naMe: COokIes; price: 2.25; type: Food; expiration: 3/22/2016; ","naMe: COOkieS; Price: 2.25; type: Food; expiration: 1/25/2016; ","NAME: MilK; price: 3.23; type: Food; expiration: 1/17/2016; ","naMe: MilK; priCe: ; type: Food; expiration: 4/25/2016; ","naMe: apPles; prIce: 0.25; type: Food; expiration: 1/23/2016; ","naMe: apPles; pRice: 0.23; type: Food; expiration: 5/02/2016; ","NAMe: BrEAD; price: 1.23; type: Food; expiration: 1/25/2016; ","naMe: ; price: 3.23; type: Food; expiration: 1/04/2016; "};

        String[] actual = jerkson.format();
        Assert.assertArrayEquals(expected, actual);

    }

    @Test
    public void testFieldProcessor() {
        String expected = "naMe: Milk; price: 3.23; type: Food; expiration: 1/25/2016; ";

        String[] objects = jerkson.separateObjects(rawData);
        String actual = jerkson.processFields(objects[0]);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testFindOccurrences(){
        String input = "Milk";
        String[] objects = jerkson.format();
        int expected = 8;
        int actual = jerkson.countOccurrences(objects, input);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCookiesOccurrences() {
        String input = "Cookies";
        String[] objects = jerkson.format();
        int expected = 7;
        int actual = jerkson.countOccurrences(objects, input);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testBreadOccurrences() {
        String input = "Bread";
        String[] objects = jerkson.format();
        int expected = 6;
        int actual = jerkson.countOccurrences(objects, input);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testApplesOccurrences() {
        String input = "Apples";
        String[] objects = jerkson.format();
        int expected = 4;
        int actual = jerkson.countOccurrences(objects, input);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testAlreadyAdded() {
        String[] input = {"Milk", "Cookies"};
        boolean actual = jerkson.alreadyAdded("Milk", input);

        Assert.assertTrue(actual);
    }

    @Test
    public void testAlreadyAdded2() {
        String[] input = {"Milk", "Cookies"};
        boolean actual = jerkson.alreadyAdded("cOoKiEs", input);

        Assert.assertTrue(actual);
    }
    @Test
    public void testAlreadyAddedFalse() {
        String[] input = {"Milk", "Cookies"};
        boolean actual = jerkson.alreadyAdded("Bread", input);

        Assert.assertFalse(actual);
    }

    @Test
    public void testGetDistinctObjs() {
        String[] input = jerkson.format();
        String[] expected = {"Milk", "Bread", "Cookies", "Apples"};
        String[] actual = jerkson.getDistinctItems(input);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testHandleTypos() {
        String input = "MiLk";
        String expected = "Milk";

        String actual = jerkson.handelTypos(input);
        Assert.assertEquals(expected, actual);
    }
    @Test
    public void testHandleTyposC0okies() {
        String input = "C0okies";
        String expected = "Cookies";

        String actual = jerkson.handelTypos(input);
        Assert.assertEquals(expected, actual);
    }
}
