package com.voltx.codefury.service.impl;

import com.voltx.codefury.auth.repository.ProblemRepository;
import com.voltx.codefury.entity.Problem;
import com.voltx.codefury.service.ProblemService;

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
}
