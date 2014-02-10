/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.koogy.acdepub;

import java.util.List;
import java.util.Map;
import junit.framework.Assert;
import junit.framework.TestCase;
import me.koogy.acdepub.objects.AcdParser;

/**
 * @author adean
 */
public class AcdParserTest extends TestCase {
    
    public void testAttributes() {
        String input = "<tag name1=\"value1\" name2=\"value2\"/>";
        
        Map map = AcdParser.extractAttributes(input);
        Assert.assertEquals("value1", map.get("name1"));
        Assert.assertEquals("value2", map.get("name2"));
    }
    
    public void testExtractContentsTag() {
        String source = "Preamble <tag>This is a string</tag> postamble";
        String name = "tag";
        String output = AcdParser.extractContents(source, name);
        Assert.assertEquals("This is a string", output);
    }

    public void testExtractContentsTag2() {
        String source = "Preamble <tag name=\"value\"/> postamble";
        String name = "tag";
        String output = AcdParser.extractContents(source, name);
        Assert.assertEquals("<tag name=\"value\"/>", output);
    }
    
    public void testExtractAllContents() {
        String source = "<tag name1=\"value1\"/><tag name2=\"value2\"/>";
        List<String> list = AcdParser.extractAllContents(source, "tag");
        Assert.assertEquals("<tag name1=\"value1\"/>", list.get(0));
        Assert.assertEquals("<tag name2=\"value2\"/>", list.get(1));
    }

    public void testExtractAllContents2() {
        String source = "Preamble <tag name1=\"value1\"/> stuffing <tag name2=\"value2\"/> postamble";
        List<String> list = AcdParser.extractAllContents(source, "tag");
        Assert.assertEquals("<tag name1=\"value1\"/>", list.get(0));
        Assert.assertEquals("<tag name2=\"value2\"/>", list.get(1));
    }

    public void testExtractAllContents3() {
        String source = "Preamble <tag>contents1</tag> stuffing <tag>contents2</tag> postamble";
        List<String> list = AcdParser.extractAllContents(source, "tag");
        Assert.assertEquals("contents1", list.get(0));
        Assert.assertEquals("contents2", list.get(1));
    }
}
