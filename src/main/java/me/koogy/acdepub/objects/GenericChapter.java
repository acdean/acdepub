package me.koogy.acdepub.objects;

import java.util.List;

/**
 * Details of a chapter. Preamble, Chapters and PostAmble are all chapters.
 * @author adean
 */
public class GenericChapter {

    String numbering;
    String title;
    List<GenericParagraph> paras;
    String filename;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getNumbering() {
        return numbering;
    }

    public void setNumbering(String numbering) {
        this.numbering = numbering;
    }

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
        s.append("Filename:{").append(filename).append("}");
        s.append(", ");
        s.append("Paragraphs:{").append(paras).append("}");
        s.append("}");
        return s.toString();
    }
}
