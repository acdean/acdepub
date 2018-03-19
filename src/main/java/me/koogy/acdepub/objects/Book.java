/*
 * A Book
 * 
 * book
 *   preamble
 *   part 1
 *     chapter 1...
 *     chapter 2...
 *   [part 2
 *     chapter 1...
 *   ...]
 *   postamble
 * 
 */
package me.koogy.acdepub.objects;

import java.util.ArrayList;
import java.util.List;

/**
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
    List<Part> parts; // always at least one part, this is where the chapters are
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

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

    // returns trus is this book has (real) parts
    public boolean hasParts() {
        return parts.size() != 1;
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
        s.append("Options:{").append(options).append("}");
        s.append(", ");
        s.append("Prefaces:{").append(prefaces).append("}");
        s.append(", ");
        s.append("Parts:{").append(parts).append("}");
        s.append(", ");
        s.append("Appendices:{").append(appendices).append("}");
        s.append(", ");
        s.append("Footnotes:{").append(footnotes).append("}");
        s.append("}");
        return s.toString();
    }
}
