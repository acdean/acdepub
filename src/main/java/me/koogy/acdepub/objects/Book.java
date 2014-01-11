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
    ArrayList<GenericChapter> preambles;
    ArrayList<Part> parts;           // one of parts or chapters, not both
    ArrayList<GenericChapter> chapters;
    ArrayList<GenericChapter> postambles;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ArrayList<GenericChapter> getChapters() {
        return chapters;
    }

    public void setChapters(ArrayList<GenericChapter> chapters) {
        this.chapters = chapters;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @XmlElement(name = "part")
    public ArrayList<Part> getParts() {
        return parts;
    }

    public void setParts(ArrayList<Part> parts) {
        this.parts = parts;
    }

    public ArrayList<GenericChapter> getPostambles() {
        return postambles;
    }

    public void setPostambles(ArrayList<GenericChapter> postambles) {
        this.postambles = postambles;
    }

    public ArrayList<GenericChapter> getPreambles() {
        return preambles;
    }

    public void setPreambles(ArrayList<GenericChapter> preambles) {
        this.preambles = preambles;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Book:{");
        s.append("Preambles:{").append(preambles).append("}");
        s.append(", ");
        s.append("Parts:{").append(parts).append("}");
        s.append(", ");
        s.append("Chapters:{").append(chapters).append("}");
        s.append(", ");
        s.append("Postambles:{").append(postambles).append("}");
        s.append("}");
        return s.toString();
    }
}
