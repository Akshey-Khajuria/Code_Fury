package com.voltx.codefury.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.voltx.codefury.service.ProblemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.voltx.codefury.entity.Problem;
import java.util.List;

@RestController
@RequestMapping("/api/problems")
public class ProblemController {
    @Autowired
    ProblemService problemService;

    @GetMapping("/all")
    public List<Problem> getAllProblems() {
        return problemService.getAllProblems();
    }
    
}
