package com.voltx.codefury;

@Entity
public class User {
    @Id @GeneratedValue
    private Long id;

    @Indexed(unique = true)
    private String email;
    private String password_hash;

    @Indexed(unique = true)
    private String username;

    private int shards;
    private int rank;
    private int questions_solved;
    private int questions_attempted;
    private int challenges_played;
    private int challenges_won;
    private String google_auth_id;

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword_hash() {
        return password_hash;
    }
    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
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
    public int getQuestions_solved() {
        return questions_solved;
    }
    public void setQuestions_solved(int questions_solved) {
        this.questions_solved = questions_solved;
    }
    public int getQuestions_attempted() {
        return questions_attempted; 
    }
    public void setQuestions_attempted(int questions_attempted) {
        this.questions_attempted = questions_attempted;
    }
    public int getChallenges_played() {
        return challenges_played;
    }
    public void setChallenges_played(int challenges_played) {
        this.challenges_played = challenges_played;
    }
    public int getChallenges_won() {
        return challenges_won;
    }
    public void setChallenges_won(int challenges_won) {
        this.challenges_won = challenges_won;
    }
    public String getGoogle_auth_id() {
        return google_auth_id;
    }
    public void setGoogle_auth_id(String google_auth_id) {
        this.google_auth_id = google_auth_id;
    }
}