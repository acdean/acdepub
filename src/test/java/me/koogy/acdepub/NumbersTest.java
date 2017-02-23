package me.koogy.acdepub;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author adean
 */
public class NumbersTest {

    /**
     * Test of words method, of class Numbers.
     */
    @Test
    public void testWords1() {
        for (int i = 0 ; i < 131 ; i++) {
            String result = Numbers.number("one", i);
            System.out.println("one[" + i + "]: [" + result + "]");
        }
    }

    @Test
    public void testWords2() {
        for (int i = 0 ; i < 131 ; i++) {
            String result = Numbers.number("One", i);
            System.out.println("One[" + i + "]: [" + result + "]");
        }
    }

    @Test
    public void testWords3() {
        for (int i = 0 ; i < 131 ; i++) {
            String result = Numbers.number("ONE", i);
            System.out.println("ONE[" + i + "]: [" + result + "]");
        }
    }

    @Test
    public void testDigits() {
        for (int i = 0 ; i < 131 ; i++) {
            String result = Numbers.number("1", i);
            System.out.println("Digit[" + i + "]: [" + result + "]");
        }
    }

    @Test
    public void testRoman() {
        for (int i = 0 ; i < 131 ; i++) {
            String result = Numbers.number("i", i);
            System.out.println("roman[" + i + "]: [" + result + "]");
        }
    }

    @Test
    public void testRoman2() {
        for (int i = 0 ; i < 131 ; i++) {
            String result = Numbers.number("I", i);
            System.out.println("ROMAN[" + i + "]: [" + result + "]");
        }
    }

    @Test
    public void testAlpha() {
        for (int i = 0 ; i < 30 ; i++) {
            String result = Numbers.number("a", i);
            System.out.println("alpha[" + i + "]: [" + result + "]");
        }
    }

    @Test
    public void testAlpha2() {
        for (int i = 0 ; i < 30 ; i++) {
            String result = Numbers.number("A", i);
            System.out.println("ALPHA[" + i + "]: [" + result + "]");
        }
    }

    @Test
    public void testOrdinals() {
        String[] result = {
            null,
            "First",
            "Second",
            "Third",
            "Fourth",
            "Fifth",
            "Sixth",
            "Seventh",
            "Eighth",
            "Ninth",
            "Tenth",
            "Eleventh",
            "Twelfth",
            "Thirteenth",
            "Fourteenth",
            "Fifteenth",
            "Sixteenth",
            "Seventeenth",
            "Eighteenth",
            "Nineteenth",
            "Twentieth",
            "Twenty-First",
            "Twenty-Second",
            "Twenty-Third",
            "Twenty-Fourth",
            "Twenty-Fifth",
            "Twenty-Sixth",
            "Twenty-Seventh",
            "Twenty-Eighth",
            "Twenty-Ninth",
            "Thirtieth",
            "Thirty-First",
            "Thirty-Second",
            "Thirty-Third",
            "Thirty-Fourth",
            "Thirty-Fifth",
            "Thirty-Sixth",
            "Thirty-Seventh",
            "Thirty-Eighth",
            "Thirty-Ninth",
            "Fortieth",
            "Forty-First",
            "Forty-Second",
            "Forty-Third",
            "Forty-Fourth",
            "Forty-Fifth",
            "Forty-Sixth",
            "Forty-Seventh",
            "Forty-Eighth",
            "Forty-Ninth",
            "Fiftieth",
            "Fifty-First",
            "Fifty-Second",
            "Fifty-Third",
            "Fifty-Fourth",
            "Fifty-Fifth",
            "Fifty-Sixth",
            "Fifty-Seventh",
            "Fifty-Eighth",
            "Fifty-Ninth",
            "Sixtieth",
            "Sixty-First",
            "Sixty-Second",
            "Sixty-Third",
            "Sixty-Fourth",
            "Sixty-Fifth",
            "Sixty-Sixth",
            "Sixty-Seventh",
            "Sixty-Eighth",
            "Sixty-Ninth",
            "Seventieth",
            "Seventy-First",
            "Seventy-Second",
            "Seventy-Third",
            "Seventy-Fourth",
            "Seventy-Fifth",
            "Seventy-Sixth",
            "Seventy-Seventh",
            "Seventy-Eighth",
            "Seventy-Ninth",
            "Eightieth",
            "Eighty-First",
            "Eighty-Second",
            "Eighty-Third",
            "Eighty-Fourth",
            "Eighty-Fifth",
            "Eighty-Sixth",
            "Eighty-Seventh",
            "Eighty-Eighth",
            "Eighty-Ninth",
            "Ninetieth",
            "Ninety-First",
            "Ninety-Second",
            "Ninety-Third",
            "Ninety-Fourth",
            "Ninety-Fifth",
            "Ninety-Sixth",
            "Ninety-Seventh",
            "Ninety-Eighth",
            "Ninety-Ninth"
        };
        for (int i = 1 ; i < 100 ; i++) {
            String s = Numbers.number("First", i);
            System.out.println(s);
            Assert.assertEquals(result[i], s);
        }
    }
}
