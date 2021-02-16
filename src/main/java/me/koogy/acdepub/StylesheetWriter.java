package me.koogy.acdepub;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Default stylesheet.
 * @author adean
 */
public class StylesheetWriter {

    // copy stylesheet from resources
    public static void write(File dir) {
        try {
            InputStream input = StylesheetWriter.class.getClassLoader().getResourceAsStream("stylesheet.css");
            Files.copy(input,
                    Paths.get(dir.getCanonicalPath() + File.separator + "stylesheet.css"),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
