package com.example.graduaionresearch_13;

public class Result {
    private Problem problem;
    private boolean isCorrect;
    private String userAnswer;

    public Result(Problem problem, boolean isCorrect, String userAnswer){
        this.problem = problem;
        this.isCorrect = isCorrect;
        this.userAnswer = userAnswer;
    }

    public String getProblem(){
        return this.problem.getProblem();
    }

    public String getAnswer(){
        return this.problem.getAnswer();
    }

    public String getUserAnswer(){
        return this.userAnswer;
    }

    public boolean isCorrect(){
        return this.isCorrect;
    }
}
