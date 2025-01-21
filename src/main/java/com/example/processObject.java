package com.example;

import io.swagger.v3.oas.models.media.Schema;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;

import java.util.Map;

import static com.example.ProcerssType.processTypeVal;
import static com.example.ProcessXxxOf.processXxxVal;

public class processObject {
    private static final ch.qos.logback.classic.Logger logger =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(processObject.class);

    private processObject() {}

    public static void processObjectVal(Schema<?> s, StringBuilder pStr) {
        logger.setLevel(Level.INFO);
        logger.debug("Processing object {}");
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
            processXxxVal(pStr, null, anyOf);
        }
        var oneOf = s.getOneOf();
        if (oneOf != null) {
            pStr.append(", oneOf ");
            processXxxVal(pStr, null, oneOf);
        }
        var allOf = s.getAllOf();
        if (allOf != null) {
            pStr.append(", allOf ");
            processXxxVal(pStr, null, allOf);
        }
        var discriminator = s.getDiscriminator();
        if (discriminator != null) {
            logger.debug("\"Discriminator\" parsing not supported yet {}", discriminator);
        }
        var nullable = s.getNullable();
        if (nullable != null) {
            logger.debug("\"Nullable\" parsing not supported yet {}", nullable);
        }
        var Not = s.getNot();
        if (Not != null) {
            logger.debug("\"Not\" parsing not supported yet {}", Not);
        }
        var additionalProperties = s.getAdditionalProperties();
        if (additionalProperties != null) {
            logger.debug("\"AdditionalProperties\" parsing not supported yet {}", additionalProperties);
        }
        var externalDocs = s.getExternalDocs();
        if (externalDocs != null) {
            logger.debug("\"ExternalDocs\" parsing not supported yet {}", externalDocs);
        }
        var deprecated = s.getDeprecated();
        if (deprecated != null) {
            logger.debug("\"Deprecated\" parsing not supported yet {}", deprecated);
        }

        var minProperties = s.getMinProperties();
        if (minProperties != null) {
            logger.debug("\"MinProperties\" parsing not supported yet {}", minProperties);
        }


    }

}
