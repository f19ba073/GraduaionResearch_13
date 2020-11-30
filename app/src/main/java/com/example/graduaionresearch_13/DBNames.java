package com.example.graduaionresearch_13;

public class DBNames {
    public static final String TABLE_NAME_BOOKS = "BOOKS";
    public static final String TABLE_NAME_PROBLEMS = "PROBLEMS";
    public static final String TABLE_NAME_BOOK_LOGS = "BOOK_LOGS";
    public static final String TABLE_NAME_USER_PROFILE = "USER_PROFILE";

    public static final String COLUMN_NAME_ID = "book_id";
    public static final String COLUMN_NAME_BOOK_NAME = "book_name";
    public static final String COLUMN_NAME_PROBLEM_ID = "problem_id";
    public static final String COLUMN_NAME_PROBLEM = "problem";
    public static final String COLUMN_NAME_ANSWER = "answer";
    public static final String COLUMN_NAME_BOOK_ID = "book_id";
    public static final String COLUMN_NAME_LOG_ID = "log_id";
    public static final String COLUMN_NAME_ACCURACY_RATE = "accuracy_rate";
    public static final String COLUMN_NAME_TREE_RATE = "tree_rate";
    public static final String COLUMN_NAME_TREE_VALUE = "tree_value";

    public static final String[] BOOKS_COLUMNS = {
            COLUMN_NAME_BOOK_ID,
            COLUMN_NAME_BOOK_NAME
    };

    public static final String[] PROBLEM_COLUMNS = {
            COLUMN_NAME_PROBLEM_ID,
            COLUMN_NAME_PROBLEM,
            COLUMN_NAME_ANSWER,
            COLUMN_NAME_BOOK_ID
    };

    public static final String[] BOOK_LOGS_COLUMNS = {
            COLUMN_NAME_LOG_ID,
            COLUMN_NAME_ACCURACY_RATE,
            COLUMN_NAME_BOOK_ID
    };
}
