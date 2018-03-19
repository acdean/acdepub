package me.koogy.acdepub;

import java.io.File;
import me.koogy.acdepub.objects.AcdParser;
import me.koogy.acdepub.objects.Book;
import me.koogy.acdepub.objects.Chapter;
import me.koogy.acdepub.objects.Options;
import me.koogy.acdepub.objects.Part;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * Hello world!
 */
public class Main {
    
    private static final Logger log = LogManager.getLogger(Main.class);

    // private static final String BOOK_XML = "/home/adean/Documents/eBooks/balzac/04/1374/balzac04.xml";
    // private static final String BOOK_XML = "/home/adean/Documents/eBooks/algernon/acdepub/four_weird_tales.xml";
    // private static final String BOOK_XML = "/home/adean/Documents/eBooks/wodehouse/school_stories/07_mike/07_mike.xml";
    // private static final String BOOK_XML = "/home/adean/Documents/eBooks/lad_and_lass/lad_and_lass.xml";
//    private static final String BOOK_XML = "/home/adean18/Documents/eBooks/hardy/a_changed_man.xml";
//    private static final String BOOK_XML = "/home/adean18/Documents/eBooks/dickens/hard_times/hard_times.xml";
//    private static final String BOOK_XML = "/home/adean18/dev/NetBeansProjects/acdepub/src/test/xml/example_04.xml";
    private static final String BOOK_XML = "/home/adean18/Documents/eBooks/dickens/chuzzlewit/martin.xml";
    public static final String PREFACE_ID_FORMAT           = "pre%03d";
    public static final String CHAPTER_ID_FORMAT           = "ch%03d";
    public static final String PART_ID_FORMAT              = "pt%02d";
    public static final String PART_CHAPTER_ID_FORMAT      = "ch%02d%03d";
    public static final String APPENDIX_ID_FORMAT          = "app%03d";

    public static void main( String[] args ) {
        
        // setup logger
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.DEBUG);
        PatternLayout layout = new PatternLayout("%d{ABSOLUTE} %-5p %c{1} - %m%n");
        rootLogger.addAppender(new ConsoleAppender(layout));

        String filename;
        if (args.length != 1) {
//            System.out.println("Usage: generate filename");
//            System.exit(-1);
            filename = BOOK_XML; // debugging
        } else {
            filename = args[0];
        }
        File file = new File(filename);
        File dir = new File("/tmp/acdepub_" + file.getName().replace(".xml", ""));
        dir.mkdirs();
        File metadir = new File(dir, "META-INF");
        metadir.mkdirs();
        System.out.println("Dir: " + dir);
        
        try {
            Book book = AcdParser.parseBook(filename);
            log.debug("Book[" + book + "]");

            // write everything
            CoverWriter.write(dir, book);
            ContentWriter.write(dir, book);
            MetaWriter.write(metadir);
            MimetypeWriter.write(dir);
            StylesheetWriter.write(dir);
            TitlePageWriter.write(dir, book);
            TocWriter.write(dir, book);

            // Prefixes
            if (book.getPrefaces() != null) {
                for (Chapter preface : book.getPrefaces()) {
                    ChapterWriter.write(dir, book.getInfo(), null, book.getOptions(), preface);
                }
            }
            // now all books have parts
            for (Part part : book.getParts()) {
                if (book.hasParts()) {
                    PartWriter.write(dir, book.getInfo(), part);
                }
                for (Chapter chapter : part.getChapters()) {
                    // NB part info
                    ChapterWriter.write(dir, book.getInfo(), part.getInfo(), part.getOptions(), chapter, part.getChapters().size());
                }
            }
            // Appendices
            if (book.getAppendices() != null) {
                for (Chapter appendix : book.getAppendices()) {
                    ChapterWriter.write(dir, book.getInfo(), null, book.getOptions(), appendix);
                }
            }

            // footnotes
            if (book.getFootnotes() != null) {
                FootnotesWriter.write(dir, book);
            }

            // generate epub
            ZipWriter.write(dir, filename);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // sets the numbering
    // 'Part III' or 'Three' or null
    public static String numbering(String name, String style, int i) {
        StringBuilder sb = new StringBuilder();
        if (style.equalsIgnoreCase(Options.CHAPTER_TITLE_TEXT_NONE)) {
            return null;
        }
        if (name != null && !name.equalsIgnoreCase(Options.CHAPTER_TITLE_TEXT_NONE)) {
            sb.append(name);
            sb.append(" ");
        }
        sb.append(Numbers.number(style, i));
        return sb.toString();
    }
}
