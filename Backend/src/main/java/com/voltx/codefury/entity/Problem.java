package com.voltx.codefury.entity;

import com.voltx.codefury.enums.Difficulty;
import jakarta.persistence.Id;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection = "problems")
public class Problem {
    @Id
    private ObjectId id;

    @Indexed(unique = true)
    // @Field("problem_id")
    private Long problemId;
    
    @Indexed(unique = true)
    private String title;
    
    private String description;
    private Difficulty difficulty;
    private String constraints;
    private List<TestCase> sampleTestCases;
    private List<TestCase> allTestCases;

    // Getters and Setters
    public Long getProblemId() {
        return problemId;
    }
    public void setProblemId(Long problemId) {
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
    public List<TestCase> getSampleTestCases() {
        return sampleTestCases;
    }
    public void setSampleTestCases(List<TestCase> sampleTestCases) {
        this.sampleTestCases = sampleTestCases;
    }
    public List<TestCase> getAllTestCases() {
        return allTestCases;
    }
    public void setAllTestCases(List<TestCase> allTestCases) {
        this.allTestCases = allTestCases;
    }
}
