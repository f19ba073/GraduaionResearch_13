package com.example.graduaionresearch_13;

import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import static com.example.graduaionresearch_13.DBNames.*;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static DBOpenHelper dbOpenHelper;

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

    private DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DBOpenHelper getInstance(Context context){
        if(dbOpenHelper == null){
            dbOpenHelper = new DBOpenHelper(context);
        }
        return dbOpenHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_BOOKS_TABLE);
        db.execSQL(SQL_CREATE_PROBLEMS_TABLE);
        db.execSQL(SQL_CREATE_USER_PROFILE_TABLE);

        ContentValues profValues = new ContentValues();
        profValues.put(COLUMN_NAME_TREE_POINT,0);
        profValues.put(COLUMN_NAME_TREE_VALUE,0);

        putTestCase(db);
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

    private void putTestCase(SQLiteDatabase db){
        ContentValues bookValues = new ContentValues();
        ContentValues problemValues = new ContentValues();

        bookValues.put(COLUMN_NAME_BOOK_NAME, "英語");
        db.insert(TABLE_NAME_BOOKS, null, bookValues);
        bookValues.put(COLUMN_NAME_BOOK_NAME, "漢字　読み");
        db.insert(TABLE_NAME_BOOKS, null, bookValues);
        bookValues.put(COLUMN_NAME_BOOK_NAME, "化学基礎");
        db.insert(TABLE_NAME_BOOKS, null, bookValues);
        bookValues.put(COLUMN_NAME_BOOK_NAME, "日本史");
        db.insert(TABLE_NAME_BOOKS, null, bookValues);

        problemValues.put(COLUMN_NAME_PROBLEM,"りんご");
        problemValues.put(COLUMN_NAME_ANSWER,"apple");
        problemValues.put(COLUMN_NAME_BOOK_ID,1);
        db.insert(TABLE_NAME_PROBLEMS,null,problemValues);
        problemValues.put(COLUMN_NAME_PROBLEM,"みかん");
        problemValues.put(COLUMN_NAME_ANSWER,"orange");
        problemValues.put(COLUMN_NAME_BOOK_ID,1);
        db.insert(TABLE_NAME_PROBLEMS,null,problemValues);
        problemValues.put(COLUMN_NAME_PROBLEM,"バナナ");
        problemValues.put(COLUMN_NAME_ANSWER,"banana");
        problemValues.put(COLUMN_NAME_BOOK_ID,1);
        db.insert(TABLE_NAME_PROBLEMS,null,problemValues);

        problemValues.put(COLUMN_NAME_PROBLEM,"捺印");
        problemValues.put(COLUMN_NAME_ANSWER,"なついん");
        problemValues.put(COLUMN_NAME_BOOK_ID,2);
        db.insert(TABLE_NAME_PROBLEMS,null,problemValues);
        problemValues.put(COLUMN_NAME_PROBLEM,"凄絶");
        problemValues.put(COLUMN_NAME_ANSWER,"せいぜつ");
        problemValues.put(COLUMN_NAME_BOOK_ID,2);
        db.insert(TABLE_NAME_PROBLEMS,null,problemValues);
        problemValues.put(COLUMN_NAME_PROBLEM,"補填");
        problemValues.put(COLUMN_NAME_ANSWER,"ほてん");
        problemValues.put(COLUMN_NAME_BOOK_ID,2);
        db.insert(TABLE_NAME_PROBLEMS,null,problemValues);

        problemValues.put(COLUMN_NAME_PROBLEM,"２種類以上の物質が混じり合っているもの。");
        problemValues.put(COLUMN_NAME_ANSWER,"混合物");
        problemValues.put(COLUMN_NAME_BOOK_ID,3);
        db.insert(TABLE_NAME_PROBLEMS,null,problemValues);
        problemValues.put(COLUMN_NAME_PROBLEM,"不純物を含んだ結晶を適当な液体に溶かし、" +
                "温度による溶解度の変化や触媒を蒸発させる操作により、不純物を取り除いて純粋な結晶を得ること。");
        problemValues.put(COLUMN_NAME_ANSWER,"再結晶");
        problemValues.put(COLUMN_NAME_BOOK_ID,3);
        db.insert(TABLE_NAME_PROBLEMS,null,problemValues);
        problemValues.put(COLUMN_NAME_PROBLEM,"２種類以上の元素からなる物質。");
        problemValues.put(COLUMN_NAME_ANSWER,"化合物");
        problemValues.put(COLUMN_NAME_BOOK_ID,3);
        db.insert(TABLE_NAME_PROBLEMS,null,problemValues);

        problemValues.put(COLUMN_NAME_PROBLEM,"日本はかつて鎖国政策をとっていたが、琉球王国とどこの国とは外交関係を持っていたか。");
        problemValues.put(COLUMN_NAME_ANSWER,"朝鮮");
        problemValues.put(COLUMN_NAME_BOOK_ID,4);
        db.insert(TABLE_NAME_PROBLEMS,null,problemValues);
        problemValues.put(COLUMN_NAME_PROBLEM,"杉田玄白や前野良沢らによって、翻訳された本。");
        problemValues.put(COLUMN_NAME_ANSWER,"解体新書");
        problemValues.put(COLUMN_NAME_BOOK_ID,4);
        db.insert(TABLE_NAME_PROBLEMS,null,problemValues);
        problemValues.put(COLUMN_NAME_PROBLEM,"老中の水野忠邦が1841年に幕府の立て直しに着手したこと。");
        problemValues.put(COLUMN_NAME_ANSWER,"天保の改革");
        problemValues.put(COLUMN_NAME_BOOK_ID,4);
        db.insert(TABLE_NAME_PROBLEMS,null,problemValues);
    }
}
