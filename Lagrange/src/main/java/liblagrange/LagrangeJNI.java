package liblagrange;

import java.io.*;
import java.nio.file.*;

public class LagrangeJNI {

    static {
        try {
            // Cargar desde classpath en lugar de library path
            InputStream inputStream = LagrangeJNI.class.getClassLoader()
                    .getResourceAsStream("native/liblagrange.so");
            File tempFile = File.createTempFile("liblagrange", ".so");
            Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            tempFile.deleteOnExit();
            System.load(tempFile.getAbsolutePath());
        } catch (Exception e) {
            throw new RuntimeException("Error loading native library", e);
        }
    }

    public native double interpolar(double[][] puntos, double x);
}
