package me.koogy.acdepub;

/**
 * @author adean
 */
public class Numbers {

    public static String number(String style, int i) {
        if (style.equals("one")) {
            return words(i).toLowerCase();
        }
        if (style.equals("ONE")) {
            return words(i).toUpperCase();
        }
        if (style.equals("One")) {
            return words(i);
        }
        if (style.equals("i")) {
            return roman(i);
        }
        if (style.equals("I")) {
            return roman(i).toUpperCase();
        }
        if (style.equals("1")) {
            return digits(i);
        }
        if (style.equals("a")) {
            return alpha(i);
        }
        if (style.equals("A")) {
            return alpha(i).toUpperCase();
        }
        return null;
    }
    
    public static String words(int i) {
        StringBuilder result = new StringBuilder();
        String hundredStr = null, tenStr = null, unitStr = null;
        if (i >= 100) {
            hundredStr = (units(i / 100)) + " Hundred";
            i = i % 100;
        }
        if (i >= 20) {
            int t = i / 10;
            switch(t) {
                case 2: tenStr = "Twenty"; break;
                case 3: tenStr = "Thirty"; break;
                case 4: tenStr = "Forty"; break;
                case 5: tenStr = "Fifty"; break;
                case 6: tenStr = "Sixty"; break;
                case 7: tenStr = "Seventy"; break;
                case 8: tenStr = "Eighty"; break;
                case 9: tenStr = "Ninety"; break;
            }
            i = i - 10 * t;
        }
        switch(i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                unitStr = units(i); break;
            case 10: unitStr = "Ten"; break;
            case 11: unitStr = "Eleven"; break;
            case 12: unitStr = "Twelve"; break;
            case 13: unitStr = "Thirteen"; break;
            case 14: unitStr = "Fourteen"; break;
            case 15: unitStr = "Fifteen"; break;
            case 16: unitStr = "Sixteen"; break;
            case 17: unitStr = "Seventeen"; break;
            case 18: unitStr = "Eighteen"; break;
            case 19: unitStr = "Nineteen"; break;
            default: break;
        }
        
        if (hundredStr != null) {
            result.append(hundredStr);
        }
        if (hundredStr != null && (tenStr != null || unitStr != null)) {
            result.append(" and");
        }
        if (tenStr != null) {
            if (result.length() != 0) {
                result.append(" ");
            }
            result.append(tenStr);
        }
        if (unitStr != null) {
            if (result.length() != 0) {
                result.append(" ");
            }
            result.append(unitStr);
        }
        return result.toString();
    }

    private static String units(int i) {
        switch(i) {
            case 0: return null;
            case 1: return "One";
            case 2: return "Two";
            case 3: return "Three";
            case 4: return "Four";
            case 5: return "Five";
            case 6: return "Six";
            case 7: return "Seven";
            case 8: return "Eight";
            case 9: return "Nine";
        }
        return null;
    }

    public static String digits(int i) {
        return new Integer(i).toString();
    }
    
    public static String alpha(int i) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        if (i <= 0 || i > alphabet.length()) {
            return "";
        }
        return "" + alphabet.charAt(i - 1);
    }
    
    // 0 to 399
    private static String roman(int i) {
        StringBuilder result = new StringBuilder();
        switch (i / 100) {
            case 3: result.append("ccc"); break;
            case 2: result.append("cc"); break;
            case 1: result.append("c"); break;
        }
        i = i % 100;
        switch (i / 10) {
            case 9: result.append("xc"); break;
            case 8: result.append("lxxx"); break;
            case 7: result.append("lxx"); break;
            case 6: result.append("lx"); break;
            case 5: result.append("l"); break;
            case 4: result.append("xl"); break;
            case 3: result.append("xxx"); break;
            case 2: result.append("xx"); break;
            case 1: result.append("x"); break;
        }
        i = i % 10;
        switch (i) {
            case 9: result.append("ix"); break;
            case 8: result.append("viii"); break;
            case 7: result.append("vii"); break;
            case 6: result.append("vi"); break;
            case 5: result.append("v"); break;
            case 4: result.append("iv"); break;
            case 3: result.append("iii"); break;
            case 2: result.append("ii"); break;
            case 1: result.append("i"); break;
        }
        return result.toString();
    }
}
