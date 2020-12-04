package com.example.graduaionresearch_13;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.example.graduaionresearch_13.DBNames.*;

//画面遷移時に遷移先画面にIntentで渡すためにSerializableを実装
public class VocabularyBook implements Serializable {
    private int book_id;
    private String book_name;
    private int time_limit;

    public static final int DEFAULT_TIME_LIMIT = 10000;
    
    public VocabularyBook(int book_id, String book_name, int time_limit){
        this.book_id = book_id;
        this.book_name = book_name;
        this.time_limit = time_limit;
    }

    //BOOKSテーブルからすべての行をVocabularyBook型リストとして返す
    public static List<VocabularyBook> getList(Context context){
        List<VocabularyBook> list = new ArrayList<>();

        DBOpenHelper helper = DBOpenHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor selectData = db.query(
                TABLE_NAME_BOOKS,
                BOOKS_COLUMNS,
                null, null, null, null, null);

        if(selectData == null){return list;}
        try{
            while(selectData.moveToNext()){
                list.add(new VocabularyBook(
                        selectData.getInt(0),
                        selectData.getString(1),
                        selectData.getInt(2)
                        )
                );
            }
        }finally {
            selectData.close();
        }

        return list;
    }

    //BOOKSテーブルに登録されている最大IDに1を加えたIDを返す
    public static int getNewId(Context context){
        DBOpenHelper helper = DBOpenHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME_BOOKS,
                new String[]{"MAX(" + COLUMN_NAME_ID + ") AS MAX"},
                null, null, null, null, null);

        cursor.moveToFirst();
        int index = cursor.getInt(0);
        return index + 1;
    }

    public void delete(Context context){
        DBOpenHelper helper = DBOpenHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        //この単語帳データ及び、紐づけられている問題データをデータベースから全て削除
        db.delete(TABLE_NAME_BOOKS,COLUMN_NAME_ID + " = ?",
                new String[]{String.valueOf(this.book_id)});
        db.delete(TABLE_NAME_PROBLEMS,COLUMN_NAME_ID + " = ?",
                new String[]{String.valueOf(this.book_id)});
    }

    public void update(Context context){
        DBOpenHelper helper = DBOpenHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_BOOK_NAME,this.book_name);
        contentValues.put(COLUMN_NAME_TIME_LIMIT, this.time_limit);
        db.update(TABLE_NAME_BOOKS,contentValues,
                COLUMN_NAME_ID + " = " + this.getBook_id(),null);
    }

    public int getBook_id(){
        return this.book_id;
    }

    public String getBook_name(){
        return this.book_name;
    }

    public int getTime_limit(){
        return this.time_limit;
    }

    public void setBook_id(int book_id) { this.book_id = book_id; }

    public void setBook_name(String book_name){
        this.book_name = book_name;
    }

    public void setTime_limit(int time_limit){
        this.time_limit = time_limit;
    }
}
