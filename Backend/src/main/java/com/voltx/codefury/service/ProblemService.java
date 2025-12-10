package com.voltx.codefury.service;

import java.util.List;
import com.voltx.codefury.entity.Problem;

public interface ProblemService {
    public List<Problem> getAllProblems();
    public List<Problem> getProblemsByDifficulty(String difficulty);
    public Problem getProblemById(Long problemId);
    public String addProblem(Problem problem);
}
