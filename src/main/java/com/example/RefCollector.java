package com.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import io.swagger.v3.parser.util.RefUtils;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class RefCollector {

    private RefCollector() {}
    private static final ch.qos.logback.classic.Logger logger =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(RefCollector.class);

    private static String file;
    public static Set<Reference> parseYamlFile(String fileName) throws IOException {
        YAMLMapper yamlMapper = new YAMLMapper(new YAMLFactory());
        file = fileName;
        JsonNode rootNode = yamlMapper.readTree(new File(fileName));

        return findAndProcessRefs(rootNode, null);
    }

    public static void printTree(String fileName) throws IOException {
        YAMLMapper yamlMapper = new YAMLMapper(new YAMLFactory());
        file = fileName;
        printYamlTree(yamlMapper.readTree(new File(fileName)), "");
    }

    private static Set<Reference> findAndProcessRefs(JsonNode node, Set<Reference> refSet) {

        Set<Reference> referenceSet;
        referenceSet = Objects.requireNonNullElseGet(refSet, () -> new TreeSet<>(new ReferenceComparator()));

        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();

            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                if (field.getKey().equals("$ref")) {
                    logger.info("key: {} val = {}", field.getKey(), field.getValue());
                    String refValue = field.getValue().asText();
                    referenceSet.add(processRef(refValue));
                } else {
                    findAndProcessRefs(field.getValue(), referenceSet);
                }
            }
        } else if (node.isArray()) {
            for (JsonNode element : node) {
                findAndProcessRefs(element, referenceSet);
            }
        }
        return referenceSet;
    }

    private static Reference processRef(String refValue) {
        RefUtils.computeRefFormat(refValue);
        String schemaName;
        Reference ref;

        logger.info("$ref = {}", refValue);

        if (RefUtils.isAnExternalRefFormat(RefUtils.computeRefFormat(refValue))) {

            String[] parts = refValue.split("#", 2);
            String domainAndPath = parts[0];
            String localPart = "#" + (parts.length > 1 ? parts[1] : "");

            final String[] schemaNames = localPart.split("/");
            schemaName = schemaNames[schemaNames.length - 1];
            ref = new Reference(false, file, refValue, domainAndPath, localPart, schemaName);
        } else {
            final String[] schemaNames = refValue.split("/");
            schemaName = schemaNames[schemaNames.length - 1];
            ref = new Reference(true, file, refValue, null, refValue, schemaName);
        }
        return ref;
    }

    // Recursive method to print YAML structure
    private static void printYamlTree(JsonNode node, String indent) {
        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            String localIndent = indent;
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                localIndent += field.getKey();
                if (field.getValue().isObject()) {
                    System.out.println(localIndent);
                    printYamlTree(field.getValue(), localIndent + ".");
                }  else if (field.getValue().isArray()) {
                    System.out.println(localIndent);
                    printYamlTree(field.getValue(), localIndent + ".");
                } else {
                    localIndent += "=";
                    System.out.println(localIndent + field.getValue().asText());
                }
                localIndent = indent;
            }
        } else if (node.isArray()) {
            for (JsonNode arrayElement : node) {
                printYamlTree(arrayElement, indent);
            }
        } else {
            System.out.println(indent + node.asText());
        }
    }

    private static void parseYamlMap(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey());
            Object value = entry.getValue();
            if (value instanceof Map) {
                parseYamlMap((Map<String, Object>) value);
            } else if (value instanceof List) {
                parseYamlList((List<Object>) value);
            } else {
                System.out.println("Value: " + value);
            }
        }
    }

    private static void parseYamlList(List<Object> list) {
        for (Object item : list) {
            if (item instanceof Map) {
                parseYamlMap((Map<String, Object>) item);
            } else {
                System.out.println("Item: " + item);
            }
        }
    }





}





