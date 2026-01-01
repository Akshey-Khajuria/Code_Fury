package com.voltx.codefury.dto;

public class ExecutionResult {

    private final String status;
    private final String output;
    private final String error;

    private ExecutionResult(String status, String output, String error) {
        this.status = status;
        this.output = output;
        this.error = error;
    }

    public static ExecutionResult success(String output) {
        return new ExecutionResult("OK", output, null);
    }

    public static ExecutionResult compileError(String error) {
        return new ExecutionResult("CE", null, error);
    }

    public static ExecutionResult runtimeError(String error) {
        return new ExecutionResult("RE", null, error);
    }

    public static ExecutionResult timeout() {
        return new ExecutionResult("TLE", null, "Time limit exceeded");
    }

    public static ExecutionResult systemError(String error) {
        return new ExecutionResult("SE", null, error);
    }

    public String getStatus() { return status; }
    public String getOutput() { return output; }
    public String getError() { return error; }
}
