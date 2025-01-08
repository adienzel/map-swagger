package com.example;

import io.swagger.v3.oas.models.media.Schema;
import org.slf4j.LoggerFactory;

import java.util.Map;

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
                pStr.append(" name: ").append(entry.getKey()).append(" value { ");
                var d = value.getDescription();
                if (d != null) {
                    pStr.append(" desc = ").append(d);
                }
                var ref = value.get$ref();



            }

            pStr.append("}");

        }
        var anyOf = s.getAnyOf();
        if (anyOf != null) {

        }
        var oneOf = s.getOneOf();
        if (oneOf != null) {

        }
        var allOf = s.getAllOf();
        if (allOf != null) {

        }
        var required = s.getRequired();
        if (required != null) {

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


    }

}
