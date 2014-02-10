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

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author adean
 */
@XmlRootElement(name = "book")
public class Book {
    Info info;
    List<GenericChapter> prefaces;
    List<Part> parts;           // one of parts or chapters, not both
    List<GenericChapter> chapters;
    List<GenericChapter> appendices;
    String uuid;

    @XmlElement(name = "info")
    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    @XmlElement(name = "prefix")
    //@XmlAnyElement(PrefixHandler.class)
    public List<GenericChapter> getPrefaces() {
        return prefaces;
    }

    public void setPrefaces(List<GenericChapter> prefaces) {
        this.prefaces = prefaces;
    }

//    @XmlAnyElement(ChapterHandler.class)
    @XmlElement(name = "chapter")
    public List<GenericChapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<GenericChapter> chapters) {
        this.chapters = chapters;
    }

    @XmlElement(name = "part")
    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

    @XmlElement(name = "appendix")
//    @XmlAnyElement(AppendixHandler.class)
    public List<GenericChapter> getAppendices() {
        return appendices;
    }

    public void setAppendices(List<GenericChapter> appendices) {
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
        s.append("Info:{").append(info).append("}");
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
