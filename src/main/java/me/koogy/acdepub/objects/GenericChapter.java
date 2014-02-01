package me.koogy.acdepub.objects;

import java.util.List;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import me.koogy.acdepub.ParaHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Details of a chapter. Prefaces, Chapters and Appendices are all chapters.
 * 
 * A chapter can contain a title and many paragraphs
 * 
 * @author adean
 */
public class GenericChapter {

    private static Logger log = LogManager.getLogger(GenericChapter.class);

    String numbering;
    String title;
    List<String> paras; // the payload
    String id;

    public GenericChapter() {
        log.debug("Constructor");
    }

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

    @XmlAnyElement(ParaHandler.class)
    public List<String> getParas() {
        return paras;
    }

    public void setParas(List<String> paras) {
        this.paras = paras;
    }

    @XmlElement
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
        if (paras != null) {
            s.append("Paras x " + paras.size());
        }
        s.append("}");
        return s.toString();
    }
}
