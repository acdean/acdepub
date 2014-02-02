package me.koogy.acdepub.objects;

import java.util.ArrayList;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Dom parsing code. Not needed for jaxb (if that worked)
 * 
 * @author adean
 */
public class Parser {
    
    private static Logger log = LogManager.getLogger(Parser.class);
    
    public static void parseBook(Book book, Document doc) {
        log.info("parseBook");
        parseInfo(book, doc);
        parsePrefix(book, doc);
        parseParts(book, doc);
        if (book.parts == null) {
            // there are no parts, chapters are standalone
            parseChapters(book, doc);
        }
        parseAppendix(book, doc);
    }
    
    public static void parseInfo(Book book, Document doc) {
        log.info("parseInfo");
        
        NodeList nodeList = doc.getElementsByTagName(Tag.INFO).item(0).getChildNodes();
        for (int i = 0 ; i < nodeList.getLength() ; i++) {
            Node node = nodeList.item(i);
            String name = node.getNodeName();
            String value = node.getTextContent();
            if (name.equalsIgnoreCase(Tag.TITLE)) {
                book.setTitle(value);
            }
            if (name.equalsIgnoreCase(Tag.AUTHOR)) {
                book.setAuthor(value);
            }
            if (name.equalsIgnoreCase(Tag.DATE)) {
                book.setDate(value);
            }
            if (name.equalsIgnoreCase(Tag.OPTION)) {
                parseOption(book, node);
            }
        }
    }

    // options determine rendering style
    // they also have attributes - name, value
    private static void parseOption(Book book, Node node) {
        log.info("parseOption");
        NamedNodeMap attr = node.getAttributes();
        Node nameNode = attr.getNamedItem("name");
        Node valueNode = attr.getNamedItem("value");
        String name = nameNode.getTextContent();
        String value = valueNode.getTextContent();
        if (name.equalsIgnoreCase(Options.CHAPTER_TITLES_PROPERTY)) {
            book.getOptions().setChapterTitles(Boolean.parseBoolean(value));
        }
        if (name.equalsIgnoreCase(Options.CHAPTER_TITLE_TEXT_PROPERTY)) {
            book.getOptions().setChapterTitleText(value);
        }
        if (name.equalsIgnoreCase(Options.CHAPTER_NUMBER_STYLE_PROPERTY)) {
            book.getOptions().setChapterNumberStyle(value);
        }
        if (name.equalsIgnoreCase(Options.PART_TITLE_TEXT_PROPERTY)) {
            book.getOptions().setPartTitleText(value);
        }
        if (name.equalsIgnoreCase(Options.PART_NUMBER_STYLE_PROPERTY)) {
            book.getOptions().setPartNumberStyle(value);
        }
    }
    
    // these are chapters
    public static void parsePrefix(Book book, Document doc) {
        log.info("parsePrefix");
        
        NodeList nodeList = doc.getElementsByTagName(Tag.PREFIX);
        if (nodeList.getLength() != 0) {
            book.setPrefaces(new ArrayList<GenericChapter>());
            for (int i = 0 ; i < nodeList.getLength() ; i++) {
                Node node = nodeList.item(i);
                Chapter chapter = parseChapter(node);
                chapter.setType(GenericChapter.PREFIX);
                book.getPrefaces().add(chapter);
            }
        }
    }

    // these are chapters (not in parts)
    // TODO how to distinguish between the two?
    public static void parseChapters(Book book, Document doc) {
        log.info("parseChapters");
        
        NodeList nodeList = doc.getElementsByTagName(Tag.CHAPTER);
        if (nodeList.getLength() != 0) {
            book.setChapters(new ArrayList<GenericChapter>());
            for (int i = 0 ; i < nodeList.getLength() ; i++) {
                Node node = nodeList.item(i);
                Chapter chapter = parseChapter(node);
                chapter.setType(GenericChapter.CHAPTER);
                book.getChapters().add(chapter);
            }
        }
    }

    // Parse parts, each of which can contain title and chapters
    public static void parseParts(Book book, Document doc) {
        log.info("parseParts");
        
        NodeList partNodeList = doc.getElementsByTagName(Tag.PART);
        if (partNodeList.getLength() != 0) {
            book.setParts(new ArrayList<Part>());
            for (int i = 0 ; i < partNodeList.getLength() ; i++) {
                Node partNode = partNodeList.item(i);
                Part part = new Part();
                part.setChapters(new ArrayList<GenericChapter>());
                NodeList childList = partNode.getChildNodes();
                for (int j = 0 ; j < childList.getLength() ; j++) {
                    Node childNode = childList.item(j);
                    String name = childNode.getNodeName();
                    if (name.equalsIgnoreCase(Tag.TITLE)) {
                        part.setTitle(childNode.getTextContent());
                    }
                    if (name.equalsIgnoreCase(Tag.CHAPTER)) {
                        Chapter chapter = parseChapter(childNode);
                        chapter.setType(GenericChapter.PART_CHAPTER);
                        part.getChapters().add(chapter);
                    }
                }
                book.getParts().add(part);
            }
        }
    }

    // these are chapters
    public static void parseAppendix(Book book, Document doc) {
        log.info("parseAppendix");
        
        NodeList nodeList = doc.getElementsByTagName(Tag.APPENDIX);
        if (nodeList.getLength() != 0) {
            book.setAppendices(new ArrayList<GenericChapter>());
            for (int i = 0 ; i < nodeList.getLength() ; i++) {
                Node node = nodeList.item(i);
                Chapter chapter = parseChapter(node);
                chapter.setType(GenericChapter.APPENDIX);
                book.getAppendices().add(chapter);
            }
        }
    }

    private static Chapter parseChapter(Node chapterNode) {
        log.info("parseChapter");
        Chapter chapter = new Chapter();
        NodeList nodeList = chapterNode.getChildNodes();
        if (nodeList.getLength() != 0) {
            chapter.setParas(new ArrayList<String>());
            for (int i = 0 ; i < nodeList.getLength() ; i++) {
                Node node = nodeList.item(i);
                String name = node.getNodeName();
                String value = node.getTextContent();
                if (name.equalsIgnoreCase(Tag.PARA)) {
                    chapter.getParas().add(value);
                }
                if (name.equals(Tag.TITLE)) {
                    chapter.setTitle(value);
                }
            }
        }
        return chapter;
    }
}
