package com.example.graduaionresearch_13;

import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import static com.example.graduaionresearch_13.DBNames.*;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "WordsAppDB.db";

    private static final String SQL_CREATE_BOOKS_TABLE = "CREATE TABLE " + TABLE_NAME_BOOKS +
            "(" + COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " + COLUMN_NAME_BOOK_NAME + " TEXT)";
    private static final String SQL_CREATE_PROBLEMS_TABLE = "CREATE TABLE " + TABLE_NAME_PROBLEMS +
            "(" + COLUMN_NAME_PROBLEM_ID +" INTEGER PRIMARY KEY, " + COLUMN_NAME_PROBLEM +
            " TEXT, "+ COLUMN_NAME_ANSWER +" TEXT, " + COLUMN_NAME_BOOK_ID + " INTEGER)";
    private static final String SQL_CREATE_USER_PROFILE_TABLE = "CREATE TABLE " + TABLE_NAME_USER_PROFILE +
            "(" + COLUMN_NAME_TREE_POINT+ " INTEGER, " + COLUMN_NAME_TREE_VALUE + " INTEGER)";

    private static final String SQL_DELETE_BOOKS = "DROP TABLE " + TABLE_NAME_BOOKS;
    private static final String SQL_DELETE_PROBLEMS = "DROP TABLE " + TABLE_NAME_PROBLEMS;
    private static final String SQL_DELETE_USER_PROFILE = "DROP TABLE " + TABLE_NAME_USER_PROFILE;

    DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_BOOKS_TABLE);
        db.execSQL(SQL_CREATE_PROBLEMS_TABLE);
        db.execSQL(SQL_CREATE_USER_PROFILE_TABLE);

        ContentValues bookValues = new ContentValues();
        ContentValues problemValues = new ContentValues();
        ContentValues profValues = new ContentValues();
        bookValues.put(COLUMN_NAME_BOOK_NAME, "英語");
        problemValues.put(COLUMN_NAME_PROBLEM,"JVMはなんの略？");
        problemValues.put(COLUMN_NAME_ANSWER,"JavaVirtualMachine");
        problemValues.put(COLUMN_NAME_BOOK_ID,1);
        profValues.put(COLUMN_NAME_TREE_POINT,0);
        profValues.put(COLUMN_NAME_TREE_VALUE,0);
        db.insert(TABLE_NAME_BOOKS, null, bookValues);
        db.insert(TABLE_NAME_PROBLEMS,null,problemValues);
        db.insert(TABLE_NAME_USER_PROFILE,null,profValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_BOOKS);
        db.execSQL(SQL_DELETE_PROBLEMS);
        db.execSQL(SQL_DELETE_USER_PROFILE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
