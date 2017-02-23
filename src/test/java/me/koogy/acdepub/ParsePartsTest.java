package me.koogy.acdepub;

import java.util.List;
import me.koogy.acdepub.objects.AcdParser;
import me.koogy.acdepub.objects.Book;
import me.koogy.acdepub.objects.Part;
import org.junit.Test;
import org.junit.Assert;

/**
 * Parsing books.
 * @author adean
 */
public class ParsePartsTest {

    // part without info should still parse
    @Test
    public void testParseParts1() {
        System.out.println("\ntestParseParts1");
        // book without part info
        String s = "<xml>"
                + "<book>"
                + "<info>"
                +   "<title>Title</title>"
                +   "<author>Author</author>"
                +   "<date>2001</date>"
                + "</info>"
                + "<part>"
                +   "<chapter>"
                +     "<p>Chapter 1 Paragraph 1</p>"
                +   "</chapter>"
                +   "<chapter>"
                +     "<p>Chapter 2 Paragraph 1</p>"
                +   "</chapter>"
                + "</part>"
                + "</book>";
        String expected =
                "[Part:{"
                    + "Info:{Info{TocTitle[null], Title[null], SubTitle[null], Author[null], Date[null], HasCover[false]}}, "
                    + "Numbering:{Part One}, Id:{pt01}, "
                    + "Chapters:{"
                        + "[Chapter:{Numbering:{Chapter I} Id:{ch01001} Normal:{true} Content: 29}, "
                        + "Chapter:{Numbering:{Chapter II} Id:{ch01002} Normal:{true} Content: 29}]"
                    + "}"
                + "}]";

        parseBook(s, expected);
    }

    @Test
    public void testAllBits() {
        System.out.println("\ntestParseParts1");
        // book without part info
        String s = "<xml>"
                + "<book>"
                + "<info>"
                +   "<title>Title</title>"
                +   "<subtitle>Subtitle</subtitle>"
                +   "<author>Author</author>"
                +   "<date>2001</date>"
                + "</info>"
                + "<part>"
                +   "<info>"
                +     "<title>Part One</title>"
                +     "<subtitle>Subtitle One</subtitle>"
                +   "</info>"
                +   "<chapter>"
                +     "<info>"
                +       "<title>Chapter Title 1</title>"
                +       "<subtitle>Chapter Subtitle 1</subtitle>"
                +     "</info>"
                +     "<p>Chapter 1 Paragraph 1</p>"
                +   "</chapter>"
                +   "<chapter>"
                +     "<info>"
                +       "<title>Chapter Title 2</title>"
                +     "</info>"
                +     "<p>Chapter 2 Paragraph 1</p>"
                +   "</chapter>"
                + "</part>"
                + "</book>";
        String expected =
                "[Part:{"
                    + "Info:{Info{TocTitle[Part One], Title[Part One], SubTitle[Subtitle One], Author[null], Date[null], HasCover[false]}}, "
                    + "Numbering:{Part One}, Id:{pt01}, "
                    + "Chapters:{"
                        + "[Chapter:{Numbering:{Chapter I} Title:{Chapter Title 1} Id:{ch01001} Normal:{true} Content: 81}, "
                        + "Chapter:{Numbering:{Chapter II} Title:{Chapter Title 2} Id:{ch01002} Normal:{true} Content: 42}]"
                    + "}"
                + "}]";

        parseBook(s, expected);
    }

    // various artists
    @Test
    public void testParseParts2() {
        System.out.println("\ntestParseParts2");
        // book without part info
        String s = "<xml>"
                + "<book>"
                + "<info>"
                +   "<title>Title</title>"
                +   "<subtitle>Subtitle</subtitle>"
                +   "<author>Various</author>"
                +   "<date>1970</date>"
                +   "<option name=\"part.title_text\" value=\"PART\"/>"
                +   "<option name=\"part.number_style\" value=\"ONE\"/>"
                + "</info>"
                + "<part>"
                + "<info>"
                +   "<title>Title 1</title>"
                +   "<subtitle>Subtitle 1</subtitle>"
                +   "<author>Author 1</author>"
                +   "<date>2001</date>"
                + "</info>"
                +   "<chapter>"
                +     "<p>Part 1 Chapter 1</p>"
                +   "</chapter>"
                + "</part>"
                + "<part>"
                + "<info>"
                +   "<title>Title 2</title>"
                +   "<subtitle>Subtitle 2</subtitle>"
                +   "<author>Author 2</author>"
                    // no date
                + "</info>"
                +   "<chapter>"
                +     "<p>Part 2 Chapter 1</p>"
                +   "</chapter>"
                + "</part>"
                + "</book>";
        String expected = "[Part:{"
                    + "Info:{Info{TocTitle[Title 1], Title[Title 1], SubTitle[Subtitle 1], Author[Author 1], Date[2001], HasCover[false]}}, "
                    + "Numbering:{PART ONE}, Id:{pt01}, "
                    + "Chapters:{[Chapter:{Numbering:{Chapter I} Id:{ch01001} Normal:{true} Content: 24}]}}, "
                + "Part:{"
                    + "Info:{Info{TocTitle[Title 2], Title[Title 2], SubTitle[Subtitle 2], Author[Author 2], Date[null], HasCover[false]}}, "
                    + "Numbering:{PART TWO}, Id:{pt02}, "
                    + "Chapters:{[Chapter:{Numbering:{Chapter I} Id:{ch02001} Normal:{true} Content: 24}]}}"
                + "]";
        
        parseBook(s, expected);
    }

    // attributes
    // title etc of part 1 are done as attributes rather than info. should be identical result to above.
    @Test
    public void testParsePartAttributes() {
        System.out.println("\ntestParseParts2");
        // book without part info
        String s = "<xml>"
                + "<book>"
                + "<info>"
                +   "<title>Title</title>"
                +   "<subtitle>Subtitle</subtitle>"
                +   "<toctitle>TocTitle</toctitle>"
                +   "<author>Various</author>"
                +   "<date>1970</date>"
                +   "<option name=\"part.title_text\" value=\"PART\"/>"
                +   "<option name=\"part.number_style\" value=\"ONE\"/>"
                + "</info>"
                + "<part>"
                +   "<info title=\"Title 1\" subtitle=\"Subtitle 1\" author=\"Author 1\" date=\"2001\"/>"
                +   "<chapter>"
                +     "<p>Part 1 Chapter 1</p>"
                +   "</chapter>"
                + "</part>"
                + "<part>"
                + "<info>"
                +   "<title>Title 2</title>"
                +   "<subtitle>Subtitle 2</subtitle>"
                +   "<author>Author 2</author>"
                    // no date
                + "</info>"
                +   "<chapter>"
                +     "<p>Part 2 Chapter 1</p>"
                +   "</chapter>"
                + "</part>"
                + "</book>";
        String expected = "[Part:{"
                    + "Info:{Info{TocTitle[Title 1], Title[Title 1], SubTitle[Subtitle 1], Author[Author 1], Date[2001], HasCover[false]}}, "
                    + "Numbering:{PART ONE}, Id:{pt01}, "
                    + "Chapters:{[Chapter:{Numbering:{Chapter I} Id:{ch01001} Normal:{true} Content: 24}]}}, "
                + "Part:{"
                    + "Info:{Info{TocTitle[Title 2], Title[Title 2], SubTitle[Subtitle 2], Author[Author 2], Date[null], HasCover[false]}}, "
                    + "Numbering:{PART TWO}, Id:{pt02}, "
                    + "Chapters:{[Chapter:{Numbering:{Chapter I} Id:{ch02001} Normal:{true} Content: 24}]}}"
                + "]";
        
        parseBook(s, expected);
    }

    // utility function, not a test
    void parseBook(String input, Book expected) {
        Book actual = AcdParser.parseBook(input.getBytes());
        System.out.println("Actual: " + actual);
        System.out.println("Expected: " + expected);
        Assert.assertEquals(expected, actual);
    }

    // utility funciton
    // check parts only
    void parseBook(String input, String expected) {
        Book book = AcdParser.parseBook(input.getBytes());
        List<Part> actual = book.getParts();
        System.out.println("Actual: " + actual);
        System.out.println("Expected: " + expected);
        Assert.assertEquals(expected, actual.toString());
    }
}
