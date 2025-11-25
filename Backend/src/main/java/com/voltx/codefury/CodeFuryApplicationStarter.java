package com.voltx.codefury;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CodeFuryApplicationStarter{
    public static void main(String[] args) {
        SpringApplication.run(CodeFuryApplicationStarter.class, args);
        System.out.println("Application Started Successfully");

    }
}