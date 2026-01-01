package com.voltx.codefury.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class TestCaseSerializer {

    private final ObjectMapper mapper = new ObjectMapper();

    public String serializeInput(Object input) {
        try {
            // pretty=false, compact JSON
            return mapper.writeValueAsString(input);
        } catch (Exception e) {
            throw new RuntimeException("Invalid test case input");
        }
    }

    public String serializeOutput(Object output) {
        try {
            return mapper.writeValueAsString(output);
        } catch (Exception e) {
            throw new RuntimeException("Invalid test case output");
        }
    }
}
