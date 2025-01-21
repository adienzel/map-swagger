package com.example;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.parser.OpenAPIV3Parser;

import static com.example.ProcerssType.processTypeVal;
import static com.example.ProcessXxxOf.processXxxVal;


public class OpenAPIDemo {
    private static final ch.qos.logback.classic.Logger logger =
            (ch.qos.logback.classic.Logger)LoggerFactory.getLogger(OpenAPIDemo.class);
    public static void main(String[] args) {
        // Load the OpenAPI file
        logger.setLevel(Level.INFO);

        try {
            String specsDir = "specs";
            String inputFile = "TS29562_Nhss_imsSDM.yaml";
            //String inputFile = "TS28312_IntentNrm.yaml";

            OpenAPIV3Parser parser = new OpenAPIV3Parser();
            String fileName = Paths.get("").
                    toAbsolutePath().
                    resolve(specsDir).
                    resolve(inputFile).
                    toUri().
                    toString().
                    substring(7);

            logger.info("File name = {}", fileName);
            logger.setLevel(Level.INFO);
            OpenAPI openAPI = parser.read(fileName);


            // Print all definitions (schemas)
            var schemas = openAPI.getComponents().getSchemas();
            StringBuilder pStr = new StringBuilder();
            for (var entry : schemas.entrySet()) {
                String name = entry.getKey();
                pStr.append("name:").append(name);
                Schema<?> s = entry.getValue();
                var description = s.getDescription();
                String ref = s.get$ref();
                var anyOf = s.getAnyOf();
                var oneOf = s.getOneOf();
                var allOf = s.getAllOf();
                if (s.getType() != null) {
                    processTypeVal(s, pStr);
                }
                else if (ref != null) {
                    pStr.append(", ref: ").append(ref);
                    // TODO Handle References
                } else if (anyOf != null) {
                    pStr.append(", anyOf:");
                    processXxxVal(pStr, description, anyOf);
                } else if (oneOf != null) {
                    pStr.append(", oneOf ");
                    processXxxVal(pStr, description, oneOf);
                } else if (allOf != null) {
                    pStr.append(", allOf ");
                    processXxxVal(pStr, description, allOf);
                } else {
                    logger.error("Undefined case");
                    pStr.append(",NO TYPE *************************************");
                }
                logger.info("{}", pStr);
                pStr.delete(0, pStr.length()); // empty buffer
            }

            // Check each operation's parameters
//            Map<String, PathItem> paths = openAPI.getPaths();
//            for (String path : paths.keySet()) {
//                PathItem pathItem = paths.get(path);
//                for (PathItem.HttpMethod method : pathItem.readOperationsMap().keySet()) {
//                    Operation operation = pathItem.readOperationsMap().get(method);
//                    System.out.println("Operation: " + method + " " + path);
//                    if (operation.getParameters() != null) {
//                        for (Parameter param : operation.getParameters()) {
//                            Schema s = param.getSchema();
//                            if (s != null) {
//                                if (s.getType() != null) {
//                                    System.out.println("Parameter: " + param.getName() + ", In: " + param.getIn() + ", Type: " + s.getType());
//                                } else {
//                                    System.out.println("Parameter: " + param.getName() + ", In: " + param.getIn() + ", Type: " + s.get$ref());
//                                }
//                            }
//                        }
//                    }
//                }
//            }
        } catch (Exception e) {
            logger.error("{}", Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining("\n")));
        }
    }
}
