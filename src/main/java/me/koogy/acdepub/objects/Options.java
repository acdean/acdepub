package me.koogy.acdepub.objects;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Options
 * @author adean
 */
public class Options {

    private static Logger log = LogManager.getLogger(Options.class);

    public static String PART_TITLE_TEXT_PROPERTY       = "part.title_text";
    public static String PART_NUMBER_STYLE_PROPERTY     = "part.number_style";
    public static String CHAPTER_TITLES_PROPERTY        = "chapter.titles";
    public static String CHAPTER_TITLE_TEXT_PROPERTY    = "chapter.title_text";
    public static String CHAPTER_NUMBER_STYLE_PROPERTY  = "chapter.number_style";

    public static String CHAPTER_TITLE_TEXT_NONE        = "none";

    String partTitleText = null;
    String partNumberStyle = null;
    String chapterTitleText = null;
    String chapterNumberStyle = null;
    boolean chapterTitles = true;
    
    public Options() {
        partTitleText = System.getProperty(PART_TITLE_TEXT_PROPERTY, "Part");
        partNumberStyle = System.getProperty(PART_NUMBER_STYLE_PROPERTY, "1");
        chapterTitleText = System.getProperty(CHAPTER_TITLE_TEXT_PROPERTY, "Chapter");
        chapterNumberStyle = System.getProperty(CHAPTER_NUMBER_STYLE_PROPERTY, "I");
        // whether any chapter number is printed - defaults to true
        chapterTitles = System.getProperty(CHAPTER_TITLES_PROPERTY, "true").equalsIgnoreCase("true");
    }
    
    public boolean getChapterTitles() {
        return chapterTitles;
    }

    public void setChapterTitles(boolean chapterTitles) {
        this.chapterTitles = chapterTitles;
    }

    public String getChapterTitleText() {
        return chapterTitleText;
    }

    public void setChapterTitleText(String chapterTitleText) {
        this.chapterTitleText = chapterTitleText;
    }

    public String getChapterNumberStyle() {
        return chapterNumberStyle;
    }

    public void setChapterNumberStyle(String chapterNumberStyle) {
        this.chapterNumberStyle = chapterNumberStyle;
    }

    public String getPartTitleText() {
        return partTitleText;
    }

    public void setPartTitleText(String partTitleText) {
        this.partTitleText = partTitleText;
    }

    public String getPartNumberStyle() {
        return partNumberStyle;
    }

    public void setPartNumberStyle(String partNumberStyle) {
        this.partNumberStyle = partNumberStyle;
    }
    
    // options determine rendering style
    // they also have attributes - name, value
    public static void parseOption(Book book, Node node) {
        log.info("parseOption");
        NamedNodeMap attr = node.getAttributes();
        Node nameNode = attr.getNamedItem("name");
        Node valueNode = attr.getNamedItem("value");
        String name = nameNode.getTextContent();
        String value = valueNode.getTextContent();
        if (name.equalsIgnoreCase(CHAPTER_TITLES_PROPERTY)) {
            book.getOptions().setChapterTitles(Boolean.parseBoolean(value));
        }
        if (name.equalsIgnoreCase(CHAPTER_TITLE_TEXT_PROPERTY)) {
            book.getOptions().setChapterTitleText(value);
        }
        if (name.equalsIgnoreCase(CHAPTER_NUMBER_STYLE_PROPERTY)) {
            book.getOptions().setChapterNumberStyle(value);
        }
        if (name.equalsIgnoreCase(PART_TITLE_TEXT_PROPERTY)) {
            book.getOptions().setPartTitleText(value);
        }
        if (name.equalsIgnoreCase(PART_NUMBER_STYLE_PROPERTY)) {
            book.getOptions().setPartNumberStyle(value);
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        str.append("chapter.titles[").append(getChapterTitles()).append("] ");
        str.append("chapter.titleText[").append(getChapterTitleText()).append("] ");
        str.append("chapter.numberStyle[").append(getChapterNumberStyle()).append("] ");
        str.append("part.titleText[").append(getPartTitleText()).append("] ");
        str.append("part.numberStyle[").append(getPartNumberStyle()).append("]}");
        return str.toString();
    }
    
    public Options copy() {
        Options options = new Options();
        options.setChapterNumberStyle(getChapterNumberStyle());
        options.setChapterTitleText(getChapterTitleText());
        options.setChapterTitles(getChapterTitles());
        options.setPartNumberStyle(getPartNumberStyle());
        options.setPartTitleText(getPartTitleText());
        return options;
    }

    public void merge(Options second) {
        if (getChapterNumberStyle() == null) {
            setChapterNumberStyle(second.getChapterNumberStyle());
        }
        if (getChapterTitleText() == null) {
            setChapterTitleText(second.getChapterTitleText());
        }
        // boolean?
//        if (getChapterTitles() == null) {
//            setChapterTitles(second.getChapterTitles());
//        }
        if (getPartNumberStyle() == null) {
            setPartNumberStyle(second.getPartNumberStyle());
        }
        if (getPartTitleText() == null) {
            setPartTitleText(second.getPartTitleText());
        }
    }
}
