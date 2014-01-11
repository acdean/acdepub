package me.koogy.acdepub.objects;

import javax.xml.bind.annotation.XmlElement;

/**
 * As per html paragraph. Can contain <em> and &emdash; etc
 * @author adean
 */
public class GenericParagraph {
    String contents;
    
    @Override
    public String toString() {
        return("{Para}");
    }
}
