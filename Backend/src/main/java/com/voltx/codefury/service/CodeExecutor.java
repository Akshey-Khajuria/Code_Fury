package com.voltx.codefury.service;

import com.voltx.codefury.dto.ExecutionResult;

public interface CodeExecutor {
    ExecutionResult execute(String code, String input);
}