package me.koogy.acdepub.objects;

/**
 * @author adean
 */
public class Info {

    private String title;
    private String subtitle;
    private String author;
    private String date;
    private boolean hasCover = false;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean hasCover() {
        return hasCover;
    }

    public void hasCover(boolean hasCover) {
        this.hasCover = hasCover;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Info{");
        str.append("Title[").append(this.getTitle()).append("], ");
        str.append("SubTitle[").append(this.getSubtitle()).append("], ");
        str.append("Author[").append(this.getAuthor()).append("], ");
        str.append("Date[").append(this.getDate()).append("], ");
        str.append("HasCover[").append(this.hasCover()).append("]");
        str.append("}");
        return str.toString();
    }
    
//    public Info copy() {
//        Info info = new Info();
//        info.setTitle(getTitle());
//        //info.setSubtitle(getSubtitle());
//        info.setAuthor(getAuthor());
//        info.setDate(getDate());
//        return info;
//    }
}
