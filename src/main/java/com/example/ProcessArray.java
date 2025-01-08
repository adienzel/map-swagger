package com.example;

import static com.example.ProcessInteger.processIntegerVal;
import static com.example.ProcessString.processStringVal;
import io.swagger.v3.oas.models.media.Schema;


public class ProcessArray {

    private ProcessArray(){}

    public static void prossesArrayVal(Schema<?> s, StringBuilder pStr) {
        var description = s.getDescription();
        if (description != null) {
            pStr.append(", description:").append(description);
        }
        var examples = s.getExamples();
        if (examples != null) {
            pStr.append("example: [");
            for (Object o : examples) {
                pStr.append(o.toString()).append(", ");
            }
            pStr.setLength(pStr.length() - 2);
            pStr.append("]");
        }

        var minItems = s.getMinItems();
        if (minItems != null) {
            pStr.append(", minItems:").append(minItems.toString());
        }

        var maxItems = s.getMaxItems();
        if (maxItems != null) {
            pStr.append(", maxItems:").append(maxItems.toString());
        }

        var nullable = s.getNullable();
        if (nullable != null) {
            pStr.append(", nullable:").append(nullable.toString());
        }

        var uniqueItems = s.getUniqueItems();
        if (uniqueItems != null) {
            pStr.append(", uniqueItems:").append(uniqueItems.toString());
        }
        var defaultVal = s.getDefault();
        if (defaultVal instanceof String) {
            pStr.append("Default: ").append(defaultVal.toString());
        }

        var items = s.getItems(); // loop on items
        if (items != null) {
            pStr.append(", items: {");
            var desc = items.getDescription();
            if (desc != null) {
                pStr.append("description:").append(desc).append(", ");
            }
            var arrayType = items.getType();
            if (arrayType != null) {
                pStr.append("array type:").append(arrayType).append(", ");
                switch (arrayType) {
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
                    pStr.append("reference:").append(reference).append(", ");
                }
            }

            pStr.setLength(pStr.length() - 2);
            pStr.append("}");
        }
    }
}