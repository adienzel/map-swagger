package com.example;

import io.swagger.v3.oas.models.media.Schema;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.example.ProcerssType.processTypeVal;
import static com.example.ProcessXxxOf.processXxxVal;

public class processObject {
    private static final ch.qos.logback.classic.Logger logger =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(processObject.class);

    private processObject() {}

    public static void processObjectVal(Schema<?> s, StringBuilder pStr) {
        var description = s.getDescription();
        if (description != null) {
            pStr.append(", description:").append(description);
        }
        var properties = s.getProperties();
        if (properties != null) {
            pStr.append(", properties {");
            for (Map.Entry<String, Schema> entry : properties.entrySet()) {
                var value = entry.getValue();
                pStr.append(" name: ").append(entry.getKey()).append(" value {");
                var d = value.getDescription();
                if (d != null) {
                    pStr.append(" desc = ").append(d);
                }
                var ref = value.get$ref();
                if (ref != null) {
                    pStr.append(" ref = ").append(ref);
                }
                if (value.getType() != null) {
                    processTypeVal(value, pStr);
                    //Str.append("type = ").append(type);
                }
            }

            pStr.append("}");

        }
        var required = s.getRequired();
        if (required != null) {
            pStr.append(" required: {");
            for (var r : required) {
                pStr.append(required).append(", ");
            }
            pStr.setLength(pStr.length() -2);
            pStr.append("}");
        }
        var anyOf = s.getAnyOf();
        if (anyOf != null) {
            pStr.append(", anyOf ");
            processXxxVal(pStr, anyOf);
        }
        var oneOf = s.getOneOf();
        if (oneOf != null) {
            pStr.append(", oneOf ");
            processXxxVal(pStr, oneOf);
        }
        var allOf = s.getAllOf();
        if (allOf != null) {
            pStr.append(", allOf ");
            processXxxVal(pStr, allOf);
        }
        var discriminator = s.getDiscriminator();
        if (discriminator != null) {

        }
        var nullable = s.getNullable();
        if (nullable != null) {

        }
        var Not = s.getNot();
        if (Not != null) {

        }
        var additionalProperties = s.getAdditionalProperties();
        if (additionalProperties != null) {

        }
        var externalDocs = s.getExternalDocs();
        if (externalDocs != null) {

        }
        var deprecated = s.getDeprecated();
        if (deprecated != null) {

        }

        var minProperties = s.getMinProperties();
        if (minProperties != null) {

        }


    }

}
