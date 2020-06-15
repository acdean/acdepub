package me.koogy.acdepub;

import java.io.File;
import me.koogy.acdepub.objects.AcdParser;
import me.koogy.acdepub.objects.Book;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Issue008ChapterTitlesNpeTest {

    private static final Logger log = LoggerFactory.getLogger(Issue008ChapterTitlesNpeTest.class);

    String inputFilename = System.getProperty("user.dir") + "/src/test/xml/issue_008.xml";

    @Test
    public void test() {
        log.info("\nIssue008");

        Book book = AcdParser.parseBook(inputFilename);
        log.info("Book [{}]", book);

        File file = new File(inputFilename);
        File dir = new File("/tmp/acdepub_" + file.getName().replace(".xml", ""));
        dir.mkdirs();

        // error is in tocwriter
        TocWriter.write(dir, book);
    }
}
