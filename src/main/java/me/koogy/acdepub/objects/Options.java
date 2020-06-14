package me.koogy.acdepub.objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Options
 * Defaults are returned by getters if value is null - allows for merging.
 * 
 * @author adean
 */
public class Options {

    private static Logger log = LoggerFactory.getLogger(Options.class);

    public static String PART_TITLE_ENABLED_PROPERTY    = "part.titles";
    public static String PART_TITLE_TEXT_PROPERTY       = "part.title_text";
    public static String PART_NUMBER_STYLE_PROPERTY     = "part.number_style";
    public static String CHAPTER_TITLE_ENABLED_PROPERTY = "chapter.titles";
    public static String CHAPTER_TITLE_TEXT_PROPERTY    = "chapter.title_text";
    public static String CHAPTER_NUMBER_STYLE_PROPERTY  = "chapter.number_style";
    public static String CHAPTER_NUMBER_IN_TOC_PROPERTY = "chapter.number_in_toc";  // part chapters in toc?
    public static String CHAPTER_NUMBERS_CONTINUOUS     = "chapter.numbers_continuous";  // chapter numbers continue

    public static String CHAPTER_TITLE_TEXT_NONE        = "none";

    String partTitleEnabled = null;
    String partTitleText = null;
    String partNumberStyle = null;
    String chapterTitleText = null;
    String chapterNumberStyle = null;
    String chapterTitleEnabled = null;
    String chapterNumberInToc = null;
    String chapterNumbersContinuous = null;
    
    public Options() {
        partTitleEnabled = System.getProperty(PART_TITLE_ENABLED_PROPERTY);
        partTitleText = System.getProperty(PART_TITLE_TEXT_PROPERTY);
        partNumberStyle = System.getProperty(PART_NUMBER_STYLE_PROPERTY);
        chapterTitleText = System.getProperty(CHAPTER_TITLE_TEXT_PROPERTY);
        chapterNumberStyle = System.getProperty(CHAPTER_NUMBER_STYLE_PROPERTY);
        // whether any chapter number is printed - defaults to true
        chapterTitleEnabled = System.getProperty(CHAPTER_TITLE_ENABLED_PROPERTY);
        // does the chapter number appear in the toc - defaults to true
        // (as a second level entry if PartChapter)
        chapterNumberInToc = System.getProperty(CHAPTER_NUMBER_IN_TOC_PROPERTY);
        if (chapterNumberInToc == null) {
            chapterNumberInToc = "true";
        }
        chapterNumbersContinuous = System.getProperty(CHAPTER_NUMBERS_CONTINUOUS);
    }
    
    public Boolean isChapterTitleEnabled() {
        if (chapterTitleEnabled != null) {
            return chapterTitleEnabled.equalsIgnoreCase("true");
        }
        // default
        return true;
    }

    public void setChapterTitleEnabled(Boolean chapterTitleEnabled) {
        if (chapterTitleEnabled == null) {
            this.chapterTitleEnabled = null;
        } else {
            // convert to string
            this.chapterTitleEnabled = "" + chapterTitleEnabled;
        }
    }
    public void setChapterTitleEnabled(String chapterTitleEnabled) {
        this.chapterTitleEnabled = chapterTitleEnabled;
    }

    public Boolean getChapterNumberInToc() {
        if (chapterNumberInToc != null) {
            return chapterNumberInToc.equalsIgnoreCase("true");
        }
        // default
        return false;
    }

    public void setChapterNumberInToc(Boolean chapterNumberInToc) {
        if (chapterNumberInToc == null) {
            this.chapterNumberInToc = null;
        } else {
            // convert to String
            this.chapterNumberInToc = "" + chapterNumberInToc;
        }
    }
    public void setChapterNumberInToc(String chapterNumberInToc) {
        this.chapterNumberInToc = chapterNumberInToc;
    }

    public String getChapterTitleText() {
        if (chapterTitleText != null) {
            return chapterTitleText;
        }
        // default
        return "Chapter";
    }

    public void setChapterTitleText(String chapterTitleText) {
        this.chapterTitleText = chapterTitleText;
    }

    public String getChapterNumberStyle() {
        if (chapterNumberStyle != null) {
            return chapterNumberStyle;
        }
        // default
        return "I";
    }

    public void setChapterNumberStyle(String chapterNumberStyle) {
        this.chapterNumberStyle = chapterNumberStyle;
    }

    public Boolean isPartTitleEnabled() {
        if (partTitleEnabled != null) {
            return partTitleEnabled.equalsIgnoreCase("true");
        }
        // default
        return true;
    }

    public void setPartTitleEnabled(Boolean partTitleEnabled) {
        if (partTitleEnabled == null) {
            this.partTitleEnabled = null;
        } else {
            // convert to string
            this.partTitleEnabled = "" + partTitleEnabled;
        }
    }
    public void setPartTitleEnabled(String partTitleEnabled) {
        this.partTitleEnabled = partTitleEnabled;
    }

    public String getPartTitleText() {
        if (partTitleText != null) {
            return partTitleText;
        }
        // default
        return "Part";
    }

    public void setPartTitleText(String partTitleText) {
        this.partTitleText = partTitleText;
    }

    public String getPartNumberStyle() {
        if (partNumberStyle != null) {
            return partNumberStyle;
        }
        // default
        return "One";
    }

    public void setPartNumberStyle(String partNumberStyle) {
        this.partNumberStyle = partNumberStyle;
    }
    
    public boolean getChapterNumbersContinuous() {
        if (chapterNumbersContinuous != null) {
            return chapterNumbersContinuous.equalsIgnoreCase("true");
        }
        // default
        return false;
    }

    public void setChapterNumbersContinuous(Boolean value) {
        if (value == null) {
            this.chapterNumbersContinuous = null;
        } else {
            // convert to string
            this.chapterNumbersContinuous = "" + value;
        }
    }
    public void setChapterNumbersContinuous(String value) {
        this.chapterNumbersContinuous = value;
    }
    
    // options determine rendering style
    // they also have attributes - name, value
    public static void parseOption(Book book, Node node) {
        log.info("parseOption: IS THIS USED");
        log.info("parseOption");
        NamedNodeMap attr = node.getAttributes();
        Node nameNode = attr.getNamedItem("name");
        Node valueNode = attr.getNamedItem("value");
        String name = nameNode.getTextContent();
        String value = valueNode.getTextContent();
        if (name.equalsIgnoreCase(CHAPTER_TITLE_ENABLED_PROPERTY)) {
            book.getOptions().setChapterTitleEnabled(Boolean.parseBoolean(value));
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
        if (name.equalsIgnoreCase(CHAPTER_NUMBER_IN_TOC_PROPERTY)) {
            book.getOptions().setChapterNumberInToc(Boolean.parseBoolean(value));
        }
        if (name.equalsIgnoreCase(CHAPTER_NUMBERS_CONTINUOUS)) {
            book.getOptions().setChapterNumbersContinuous(Boolean.parseBoolean(value));
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        str.append("chapter.titles[").append(isChapterTitleEnabled()).append("] ");
        str.append("chapter.titleText[").append(getChapterTitleText()).append("] ");
        str.append("chapter.numberStyle[").append(getChapterNumberStyle()).append("] ");
        str.append("part.titles[").append(isPartTitleEnabled()).append("] ");
        str.append("part.titleText[").append(getPartTitleText()).append("] ");
        str.append("part.numberStyle[").append(getPartNumberStyle()).append("] ");
        str.append("chapter.numberInToc[").append(getChapterNumberInToc()).append("] ");
        str.append("chapter.numbersContinuous[").append(getChapterNumbersContinuous()).append("]}");
        return str.toString();
    }
    
    // copy without defaults
    public Options copy() {
        Options options = new Options();
        options.setChapterNumberStyle(chapterNumberStyle);
        options.setChapterTitleText(chapterTitleText);
        options.setChapterTitleEnabled(chapterTitleEnabled);
        options.setPartNumberStyle(partNumberStyle);
        options.setPartTitleText(partTitleText);
        options.setPartTitleEnabled(partTitleEnabled);
        options.setChapterNumberInToc(chapterNumberInToc);
        options.setChapterNumbersContinuous(chapterNumbersContinuous);
        return options;
    }

    // merge without defaults
    public void merge(Options second) {
        if (chapterNumberStyle == null) {
            setChapterNumberStyle(second.chapterNumberStyle);
        }
        if (chapterTitleText == null) {
            setChapterTitleText(second.chapterTitleText);
        }
        if (chapterTitleEnabled == null) {
            setChapterTitleEnabled(second.chapterTitleEnabled);
        }
        if (partNumberStyle == null) {
            setPartNumberStyle(second.partNumberStyle);
        }
        if (partTitleText == null) {
            setPartTitleText(second.partTitleText);
        }
        if (partTitleEnabled == null) {
            setPartTitleEnabled(second.partTitleEnabled);
        }
        if (chapterNumberInToc == null) {
            setChapterNumberInToc(second.chapterNumberInToc);
        }
        if (chapterNumbersContinuous == null) {
            setChapterNumbersContinuous(second.chapterNumbersContinuous);
        }
    }
}
