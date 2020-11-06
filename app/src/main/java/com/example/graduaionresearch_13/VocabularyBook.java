package com.example.graduaionresearch_13;

public class VocabularyBook {
    private int book_id;
    private String book_name;
    
    public VocabularyBook(int book_id, String book_name){
        this.book_id=book_id;
        this.book_name=book_name;
    }

    public int getBook_id(){
        return this.book_id;
    }

    public String getBook_name(){
        return this.book_name;
    }

    public void setBook_id(int book_id) { this.book_id=book_id; }

    public void setBook_name(String book_name){
        this.book_name=book_name;
    }

}
