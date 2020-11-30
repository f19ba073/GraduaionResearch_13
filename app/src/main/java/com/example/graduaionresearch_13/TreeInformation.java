package com.example.graduaionresearch_13;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static com.example.graduaionresearch_13.DBNames.*;

public class TreeInformation {
    private static TreeInformation treeInformation;
    private int treeValue;
    private float treeRate;

    private TreeInformation(int treeRate , int treeValue) {
        this.treeRate = treeRate;
        this.treeValue = treeValue;
    }

    public static TreeInformation getInstance(Context context){
        if(treeInformation == null){
            DBOpenHelper helper = DBOpenHelper.getInstance(context);
            SQLiteDatabase db = helper.getWritableDatabase();
            Cursor selectData = db.query(TABLE_NAME_USER_PROFILE ,new String[]{COLUMN_NAME_TREE_RATE , COLUMN_NAME_TREE_VALUE},null,null,null,null,null);
            try {
                selectData.moveToFirst();
                treeInformation = new TreeInformation(selectData.getInt(0), selectData.getInt(1));
            }finally {
                selectData.close();
            }
        }
        return treeInformation;
    }

    public int getTreeValue(){
        return this.treeValue;
    }

    public float getTreeRate(){
        return this.treeRate;
    }
}
