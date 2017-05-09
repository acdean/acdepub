package me.koogy.acdepub;

import java.util.List;
import java.util.Map;
import me.koogy.acdepub.objects.AcdParser;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author adean
 */
public class AcdParserTest {

    @Test
    public void testAttributes() {
        String input = "<tag name1=\"value1\" name2=\"value2\"/>";
        
        Map<String, String> map = AcdParser.extractAttributes(input);
        Assert.assertEquals("value1", map.get("name1"));
        Assert.assertEquals("value2", map.get("name2"));
    }

    // https://github.com/acdean/acdepub/issues/2
    @Test
    public void testAttributesWithSpace() {
        String input = "<tag name1=\"two words\" name2=\"value2\"/>";
        
        Map<String, String> map = AcdParser.extractAttributes(input);
        Assert.assertEquals("two words", map.get("name1"));
        Assert.assertEquals("value2", map.get("name2"));
    }

    @Test
    public void testExtractContentsTag() {
        String source = "Preamble <tag>This is a string</tag> postamble";
        String name = "tag";
        String output = AcdParser.extractContents(source, name);
        Assert.assertEquals("This is a string", output);
    }

    @Test
    public void testExtractContentsTag2() {
        String source = "Preamble <tag name=\"value\"/> postamble";
        String name = "tag";
        String output = AcdParser.extractContents(source, name);
        Assert.assertEquals("<tag name=\"value\"/>", output);
    }

    @Test
    public void testExtractAllContents() {
        String source = "<tag name1=\"value1\"/><tag name2=\"value2\"/>";
        List<String> list = AcdParser.extractAllContents(source, "tag");
        Assert.assertEquals("<tag name1=\"value1\"/>", list.get(0));
        Assert.assertEquals("<tag name2=\"value2\"/>", list.get(1));
    }

    @Test
    public void testExtractAllContents2() {
        String source = "Preamble <tag name1=\"value1\"/> stuffing <tag name2=\"value2\"/> postamble";
        List<String> list = AcdParser.extractAllContents(source, "tag");
        Assert.assertEquals("TagName", "<tag name1=\"value1\"/>", list.get(0));
        Assert.assertEquals("TagName2", "<tag name2=\"value2\"/>", list.get(1));
    }

    @Test
    public void testExtractAllContents3() {
        String source = "Preamble <tag>contents1</tag> stuffing <tag>contents2</tag> postamble";
        List<String> list = AcdParser.extractAllContents(source, "tag");
        Assert.assertEquals("contents1", list.get(0));
        Assert.assertEquals("contents2", list.get(1));
    }
}
