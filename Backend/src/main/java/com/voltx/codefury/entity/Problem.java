package com.voltx.codefury.entity;

import com.voltx.codefury.enums.Difficulty;
import jakarta.persistence.Id;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "problems")
public class Problem {
    @Id
    @Indexed(unique = true)
    private String problem_id;
    
    private String title;
    private String description;
    private Difficulty difficulty;
    private String constraints;
    private List<TestCase> testCases;

    // Getters and Setters
    public String getProblem_id() {
        return problem_id;
    }
    public void setProblem_id(String problem_id) {
        this.problem_id = problem_id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Difficulty getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
    public String getConstraints() {
        return constraints;
    }
    public void setConstraints(String constraints) {
        this.constraints = constraints;
    }
    public List<TestCase> getTestCases() {
        return testCases;
    }
    public void setTestCases(List<TestCase> testCases) {
        this.testCases = testCases;
    }
}
