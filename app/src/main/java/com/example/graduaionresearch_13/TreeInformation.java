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

    public static final float MAX_TREE_RATE = 100f;
    public static final float TREE_RATE_PLUS_POINT = 0.2f;

    private TreeInformation(int id, float treeRate, int treeValue) {
        this.id = id;
        this.treeRate = treeRate;
        this.treeCount = treeValue;
    }

    public static synchronized TreeInformation getInstance(Context context){
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
                        selectData.getFloat(1),
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
        updateForDataBase(context, values);
    }

    public void setTreeRate(Context context, float delta){
        this.treeRate = delta;
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_TREE_RATE, this.treeRate);
        updateForDataBase(context, values);
    }

    public void incrementTreeCount(Context context){
        this.treeCount++;
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_TREE_COUNT, this.treeRate);
        updateForDataBase(context, values);
    }

    //DBの値を更新
    private void updateForDataBase(Context context, ContentValues values){
        DBOpenHelper helper = DBOpenHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.update(TABLE_NAME_USER_PROFILE,
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
