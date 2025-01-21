package com.example;

import static com.example.FileScrapper.getFileList;
import static org.junit.Assert.*;

import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.regex.Pattern;

public class FileScrapperTest {

    @Test
    public void getList() {
        Pattern pattern = Pattern.compile("TS\\d[A-Za-z0-9_/-]+\\.yaml");
        String url = "https://github.com/adienzel/5GC_APIs/blob/Rel-18/";
        try {
            HashSet<String> fileNames = getFileList(url, pattern);
            assertFalse(fileNames.isEmpty());
            for (String s : fileNames) {
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void failedGetList() {
        try {
            Pattern pattern = Pattern.compile("TS\\d[A-Za-z0-9_/-]+\\.yaml");

            String url = "https://github.com/adienzel/5GC_APIs/blob/Rel/";
            HashSet<String> fileNames = new HashSet<String>();
            try {
                fileNames = getFileList(url, pattern);
            } catch (IOException e) {
                //e.printStackTrace();
            }
            assertTrue(fileNames.isEmpty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
