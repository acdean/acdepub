package me.koogy.acdepub;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @author adean
 */
public class MetaWriter {

    public static void write(File metadir) {

        PrintStream p = null;
        try {
            File file = new File(metadir, "container.xml");
            p = new PrintStream(new FileOutputStream(file));
            
            p.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            p.println("<container version=\"1.0\" xmlns=\"urn:oasis:names:tc:opendocument:xmlns:container\">");
            p.println("  <rootfiles>");
            p.println("    <rootfile full-path=\"content.opf\" media-type=\"application/oebps-package+xml\"/>");
            p.println("  </rootfiles>");
            p.println("</container>");
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.close();
            }
        }
    }            
}
