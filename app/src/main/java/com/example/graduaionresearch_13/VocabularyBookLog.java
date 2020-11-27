package com.example.graduaionresearch_13;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class VocabularyBookLog implements Comparable<VocabularyBookLog> {
    private final int log_id;
    private final float accuracy_rate;
    private final int book_id;

    public VocabularyBookLog(int id, float rate, int book_id){
        this.log_id = id;
        this.accuracy_rate = rate;
        this.book_id = book_id;
    }

    //TODO DBからデータを取得してリストで返す
    public static List<VocabularyBookLog> getList(Context context, int book_id){
        List<VocabularyBookLog> list = new ArrayList<>();
        return list;
    }

    //TODO 新しいオブジェクトを生成してDBに登録
    public static synchronized VocabularyBookLog createNewLog(double rate, int book_id){
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
