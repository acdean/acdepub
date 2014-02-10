package me.koogy.acdepub.objects;

import java.util.ArrayList;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Dom parsing code. Not needed for jaxb (if that worked)
 * 
 * @author adean
 */
@Deprecated
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
                book.getInfo().setTitle(value);
            }
            if (name.equalsIgnoreCase(Tag.AUTHOR)) {
                book.getInfo().setAuthor(value);
            }
            if (name.equalsIgnoreCase(Tag.DATE)) {
                book.getInfo().setDate(value);
            }
            if (name.equalsIgnoreCase(Tag.OPTION)) {
                Options.parseOption(book, node);
            }
        }
    }
    
    // these are chapters
    public static void parsePrefix(Book book, Document doc) {
        log.info("parsePrefix");
        
//        NodeList nodeList = doc.getElementsByTagName(Tag.PREFIX);
//        if (nodeList.getLength() != 0) {
//            book.setPrefaces(new ArrayList<Preface>());
//            for (int i = 0 ; i < nodeList.getLength() ; i++) {
//                Node node = nodeList.item(i);
//                Preface preface = (Preface)parseChapter(node);
//                preface.setType(GenericChapter.PREFIX);
//                book.getPrefaces().add(preface);
//            }
//        }
    }

    // these are chapters (not in parts)
    // TODO how to distinguish between the two?
    public static void parseChapters(Book book, Document doc) {
        log.info("parseChapters");
        
//        NodeList nodeList = doc.getElementsByTagName(Tag.CHAPTER);
//        if (nodeList.getLength() != 0) {
//            book.setChapters(new ArrayList<Chapter>());
//            for (int i = 0 ; i < nodeList.getLength() ; i++) {
//                Node node = nodeList.item(i);
//                Chapter chapter = (Chapter)parseChapter(node);
//                chapter.setType(GenericChapter.CHAPTER);
//                book.getChapters().add(chapter);
//            }
//        }
    }

    // Parse parts, each of which can contain title and chapters
    public static void parseParts(Book book, Document doc) {
        log.info("parseParts");
//        
//        NodeList partNodeList = doc.getElementsByTagName(Tag.PART);
//        if (partNodeList.getLength() != 0) {
//            book.setParts(new ArrayList<Part>());
//            for (int i = 0 ; i < partNodeList.getLength() ; i++) {
//                Node partNode = partNodeList.item(i);
//                Part part = new Part();
//                part.setChapters(new ArrayList<Chapter>());
//                NodeList childList = partNode.getChildNodes();
//                for (int j = 0 ; j < childList.getLength() ; j++) {
//                    Node childNode = childList.item(j);
//                    String name = childNode.getNodeName();
//                    if (name.equalsIgnoreCase(Tag.TITLE)) {
//                        part.setTitle(childNode.getTextContent());
//                    }
//                    if (name.equalsIgnoreCase(Tag.CHAPTER)) {
//                        Chapter chapter = (Chapter)parseChapter(childNode);
//                        chapter.setType(GenericChapter.PART_CHAPTER);
//                        part.getChapters().add(chapter);
//                    }
//                }
//                book.getParts().add(part);
//            }
//        }
    }

    // these are chapters
    public static void parseAppendix(Book book, Document doc) {
        log.info("parseAppendix");
        
//        NodeList nodeList = doc.getElementsByTagName(Tag.APPENDIX);
//        if (nodeList.getLength() != 0) {
//            book.setAppendices(new ArrayList<Appendix>());
//            for (int i = 0 ; i < nodeList.getLength() ; i++) {
//                Node node = nodeList.item(i);
//                Appendix appendix = (Appendix)parseChapter(node);
//                appendix.setType(GenericChapter.APPENDIX);
//                book.getAppendices().add(appendix);
//            }
//        }
    }

    private static GenericChapter parseChapter(Node chapterNode) {
        log.info("parseChapter");
        Chapter chapter = new Chapter();
        NodeList nodeList = chapterNode.getChildNodes();
        if (nodeList.getLength() != 0) {
            StringBuilder s = new StringBuilder();
//            chapter.setParas(new ArrayList<String>());
            for (int i = 0 ; i < nodeList.getLength() ; i++) {
                Node node = nodeList.item(i);
                String name = node.getNodeName();
                String value = node.getTextContent();
                if (name.equalsIgnoreCase(Tag.PARA)) {
                    s.append("<p>").append(value).append("</p>\n");
                    //chapter.getParas().add(value);
                }
                if (name.equals(Tag.TITLE)) {
                    chapter.setTitle(value);
                }
                if (name.equals(Tag.RULER)) {
                    s.append("<hr/>\n");
                }
            }
            chapter.setContent(s.toString());
        }
        return chapter;
    }
}
