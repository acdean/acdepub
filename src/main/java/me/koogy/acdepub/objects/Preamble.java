package me.koogy.acdepub.objects;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Preamble is used for things like Dedications and Preface.
 * It can have a title (eg Preface) but probably won't be numbered. 
 * 
 * @author adean
 */
@XmlRootElement(name = "preamble")
public class Preamble extends Chapter {
    
}
