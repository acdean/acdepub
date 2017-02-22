package me.koogy.acdepub.objects;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author adean
 */
public class Chapter {

    // chapter types
    public static final int PREFIX = 1;
    public static final int CHAPTER = 2;
    public static final int PART_CHAPTER = 3;
    public static final int APPENDIX = 4;
    public static final int FOOTNOTE = 5;
    // see also Part.PART

    private static Logger log = LogManager.getLogger(Chapter.class);
    private String numbering;
    private String title;
    private String content; // the payload
    private String id;
    private int type; // type of chapter

    public Chapter() {
        log.debug("constructor");
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
        return type == CHAPTER || type == PART_CHAPTER;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Chapter:{");
        if (numbering != null) {
            s.append("Numbering:{").append(numbering).append("} ");
        }
        if (title != null) {
            s.append("Title:{").append(title).append("} ");
        }
        s.append("Id:{").append(id).append("} ");
        s.append("Normal:{").append(isNormalChapter()).append("} ");
        if (content != null) {
            s.append("Content: ").append(content.length());
        }
        s.append("}");
        return s.toString();
    }
}
