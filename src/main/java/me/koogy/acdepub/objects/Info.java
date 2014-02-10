package me.koogy.acdepub.objects;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author adean
 */
public class Info {

    private String title;
    private String subtitle;
    private String author;
    private String date;
    private Options options;

    @XmlElement(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement(name = "subtitle")
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    @XmlElement(name = "author")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @XmlElement(name = "date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @XmlElement(name = "options")
    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Info:{");
        str.append("Title: ").append(this.getTitle()).append("},");
        str.append("SubTitle: ").append(this.getSubtitle()).append("},");
        str.append("Author: ").append(this.getAuthor()).append("},");
        str.append("Date: ").append(this.getDate()).append("}");
        str.append("}");
        return str.toString();
    }
}
