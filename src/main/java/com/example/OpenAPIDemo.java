package com.example;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.parser.OpenAPIV3Parser;

import static com.example.ProcessArray.prossesArrayVal;
import static com.example.ProcessBoolean.prossesBooleanVal;
import static com.example.ProcessInteger.processIntegerVal;
import static com.example.ProcessString.processStringVal;
import static com.example.ProcessXxxOf.processXxxVal;
import static com.example.processObject.processObjectVal;


public class OpenAPIDemo {
    private static final ch.qos.logback.classic.Logger logger =
            (ch.qos.logback.classic.Logger)LoggerFactory.getLogger(OpenAPIDemo.class);
    public static void main(String[] args) {
        // Load the OpenAPI file
        logger.setLevel(Level.INFO);

        try {
            String specsDir = "specs";
            String inputFile = "TS29562_Nhss_imsSDM.yaml";

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
                String type = s.getType();
                var description = s.getDescription();
                String ref = s.get$ref();
                var anyOf = s.getAnyOf();
                var oneOf = s.getOneOf();
                var allOf = s.getAllOf();

                if (type != null) {
                    pStr.append(", type:" + type);
                    switch (type) {
                        case "string": {
                            processStringVal(s, pStr);
                            break;
                        }
                        case "integer": {
                            processIntegerVal(s, pStr);
                            break;
                        }
                        case "object": {
                            processObjectVal(s, pStr);
                            break;
                        }
                        case "array": {
                            prossesArrayVal(s, pStr);
                            break;
                        }
                        case "boolean": {
                            prossesBooleanVal(s, pStr);
                            break;
                        }
                        default: {
                            logger.error("Undefined type value: {}", type);
                        }
                    }
                } else if (ref != null) {
                    pStr.append(", ref: ").append(ref);
                    // TODO Handle References
                } else if (anyOf != null) {
                    pStr.append(", anyOf:");
                    processXxxVal(pStr, anyOf, description);
                } else if (oneOf != null) {
                    pStr.append(", oneOf ");
                    processXxxVal(pStr, oneOf, description);
                } else if (allOf != null) {
                    pStr.append(", allOf ");
                    processXxxVal(pStr, allOf, description);
                } else {
                    logger.error("Undefined case");
                    pStr.append(",NO TYPE *************************************");
                }


//                System.out.println("key :" + entry.getKey() + " val = " + entry.getValue());
//                final Map<String, Schema> properties = schema.getProperties();
//                StringBuilder pStr = new StringBuilder();
//                if (properties != null) {
//                    for (final Map.Entry<String, Schema> p : properties.entrySet()) {
//                        pStr.append(" name: ").append(p.getKey()).append(" value = ").append(p.getValue().get$ref());
//                    }
//                }
//                System.out.println("Schema: " + name +
//                        ", Type: " + schema.getType() +
//                        ", Description: " + schema.getDescription() +
//                        ", Props : " + pStr.toString());
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
