package com.example;

import io.swagger.v3.oas.models.media.Schema;
import org.slf4j.LoggerFactory;

public class ProcessBoolean {
    private static final ch.qos.logback.classic.Logger logger =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ProcessBoolean.class);

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
