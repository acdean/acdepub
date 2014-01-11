package me.koogy.acdepub.objects;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;

/**
 * Details of a chapter. Preamble, Chapters and PostAmble are all chapters.
 * @author adean
 */
public class GenericChapter {

    String numbering;
    String title;
    List<GenericParagraph> paras;
    String id;

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

    @XmlElement(name = "para")
    public List<GenericParagraph> getParas() {
        return paras;
    }

    public void setParas(List<GenericParagraph> paras) {
        this.paras = paras;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        s.append("Paragraphs:{").append(".").append("}");
        s.append("}");
        return s.toString();
    }
}
