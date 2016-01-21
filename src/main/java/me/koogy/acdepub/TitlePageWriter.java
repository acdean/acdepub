package me.koogy.acdepub;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import me.koogy.acdepub.objects.Book;
import me.koogy.acdepub.objects.Info;

/**
 * Prints a title page
 * @author adean
 */
public class TitlePageWriter {
    
    public static void write(File dir, Book book) {
        Info info = book.getInfo();

        PrintStream p = null;
        try {
            File file = new File(dir, "title_page.xhtml");
            p = new PrintStream(new FileOutputStream(file));

            WriterUtils.writeHead(p, info.getTocTitle());
            WriterUtils.startTitlePage(p);
            
            p.println("<h1 class=\"title\">" + info.getTitle() + "</h1>");
            if (info.getSubtitle() != null) {
                p.println("<h1 class=\"subtitle\">" + info.getSubtitle() + "</h1>");
            }
            p.println("<h2 class=\"author\">" + info.getAuthor() + "</h2>");
            if (info.getDate() != null) {
                p.println("<h3 class=\"date\">" + info.getDate() + "</h3>");
            }

            WriterUtils.endTitlePage(p);
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
