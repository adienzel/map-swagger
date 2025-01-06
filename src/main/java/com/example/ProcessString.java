package com.example;

import io.swagger.v3.oas.models.media.Schema;

public class ProcessString {

    public static void processStringVal(Schema s, StringBuilder pStr) {
        var description = s.getDescription();
        var format = s.getFormat();
        var pattern = s.getPattern();
        var nullable = s.getNullable();
        var enumValues = s.getEnum();
        var minLength = s.getMinLength();
        var maxLength = s.getMaxLength();
        var defaultVal = s.getDefault();
        if (description != null) {
            pStr.append(", description:" + description);
        }
        if (format != null) {
            pStr.append(", format:" + format);
        }
        if (pattern != null) {
            pStr.append(", pattern:" + pattern);
        }
        if (nullable != null) {
            pStr.append(", nullable:" + nullable.toString());
        }
        if (enumValues != null) {
            pStr.append(", ENUM: [");
            for (Object e : enumValues) {
                pStr.append(e.toString() + ", ");
            }
            pStr.setLength(pStr.length() - 2);
            pStr.append("]");
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
        if (maxLength != null) {
            pStr.append(", maxLength: " + maxLength.toString());
        }
        if (minLength != null) {
            pStr.append(", minLength: " + minLength.toString());
        }
        if (defaultVal instanceof String) {
            pStr.append("Default: " + defaultVal.toString());
        }

    }
}
