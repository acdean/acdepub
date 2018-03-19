package me.koogy.acdepub;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import me.koogy.acdepub.objects.Book;
import me.koogy.acdepub.objects.Chapter;
import me.koogy.acdepub.objects.Info;
import me.koogy.acdepub.objects.Options;
import me.koogy.acdepub.objects.Part;

/**
 * toc.ncx
 * 
 * TODO footnotes?
 * 
 * @author adean
 */
public class TocWriter {

    public static void write(File dir, Book book) {
        Info info = book.getInfo();
        Options options = book.getOptions();

        PrintStream p = null;
        try {
            File file = new File(dir, "toc.ncx");
            p = new PrintStream(new FileOutputStream(file));

            p.println("<?xml version='1.0' encoding='utf-8'?>");
            p.println("<ncx xmlns=\"http://www.daisy.org/z3986/2005/ncx/\" version=\"2005-1\" xml:lang=\"eng\">");
            p.println("  <head>");
            p.println("    <meta name=\"dtb:uid\" content=\"" + book.getUuid() + "\"/>");
            p.println("    <meta name=\"dtb:depth\" content=\"1\"/>");
            p.println("    <meta name=\"dtb:totalPageCount\" content=\"0\"/>");
            p.println("    <meta name=\"dtb:maxPageNumber\" content=\"0\"/>");
            if (info.hasCover()) {
                p.println("    <meta name=\"cover\" content=\"cover-image\"/>");
            }
            p.println("  </head>");
            p.println("  <docTitle>");
            p.println("    <text>" + info.getTocTitle() + "</text>");
            p.println("  </docTitle>");
            
            // navmap
            p.println("  <navMap>");
            int count = 1;
            navPoint(p, info.getTocTitle(), "title_page", count++);

            // prefaces
            if (book.getPrefaces() != null) {
                for (Chapter chap : book.getPrefaces()) {
                    if (chap.getTitle() != null) {
                        navPoint(p, chap.getTitle(), chap.getId(), count);
                    } else if (chap.getNumbering() != null) {
                        navPoint(p, chap.getNumbering(), chap.getId(), count);
                    } else {
                        // default to "Preface"
                        navPoint(p, "Preface", chap.getId(), count);
                    }
                    count++;
                }
            }

            // parts / chapters
            for (Part part : book.getParts()) {
                if (book.hasParts()) {
                    // outer nav point
                    String partLabel = getLabel(part.getInfo().getTitle(), part.getNumbering());
                    navPointStart(p, partLabel, part.getId(), count);
                    count++;
                }
                if (part.getChapters() != null && options.isChapterTitleEnabled() && options.getChapterNumberInToc()) {
                    for (Chapter chap : part.getChapters()) {
                        if (part.hasChapters()) {
                            // more than one chapter
                            String chapLabel = getLabel(chap.getTitle(), chap.getNumbering());
                            navPoint(p, chapLabel, chap.getId(), count);
                            count++;
                        }
                    }
                }
                if (book.hasParts()) {
                    // outer nav point
                    navPointEnd(p);
                }
            }

            if (book.getAppendices() != null) {
                for (Chapter chap : book.getAppendices()) {
                    if (chap.getTitle() != null) {
                        navPoint(p, chap.getTitle(), chap.getId(), count);
                    } else if (chap.getNumbering() != null) {
                        navPoint(p, chap.getNumbering(), chap.getId(), count);
                    } else {
                        // default to "Appendix"
                        navPoint(p, "Appendix", chap.getId(), count);
                    }
                    count++;
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

    // NB filenames are all {id}.html
    private static void navPoint(PrintStream p, String title, String filename, int count) {
        navPointStart(p, title, filename, count);
        navPointEnd(p);
    }

    // NB filenames are all {id}.html
    private static void navPointStart(PrintStream p, String title, String filename, int count) {
        StringBuilder s = new StringBuilder();
        s.append("    <navPoint class=\"chapter\" id=\"navPoint-").append(count).append("\" playOrder=\"").append(count).append("\">\n");
        s.append("      <navLabel>\n");
        // TODO what if chapters have no titles? use "Chapter nnn".
        s.append("        <text>").append(title).append("</text>\n");
        s.append("      </navLabel>\n");
        s.append("      <content src=\"").append(filename).append(".html\"/>\n");
        p.print(s.toString());
    }

    private static void navPointEnd(PrintStream p) {
        StringBuilder s = new StringBuilder();
        s.append("    </navPoint>\n");
        p.print(s.toString());
    }
}
