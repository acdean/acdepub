package me.koogy.acdepub.objects;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * A Noddy parser that does what i want it to do.
 * 
 * @author adean
 */
public class AcdParser {

    private static Logger log = LogManager.getLogger(Parser.class);

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
            
            String str = new String(b, "UTF-8");
            // remove newlines
            str = str.replaceAll("\\n", "");
            
            book.setInfo(parseInfo(str));
            parsePrefix(book, str);
            parseParts(book, str);
            if (book.parts == null) {
                parseChapters(book, str);
            }
            parseAppendix(book, str);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return book;
    }

    // a book or a part has info
    private static Info parseInfo(String xml) {
        log.info("ParsePrefix");
        String infoXml = extractContents(xml, Tag.INFO);
        System.out.println("[" + infoXml + "]");
        Info info = new Info();
        info.setTitle(extractContents(infoXml, Tag.TITLE));
        info.setAuthor(extractContents(infoXml, Tag.AUTHOR));
        info.setDate(extractContents(infoXml, Tag.DATE));
        Options options = parseOptions(infoXml);
        info.setOptions(options);
        return info;
    }

    private static Options parseOptions(String infoXml) {
        log.info("ParseOptions");
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
                options.setChapterTitleText(value);
            }
            if (name.equalsIgnoreCase(Options.CHAPTER_TITLE_TEXT_PROPERTY)) {
                options.setChapterTitles(Boolean.parseBoolean(value));
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
        book.setPrefaces(parseGenericChapters(list, GenericChapter.PREFIX));
    }

    private static void parseParts(Book book, String xml) {
        log.info("ParseParts");
        List<String> parts = extractAllContents(xml, Tag.PART);
        if (parts != null && !parts.isEmpty()) {
            book.setParts(new ArrayList<Part>());
            for (String partXml : parts) {
                Part part = new Part();
                Info info = parseInfo(partXml);
                List<String> chapters = extractAllContents(partXml, Tag.CHAPTER);
                part.setChapters(new ArrayList<Chapter>());
                for (String chapterXml : chapters) {
                    Chapter chapter = (Chapter)parseChapter(chapterXml, GenericChapter.PART_CHAPTER);
                    part.getChapters().add(chapter);
                }
                book.getParts().add(part);
            }
        }
    }

    private static List<GenericChapter> parseGenericChapters(List<String> chapterStrings, int type) {
        if (chapterStrings == null || chapterStrings.isEmpty()) {
            return null;
        }
        List<GenericChapter> list = new ArrayList<GenericChapter>();
        for(String str : chapterStrings) {
            GenericChapter chapter = parseChapter(str, type);
            list.add(chapter);
        }
        return list;
    }

    private static void parseChapters(Book book, String xml) {
        log.info("ParseChapters");
        List<String> list = extractAllContents(xml, Tag.CHAPTER);
        book.setChapters(parseGenericChapters(list, GenericChapter.CHAPTER));
    }

    private static void parseAppendix(Book book, String xml) {
        log.info("ParseAppendix");
        List<String> list = extractAllContents(xml, Tag.APPENDIX);
        book.setAppendices(parseGenericChapters(list, GenericChapter.APPENDIX));
    }

    // chapter has a title tag as first element, rest is verbatim body
    private static GenericChapter parseChapter(String xml, int type) {
        log.info("ParseChapter");
        GenericChapter chapter = new GenericChapter();
        chapter.setTitle(extractContents(xml, Tag.TITLE));
        // remove title
        xml = xml.replaceFirst("<" + Tag.TITLE + ">.*</" + Tag.TITLE + ">", "");
        xml = xml.replaceAll("</p>", "</p>\n");
        // everything else is content
        chapter.setContent(xml);
        chapter.setType(type);
        return chapter;
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
