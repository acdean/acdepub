package me.koogy.acdepub;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import me.koogy.acdepub.objects.Book;

/**
 * Prints a title page
 * @author adean
 */
public class TitlePageWriter {
    
    public static void write(File dir, Book book) {

        PrintStream p = null;
        try {
            File file = new File(dir, "title_page.xhtml");
            p = new PrintStream(new FileOutputStream(file));
        
            p.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            p.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
            p.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
            p.println("<head>");
            p.println("<title>" + book.getTitle() + "</title>");
            p.println("<link href=\"stylesheet.css\" type=\"text/css\" rel=\"stylesheet\"/>");
            p.println("</head>");
            p.println("<body>");
            p.println("<h1 class=\"title\">" + book.getTitle() + "</h1>");
            if (book.getSubtitle() != null) {
                p.println("<h1 class=\"subtitle\">" + book.getSubtitle() + "</h1>");
            }
            p.println("<h2 class=\"author\">" + book.getAuthor() + "</h2>");
            if (book.getDate() != null) {
                p.println("<h3 class=\"date\">" + book.getDate() + "</h3>");
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
