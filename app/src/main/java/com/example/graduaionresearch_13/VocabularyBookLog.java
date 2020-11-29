package com.example.graduaionresearch_13;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import static com.example.graduaionresearch_13.DBNames.BOOK_LOGS_COLUMNS;
import static com.example.graduaionresearch_13.DBNames.COLUMN_NAME_BOOK_ID;
import static com.example.graduaionresearch_13.DBNames.PROBLEM_COLUMNS;
import static com.example.graduaionresearch_13.DBNames.TABLE_NAME_BOOK_LOGS;
import static com.example.graduaionresearch_13.DBNames.TABLE_NAME_PROBLEMS;

//正解率のデータを保持するクラス
public class VocabularyBookLog implements Comparable<VocabularyBookLog> {
    private final int log_id;
    private final float accuracy_rate;
    private final int book_id;

    public VocabularyBookLog(int id, float rate, int book_id){
        this.log_id = id;
        this.accuracy_rate = rate;
        this.book_id = book_id;
    }

    //DBからデータを取得してリストで返す
    public static List<VocabularyBookLog> getList(Context context, int book_id){
        List<VocabularyBookLog> list = new ArrayList<>();

        DBOpenHelper helper = DBOpenHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor selectData = db.query(
                TABLE_NAME_BOOK_LOGS, BOOK_LOGS_COLUMNS,
                COLUMN_NAME_BOOK_ID + " = " + book_id,
                null, null, null, null);

        if(selectData == null){return list;}
        try{
            while(selectData.moveToNext()){
                list.add(new VocabularyBookLog(
                        selectData.getInt(0),
                        selectData.getFloat(1),
                        selectData.getInt(2)
                        )
                );
            }
        }finally {
            selectData.close();
        }

        return list;
    }

    //TODO 新しいオブジェクトを生成してDBに登録
    public static synchronized VocabularyBookLog createNewLog
    (Context context, double rate, int book_id){
        return null;
    }

    public int getId(){
        return this.log_id;
    }

    public float getAccuracy_rate(){
        return this.accuracy_rate;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int compareTo(VocabularyBookLog log) {
        return Integer.compare(this.log_id, log.getId());
    }
}
