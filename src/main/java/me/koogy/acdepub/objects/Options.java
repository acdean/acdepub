package me.koogy.acdepub.objects;

/**
 * Options
 * @author adean
 */
public class Options {

    public static String PART_NAME_PROPERTY = "part.name";
    public static String PART_NUMBER_STYLE_PROPERTY = "part.number_style";
    public static String CHAPTER_NAME_PROPERTY = "chapter.name";
    public static String CHAPTER_NUMBER_STYLE_PROPERTY = "chapter.number_style";

    String partName = null;
    String partNumberStyle = null;
    String chapterName = null;
    String chapterNumberStyle = null;
    
    public Options() {
        partName = System.getProperty(PART_NAME_PROPERTY, "Part");
        partNumberStyle = System.getProperty(PART_NUMBER_STYLE_PROPERTY, "1");
        chapterName = System.getProperty(CHAPTER_NAME_PROPERTY, "Chapter");
        chapterNumberStyle = System.getProperty(CHAPTER_NUMBER_STYLE_PROPERTY, "I");
    }
    
    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getChapterNumberStyle() {
        return chapterNumberStyle;
    }

    public void setChapterNumberStyle(String chapterNumberStyle) {
        this.chapterNumberStyle = chapterNumberStyle;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getPartNumberStyle() {
        return partNumberStyle;
    }

    public void setPartNumberStyle(String partNumberStyle) {
        this.partNumberStyle = partNumberStyle;
    }
}
