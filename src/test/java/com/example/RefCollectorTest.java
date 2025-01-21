package com.example;

import static com.example.RefCollector.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;

public class RefCollectorTest {
    private static final ch.qos.logback.classic.Logger logger =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(RefCollectorTest.class);
    @Test
    public void printOutTest() throws IOException {
        String specsDir = "specs";
        String inputFile = "TS29562_Nhss_imsSDM.yaml";
        String fileName = Paths.get("").
                toAbsolutePath().
                resolve(specsDir).
                resolve(inputFile).
                toUri().
                toString().
                substring(7);

        logger.info("File name = {}", fileName);

        var refSet = parseYamlFile(fileName);
        assertFalse(refSet.isEmpty());
        for (var r : refSet) {
            logger.info(r.toString());
        }
    }

    @Test
    public void printYAMLtest() throws IOException {
        String specsDir = "specs";
        String inputFile = "TS29562_Nhss_imsSDM.yaml";
        String fileName = Paths.get("").
                toAbsolutePath().
                resolve(specsDir).
                resolve(inputFile).
                toUri().
                toString().
                substring(7);

        logger.info("File name = {}", fileName);
        printTree(fileName);
    }
}
