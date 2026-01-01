package com.voltx.codefury.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.voltx.codefury.service.ProblemService;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import com.voltx.codefury.entity.Problem;
import com.voltx.codefury.enums.Difficulty;
import com.voltx.codefury.constants.Responses;
import com.voltx.codefury.dto.SolveRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("/api/problems")
public class ProblemController {
    private static Logger logger = LoggerFactory.getLogger(ProblemController.class);

    @Autowired
    ProblemService problemService;

    @GetMapping("/all")
    public List<Problem> getAllProblems() {
        return problemService.getAllProblems();
    }

    @GetMapping("/byDifficulty/{difficulty}")
    public ResponseEntity<List<Problem>> getProblemsByDifficulty(@PathVariable String difficulty) {
        //clean string and convert to enum
        logger.info("Fetching problems with difficulty: {}", difficulty);
        difficulty = difficulty.trim().toUpperCase();
        
        try {
            //check if valid difficulty
            Difficulty diffEnum = Difficulty.valueOf(difficulty);
            List<Problem> allProblems = problemService.getProblemsByDifficulty(diffEnum.name());
            return ResponseEntity.ok(allProblems);
        } catch (Exception e) {
            logger.error("Invalid difficulty level provided: {}", difficulty);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/byId/{problemId}")
    public ResponseEntity<Problem> getProblemById(@PathVariable Long problemId) {
        logger.info("Fetching problem with ID: {}", problemId);
        Problem problem = problemService.getProblemById(problemId);
        if (problem != null) {
            return ResponseEntity.ok(problem);
        } else {
            logger.warn("Problem not found with ID: {}", problemId);
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROBLEM_SETTER')")
    @PostMapping("/add")
    public ResponseEntity<String> postMethodName(@RequestBody Problem problem) {
        logger.info("Adding new problem: {}", problem.getTitle());

        String response = problemService.addProblem(problem);
        if(response!=Responses.PROBLEM_ADDED_SUCCESSFULLY){
            return ResponseEntity.badRequest().body(response);
        }
        logger.info("Problem added successfully: {}", problem.getTitle());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/solve")
    public ResponseEntity<String> solveProblem(@RequestBody SolveRequest body) {
        Long problemId = body.getProblemId();
        String language = body.getLanguage();
        String solution = body.getSolution();
        String mode = body.getMode();

        logger.info("Solving problem with ID: {}", problemId);
        String response = problemService.solveProblem(problemId, language, solution, mode);
        if(response!=Responses.PROBLEM_SOLVED_SUCCESSFULLY){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
    
}
