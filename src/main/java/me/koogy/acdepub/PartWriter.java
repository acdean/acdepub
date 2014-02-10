package me.koogy.acdepub;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import me.koogy.acdepub.objects.Book;
import me.koogy.acdepub.objects.Info;
import me.koogy.acdepub.objects.Options;
import me.koogy.acdepub.objects.Part;

/**
 * Prints a part title page
 * @author adean
 */
public class PartWriter {
    
    public static void write(File dir, Book book, Part part) {
        Info info = book.getInfo();
        Options options = info.getOptions();
        PrintStream p = null;
        try {
            File file = new File(dir, part.getId() + ".xhtml");
            p = new PrintStream(new FileOutputStream(file));

            p.print("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
            p.print("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
            p.print("<html xmlns=\"http://www.w3.org/1999/xhtml\">");

            p.print("<head>");
            p.print("<title>" + info.getTitle() + "</title>");
            p.print("<link rel=\"stylesheet\" type=\"text/css\" href=\"stylesheet.css\"/>");
            p.print("</head>");

            p.print("<body>");
            p.print("<h1>");
            if (part.getNumbering() != null) {
                p.print("<span class=\"partNumber\">Part " + part.getNumbering() + "</span>");
                if (info.getTitle() != null) {
                    p.print("<br/>");
                }
            }
            if (info.getTitle() != null) {
                p.print("<span class=\"partTitle\">" + info.getTitle() + "</span>");
            }
            p.println("</h1>");
            p.print("</body>");

            p.print("</html>");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.close();
            }
        }
    }
}
