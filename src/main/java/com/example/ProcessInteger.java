package com.example;

import io.swagger.v3.oas.models.media.Schema;

public class ProcessInteger {
    private  ProcessInteger() {}
    public static void processIntegerVal(Schema<?> s, StringBuilder pStr) {
        var description = s.getDescription();
        var format = s.getFormat();
        var pattern = s.getPattern();
        var nullable = s.getNullable();
        var enumValues = s.getEnum();
        var min = s.getMinimum();
        var max = s.getMaximum();
        var defaultVal = s.getDefault();
        if (description != null) {
            pStr.append(", description:").append(description);
        }
        if (format != null) {
            pStr.append(", format:").append(format);
        }
        if (pattern != null) {
            pStr.append(", pattern:").append(pattern);
        }
        if (nullable != null) {
            pStr.append(", nullable:").append(nullable.toString());
        }
        if (enumValues != null) {
            pStr.append(", ENUM: [");
            for (Object e : enumValues) {
                pStr.append(e.toString()).append(", ");
            }
            pStr.setLength(pStr.length() - 2);
            pStr.append("]");
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
        if (max != null) {
            pStr.append(", maximum: ").append(max.toString());
        }
        if (min != null) {
            pStr.append(", minimum: ").append(min.toString());
        }
        if (defaultVal instanceof Integer) {
            pStr.append("Default: ").append(defaultVal.toString());
        }
    }
}
