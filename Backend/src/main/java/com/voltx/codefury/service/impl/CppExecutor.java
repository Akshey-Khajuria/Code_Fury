package com.voltx.codefury.service.impl;

import com.voltx.codefury.dto.ExecutionResult;
import com.voltx.codefury.service.AbstractDockerExecutor;
import com.voltx.codefury.service.CodeExecutor;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class CppExecutor extends AbstractDockerExecutor
        implements CodeExecutor {

    @Override
    public ExecutionResult execute(String code, String input) {

        return runInDocker(
            "gcc:13",
            "g++ solution.cpp -O2 -o sol 2> ce.txt &&",
            "timeout 3 ./sol < input.txt > out.txt 2> re.txt",
            Map.of(
                "solution.cpp", code,
                "input.txt", input
            )
        );
    }
}