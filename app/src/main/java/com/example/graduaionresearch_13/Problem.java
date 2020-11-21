package com.example.graduaionresearch_13;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.graduaionresearch_13.DBNames.*;

public class Problem {
    private int id;
    private String problem;
    private String answer;
    private int book_id;

    public Problem(int id, String problem, String answer, int book_id){
        this.id = id;
        this.problem = problem;
        this.answer = answer;
        this.book_id = book_id;
    }

    public static List<Problem> getList(Context context, int id){
        List<Problem> list = new ArrayList<>();
        DBOpenHelper helper = new DBOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.query(TABLE_NAME_PROBLEMS,PROBLEM_COLUMNS, COLUMN_NAME_BOOK_ID + " = " + id,
                null, null, null,null);
        if(c == null){return list;}
        try{
            while(c.moveToNext()){
                list.add(new Problem(c.getInt(0),c.getString(1),
                        c.getString(2),c.getInt(3)));
            }
        }finally {
            c.close();
        }
        return list;
    }

    public void delete(Context context){
        DBOpenHelper helper = new DBOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_NAME_PROBLEMS,COLUMN_NAME_PROBLEM_ID + " = ?",
                new String[]{String.valueOf(this.id)});
    }

    public void update(Context context){
        DBOpenHelper helper = new DBOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_PROBLEM,this.problem);
        contentValues.put(COLUMN_NAME_ANSWER,this.answer);
        db.update(TABLE_NAME_PROBLEMS,contentValues,
                COLUMN_NAME_PROBLEM_ID + " = " + this.getId(),null);
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
        this.id = id;
    }

    public void setProblem(String problem){
        this.problem = problem;
    }

    public void setAnswer(String answer){
        this.answer = answer;
    }

    public void setBook_id(int book_id){
        this.book_id = book_id;
    }
}
