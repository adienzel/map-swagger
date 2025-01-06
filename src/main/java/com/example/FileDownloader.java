package com.example;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class FileDownloader {
    public static void main(String[] args) {
        String fileURL = "https://example.com/file.zip"; // Replace with your URL
        String saveDir = "path/to/your/save/directory/file.zip"; // Replace with your save directory
        try {
            downloadFile(fileURL, saveDir);
            System.out.println("File downloaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to download file.");
        }
    }

    public static void downloadFile(String fileURL, String saveDir) throws IOException {
        URL url = new URL(fileURL);
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


