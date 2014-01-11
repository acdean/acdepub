package me.koogy.acdepub.objects;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Book can have many parts (or none)
 * @author adean
 */
@XmlRootElement(name = "part")
public class Part {
    String title;
    String numbering;
    String id;
    ArrayList<GenericChapter> chapters;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
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
    
    @XmlElement(name = "chapter")
    public ArrayList<GenericChapter> getChapters() {
        return chapters;
    }

    public void setChapters(ArrayList<GenericChapter> chapters) {
        this.chapters = chapters;
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Part:{");
        s.append("Numbering:{").append(numbering).append("}");
        s.append(", ");
        s.append("Title:{").append(title).append("}");
        s.append(", ");
        s.append("Id:{").append(id).append("}");
        s.append(", ");
        s.append("Chapters:{").append(chapters).append("}");
        s.append("}");
        return s.toString();
    }
}
