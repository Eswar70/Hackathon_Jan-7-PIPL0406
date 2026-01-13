package com.hackathon.utils;

import org.json.JSONArray;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonUtil {
    public static JSONArray readArray(String fileName) {
        try {
            String path = "src/test/resources/" + fileName;
            String content = Files.readString(Paths.get(path));
            return new JSONArray(content);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON array from " + fileName, e);
        }
    }
}
