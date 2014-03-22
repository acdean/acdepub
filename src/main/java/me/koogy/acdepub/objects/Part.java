package me.koogy.acdepub.objects;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAnyElement;

/**
 * Book can have many parts (or none)
 * A Part has a title and many chapters
 * 
 * @author adean
 */
public class Part {
    Info info;
    String numbering;
    String id;
    ArrayList<Chapter> chapters;
    Options options;

    public String getNumbering() {
        return numbering;
    }

    public void getNumbering(String numbering) {
        this.numbering = numbering;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(ArrayList<Chapter> chapters) {
        this.chapters = chapters;
    }

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

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Part:{");
        s.append("Info:{").append(info).append("}");
        s.append(", ");
        s.append("Numbering:{").append(numbering).append("}");
        s.append(", ");
        s.append("Id:{").append(id).append("}");
        s.append(", ");
        s.append("Chapters:{").append(chapters).append("}");
        s.append("}");
        return s.toString();
    }
}
