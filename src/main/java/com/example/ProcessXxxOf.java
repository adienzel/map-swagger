package com.example;

import io.swagger.v3.oas.models.media.Schema;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.example.ProcerssType.processTypeVal;

public class ProcessXxxOf {
    private static final ch.qos.logback.classic.Logger logger =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ProcessXxxOf.class);

    private ProcessXxxOf() {}

    public static void processXxxVal(StringBuilder pStr, String description, List<Schema> any) {
        if (description != null) {
            pStr.append("{ desc: ").append(description);
        }
        for (Schema<?> sc : any) {
            pStr.append(" {");
            var desc = sc.getDescription();
            if (desc != null) {
                pStr.append("desc: ").append(desc);
            }
            var t = sc.getType();
            var r = sc.get$ref();
            var required = sc.getRequired();
            //each schema may have eiter type or reference
            var des = sc.getDescription();
            if (t != null) {
                processTypeVal(sc, pStr);

//                pStr.append("type: ").append(t);
//                if (des != null) {
//                    pStr.append(", desc:").append(des);
//                }
//                var e = sc.getEnum();
//                if (e != null) {
//                    pStr.append(" ENUM [");
//                    for (Object o : e) {
//                        pStr.append(o.toString()).append(", ");
//                    }
//                    pStr.setLength(pStr.length() -2);
//                    pStr.append("]");
//                }
            } else if (r != null) {
                pStr.append(" reference: ").append(r);
            }

            if (required != null) {
                pStr.append("Required: [");
                for (Object o : required) {
                    pStr.append(o.toString()).append(", ");
                }
                pStr.setLength(pStr.length() -2);
                pStr.append("]");
            }
            pStr.append("},");
        }
        pStr.setLength(pStr.length() -2);
        pStr.append("}}");
    }
}
