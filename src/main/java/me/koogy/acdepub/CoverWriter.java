package me.koogy.acdepub;

import java.io.File;
import java.io.FileInputStream;
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

    private static final String COVER_FILENAME = "cover-image.jpg";
    
    public static void write(File dir, Book book) {
        
        Info info = book.getInfo();

        // do we need to do this?
        File cover = new File(COVER_FILENAME);
        if (!cover.exists()) {
            info.hasCover(false);
            return;
        }
        info.hasCover(true);

        // copy file to new directory
        File outCover = new File(dir, COVER_FILENAME);
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(cover);
            fos = new FileOutputStream(outCover);
            byte[] bytes = new byte[2048];
            int count;
            while((count = fis.read(bytes)) != -1) {
                fos.write(bytes, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        } finally {
            if (fis != null) {
                try {fis.close();} catch (IOException ex) {/* ignore */}
            }
            if (fos != null) {
                try {fos.close();} catch (IOException ex) {/* ignore */}
            }
        }
        
        // generate cover wrapper
        PrintStream p = null;
        try {
            File file = new File(dir, "cover.html");
            p = new PrintStream(new FileOutputStream(file));

            WriterUtils.writeHead(p, info.getTocTitle());

            p.println("<body class=\"image\">");
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
