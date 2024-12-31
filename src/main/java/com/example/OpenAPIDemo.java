package com.example;


// import io.swagger.parser.v3.OpenAPIV3Parser;
import java.util.Map;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.parser.OpenAPIV3Parser;

//package com.example.map-swagger;

public class OpenAPIDemo {

    public static void main(String[] args) {
        // Load the OpenAPI file
        OpenAPIV3Parser parser = new OpenAPIV3Parser();
        OpenAPI openAPI = parser.read("TS29562_Nhss_imsSDM.yaml");

        // Print all definitions (schemas)
        var schemas = openAPI.getComponents().getSchemas();
        for (String name : schemas.keySet()) {
            var schema = schemas.get(name);
            Map<String, Schema> properties = schema.getProperties();
            
            String p_str = "";
            if (properties != null) {
                for (Map.Entry<String, Schema> p : properties.entrySet()) {
                    
                     //Schema sc = (Schema)properties.get((String)p);
                     
                     p_str = p_str + " name: " + p.getKey() + " " + p.getValue().get$ref();
                     
                }
            }
            System.out.println("Schema: " + name + 
                               " Type: " + schema.getType() + 
                               " Description: " + schema.getDescription() + 
                               " Props : " + p_str);
        }

        // Check each operation's parameters
        Map<String, PathItem> paths = openAPI.getPaths();
        for (String path : paths.keySet()) {
            PathItem pathItem = paths.get(path);
            for (PathItem.HttpMethod method : pathItem.readOperationsMap().keySet()) {
                Operation operation = pathItem.readOperationsMap().get(method);
                System.out.println("Operation: " + method + " " + path);
                if (operation.getParameters() != null) {
                    for (Parameter param : operation.getParameters()) {
                        System.out.println("Parameter: " + param.getName() + ", In: " + param.getIn() + ", Type: " + param.getSchema().getType());
                    }
                }
            }
        }
    }
}
