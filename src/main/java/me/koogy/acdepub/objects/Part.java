package me.koogy.acdepub.objects;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Book can have many parts (or none)
 * @author adean
 */
@XmlRootElement(name = "part")
public class Part {
    String title;
    String numbering;
    String filename;
    ArrayList<GenericChapter> chapters;

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
        s.append("Filename:{").append(filename).append("}");
        s.append(", ");
        s.append("Chapters:{").append(chapters).append("}");
        s.append("}");
        return s.toString();
    }
}
