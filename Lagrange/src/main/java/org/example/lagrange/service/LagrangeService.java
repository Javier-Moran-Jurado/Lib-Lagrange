package org.example.lagrange.service;
import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.file.*;


@Service
public class LagrangeService {

    static {
        try {
            // Cargar tu librería nativa real
            InputStream inputStream = LagrangeService.class.getClassLoader()
                    .getResourceAsStream("native/liblagrange.so");
            File tempFile = File.createTempFile("liblagrange", ".so");
            Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            tempFile.deleteOnExit();
            System.load(tempFile.getAbsolutePath());
        } catch (Exception e) {
            throw new RuntimeException("Error loading native library", e);
        }
    }

    public double interpolar(double[][] puntos, double x) {
        // Si no tiene package, usar directamente
        liblagrange.LagrangeJNI lagrange = new liblagrange.LagrangeJNI();
        return lagrange.interpolar(puntos, x);
    }
}