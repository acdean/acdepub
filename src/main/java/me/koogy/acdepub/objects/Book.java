/*
 * A Book
 * 
 * book
 *   preamble
 *   chapter 1
 *   chapter 2
 *   ...
 *   postamble
 * 
 * OR
 * 
 * book
 *   preamble
 *   part 1
 *     chapter 1...
 *   part 2
 *     chapter 1...
 *   ...
 *   postamble
 * 
 * OR
 * 
 * book
 *   preamble
 *   story
 *     chapter 1...
 *   story
 *     chapter 1...
 *   ...
 *   postamble
 */
package me.koogy.acdepub.objects;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adean
 */
public class Book {
    
    public static final String FOOTNOTE_ANCHOR_PREFIX       = "fn_"; 
    public static final String FOOTNOTES_FILENAME           = "footnotes.html"; 
    public static final String FOOTNOTES_ID                 = "footnotes"; 
    public static final String FOOTNOTE_LINK_ANCHOR_PREFIX  = "fn_"; 
    
    Info info;
    Options options;
    List<Chapter> prefaces;
    List<Part> parts;           // one of parts or chapters, not both
    List<Chapter> chapters;
    List<Chapter> appendices;
    String uuid;
    List<Chapter> footnotes;
    int footnoteCounter; // number of footnotes
    public List<String> footnoteLinks = new ArrayList<String>();   // where the footnote links are

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public List<Chapter> getPrefaces() {
        return prefaces;
    }

    public void setPrefaces(List<Chapter> prefaces) {
        this.prefaces = prefaces;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

    public List<Chapter> getAppendices() {
        return appendices;
    }

    public void setAppendices(List<Chapter> appendices) {
        this.appendices = appendices;
    }

    public List<Chapter> getFootnotes() {
        return footnotes;
    }

    public void setFootnotes(List<Chapter> footnotes) {
        this.footnotes = footnotes;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Book:{");
        s.append("Info:{").append(info).append("}");
        s.append(", ");
        s.append("Prefaces:{").append(prefaces).append("}");
        s.append(", ");
        s.append("Parts:{").append(parts).append("}");
        s.append(", ");
        s.append("Chapters:{").append(chapters).append("}");
        s.append(", ");
        s.append("Appendices:{").append(appendices).append("}");
        s.append(", ");
        s.append("Footnotes:{").append(footnotes).append("}");
        s.append("}");
        return s.toString();
    }
}
