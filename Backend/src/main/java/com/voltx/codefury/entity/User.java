package com.voltx.codefury.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.index.Indexed;
import com.voltx.codefury.enums.Role;

@Document(collection = "users")
public class User {
    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    @Transient
    private String password;
    private String passwordHash;
    private String name;

    @Indexed(unique = true)
    private String username;

    private int shards;
    private int rank;
    private int questionsSolved;
    private int questionsAttempted;
    private int challengesPlayed;
    private int challengesWon;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // @Field("google_auth_id")
    private String googleAuthId;

    // Getters and Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    public void setName(String username) {
        this.name = username;
    }
    public String getName() {
        return name;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getShards() {
        return shards;
    }
    public void setShards(int shards) {
        this.shards = shards;
    }
    public int getRank() {
        return rank;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }
    public int getQuestionsSolved() {
        return questionsSolved;
    }
    public void setQuestionsSolved(int questionsSolved) {
        this.questionsSolved = questionsSolved;
    }
    public int getQuestionsAttempted() {
        return questionsAttempted; 
    }
    public void setQuestionsAttempted(int questionsAttempted) {
        this.questionsAttempted = questionsAttempted;
    }
    public int getChallengesPlayed() {
        return challengesPlayed;
    }
    public void setChallengesPlayed(int challengesPlayed) {
        this.challengesPlayed = challengesPlayed;
    }
    public int getChallengesWon() {
        return challengesWon;
    }
    public void setChallengesWon(int challengesWon) {
        this.challengesWon = challengesWon;
    }
    public String getGoogleAuthId() {
        return googleAuthId;
    }
    public void setGoogleAuthId(String googleAuthId) {
        this.googleAuthId = googleAuthId;
    }
    public Role getRole() {
        return role;
    }
    //restricted
    public void setRole(Role role) {
        // Default role is USER
        this.role = Role.USER;
    }
}