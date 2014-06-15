package me.koogy.acdepub;

import java.io.File;
import java.util.UUID;
import me.koogy.acdepub.objects.AcdParser;
import me.koogy.acdepub.objects.Book;
import me.koogy.acdepub.objects.GenericChapter;
import me.koogy.acdepub.objects.Options;
import me.koogy.acdepub.objects.Part;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * Hello world!
 *
 */
public class Main {
    
    private static Logger log = LogManager.getLogger(Main.class);

    // private static final String BOOK_XML = "/home/adean/Documents/eBooks/balzac/04/1374/balzac04.xml";
//    private static final String BOOK_XML = "/home/adean/Documents/eBooks/algernon/acdepub/four_weird_tales.xml";
//    private static final String BOOK_XML = "/home/adean/Documents/eBooks/wodehouse/school_stories/07_mike/07_mike.xml";
    private static final String BOOK_XML = "/home/adean/Documents/eBooks/lad_and_lass/lad_and_lass.xml";
    
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
        UUID uid = UUID.randomUUID();
        
        try {
            Book book = AcdParser.parseBook(filename);
            log.debug("Book[" + book + "]");
            
            // generate numbers and filenames
            // NB functionality moving to parser
//            numberParts(book);
//            log.debug("Book[" + book + "]");
            
            // write everything
            CoverWriter.write(dir, book);
            ContentWriter.write(dir, book, uid);
            MetaWriter.write(metadir);
            MimetypeWriter.write(dir);
            StylesheetWriter.write(dir);
            TitlePageWriter.write(dir, book);
            TocWriter.write(dir, book, uid);

            // Chapters
            if (book.getPrefaces() != null) {
                for (GenericChapter preface : book.getPrefaces()) {
                    ChapterWriter.write(dir, book.getInfo(), book.getOptions(), preface);
                }
            }
            if (book.getChapters() != null) {
                for (GenericChapter chapter : book.getChapters()) {
                    ChapterWriter.write(dir, book.getInfo(), book.getOptions(), chapter);
                }
            }
            if (book.getParts() != null) {
                for (Part part : book.getParts()) {
                    PartWriter.write(dir, part);
                    for (GenericChapter chapter : part.getChapters()) {
                        // NB part info
                        ChapterWriter.write(dir, part.getInfo(), part.getOptions(), chapter);
                    }
                }
            }
            if (book.getAppendices() != null) {
                for (GenericChapter appendix : book.getAppendices()) {
                    ChapterWriter.write(dir, book.getInfo(), book.getOptions(), appendix);
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

    // jaxb version (out of memory)
//    static Book parseJaxb(String filename) {
//        Book book = null;
//        try {
//            JAXBContext context = JAXBContext.newInstance(Book.class);
//            Unmarshaller um = context.createUnmarshaller();
//            // read book from xml
//            book = (Book)um.unmarshal(new FileReader(filename));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return book;
//    }
    
    // dom version
//    static Book parseDom(String filename) {
//        Book book = new Book();
//        System.out.println("Options: " + book.getOptions());
//        try {
//            File file = new File(filename);
//            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//            Document doc = dBuilder.parse(file);
//            Parser.parseBook(book, doc);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return book;
//    }

    // moving this to parser
    static void numberParts(Book book) {
//        Options options = book.getOptions();
//        int count;
//        
//        // preamble - no numbering
//        count = 1;
//        if (book.getPrefaces() != null) {
//            for(GenericChapter chap : book.getPrefaces()) {
//                chap.setId(String.format(PREFACE_ID_FORMAT, count));
//                count++;
//            }
//        }
//
//        // there'll be one of chapters or parts
//
//        // chapters - with numbering
//        if (book.getChapters() != null) {
//            count = 1;
//            for(GenericChapter chap : book.getChapters()) {
//                chap.setId(String.format(CHAPTER_ID_FORMAT, count));
//                chap.setNumbering(numbering(options.getChapterTitleText(), options.getChapterNumberStyle(), count));
//                count++;
//            }
//        }
//        // parts / chapters - with numbering
//        if (book.getParts() != null) {
//            int partCount = 1;
//            for(Part part : book.getParts()) {
//                Options partOptions = part.getOptions();
//                part.setId(String.format(PART_ID_FORMAT, partCount));
//                count = 1;
//                if (part.getChapters() != null) {
//                    for(GenericChapter chap : part.getChapters()) {
//                        chap.setId(String.format(PART_CHAPTER_ID_FORMAT, partCount, count));
//                        chap.setNumbering(numbering(partOptions.getChapterTitleText(), partOptions.getChapterNumberStyle(), count));
//                        count++;
//                    }
//                }
//                partCount++;
//            }
//        }
//
//        // postamble - no numbering
//        if (book.getAppendices() != null) {
//            count = 1;
//            for(GenericChapter chap : book.getAppendices()) {
//                chap.setId(String.format(APPENDIX_ID_FORMAT, count));
//                count++;
//            }
//        }
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
