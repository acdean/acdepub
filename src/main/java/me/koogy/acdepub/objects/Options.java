package me.koogy.acdepub.objects;

/**
 * Options
 * @author adean
 */
public class Options {

    public static String PART_TITLE_TEXT_PROPERTY = "part.title_text";
    public static String PART_NUMBER_STYLE_PROPERTY = "part.number_style";
    public static String CHAPTER_TITLES_PROPERTY = "chapter.titles";
    public static String CHAPTER_TITLE_TEXT_PROPERTY = "chapter.title_text";
    public static String CHAPTER_NUMBER_STYLE_PROPERTY = "chapter.number_style";

    String partTitleText = null;
    String partNumberStyle = null;
    String chapterTitleText = null;
    String chapterNumberStyle = null;
    boolean chapterTitles = true;
    
    public Options() {
        partTitleText = System.getProperty(PART_TITLE_TEXT_PROPERTY, "Part");
        partNumberStyle = System.getProperty(PART_NUMBER_STYLE_PROPERTY, "1");
        chapterTitleText = System.getProperty(CHAPTER_TITLE_TEXT_PROPERTY, "Chapter");
        chapterNumberStyle = System.getProperty(CHAPTER_NUMBER_STYLE_PROPERTY, "I");
        // whether any chapter number is printed - defaults to true
        chapterTitles = System.getProperty(CHAPTER_TITLES_PROPERTY, "true").equalsIgnoreCase("true");
    }
    
    public boolean getChapterTitles() {
        return chapterTitles;
    }

    public void setChapterTitles(boolean chapterTitles) {
        this.chapterTitles = chapterTitles;
    }

    public String getChapterTitleText() {
        return chapterTitleText;
    }

    public void setChapterTitleText(String chapterTitleText) {
        this.chapterTitleText = chapterTitleText;
    }

    public String getChapterNumberStyle() {
        return chapterNumberStyle;
    }

    public void setChapterNumberStyle(String chapterNumberStyle) {
        this.chapterNumberStyle = chapterNumberStyle;
    }

    public String getPartTitleText() {
        return partTitleText;
    }

    public void setPartTitleText(String partTitleText) {
        this.partTitleText = partTitleText;
    }

    public String getPartNumberStyle() {
        return partNumberStyle;
    }

    public void setPartNumberStyle(String partNumberStyle) {
        this.partNumberStyle = partNumberStyle;
    }
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("(");
        str.append("chapter.titles[").append(getChapterTitles()).append("] ");
        str.append("chapter.titleText[").append(getChapterTitleText()).append("] ");
        str.append("chapter.numberStyle[").append(getChapterNumberStyle()).append("] ");
        str.append("part.titleText[").append(getPartTitleText()).append("] ");
        str.append("part.numberStyle[").append(getPartNumberStyle()).append("])");
        return str.toString();
    }
}
