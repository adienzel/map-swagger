package com.example;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.parser.OpenAPIV3Parser;

import static com.example.ProcessInteger.processIntegerVal;
import static com.example.ProcessString.processStringVal;


public class OpenAPIDemo {

    public static void main(String[] args) {
        // Load the OpenAPI file
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

            System.out.println("File name = " + fileName);
            OpenAPI openAPI = parser.read(fileName);


            // Print all definitions (schemas)
            var schemas = openAPI.getComponents().getSchemas();
            StringBuilder pStr = new StringBuilder();
            for (var entry : schemas.entrySet()) {
                String name = entry.getKey();
                pStr.append("name:" + name);
                Schema<?> s = entry.getValue();
                String type = s.getType();
                String ref = s.get$ref();
                var anyOf = s.getAnyOf();
                var oneOf = s.getOneOf();
                var allOf = s.getAllOf();

                if (type != null) {
                    pStr.append(", type:" + type);
                    //TODO handle types (object, array, string, integer, boolean
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
                            break;
                        }
                        case "array": {
                            var description = s.getDescription();
                            if (description != null) {
                                pStr.append(", description:" + description);
                            }
                            var examples = s.getExamples();
                            if (examples != null) {
                                pStr.append("example: [");
                                for (Object o : examples) {
                                    pStr.append(o.toString() + ", ");
                                }
                                pStr.setLength(pStr.length() - 2);
                                pStr.append("]");
                            }

                            var minItems = s.getMinItems();
                            if (minItems != null) {
                                pStr.append(", minItems:" + minItems.toString());
                            }

                            var maxItems = s.getMaxItems();
                            if (maxItems != null) {
                                pStr.append(", maxItems:" + maxItems.toString());
                            }

                            var nullable = s.getNullable();
                            if (nullable != null) {
                                pStr.append(", nullable:" + nullable.toString());
                            }

                            var uniqueItems = s.getUniqueItems();
                            if (uniqueItems != null) {
                                pStr.append(", uniqueItems:" + uniqueItems.toString());
                            }
                            var defaultVal = s.getDefault();
                            if (defaultVal instanceof String) {
                                pStr.append("Default: " + defaultVal.toString());
                            }

                            var items = s.getItems(); // loop on items
                            if (items != null) {
                                pStr.append(", items: {");
                                var desc = items.getDescription();
                                if (desc != null) {
                                    pStr.append("description:" + desc + ", ");
                                }
                                var arrayType = items.getType();
                                if (arrayType != null) {
                                    pStr.append("array type:" + arrayType + ", ");
                                    switch (type) {
                                        case "string": {
                                            processStringVal(s, pStr);
                                            break;
                                        }
                                        case "integer": {
                                            processIntegerVal(s, pStr);
                                            break;
                                        }
                                        case "array": {
                                            pStr.append("============");
                                            break;
                                        }
                                        case "object": {
                                            pStr.append("-------------");
                                            break;
                                        }
                                        default: {
                                            pStr.append("*************");

                                        }
                                    }
                                } else {
                                    var reference = items.get$ref();
                                    if (reference != null) {
                                        pStr.append("reference:" + reference + ", ");
                                    }
                                }




                                pStr.setLength(pStr.length() - 2);
                                pStr.append("}");
                            }

                            break;
                        }
                        case "anyOf": {
                            break;
                        }
                        case "oneOf": {
                            break;
                        }
                        case "allOf": {
                            break;
                        }

                    }
                } else if (ref != null) {
                    pStr.append(", ref: " + ref);
                    // TODO Handle References
                } else if (!anyOf.isEmpty()) {
                    pStr.append(", anyOf ");
                    //TODO handle anyOf
                } else if (!oneOf.isEmpty()) {
                    pStr.append(", oneOf ");
                    //TODO handle oneOf
                } else if (!allOf.isEmpty()) {
                    pStr.append(", allOf ");
                    //TODO handle allOf
                } else {
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
                System.out.println(pStr.toString());
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
            System.out.println("got exception: " + e.toString());
        }
    }
}
