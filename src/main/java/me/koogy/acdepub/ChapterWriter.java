package me.koogy.acdepub;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import me.koogy.acdepub.objects.Book;
import me.koogy.acdepub.objects.GenericChapter;
import me.koogy.acdepub.objects.Options;

/**
 * Outputs a chapter file
 * @author adean
 */
public class ChapterWriter {
    
    public static void write(File dir, Options options, Book book, GenericChapter chapter) {

        PrintStream p = null;
        try {
            File file = new File(dir, chapter.getId() + ".xhtml");
            p = new PrintStream(new FileOutputStream(file));

            p.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            p.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
            p.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
            p.println("<head>");
            p.println("<title>" + book.getTitle() + "</title>");
            p.print("<link rel=\"stylesheet\" type=\"text/css\" href=\"stylesheet.css\"/>");

            p.println("</head>");
            p.println("<body>");
            // Chapter Heading
            p.print("<h1>");
            if (chapter.getNumbering() != null) {
                p.print("<div class=\"chapterNumber\">Chapter " + chapter.getNumbering() + "</div>");
                if (chapter.getTitle() != null) {
                    p.print("<br/>");
                }
            }
            if (chapter.getTitle() != null) {
                p.print("<div class=\"chapterTitle\">" + chapter.getTitle() + "</div>");
            }
            p.println("</h1>");
            
            // Paragraphs
            for (String para : chapter.getParas()) {
                p.println("<p>" + para + "</p>");
            }
            
            p.println("</body>");
            p.println("</html>");
        
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.close();
            }
        }
    }
}