package me.koogy.acdepub.objects;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.koogy.acdepub.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A parser that does what i want it to do.
 * 
 * @author adean
 */
public class AcdParser {

    private static final String HR_TEXT = "~";

    private static Logger log = LoggerFactory.getLogger(AcdParser.class);

    public static Book parseBook(String filename) {
        byte[] b = new byte[0];
        InputStream in = null;
        try {
            // slurp file into StringBuffer
            File file = new File(filename);
            in = new FileInputStream(file);
            b  = new byte[(int)(file.length())];
            int len = b.length;
            int total = 0;

            while (total < len) {
              int result = in.read(b, total, len - total);
              if (result == -1) {
                break;
              }
              total += result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return parseBook(b);
    }
    public static Book parseBook(byte[] b) {
        log.info("ParseBook");
        Book book = new Book();
        try {
            String xml = new String(b, "UTF-8");
            // remove carriage returns and newlines. dos is 0d0a, linux is 0a
            xml = xml.replaceAll("[\\r\\n]", "");
            
            String infoXml = extractContents(xml, Tag.INFO);
            log.info("Book Info[{}]", infoXml);
            book.setInfo(parseInfo(infoXml));
            book.setOptions(parseOptions(infoXml));
            // UUID, based on unique title
            String title = book.getInfo().getTocTitle();
            UUID uuid = UUID.nameUUIDFromBytes(title.getBytes());
            book.setUuid(uuid.toString());
            
            parsePrefix(book, xml);

            parseParts(book, xml);

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
            info.setTocTitle(extractContents(infoXml, Tag.TOCTITLE));
            info.setTitle(extractContents(infoXml, Tag.TITLE));
            info.setSubtitle(extractContents(infoXml, Tag.SUBTITLE));
            info.setAuthor(extractContents(infoXml, Tag.AUTHOR));
            info.setDate(extractContents(infoXml, Tag.DATE));
        }
        log.info("ParseInfo [{}]", info);
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
            log.info("Option [{}]", str);
            Map<String, String> map = extractAttributes(str);
            String name = map.get("name");
            String value = map.get("value");
            if (name.equalsIgnoreCase(Options.CHAPTER_NUMBERS_CONTINUOUS)) {
                options.setChapterNumbersContinuous(Boolean.parseBoolean(value));
            } else if (name.equalsIgnoreCase(Options.CHAPTER_NUMBER_IN_TOC_PROPERTY)) {
                options.setChapterNumberInToc(Boolean.parseBoolean(value));
            } else if (name.equalsIgnoreCase(Options.CHAPTER_NUMBER_STYLE_PROPERTY)) {
                options.setChapterNumberStyle(value);
            } else if (name.equalsIgnoreCase(Options.CHAPTER_TITLE_ENABLED_PROPERTY)) {
                options.setChapterTitleEnabled(Boolean.parseBoolean(value));
            } else if (name.equalsIgnoreCase(Options.CHAPTER_TITLE_TEXT_PROPERTY)) {
                options.setChapterTitleText(value);
            } else if (name.equalsIgnoreCase(Options.PART_NUMBER_STYLE_PROPERTY)) {
                options.setPartNumberStyle(value);
            } else if (name.equalsIgnoreCase(Options.PART_TITLE_ENABLED_PROPERTY)) {
                options.setPartTitleEnabled(Boolean.parseBoolean(value));
            } else if (name.equalsIgnoreCase(Options.PART_TITLE_TEXT_PROPERTY)) {
                options.setPartTitleText(value);
            }
        }
        log.info("Options [{}]", options);
        return options;
    }

    private static void parsePrefix(Book book, String xml) {
        log.info("ParsePrefix");
        List<String> list = extractAllContents(xml, Tag.PREFIX);
        book.setPrefaces(parseChapters(book, list, Chapter.PREFIX));
    }

    private static void parseParts(Book book, String xml) {
        log.info("ParseParts");
        List<String> parts = extractAllContents(xml, Tag.PART);
        if (parts == null || parts.isEmpty()) {
            parts = new ArrayList<>(1);
            // single part containing all chapters
            int index = xml.indexOf("<chapter");
            parts.add(xml.substring(index));
        }
        int partCount = 1;
        int chapterCount = 1;
        book.setParts(new ArrayList<Part>());
        for (String partXml : parts) {
            Part part = new Part();
            part.setId(String.format(Main.PART_ID_FORMAT, partCount));
            // part info = book info + part info
            String infoXml = extractContents(partXml, Tag.INFO);
            log.info("Parts Info [{}]", infoXml);
            Info info = null;
            Options options = null;
            if (infoXml == null || infoXml.isEmpty()) {
                info = new Info();
                options = new Options();
            } else {
                info = parseInfo(infoXml);
                options = parseOptions(infoXml);
            }
            part.setInfo(info);
            options.merge(book.getOptions());
            part.setOptions(options);
            // part numbering may not appear due to part details for Various Artists books
            part.setNumbering(getNumbering(Part.PART, options.getPartTitleText(), options.getPartNumberStyle(), partCount));
            List<String> chapters = extractAllContents(partXml, Tag.CHAPTER);
            part.setChapters(new ArrayList<Chapter>());
            if (!options.getChapterNumbersContinuous()) {
                // reset chapter count per part
                chapterCount = 1;
            }
            for (String chapterXml : chapters) {
                String id;
                if (parts.size() != 1) {    // can't use hasParts because we haven't added parts to book yet
                    // proper parts
                    id = String.format(Main.PART_CHAPTER_ID_FORMAT, partCount, chapterCount);
                } else {
                    // no parts
                    id = String.format(Main.CHAPTER_ID_FORMAT, chapterCount);
                }
                Chapter chapter = (Chapter)parseChapter(book, chapterXml, Chapter.PART_CHAPTER, id);
                chapter.setId(id);
                chapter.setNumbering(getNumbering(Chapter.PART_CHAPTER, options.getChapterTitleText(), options.getChapterNumberStyle(), chapterCount));
                part.getChapters().add(chapter);
                chapterCount++;
            }
            book.getParts().add(part);
            partCount++;
        }
    }

    private static List<Chapter> parseChapters(Book book, List<String> chapterStrings, int type) {
        if (chapterStrings == null || chapterStrings.isEmpty()) {
            return null;
        }
        Options options = book.getOptions();
        List<Chapter> list = new ArrayList<Chapter>();
        int count = 1;
        for(String str : chapterStrings) {
            String id = getId(type, count);
            Chapter chapter = parseChapter(book, str, type, id);
            chapter.setId(id);
            chapter.setNumbering(getNumbering(type, options.getChapterTitleText(), options.getChapterNumberStyle(), count));
            list.add(chapter);
            count++;
        }
        return list;
    }

    private static void parseAppendix(Book book, String xml) {
        log.info("ParseAppendix");
        List<String> list = extractAllContents(xml, Tag.APPENDIX);
        book.setAppendices(parseChapters(book, list, Chapter.APPENDIX));
    }

    private static void parseFootnotes(Book book, String xml) {
        log.info("ParseFootnotes");
        List<String> list = extractAllContents(xml, Tag.FOOTNOTE);
        int count = 0;
        for (String item : list) {
            log.info("Footnote [{}] - [{}]", count, item);
            count++;
        }
        book.setFootnotes(parseChapters(book, list, Chapter.FOOTNOTE));
    }

    // chapter has a title tag as first element, rest is verbatim body
    // protected to allow testing, otherwise private
    public static Chapter parseChapter(Book book, String xml, int type, String id) {
        log.info("ParseChapter [{}]", xml);
        Chapter chapter = new Chapter();
        chapter.setTitle(extractContents(xml, Tag.TITLE));
        // remove title
        xml = xml.replaceFirst("<" + Tag.TITLE + ">.*</" + Tag.TITLE + ">", "");

        xml = replaceSmallCaps(xml);

        xml = xml.replaceAll("</p>", "</p>\n");
        xml = xml.replaceAll("<hr/>", "<div class=\"hr\">" + HR_TEXT + "</div>\n");
        // break is a blank line
        xml = xml.replaceAll("<break/>", "<br/><br/>\n");
        // remove single line comments (they clash with mdash below)
        xml = xml.replaceAll("<!--.*-->", "");
        xml = xml.replaceAll("--", "—");    // proper mdash
        // replace some xml with some xml
        xml = xml.replaceAll("<p0>", "<p class=\"p0\">");
        xml = xml.replaceAll("</p0>", "</p>");
        xml = xml.replaceAll("<p1>", "<p class=\"p1\">");
        xml = xml.replaceAll("</p1>", "</p>");
        xml = xml.replaceAll("<p2>", "<p class=\"p2\">");
        xml = xml.replaceAll("</p2>", "</p>");
        xml = xml.replaceAll("<p3>", "<p class=\"p3\">");
        xml = xml.replaceAll("</p3>", "</p>");
        xml = xml.replaceAll("<poem>", "<div class=\"poem\">\n");
        xml = xml.replaceAll("</poem>", "</div>\n");
        xml = xml.replaceAll("<poem1>", "<div class=\"poem1\">");
        xml = xml.replaceAll("</poem1>", "</div>\n");
        xml = xml.replaceAll("<poem2>", "<div class=\"poem2\">");
        xml = xml.replaceAll("</poem2>", "</div>\n");
        xml = xml.replaceAll("<poem3>", "<div class=\"poem3\">");
        xml = xml.replaceAll("</poem3>", "</div>\n");
        xml = xml.replaceAll("<poem4>", "<div class=\"poem4\">");
        xml = xml.replaceAll("</poem4>", "</div>\n");
        xml = xml.replaceAll("<poem5>", "<div class=\"poem5\">");
        xml = xml.replaceAll("</poem5>", "</div>\n");
        xml = xml.replaceAll("<letter>", "<div class=\"letter\">");
        xml = xml.replaceAll("</letter>", "</div>\n");
        xml = xml.replaceAll("<centre>", "<div class=\"centre\">");
        xml = xml.replaceAll("</centre>", "</div>\n");
        xml = xml.replaceAll("<center>", "<div class=\"center\">");
        xml = xml.replaceAll("</center>", "</div>\n");
        xml = xml.replaceAll("<right>", "<div class=\"right\">");
        xml = xml.replaceAll("</right>", "</div>\n");
        // inter-chapter numbered sections
        xml = xml.replaceAll("<section>", "\n<h3>");
        xml = xml.replaceAll("</section>", "</h3>\n");
        // inline styles
        xml = xml.replaceAll("<smallcaps>", "<span class=\"smallcaps\">");
        xml = xml.replaceAll("</smallcaps>", "</span>");
        xml = xml.replaceAll("<sc>", "<span class=\"smallcaps\">");
        xml = xml.replaceAll("</sc>", "</span>");
        xml = xml.replaceAll("<fixed>", "<pre>");
        xml = xml.replaceAll("</fixed>", "</pre>");

        // replace all the "note" tags with a link to matching footnote
        // this perhaps should be elsewhere
        while (xml.indexOf("<note/>") != -1) {
            book.footnoteCounter++;
            log.info("Footnote [{}]", book.footnoteCounter);
            xml = xml.replaceFirst("<note/>",
                    " <a id=\"" + Book.FOOTNOTE_LINK_ANCHOR_PREFIX + book.footnoteCounter + "\">"
                    + "<a href=\"" + Book.FOOTNOTES_FILENAME
                    + "#" + Book.FOOTNOTE_ANCHOR_PREFIX + book.footnoteCounter + "\">"
                    + "[note " + book.footnoteCounter + "]"
                    + "</a>"    // end of link
                    + "</a>");  // end of anchor
            // need to store the filename for this link
            // don't know it until after numbering
            book.footnoteLinks.add(id);
        }
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
            case Part.PART:
                str = Main.numbering(titleText, numberStyle, count);
                break;
        }
        return str;
    }
    
    // <tag name="value" name2="value2"/> -> hashmap
    // use regexp? [a-zA-Z0-9]*="[^"]*"
    public static Map<String, String> extractAttributes(String source) {
        log.info("ExtractAttributes [{}]", source);
        Map<String, String> map = null;
        // strip first word - tag name
        source = source.replaceFirst("<[a-zA-Z]* ", "");
        source = source.replace("/>", " ");
        // string is now [name="value1" name="value2" ]
        // so we can split on [" ]
        String[] strings = source.split("\" ");
        if (strings.length != 0) {
            map = new HashMap<String, String>();
            for (String s : strings) {
                // name="value"
                String name = s.substring(0, s.indexOf("="));
                String value = s.substring(s.indexOf("\"") + 1);
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
        
        // check for attributes first
        Pattern pattern = Pattern.compile(".* " + name + "=\"([^\"]*)\".*");
        Matcher matcher = pattern.matcher(source);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        
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
            String m = ".*<" + name + "[^>]*/>.*";  // dos line endings break this
            if (source.matches(m)) {
                end = source.indexOf("/>");
                str = source.substring(start, end + 2);
                return str;
            } else {
                return null;
            }
        }
        str = source.substring(close + 1, end);
        // fix mdashes in titles etc
        str = str.replaceAll("--", "—");    // proper mdash
        return str;
    }

    // get the contents of the ALL instances of the named tag within string
    // NB <tag will match <tags
    public static List<String> extractAllContents(String source, String name) {
        //log.debug("ExtractAllContents: Source [{}] Name [{}]", source, name);
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

    // replace all the smallcaps
    public static String replaceSmallCaps(String xml) {
        Pattern p = Pattern.compile("(<smallcaps>[^<]*</smallcaps>)");
        Matcher m = p.matcher(xml);
        StringBuilder output = new StringBuilder();
        int start = 0;
        while (m.find()) {
            String matched = m.group();
            log.info("Match [{}] - {} = {}", matched, m.start(), m.end());
            // copy start string to output
            output.append(xml.substring(start, m.start()));
            output.append(toSmallCaps(matched));
            log.info("Output [{}]", output);
            // next start is the current end
            start = m.end();
        }
        if (output.length() != 0) {
            // copy last bit
            output.append(xml.substring(start));
            return output.toString();
        } else {
            // nothing happened - just return the input
            return xml;
        }
    }

    /* 
    ** Kobo rendering engine doesn't really do smallcaps
    ** so replace with spans of proper class
    */
    public static String toSmallCaps(String input) {
        StringBuffer output = new StringBuffer();
        // remove start and end tags
        input = input.replaceFirst("<smallcaps>", "");
        input = input.replaceFirst("</smallcaps>", "");

        boolean upper = true;
        for (int i = 0 ; i < input.length() ; i++) {
            char c = input.charAt(i);
            if (Character.isUpperCase(c)) {
                if (!upper) {
                   // was lower, so end span
                   output.append("</span>");
                }
                upper = true;
            }
            if (Character.isLowerCase(c)) {
                c = Character.toUpperCase(c);
                if (upper) {
                    // was upper so start span
                    output.append("<span class=\"sc\">");
                }
                upper = false;
            }
            output.append(c);
        }
        // done
        if (!upper) {
            // close the current span
            output.append("</span>");
        }
        return output.toString();
    }
}
