package com.voltx.codefury;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CodeFuryApplicationStarter{
    private static Logger logger = LoggerFactory.getLogger(CodeFuryApplicationStarter.class);
    public static void main(String[] args) {
        SpringApplication.run(CodeFuryApplicationStarter.class, args);
        logger.info("Application Started Successfully");

    }
}