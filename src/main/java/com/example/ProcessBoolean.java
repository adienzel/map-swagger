package com.example;

import io.swagger.v3.oas.models.media.Schema;

public class ProcessBoolean {

    private ProcessBoolean(){}

    public static void prossesBooleanVal(Schema<?> s, StringBuilder pStr) {
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
        var defaultVal = s.getDefault();
        if (defaultVal instanceof String) {
            pStr.append("Default: ").append(defaultVal.toString());
        }

    }
}
