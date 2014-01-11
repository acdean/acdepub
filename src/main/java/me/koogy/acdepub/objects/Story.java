package me.koogy.acdepub.objects;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A Short story within a book.
 * Effectively the same as Part but unnumbered.
 * 
 * @author adean
 */
@XmlRootElement(name = "story")
public class Story extends Part {
}
