package com.example;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class YamlIndentationProcessor {
    public static void main(String[] args) {
        String filePath = "specs/TS29562_Nhss_imsSDM.yaml";
        processYamlFile(filePath);
    }

    private static void processYamlFile(String filePath) {
        List<String> fileBuffer = processEnums(filePath);
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            String line;
        Vector<String> indentVector = new Vector<>();
        int prevIndentationLevel = 0;
        int indentLevel = 0;
        for (String line : fileBuffer) {
            if (!line.isEmpty() && (!"#".equals(line.substring(0, 1)))) {
                indentLevel = calculateIndentationLevel(line);
                line = reformtLine(line);
                if (indentLevel == 0) {
                    indentVector.clear();
                    indentVector.add(line);
                    //prevIndentationLevel = 0;
                    //printIndentedLine(indentVector);
                    //continue;
                }
                if (indentLevel > prevIndentationLevel) {
                    indentVector.add(line);
                } else if (indentLevel < prevIndentationLevel && (!indentVector.isEmpty())) {
                    for (var i = prevIndentationLevel; i >= indentLevel; i--) {
                        if (!indentVector.isEmpty()) {
                            indentVector.removeLast();
                        }
                    }
                    indentVector.add(line);
                } else {
                    if (!indentVector.isEmpty()) {
                        indentVector.removeLast();
                    }
                    indentVector.add(line);
                }
                prevIndentationLevel = indentLevel;
                printIndentedLine(indentVector);
            }

        }
    }

    private static String reformtLine(String line) {
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

    private static void printIndentedLine(Vector<String> indentVector) {
        StringBuilder s = new StringBuilder();
        for (String e : indentVector) {
           s.append(e).append(".");
        }
        if (!s.isEmpty()) {
            s.deleteCharAt(s.length() -1 );
            int index = s.indexOf(": ");
            if (index != -1) {
                s.replace(index, index + ": ".length(), ".");
            }
            System.out.println(s);
        }

    }

    private static List<String> processEnums(String filePath) {
        List<String> modifiedLines = new ArrayList<>();
        boolean inEnumBlock = false;
        int descriptionIndentLevel = -1;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("#") || line.isEmpty()) {
                    //modifiedLines.add(line);
                    continue;
                }

                if (line.trim().equals("enum:")) {
                    inEnumBlock = true;
                    modifiedLines.add(line);
                    continue;
                }

                if (inEnumBlock) {
                    if (line.trim().startsWith("-")) {
                        modifiedLines.add("  " + line);
                    } else {
                        inEnumBlock = false;
                        modifiedLines.add(line);
                    }
                    continue;
                }

                if (line.contains("description:")) {

                    int idx = line.indexOf("description:") + "description:".length();
                    if (line.length() >= idx + 2 && (line.substring(idx, idx + 2).contains(" |") || line.substring(idx, idx + 2).contains(" >"))) {
                        descriptionIndentLevel = getLeadingSpacesCount(line);

                        StringBuilder concatenatedDescription = new StringBuilder(line);
                        while ((line = reader.readLine()) != null) {
                            if (line.trim().startsWith("#")) {
                                modifiedLines.add(line);
                                continue;
                            }

                            int currentIndentLevel = getLeadingSpacesCount(line);
                            if (currentIndentLevel > descriptionIndentLevel) {
                                concatenatedDescription.append(" %% ").append(line.trim());
                            } else {
                                modifiedLines.add(concatenatedDescription.toString());
                                modifiedLines.add(line);
                                //descriptionIndentLevel = -1;
                                break;
                            }
                        }
                        // Add the concatenated description if end of file is reached
                        if (line == null) {
                            modifiedLines.add(concatenatedDescription.toString());
                        }
                    } else {
                        if (!line.isEmpty()) {
                            modifiedLines.add(line);
                        }
                    }
                } else {
                    if (!line.isEmpty()) {
                        modifiedLines.add(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return modifiedLines;
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