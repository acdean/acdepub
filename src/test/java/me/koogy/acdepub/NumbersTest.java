package me.koogy.acdepub;

import junit.framework.TestCase;

/**
 * @author adean
 */
public class NumbersTest extends TestCase {
    
    public NumbersTest(String testName) {
        super(testName);
    }
    
    /**
     * Test of words method, of class Numbers.
     */
    public void testWords1() {
        for (int i = 0 ; i < 131 ; i++) {
            String result = Numbers.number("one", i);
            System.out.println("one[" + i + "]: [" + result + "]");
        }
    }

    public void testWords2() {
        for (int i = 0 ; i < 131 ; i++) {
            String result = Numbers.number("One", i);
            System.out.println("One[" + i + "]: [" + result + "]");
        }
    }

    public void testWords3() {
        for (int i = 0 ; i < 131 ; i++) {
            String result = Numbers.number("ONE", i);
            System.out.println("ONE[" + i + "]: [" + result + "]");
        }
    }

    public void testDigits() {
        for (int i = 0 ; i < 131 ; i++) {
            String result = Numbers.number("1", i);
            System.out.println("Digit[" + i + "]: [" + result + "]");
        }
    }

    public void testRoman() {
        for (int i = 0 ; i < 131 ; i++) {
            String result = Numbers.number("i", i);
            System.out.println("roman[" + i + "]: [" + result + "]");
        }
    }

    public void testRoman2() {
        for (int i = 0 ; i < 131 ; i++) {
            String result = Numbers.number("I", i);
            System.out.println("ROMAN[" + i + "]: [" + result + "]");
        }
    }

    public void testAlpha() {
        for (int i = 0 ; i < 30 ; i++) {
            String result = Numbers.number("a", i);
            System.out.println("alpha[" + i + "]: [" + result + "]");
        }
    }

    public void testAlpha2() {
        for (int i = 0 ; i < 30 ; i++) {
            String result = Numbers.number("A", i);
            System.out.println("ALPHA[" + i + "]: [" + result + "]");
        }
    }
}
