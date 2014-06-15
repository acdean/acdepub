package me.koogy.acdepub.objects;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.koogy.acdepub.Main;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * A parser that does what i want it to do.
 * 
 * @author adean
 */
public class AcdParser {

    private static final String HR_TEXT = "~";

    private static Logger log = LogManager.getLogger(AcdParser.class);

    public static Book parseBook(String filename) {
        log.info("ParseBook");
        Book book = new Book();
        try {
            // slurp file into StringBuffer
            File file = new File(filename);
            InputStream in = new FileInputStream(file);
            byte[] b  = new byte[(int)(file.length())];
            int len = b.length;
            int total = 0;

            while (total < len) {
              int result = in.read(b, total, len - total);
              if (result == -1) {
                break;
              }
              total += result;
            }
            
            String xml = new String(b, "UTF-8");
            // remove newlines
            xml = xml.replaceAll("\\n", "");
            
            String infoXml = extractContents(xml, Tag.INFO);
            log.info("[" + infoXml + "]");
            book.setInfo(parseInfo(infoXml));
            book.setOptions(parseOptions(infoXml));
            
            parsePrefix(book, xml);

            parseParts(book, xml);

            if (book.parts == null) {
                parseChapters(book, xml);
            }

            parseAppendix(book, xml);
            
            // parse footnotes if we have found references
            if (book.footnoteCounter != 0) {
                parseFootnotes(book, xml);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return book;
    }

    // a book or a part has info
    private static Info parseInfo(String infoXml) {
        if (infoXml == null) {
            return null;
        }
        log.info("ParseInfo");
        Info info = null;
        if (infoXml != null) {
            info = new Info();
            info.setTitle(extractContents(infoXml, Tag.TITLE));
            info.setSubtitle(extractContents(infoXml, Tag.SUBTITLE));
            info.setAuthor(extractContents(infoXml, Tag.AUTHOR));
            info.setDate(extractContents(infoXml, Tag.DATE));
        }
        return info;
    }

    // parse options from info block
    private static Options parseOptions(String infoXml) {
        log.info("ParseOptions");
        if (infoXml == null) {
            return null;
        }
        Options options = new Options();
        List<String> optionStrings = extractAllContents(infoXml, Tag.OPTION);
        for (String str : optionStrings) {
            Map<String, String> map = extractAttributes(str);
            String name = map.get("name");
            String value = map.get("value");
            if (name.equalsIgnoreCase(Options.CHAPTER_NUMBER_STYLE_PROPERTY)) {
                options.setChapterNumberStyle(value);
            }
            if (name.equalsIgnoreCase(Options.CHAPTER_TITLES_PROPERTY)) {
                options.setChapterTitles(Boolean.parseBoolean(value));
            }
            if (name.equalsIgnoreCase(Options.CHAPTER_TITLE_TEXT_PROPERTY)) {
                options.setChapterTitleText(value);
            }
            if (name.equalsIgnoreCase(Options.PART_NUMBER_STYLE_PROPERTY)) {
                options.setPartNumberStyle(value);
            }
            if (name.equalsIgnoreCase(Options.PART_TITLE_TEXT_PROPERTY)) {
                options.setPartTitleText(value);
            }
        }
        return options;
    }

    private static void parsePrefix(Book book, String xml) {
        log.info("ParsePrefix");
        List<String> list = extractAllContents(xml, Tag.PREFIX);
        book.setPrefaces(parseGenericChapters(book, list, GenericChapter.PREFIX));
    }

    private static void parseParts(Book book, String xml) {
        log.info("ParseParts");
        List<String> parts = extractAllContents(xml, Tag.PART);
        if (parts != null && !parts.isEmpty()) {
            int partCount = 1;
            book.setParts(new ArrayList<Part>());
            for (String partXml : parts) {
                int chapterCount = 1;
                Part part = new Part();
                part.setId(String.format(Main.PART_ID_FORMAT, partCount));
                // part info = book info + part info
                String infoXml = extractContents(partXml, Tag.INFO);
                log.info("[" + infoXml + "]");
                Info info = parseInfo(infoXml);
                info.merge(book.getInfo());
                part.setInfo(info);
                Options options = parseOptions(infoXml);
                options.merge(book.getOptions());
                part.setOptions(options);                
                List<String> chapters = extractAllContents(partXml, Tag.CHAPTER);
                part.setChapters(new ArrayList<Chapter>());
                for (String chapterXml : chapters) {
                    String id = String.format(Main.PART_CHAPTER_ID_FORMAT, partCount, chapterCount);
                    Chapter chapter = (Chapter)parseChapter(book, chapterXml, GenericChapter.PART_CHAPTER, id);
                    chapter.setId(id);
                    chapter.setNumbering(getNumbering(GenericChapter.PART_CHAPTER, options.getChapterTitleText(), options.getChapterNumberStyle(), chapterCount));
                    part.getChapters().add(chapter);
                    chapterCount++;
                }
                book.getParts().add(part);
                partCount++;
            }
        }
    }

    private static List<GenericChapter> parseGenericChapters(Book book, List<String> chapterStrings, int type) {
        if (chapterStrings == null || chapterStrings.isEmpty()) {
            return null;
        }
        Options options = book.getOptions();
        List<GenericChapter> list = new ArrayList<GenericChapter>();
        int count = 1;
        for(String str : chapterStrings) {
            String id = getId(type, count);
            GenericChapter chapter = parseChapter(book, str, type, id);
            chapter.setId(id);
            chapter.setNumbering(getNumbering(type, options.getChapterTitleText(), options.getChapterNumberStyle(), count));
            list.add(chapter);
            count++;
        }
        return list;
    }

    private static void parseChapters(Book book, String xml) {
        log.info("ParseChapters");
        List<String> list = extractAllContents(xml, Tag.CHAPTER);
        book.setChapters(parseGenericChapters(book, list, GenericChapter.CHAPTER));
    }

    private static void parseAppendix(Book book, String xml) {
        log.info("ParseAppendix");
        List<String> list = extractAllContents(xml, Tag.APPENDIX);
        book.setAppendices(parseGenericChapters(book, list, GenericChapter.APPENDIX));
    }

    private static void parseFootnotes(Book book, String xml) {
        log.info("ParseFootnotes");
        List<String> list = extractAllContents(xml, Tag.FOOTNOTE);
        book.setFootnotes(parseGenericChapters(book, list, GenericChapter.FOOTNOTE));
    }

    // chapter has a title tag as first element, rest is verbatim body
    private static Chapter parseChapter(Book book, String xml, int type, String id) {
        log.info("ParseChapter");
        Chapter chapter = new Chapter();
        chapter.setTitle(extractContents(xml, Tag.TITLE));
        // remove title
        xml = xml.replaceFirst("<" + Tag.TITLE + ">.*</" + Tag.TITLE + ">", "");
        xml = xml.replaceAll("</p>", "</p>\n");
        xml = xml.replaceAll("<hr/>", "<div class=\"hr\">" + HR_TEXT + "</div>");
        xml = xml.replaceAll("--", "&mdash;");
        // replace all the "note" tags with a link to matching footnote
        while (xml.indexOf("<note/>") != -1) {
            book.footnoteCounter++;
            log.info("Footnote[" + book.footnoteCounter + "]");
            xml = xml.replaceFirst("<note/>",
                    "<a name=\"" + Book.FOOTNOTE_LINK_ANCHOR_PREFIX + book.footnoteCounter + "\"/>"
                    + "<a href=\"" + Book.FOOTNOTES_FILENAME
                    + "#" + Book.FOOTNOTE_ANCHOR_PREFIX + book.footnoteCounter + "\">"
                    + "[" + book.footnoteCounter + "]"
                    + "</a>");
            // need to store the filename for this link
            // don't know it until after numbering
            book.footnoteLinks.add(id);
        }
//        xml = xml.replaceAll("\\ue8", "&agrave;");
//        xml = xml.replaceAll("\\ue9", "&eacute;");
//        xml = xml.replaceAll("\\uef", "&aring;");
//        xml = xml.replaceAll("\\uf8", "&ostroke;");
        // everything else is content
        chapter.setContent(xml);
        chapter.setType(type);
        return chapter;
    }

    private static String getId(int type, int count) {
        String str = null;
        switch(type) {
            case Chapter.PREFIX:
                str = Main.PREFACE_ID_FORMAT;
                break;
            case Chapter.PART_CHAPTER:
                str = Main.PART_CHAPTER_ID_FORMAT;
                break;
            case Chapter.CHAPTER:
                str = Main.CHAPTER_ID_FORMAT;
                break;
            case Chapter.APPENDIX:
                str = Main.APPENDIX_ID_FORMAT;
                break;
            case Chapter.FOOTNOTE:
                str = Book.FOOTNOTES_ID;
                break;
        }
        return String.format(str, count);
    }
    
    private static String getNumbering(int type, String titleText, String numberStyle, int count) {
        String str = null;
        switch(type) {
            case Chapter.PREFIX:
                // do nothing - title only
                break;
            case Chapter.PART_CHAPTER:
                str = Main.numbering(titleText, numberStyle, count);                
                break;
            case Chapter.CHAPTER:
                str = Main.numbering(titleText, numberStyle, count);
                break;
            case Chapter.APPENDIX:
                // do nothing - title only
                break;
            case Chapter.FOOTNOTE:
                // do nothing - title only
                break;
        }
        return str;
    }
    
    // <tag name="value" name2="value2"/> -> hashmap
    public static Map<String, String> extractAttributes(String source) {
        System.out.println("ExtractAttributes: [" + source + "]");
        Map<String, String> map = null;
        // strip first word - tag name
        source = source.replaceFirst("<[a-zA-Z]* ", "");
        source = source.replace("/>", "");
        String[] strings = source.split(" ");
        if (strings.length != 0) {
            map = new HashMap<String, String>();
            for (String s : strings) {
                // name="value"
                String name = s.substring(0, s.indexOf("="));
                String value = s.substring(s.indexOf("\"") + 1, s.lastIndexOf("\""));
                map.put(name, value);
            }
        }
        return map;
    }

    // get the contents of the (first) named tag within string
    // returns everything within <tag></tag>
    // if <tag name="value"/> then returns the whole thing
    public static String extractContents(String source, String name) {
        return extractContents(source, name, 0);
    }
    private static String extractContents(String source, String name, int fromIndex) {

        source = source.substring(fromIndex);

        String str = null;
        // can't search for <name> because of attributes
        int start = source.indexOf("<" + name);
        if (start == -1) {
            return null;
        }
        // end of start tag
        int close = source.indexOf(">", start);

        // search for </name>
        int end = source.indexOf("</" + name + ">");
        if (end == -1) {
            // is this a <tag />?
            if (source.matches(".*<" + name + "[^>]*/>.*")) {
                end = source.indexOf("/>");
                str = source.substring(start, end + 2);
                return str;
            } else {
                return null;
            }
        }
        str = source.substring(close + 1, end);
        return str;
    }

    // get the contents of the ALL instances of the named tag within string
    // NB <tag will match <tags
    public static List<String> extractAllContents(String source, String name) {
        // this strips off the start
        String[] strings = source.split("<" + name);
        if (strings == null || strings.length == 0) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        // NB ignore the first
        for (int i = 1 ; i < strings.length ; i++) {
            // add the start back
            String str = "<" + name + strings[i];
            String contents = extractContents(str, name);
            list.add(contents);
        }
        return list;
    }
}
