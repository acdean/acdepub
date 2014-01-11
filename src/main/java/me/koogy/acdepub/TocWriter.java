package me.koogy.acdepub;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.UUID;
import me.koogy.acdepub.objects.Book;
import me.koogy.acdepub.objects.GenericChapter;
import me.koogy.acdepub.objects.Part;

/**
 * toc.ncx
 * 
 * @author adean
 */
public class TocWriter {

    public static void write(File dir, Book book, UUID uid) {

        PrintStream p = null;
        try {
            File file = new File(dir, "toc.ncx");
            p = new PrintStream(new FileOutputStream(file));
            
            p.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            p.println("<ncx version=\"2005-1\" xmlns=\"http://www.daisy.org/z3986/2005/ncx/\">");
            p.println("  <head>");
            p.println("    <meta name=\"dtb:uid\" content=\"urn:uuid:" + uid.toString() + "\"/>");
            p.println("    <meta name=\"dtb:depth\" content=\"1\"/>");
            p.println("    <meta name=\"dtb:totalPageCount\" content=\"0\"/>");
            p.println("    <meta name=\"dtb:maxPageNumber\" content=\"0\"/>");
            p.println("    <meta name=\"cover\" content=\"cover-image\"/>");
            p.println("  </head>");
            p.println("  <docTitle>");
            p.println("    <text>" + book.getTitle() + "</text>");
            p.println("  </docTitle>");
            
            p.println("  <navMap>");
            int count = 1;
            navPoint(p, "Title Page", "title_page", count++);
            // chapters
            if (book.getChapters() != null) {
                for (GenericChapter chap : book.getChapters()) {
                    navPoint(p, chap.getTitle(), chap.getId(), count);
                    count++;
                }
            }
            // parts / chapters
            if (book.getParts() != null) {
                for (Part part : book.getParts()) {
                    navPoint(p, part.getTitle(), part.getId(), count);
                    count++;
                    for (GenericChapter chap : part.getChapters()) {
                        // don't bother with untitled chapters
                        if (chap.getTitle() != null) {
                            navPoint(p, chap.getTitle(), chap.getId(), count);
                            count++;
                        }
                    }
                }
            }
            p.println("  </navMap>");
            p.println("</ncx>");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.close();
            }
        }
    }

    // NB filenames are all {id}.xhtml
    private static void navPoint(PrintStream p, String title, String filename, int count) {
        StringBuilder s = new StringBuilder();
        s.append("    <navPoint id=\"navPoint-").append(count).append("\" playOrder=\"").append(count).append("\">\n");
        s.append("      <navLabel>\n");
        s.append("        <text>").append(title).append("</text>\n");
        s.append("      </navLabel>\n");
        s.append("      <content src=\"").append(filename).append(".xhtml\"/>\n");
        s.append("    </navPoint>\n");
        p.print(s.toString());
    }
}
