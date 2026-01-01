package com.voltx.codefury.service.impl;

import com.voltx.codefury.auth.repository.ProblemRepository;
import com.voltx.codefury.entity.Problem;
import com.voltx.codefury.entity.TestCase;
import com.voltx.codefury.enums.Language;
import com.voltx.codefury.enums.SolveMode;
import com.voltx.codefury.service.CodeExecutor;
import com.voltx.codefury.service.ProblemService;
import com.voltx.codefury.constants.Responses;
import com.voltx.codefury.dto.ExecutionResult;
import com.voltx.codefury.service.SequenceGeneratorService;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.voltx.codefury.service.impl.ExecutorFactory;
import com.voltx.codefury.util.TestCaseSerializer;

@Service
public class ProblemServiceImpl implements ProblemService {
    private Logger logger = LoggerFactory.getLogger(ProblemServiceImpl.class);

    private final ProblemRepository repo;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final ExecutorFactory executorFactory;
    private final TestCaseSerializer testCaseSerializer;

    public ProblemServiceImpl(ProblemRepository repo, SequenceGeneratorService sequenceGeneratorService, ExecutorFactory executorFactory, TestCaseSerializer testCaseSerializer) {
        this.repo = repo;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.executorFactory = executorFactory;
        this.testCaseSerializer = testCaseSerializer;
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
        problem.setProblemId(
            sequenceGeneratorService.getNextSequence("problemId")
        );
        try{
            repo.save(problem);
            return Responses.PROBLEM_ADDED_SUCCESSFULLY;
        } catch(Exception e){
            logger.error("Error adding problem: {}", e.getMessage());
            return "Error adding problem: " + e.getMessage();
        }
        
    }

    @Override
    public String solveProblem(Long problemId, String language, String solution, String mode) {
        logger.info("Solving problem with ID: {} using language: {}", problemId, language);

        //check if language is supported
        if(!isValidLanguage(language)){
            logger.warn("Unsupported programming language: {}", language);
            return Responses.UNSUPPORTED_LANGUAGE;
        }

        if(!isvalidProblemId(problemId)){
            logger.warn("Invalid problem ID: {}", problemId);
            return Responses.INVALID_PROBLEM_ID;
        }

        //check mode and get sampleTestCases or allTestCases based on that
        List<TestCase> testCases = getTestCasesByMode(mode, problemId);
        if (testCases == null || testCases.isEmpty()) {
            return "No test cases found";
        }

        // Get executor
        Language langEnum = Language.valueOf(language.toUpperCase());
        CodeExecutor executor = executorFactory.get(langEnum);

        // Execute against testcases
         for (int i = 0; i < testCases.size(); i++) {

            TestCase tc = testCases.get(i);

            String input =
                    testCaseSerializer.serializeInput(tc.getInput());
            String expected =
                    testCaseSerializer.serializeOutput(tc.getOutput());

            ExecutionResult result =
                    executor.execute(solution, input);

            // Compile / Runtime / Timeout errors
            if (!"OK".equals(result.getStatus())) {
                logger.warn(
                        "Execution failed at testcase {} with status {}",
                        i + 1, result.getStatus()
                );
                return result.getStatus(); // CE / RE / TLE
            }

            // Wrong answer
            if (!normalize(result.getOutput())
                    .equals(normalize(expected))) {

                logger.info(
                        "Wrong answer at testcase {}",
                        i + 1
                );
                return Responses.WRONG_ANSWER;
            }
        }
        // All testcases passed
        if (SolveMode.RUN.name().equalsIgnoreCase(mode)) {
            return Responses.RUN_SUCCESSFUL;
        }

        // Here we would have logic to evaluate the solution
        logger.info("Problem solved successfully with ID: {}", problemId);
        return Responses.PROBLEM_SOLVED_SUCCESSFULLY;
    }

    private boolean isValidLanguage(String language) {
        for (Language lang : Language.values()) {
            if (lang.name().equalsIgnoreCase(language)) {
                return true;
            }
        }
        return false;
    }

    private boolean isvalidProblemId(Long problemId) {
        return repo.existsByProblemId(problemId);
    }

    private List<TestCase> getTestCasesByMode(String mode, Long problemId) {
        // if (mode.equalsIgnoreCase("RUN")) {
        if (SolveMode.RUN.name().equalsIgnoreCase(mode)) {
            Problem problem = repo.findByProblemId(problemId);
            return problem.getSampleTestCases();
        } else if (SolveMode.SUBMIT.name().equalsIgnoreCase(mode)) {
            Problem problem = repo.findByProblemId(problemId);
            return problem.getAllTestCases();
        }else{
            logger.error("Invalid mode provided: {}", mode);
            return null;
        }
    }

    private String normalize(String s) {
        return s == null
                ? ""
                : s.trim().replaceAll("\\s+", "");
    }
}
