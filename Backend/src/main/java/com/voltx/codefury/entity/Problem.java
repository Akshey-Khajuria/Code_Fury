package com.voltx.codefury.entity;

import com.voltx.codefury.enums.Difficulty;
import jakarta.persistence.Id;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection = "problems")
public class Problem {
    @Id
    @Indexed(unique = true)
    @Field("problem_id")
    private int problemId;
    
    private String title;
    private String description;
    private Difficulty difficulty;
    private String constraints;
    private List<TestCase> sample_tests;

    // Getters and Setters
    public int getProblemId() {
        return problemId;
    }
    public void setProblemId(int problemId) {
        this.problemId = problemId;
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
    public List<TestCase> getSample_tests() {
        return sample_tests;
    }
    public void setSample_tests(List<TestCase> sample_tests) {
        this.sample_tests = sample_tests;
    }
}
