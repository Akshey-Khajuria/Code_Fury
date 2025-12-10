package com.voltx.codefury.service.impl;

import com.voltx.codefury.auth.repository.ProblemRepository;
import com.voltx.codefury.entity.Problem;
import com.voltx.codefury.service.ProblemService;
import com.voltx.codefury.constants.Responses;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProblemServiceImpl implements ProblemService {
    private Logger logger = LoggerFactory.getLogger(ProblemServiceImpl.class);

    private final ProblemRepository repo;

    public ProblemServiceImpl(ProblemRepository repo) {
        this.repo = repo;
    }
    
    @Override
    public List<Problem> getAllProblems() {
        logger.info("Fetching all problems from the database");
        return repo.findAll();
    }

    @Override
    public List<Problem> getProblemsByDifficulty(String difficulty) {
        logger.info("Fetching problems with difficulty: {}", difficulty);
        return repo.findByDifficulty(difficulty);
    }

    @Override
    public Problem getProblemById(Long problemId) {
        logger.info("Fetching problem with ID: {}", problemId);
        return repo.findByProblemId(problemId);
    }

    @Override
    public String addProblem(Problem problem) {
        logger.info("Adding new problem: {}", problem.getTitle());
        try{
            repo.save(problem);
            return Responses.PROBLEM_ADDED_SUCCESSFULLY;
        } catch(Exception e){
            logger.error("Error adding problem: {}", e.getMessage());
            return "Error adding problem: " + e.getMessage();
        }
        
    }
}
