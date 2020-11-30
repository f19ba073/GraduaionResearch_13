package com.example.graduaionresearch_13;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static com.example.graduaionresearch_13.DBNames.*;

public class TreeInformation {
    private static TreeInformation treeInformation;
    private int id;
    private int treeCount;
    private float treeRate;

    private TreeInformation(int id, int treeRate, int treeValue) {
        this.id = id;
        this.treeRate = treeRate;
        this.treeCount = treeValue;
    }

    public static TreeInformation getInstance(Context context){
        if(treeInformation == null){

            //DBからデータ取得
            DBOpenHelper helper = DBOpenHelper.getInstance(context);
            SQLiteDatabase db = helper.getWritableDatabase();
            Cursor selectData = db.query(
                    TABLE_NAME_USER_PROFILE,
                    new String[]{COLUMN_NAME_PROF_ID,COLUMN_NAME_TREE_RATE , COLUMN_NAME_TREE_COUNT},
                    null,null,null,null,null);
            try {
                selectData.moveToNext();
                treeInformation = new TreeInformation(
                        selectData.getInt(0),
                        selectData.getInt(1),
                        selectData.getInt(2)
                );
            }finally {
                selectData.close();
            }
        }
        return treeInformation;
    }

    public void addTreeRate(Context context, float delta){
        this.treeRate += delta;
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_TREE_RATE, this.treeRate);
    }

    public void setTreeRate(Context context, float delta){
        this.treeRate = delta;
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_TREE_RATE, this.treeRate);
    }

    public void incrementTreeCount(Context context){
        this.treeCount++;
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_TREE_COUNT, this.treeRate);
    }

    private void putForDataBase(Context context, ContentValues values, String tableName){
        DBOpenHelper helper = DBOpenHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.update(tableName,
                values,
                COLUMN_NAME_PROF_ID + " = " + this.getId(),
                null);
    }
    
    public int getId(){
        return this.id;
    }

    public int getTreeCount(){
        return this.treeCount;
    }

    public float getTreeRate(){
        return this.treeRate;
    }
}
