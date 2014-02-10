package me.koogy.acdepub.objects;

import javax.xml.bind.annotation.XmlElement;

/**
 * As per html paragraph. Can contain <em> and &emdash; etc
 * @author adean
 */
@Deprecated
public class GenericParagraph {
    String contents;

    public GenericParagraph() {
        System.out.println("GenericParagraph");
    }

    @Override
    public String toString() {
        return("{Para}");
    }
}
