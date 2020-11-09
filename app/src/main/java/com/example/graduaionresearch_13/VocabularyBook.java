package com.example.graduaionresearch_13;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.example.graduaionresearch_13.DBNames.COLUMN_NAME_BOOK_NAME;
import static com.example.graduaionresearch_13.DBNames.COLUMN_NAME_ID;
import static com.example.graduaionresearch_13.DBNames.TABLE_NAME_BOOKS;

public class VocabularyBook implements Serializable {
    private int book_id;
    private String book_name;
    
    public VocabularyBook(int book_id, String book_name){
        this.book_id=book_id;
        this.book_name=book_name;
    }

    public static List<VocabularyBook> getList(Context context){
        List<VocabularyBook> list = new ArrayList<>();
        DBOpenHelper helper = new DBOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.query(TABLE_NAME_BOOKS,new String[]{COLUMN_NAME_ID,COLUMN_NAME_BOOK_NAME},
                null, null, null, null,null);
        if(c == null){return list;}
        try{
            while(c.moveToNext()){
                list.add(new VocabularyBook(c.getInt(0),c.getString(1)));
            }
        }finally {
            c.close();
        }
        return list;
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
