package vpiralkov.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * JsonReader util reads the JSON file and returns the JSON string.
 **/

public class JsonReader {

    public String getSubscribeMessage(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new File(filePath));
        String subscribeMessage = jsonNode.toString();
        return subscribeMessage;
    }
}
