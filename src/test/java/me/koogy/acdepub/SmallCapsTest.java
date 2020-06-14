package me.koogy.acdepub;

import me.koogy.acdepub.objects.AcdParser;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

/*
** SmallCapsTest
** Testing the <smallcaps> markup
** Examples from Trollope.
*/

public class SmallCapsTest {

    private static Logger logger = LogManager.getLogger(SmallCapsTest.class);

//    "<smallcaps>Adolphus Crosbie.</smallcaps>"
//    "<smallcaps>Grace Crawley.</smallcaps>"
//    "<smallcaps>Henry Grantly.</smallcaps>"
//    "<smallcaps>Josiah Crawley.</smallcaps>"
//    "<smallcaps>Julia De Guest.</smallcaps>"
//    "<smallcaps>Lily Dale.</smallcaps>"
//    "<smallcaps>Mortimer Tempest.</smallcaps>"
//    "<smallcaps>M. D. M.</smallcaps>"
//    "<smallcaps>Septimus Harding.</smallcaps>"
//    "<smallcaps>T. Barnum.</smallcaps>"
//    "<smallcaps>T. Grantly.</smallcaps>"
//    "<smallcaps>Theophilus Grantly.</smallcaps>"
//    "<smallcaps>Thos. Barnum.</smallcaps>"

    @Test
    public void testAll() {
        String input = "Before:<smallcaps>One</smallcaps>:Middle:<smallcaps>Two</smallcaps>:After";
        String actual = AcdParser.replaceSmallCaps(input);
        String expected = "Before:"
                + "O<span class=\"sc\">NE</span>"
                + ":Middle:"
                + "T<span class=\"sc\">WO</span>"
                + ":After";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testJoseph() {
        String input = "<smallcaps>Joseph Cradlet</smallcaps>";
        String expected = "J<span class=\"sc\">OSEPH </span>C<span class=\"sc\">RADLET</span>";
        doTest(input, expected);
    }

    @Test
    public void testMdm() {
        String input = "<smallcaps>M. D. M.</smallcaps>";
        String expected = "M. D. M.";
        doTest(input, expected);
    }

    @Test
    public void testJulia() {
        String input = "<smallcaps>Julia De Guest.</smallcaps>";
        String expected = "J<span class=\"sc\">ULIA </span>D<span class=\"sc\">E </span>G<span class=\"sc\">UEST.</span>";
        doTest(input, expected);
    }

    @Test
    public void testThis() {
        String input = "<smallcaps>Thos. Barnum.</smallcaps>";
        String expected = "T<span class=\"sc\">HOS. </span>B<span class=\"sc\">ARNUM.</span>";
        doTest(input, expected);
    }

    void doTest (String input, String expected) {
        String actual = AcdParser.toSmallCaps(input);
        Assert.assertEquals(expected, actual);
    }
}
