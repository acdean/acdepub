package me.koogy.acdepub;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import me.koogy.acdepub.objects.Book;
import me.koogy.acdepub.objects.Info;

/**
 * Writes a cover page, mostly image.
 * @author adean
 */
public class CoverWriter {

    public static void write(File dir, Book book) {
        Info info = book.getInfo();

        PrintStream p = null;
        try {
            File file = new File(dir, "cover.xhtml");
            p = new PrintStream(new FileOutputStream(file));

            p.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            p.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
            p.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
            p.println("<head>");
            p.println("<title>" + info.getTitle() + "</title>");
            p.println("<style type=\"text/css\">img{ max-width: 100%; }</style>");
            p.println("<link href=\"stylesheet.css\" type=\"text/css\" rel=\"stylesheet\" />");
            p.println("</head>");
            p.println("<body>");
            p.println("<div id=\"cover-image\">");
            p.println("<img src=\"cover-image.jpg\" alt=\"" + info.getTitle() + "\" />");
            p.println("</div>");
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
