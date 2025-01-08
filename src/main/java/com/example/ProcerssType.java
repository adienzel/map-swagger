package com.example;

import io.swagger.v3.oas.models.media.Schema;
import org.slf4j.LoggerFactory;

import static com.example.ProcessArray.prossesArrayVal;
import static com.example.ProcessBoolean.prossesBooleanVal;
import static com.example.ProcessInteger.processIntegerVal;
import static com.example.ProcessString.processStringVal;
import static com.example.processObject.processObjectVal;

public class ProcerssType {
    private static final ch.qos.logback.classic.Logger logger =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ProcerssType.class);

    private ProcerssType() {}

    public static void processTypeVal(Schema<?> s, StringBuilder pStr) {
        var type = s.getType();
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
        }
    }
}
