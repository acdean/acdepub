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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author adean
 */
@XmlRootElement(name = "book")
public class Book {
    String title;
    String subtitle;
    String author;
    String date;
    ArrayList<GenericChapter> prefaces;
    ArrayList<Part> parts;           // one of parts or chapters, not both
    ArrayList<GenericChapter> chapters;
    ArrayList<GenericChapter> appendices;
    String uuid;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @XmlElement(name = "preface")
    public ArrayList<GenericChapter> getPrefaces() {
        return prefaces;
    }

    public void setPrefaces(ArrayList<GenericChapter> prefaces) {
        this.prefaces = prefaces;
    }

    @XmlElement(name = "chapter")
    public ArrayList<GenericChapter> getChapters() {
        return chapters;
    }

    public void setChapters(ArrayList<GenericChapter> chapters) {
        this.chapters = chapters;
    }

    @XmlElement(name = "part")
    public ArrayList<Part> getParts() {
        return parts;
    }

    public void setParts(ArrayList<Part> parts) {
        this.parts = parts;
    }

    @XmlElement(name = "appendix")
    public ArrayList<GenericChapter> getAppendices() {
        return appendices;
    }

    public void setAppendices(ArrayList<GenericChapter> appendices) {
        this.appendices = appendices;
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
        s.append("Author:{").append(author).append("}");
        s.append(", ");
        s.append("Title:{").append(title).append("}");
        s.append(", ");
        s.append("Subtitle:{").append(subtitle).append("}");
        s.append(", ");
        s.append("Prefaces:{").append(prefaces).append("}");
        s.append(", ");
        s.append("Parts:{").append(parts).append("}");
        s.append(", ");
        s.append("Chapters:{").append(chapters).append("}");
        s.append(", ");
        s.append("Appendices:{").append(appendices).append("}");
        s.append("}");
        return s.toString();
    }
}
