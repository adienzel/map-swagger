package com.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

public class FileScrapper {
    private static final ch.qos.logback.classic.Logger logger =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(FileScrapper.class);

    public static HashSet<String> getFileList(String url, Pattern pattern) throws IOException {
        HashSet<String> fileNames = new HashSet<String>();
        Document doc = Jsoup.connect(url).get();


        Elements elements = doc.select("a[href]");

        for (Element element : elements) {
            String fileName = element.text();
            if (pattern.matcher(fileName).matches()) {
                fileNames.add(fileName);
            }
        }

        return fileNames;
    }
}
