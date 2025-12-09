package com.voltx.codefury.auth.repository;

import java.util.Optional;
import java.util.List;
import com.voltx.codefury.entity.Problem;
import com.voltx.codefury.entity.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProblemRepository extends MongoRepository<Problem, String>{
    //get all problems in db
    public List<Problem> findAll();
    public List<Problem> findByDifficulty(String difficulty);
}
