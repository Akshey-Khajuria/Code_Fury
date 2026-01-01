package com.voltx.codefury.service.impl;

import com.voltx.codefury.dto.ExecutionResult;
import com.voltx.codefury.service.AbstractDockerExecutor;
import com.voltx.codefury.service.CodeExecutor;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class JavaExecutor extends AbstractDockerExecutor
        implements CodeExecutor {

    @Override
    public ExecutionResult execute(String code, String input) {

        return runInDocker(
            "code-runner-java:1.0",
            "javac Solution.java 2> ce.txt &&",
            "timeout 3 java Solution < input.txt > out.txt 2> re.txt",
            Map.of(
                "Solution.java", code,
                "input.txt", input
            )
        );
    }
}
