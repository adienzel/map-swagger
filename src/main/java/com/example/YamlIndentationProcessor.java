package com.example;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class YamlIndentationProcessor {
    private static final ch.qos.logback.classic.Logger logger =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(YamlIndentationProcessor.class);

    private static final String DESCRIPTION = "description:";
    private static final String SEPARATOR = " %% ";
    private static final String COMMENT = "#";
    private static final String ENUM_STR = "enum:";


    public static void main(String[] args) {
        String filePath = "specs/TS29562_Nhss_imsSDM.yaml";
        processYamlFile(filePath);
    }

    private static void processYamlFile(String filePath) {
        List<String> fileBuffer = processEnums(filePath);
        List<String> strings = new ArrayList<>();
        buildList(fileBuffer, strings);
    }

    private static void buildList(List<String> fileBuffer, List<String> strings) {
        int indentLevel;
        int prevIndentationLevel = 0;
        for (String line : fileBuffer) {
            if (!line.isEmpty() && (!COMMENT.equals(line.substring(0, 1)))) {
                indentLevel = calculateIndentationLevel(line);
                line = reformatLine(line);
                if (indentLevel == 0) {
                    strings.clear();
                    strings.add(line);
                }
                if (indentLevel > prevIndentationLevel) {
                    strings.add(line);
                } else if (indentLevel < prevIndentationLevel && (!strings.isEmpty())) {
                    fixVectorIndentation(strings, prevIndentationLevel, indentLevel);
                    strings.add(line);
                } else {
                    removeLastAndAddLine(strings, line);
                }
                prevIndentationLevel = indentLevel;
                printIndentedLine(strings);
            }
        }
    }

    private static void removeLastAndAddLine(List<String> strings, String line) {
        if (!strings.isEmpty()) {
            strings.removeLast();
        }
        strings.add(line);
    }

    private static void fixVectorIndentation(List<String> strings, int prevIndentationLevel, int indentLevel) {
        for (var i = prevIndentationLevel; i >= indentLevel; i--) {
            if (!strings.isEmpty()) {
                strings.removeLast();
            }
        }
    }

    private static String reformatLine(String line) {
        line = line.trim();
        if ("-".equals(line.substring(0, 1))) {
            line = line.substring(2);
        }
        if (":".equals(line.substring(line.length() - 1))) {
            line = line.substring(0, line.length() - 1);
        }
        return line;
    }

    private static int calculateIndentationLevel(String line) {
        int indentLevel = 0;
        for (char c : line.toCharArray()) {
            if (c == ' ') {
                indentLevel++;
            } else if (c == '\t') {
                indentLevel += 4; // Assuming 1 tab = 4 spaces
            } else {
                break;
            }
        }
        return indentLevel / 2; // Assuming 2 spaces per indentation level
    }

    private static void printIndentedLine(List<String> strings) {
        StringBuilder s = new StringBuilder();
        for (String e : strings) {
           s.append(e).append(".");
        }
        if (!s.isEmpty()) {
            s.deleteCharAt(s.length() -1 );
            int index = s.indexOf(": ");
            if (index != -1) {
                s.replace(index, index + ": ".length(), ".");
            }
            logger.info("{}", s);
        }

    }


    private static List<String> processEnums(String filePath) {
        List<String> modifiedLines = new ArrayList<>();
        boolean inEnumBlock = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!(line.isEmpty() || line.trim().startsWith(COMMENT))) {
                    inEnumBlock = isInEnumBlock(line, inEnumBlock, modifiedLines, reader);
                }
            }
        } catch (IOException e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            logger.error("Got Exception {}", sw.toString());
        }

        return modifiedLines;
    }

    private static boolean isInEnumBlock(String line, boolean inEnumBlock, List<String> modifiedLines, BufferedReader reader) throws IOException {
        if (line.trim().equals(ENUM_STR)) {
            inEnumBlock = true;
            modifiedLines.add(line);
            return inEnumBlock;
        }

        if (inEnumBlock) {
            if (line.trim().startsWith("-")) {
                modifiedLines.add("  " + line);
            } else {
                inEnumBlock = false;
                modifiedLines.add(line);
            }
            return inEnumBlock;
        }

        if (line.contains(DESCRIPTION)) {
            buildDescriptor(line, reader, modifiedLines);
        } else {
            modifiedLines.add(line);
        }
        return inEnumBlock;
    }


    private static void buildDescriptor(String line, BufferedReader reader, List<String> modifiedLines) throws IOException {
        int idx = line.indexOf(DESCRIPTION) + DESCRIPTION.length();
        if (line.length() >= idx + 2 && (line.substring(idx, idx + 2).contains(" |") || line.substring(idx, idx + 2).contains(" >"))) {
            int descriptionIndentLevel = getLeadingSpacesCount(line);
            StringBuilder concatenatedDescription = new StringBuilder(line);
            line = handleDescriptorTemplating(reader, modifiedLines, descriptionIndentLevel, concatenatedDescription);
            // Add the concatenated description if end of file is reached
            if (line == null) {
                modifiedLines.add(concatenatedDescription.toString());
            }
        } else {
            modifiedLines.add(line);
        }
    }

    private static String handleDescriptorTemplating(BufferedReader reader, List<String> modifiedLines, int descriptionIndentLevel, StringBuilder concatenatedDescription) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().startsWith(COMMENT)) {
                int currentIndentLevel = getLeadingSpacesCount(line);
                if (currentIndentLevel > descriptionIndentLevel) {
                    concatenatedDescription.append(SEPARATOR).append(line.trim());
                } else {
                    modifiedLines.add(concatenatedDescription.toString()); // the las line in description
                    modifiedLines.add(line); // this is the next line in the file we can't ignore it
                    break;
                }
            }
        }
        return line;
    }

    private static int getLeadingSpacesCount(String line) {
        int count = 0;
        for (char c : line.toCharArray()) {
            if (c == ' ') {
                count++;
            } else {
                break;
            }
        }
        return count;
    }
}