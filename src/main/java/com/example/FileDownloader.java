package com.example;
import org.slf4j.LoggerFactory;
import java.net.URI;
import ch.qos.logback.classic.Level;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class FileDownloader {
    private static final ch.qos.logback.classic.Logger logger =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(FileDownloader.class);

    public static void downloadFile(String fileURL, String saveDir) throws IOException {
        URL url = URI.create(fileURL).toURL();
        logger.setLevel(Level.INFO);
        logger.debug("Downloading file from: {}", url);
        url.getFile();
        InputStream in = new BufferedInputStream(url.openStream());
        FileOutputStream out = new FileOutputStream(saveDir);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(buffer, 0, 1024)) != -1) {
            out.write(buffer, 0, bytesRead);
        }
        in.close();
        out.close();
    }
}
