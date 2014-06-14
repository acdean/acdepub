package me.koogy.acdepub.objects;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Details of a chapter. Prefaces, Chapters and Appendices are all chapters.
 * 
 * A chapter can contain a title and many paragraphs
 * 
 * @author adean
 */
public class GenericChapter {

    // chapter types
    public static final int PREFIX          = 1;
    public static final int CHAPTER         = 2;
    public static final int PART_CHAPTER    = 3;
    public static final int APPENDIX        = 4;
    public static final int FOOTNOTE        = 5;
    
    private static Logger log = LogManager.getLogger(GenericChapter.class);

    private String numbering;
    private String title;
    private String content; // the payload
    private String id;
    private int type;   // type of chapter

    public GenericChapter() {
        log.debug("Constructor");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumbering() {
        return numbering;
    }

    public void setNumbering(String numbering) {
        this.numbering = numbering;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // is a normal chapter, not a prefix or appendix
    public boolean isNormalChapter() {
        return (type == GenericChapter.CHAPTER || type == GenericChapter.PART_CHAPTER);
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Chapter:{");
        s.append("Numbering:{").append(numbering).append("}");
        s.append(", ");
        s.append("Title:{").append(title).append("}");
        s.append(", ");
        s.append("Id:{").append(id).append("}");
        s.append(", ");
        if (content != null) {
            s.append("Content: ").append(content.length());
        }
        s.append("}");
        return s.toString();
    }
}
