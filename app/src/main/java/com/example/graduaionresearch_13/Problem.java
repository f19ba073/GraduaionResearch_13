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

    //PROBLEMSテーブルから指定された外部キーをもつ行をProblem型リストとして返す
    public static List<Problem> getList(Context context, int book_id){
        List<Problem> list = new ArrayList<>();

        DBOpenHelper helper = new DBOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor selectData = db.query(
                TABLE_NAME_PROBLEMS,PROBLEM_COLUMNS,
                COLUMN_NAME_BOOK_ID + " = " + book_id,
                null, null, null, null);

        if(selectData == null){return list;}
        try{
            while(selectData.moveToNext()){
                list.add(new Problem(
                        selectData.getInt(0),
                        selectData.getString(1),
                        selectData.getString(2),
                        selectData.getInt(3)
                        )
                );
            }
        }finally {
            selectData.close();
        }

        return list;
    }

    public void delete(Context context){
        DBOpenHelper helper = new DBOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        db.delete(TABLE_NAME_PROBLEMS,COLUMN_NAME_PROBLEM_ID + " = ?",
                new String[]{String.valueOf(this.id)});
    }

    public void update(Context context, String newProblem, String newAnswer){
        setProblem(newProblem);
        setAnswer(newAnswer);

        ContentValues updateValues = new ContentValues();
        updateValues.put(COLUMN_NAME_PROBLEM,this.problem);
        updateValues.put(COLUMN_NAME_ANSWER,this.answer);

        DBOpenHelper helper = new DBOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.update(TABLE_NAME_PROBLEMS,
                updateValues,
                COLUMN_NAME_PROBLEM_ID + " = " + this.getId(),
                null);
    }

    public static synchronized Problem createNewProblem(Context context, String problem, String answer, int id){
        DBOpenHelper helper = new DBOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        //新規データ挿入
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_PROBLEM,problem);
        values.put(COLUMN_NAME_ANSWER,answer);
        values.put(COLUMN_NAME_BOOK_ID,id);
        db.insert(TABLE_NAME_PROBLEMS,null,values);

        //挿入したデータを取得
        Cursor selectData = db.query(
                TABLE_NAME_PROBLEMS,PROBLEM_COLUMNS,
                COLUMN_NAME_PROBLEM_ID +
                        " = (SELECT MAX(" + COLUMN_NAME_PROBLEM_ID + ") " +
                        " FROM " + TABLE_NAME_PROBLEMS +" )",
                null, null, null, null);

        //Problemオブジェクトを作成してreturn
        Problem newProblem = null;
        if(selectData == null){return newProblem;}
        try{
            selectData.moveToNext();
            newProblem = new Problem(
                            selectData.getInt(0),
                            selectData.getString(1),
                            selectData.getString(2),
                            selectData.getInt(3)
                    );

        }finally {
            selectData.close();
        }
        return newProblem;
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
