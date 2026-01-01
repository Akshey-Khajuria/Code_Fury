package com.voltx.codefury.service.impl;

import com.voltx.codefury.dto.ExecutionResult;
import com.voltx.codefury.service.AbstractDockerExecutor;
import com.voltx.codefury.service.CodeExecutor;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class PythonExecutor extends AbstractDockerExecutor
        implements CodeExecutor{

    @Override
    public ExecutionResult execute(String code, String input) {
        return runInDocker(
            "python:3.11-slim",
            "",
            "timeout 3 python solution.py < input.txt > out.txt 2> re.txt",
            Map.of(
                "solution.py", code,
                "input.txt", input
            )
        );
    }
    
}
