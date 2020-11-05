package com.example.graduaionresearch_13;

public class Problem {
    private int id;
    private String problem;
    private String answer;
    private int book_id;

    public Problem(int id, String problem, String answer, int book_id){
        this.id=id;
        this.problem=problem;
        this.answer=answer;
        this.book_id=book_id;
    }

    public int getId(){
        return this.id;
    }

    public String getProblem(){
        return this.problem;
    }

    public String getAnswer(){
        return this.answer;
    }

    public int getBook_id(){
        return this.book_id;
    }

    public void setId(int id){
        this.id=id;
    }

    public void setProblem(String problem){
        this.problem=problem;
    }

    public void setAnswer(String answer){
        this.answer=answer;
    }

    public void setBook_id(int book_id){
        this.book_id=book_id;
    }
}
