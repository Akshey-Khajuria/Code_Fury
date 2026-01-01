package com.voltx.codefury.entity;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;

public class TestCase {
    private Object input;
    private Object output;
    private String explanation;

    public Object getInput() {
        return input;
    }
    public void setInput(Object input) {
        this.input = input;
    }
    public Object getOutput() {
        return output;
    }
    public void setOutput(Object output) {
        this.output = output;
    }
    public String getExplanation() {
        return explanation;
    }
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
