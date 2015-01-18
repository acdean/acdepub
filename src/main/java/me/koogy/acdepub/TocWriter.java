package me.koogy.acdepub;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.UUID;
import me.koogy.acdepub.objects.Book;
import me.koogy.acdepub.objects.Chapter;
import me.koogy.acdepub.objects.Info;
import me.koogy.acdepub.objects.Options;
import me.koogy.acdepub.objects.Part;

/**
 * toc.ncx
 * 
 * TODO prefix, appendix, footnotes
 * 
 * @author adean
 */
public class TocWriter {

    public static void write(File dir, Book book, UUID uid) {
        Info info = book.getInfo();
        Options options = book.getOptions();

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
            if (info.hasCover()) {
                p.println("    <meta name=\"cover\" content=\"cover-image\"/>");
            }
            p.println("  </head>");
            p.println("  <docTitle>");
            p.println("    <text>" + info.getTitle() + "</text>");
            p.println("  </docTitle>");
            
            p.println("  <navMap>");
            int count = 1;
            navPoint(p, info.getTitle(), "title_page", count++);
            // chapters
            if (book.getChapters() != null && options.isChapterTitleEnabled()) {
                for (Chapter chap : book.getChapters()) {
                    if (chap.getTitle() != null) {
                        navPoint(p, chap.getTitle(), chap.getId(), count);
                    } else {
                        navPoint(p, chap.getNumbering(), chap.getId(), count);
                    }
                    count++;
                }
            }
            // parts / chapters
            if (book.getParts() != null) {
                for (Part part : book.getParts()) {
                    String partLabel = getLabel(part.getInfo().getTitle(), part.getNumbering());
                    navPointStart(p, partLabel, part.getId(), count);
                    count++;
                    if (part.getChapters() != null && options.isChapterTitleEnabled() && options.getChapterNumberInToc()) {
                        for (Chapter chap : part.getChapters()) {
                            String chapLabel = getLabel(chap.getTitle(), chap.getNumbering());
                            navPoint(p, chapLabel, chap.getId(), count);
                            count++;
                        }
                    }
                    navPointEnd(p);
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

    // return the high priorty one if it exists, else the low
    static String getLabel(String high, String low) {
        return (high != null) ? high : low;
    }

    // NB filenames are all {id}.xhtml
    private static void navPoint(PrintStream p, String title, String filename, int count) {
        navPointStart(p, title, filename, count);
        navPointEnd(p);
    }

    // NB filenames are all {id}.xhtml
    private static void navPointStart(PrintStream p, String title, String filename, int count) {
        StringBuilder s = new StringBuilder();
        s.append("    <navPoint id=\"navPoint-").append(count).append("\" playOrder=\"").append(count).append("\">\n");
        s.append("      <navLabel>\n");
        // TODO what if chapters have no titles? use "Chapter XXX".
        s.append("        <text>").append(title).append("</text>\n");
        s.append("      </navLabel>\n");
        s.append("      <content src=\"").append(filename).append(".xhtml\"/>\n");
        p.print(s.toString());
    }

    private static void navPointEnd(PrintStream p) {
        StringBuilder s = new StringBuilder();
        s.append("    </navPoint>\n");
        p.print(s.toString());
    }
}
