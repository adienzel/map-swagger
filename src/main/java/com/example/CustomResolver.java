package com.example;

import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.parser.ResolverCache;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import io.swagger.v3.parser.util.RefUtils;
import io.swagger.v3.oas.models.OpenAPI;

/**
 * CustomResolver use to add prefix to the reference url used in openAPI
 * to read references from their repository when using the common way to
 * point to other YAML files within the YAML specifications in a format like the following:
 * $ref: 'TS29571_CommonData.yaml#/components/responses/308' whe it is a location in other file
 *
 */
public class CustomResolver extends ResolverCache {
    private final String globalPrefix;

    public CustomResolver(OpenAPI openAPI, String globalPrefix) {
        super(openAPI, null, null);
        this.globalPrefix = globalPrefix;
    }

//    @Override
//    protected void updateLocalRefs(String file, Schema schema) {
//        super.updateLocalRefs(globalPrefix + file, schema);
//    }

//    @Override
//    public SwaggerParseResult readLocation(String location, OpenAPI openAPI, String parentFile) {
//        String prefixedLocation = globalPrefix + location;
//        return super.readLocation(prefixedLocation, openAPI, parentFile);
//    }
}
