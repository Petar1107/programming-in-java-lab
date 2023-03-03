package org.lecture3.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.lecture3.dto.JsonConfig;

public class JsonFileReader {
    public static JsonConfig parseJsonConfig(String filePath) throws Exception {
        try {
            var inJson = JsonConfig.class.getResourceAsStream(filePath);
            return new ObjectMapper().readValue(inJson, JsonConfig.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Can not find file", ex);
        }
    }
}
