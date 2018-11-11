package me.koogy.acdepub;

import java.io.File;
import me.koogy.acdepub.objects.AcdParser;
import me.koogy.acdepub.objects.Book;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.Before;
import org.junit.Test;

public class Issue008ChapterTitlesNpeTest {

    Logger log = Logger.getLogger(Issue008ChapterTitlesNpeTest.class);

    String inputFilename = System.getProperty("user.dir") + "/src/test/xml/issue_008.xml";

    @Before
    public void setup() {
        ConsoleAppender appender = new ConsoleAppender();
        appender.setLayout(new PatternLayout("%d [%p|%c|%C{1}] %m%n"));
        appender.activateOptions();
        Logger.getRootLogger().addAppender(appender);
    }

    @Test
    public void test() {
        System.out.println("\nIssue008");

        Book book = AcdParser.parseBook(inputFilename);
        log.info("Book: " + book);

        File file = new File(inputFilename);
        File dir = new File("/tmp/acdepub_" + file.getName().replace(".xml", ""));
        dir.mkdirs();

        // error is in tocwriter
        TocWriter.write(dir, book);
    }
}
