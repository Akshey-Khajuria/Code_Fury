package com.voltx.codefury.service.impl;

import com.voltx.codefury.enums.Language;
import com.voltx.codefury.service.CodeExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecutorFactory {

    @Autowired private JavaExecutor java;
    @Autowired private PythonExecutor python;
    @Autowired private CppExecutor cpp;

    public CodeExecutor get(Language lang) {
        return switch (lang) {
            case JAVA -> java;
            case PYTHON -> python;
            case CPP -> cpp;
        };
    }
}
