package com.example;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class YamlIndentationProcessor {
    public static void main(String[] args) {
        String filePath = "specs/TS29562_Nhss_imsSDM.yaml";
        processYamlFile(filePath);
    }

    private static void processYamlFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String prevLine = "";
            Vector<String> indentVector = new Vector<String>();
            int prevIndentationLevel = 0;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    if (!"#".equals(line.substring(0,1))) {
                        int indentLevel = calculateIndentationLevel(line);
                        line = line.trim();
                        if ("-".equals(line.substring(0,1))) {
                            line = line.substring(2);
                        }
                        if (":".equals(line.substring(line.length() -1))) {
                            line = line.substring(0,line.length() - 1);
                        }
                        if (indentLevel == 0) {
                            indentVector.clear();
                            indentVector.add(line);
                            prevIndentationLevel = 0;
                            printIndentedLine(line, indentVector);
                            continue;
                        }
                        if (indentLevel > prevIndentationLevel) {
                            indentVector.add(line);
                        } else if (indentLevel < prevIndentationLevel && (!indentVector.isEmpty()) ) {
                                indentVector.removeLast();
                        } else {
                            indentVector.removeLast();
                            indentVector.add(line);
                        }

                        prevIndentationLevel = indentLevel;
                        printIndentedLine(line, indentVector);
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private static void printIndentedLine(String line, Vector<String> indentVector) {
        StringBuilder s = new StringBuilder();
        for (String e : indentVector) {
           s.append(e).append(".");
        }
        s.deleteCharAt(s.length() -1 );
//        System.out.println(s + line);
        System.out.println(s);
    }
}
