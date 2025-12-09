package com.voltx.codefury.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.voltx.codefury.service.ProblemService;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import com.voltx.codefury.entity.Problem;
import com.voltx.codefury.enums.Difficulty;
import com.voltx.codefury.constants.Responses;
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
    
}
