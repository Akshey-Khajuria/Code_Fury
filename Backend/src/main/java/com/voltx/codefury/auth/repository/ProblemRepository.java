package com.voltx.codefury.auth.repository;

import java.util.List;
import com.voltx.codefury.entity.Problem;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProblemRepository extends MongoRepository<Problem, String>{
    public List<Problem> findAll();
    public List<Problem> findByDifficulty(String difficulty);
    public Problem findByProblemId(Long problemId);
    public Problem save(Problem problem);
}
