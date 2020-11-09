package com.example.graduaionresearch_13;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VocabularyBookActivity extends AppCompatActivity {

    private BaseAdapter adapter;

    // 要素の削除、順番変更のためArrayListを定義
    private List<String> itemNames;
    // タップされたitemの位置
    private int tappedPosition = 0;

    private static  String[] scenes = {
            // Scenes of Isle of Wight
            "現国",
            "数学",
            "科学",
            "化学",
            "English",
            "世界史",
            "日本史",
            "経済"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocabulary_book_list);

        // 配列をArrayListにコピー
        itemNames = new ArrayList<>(Arrays.asList(scenes));

        // ListViewのインスタンスを生成
        ListView listView = (ListView) findViewById(R.id.listView);

        // BaseAdapter を継承したadapterのインスタンスを生成
        // レイアウトファイル list.xml を activity_main.xml に inflate するためにadapterに引数として渡す
        adapter = new ListViewAdapter(this.getApplicationContext(),
                R.layout.row, itemNames);

        // ListViewにadapterをセット
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if(R.id.menu != id){
                    return;
                }
                final String[] items = {"編集", "削除", "キャンセル"};
                new AlertDialog.Builder(VocabularyBookActivity.this)
                        .setTitle("Selector")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        showEditDialog(position);
                                        break;
                                    case 1:
                                        showDeleteCheck(position);
                                        break;
                                    case 2:
                                        break;
                                }
                            }
                        }).setCancelable(true)
                        .show();
            }
        });

    }

    private void showEditDialog(int position){

    }

    //削除確認画面の出力
    private void showDeleteCheck(final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VocabularyBookActivity.this)
                .setTitle("削除").setMessage("本当に削除しますか？");

        // AlertDialogのYesボタンのコールバックリスナーを登録
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteItem(position);
                    }
                });
        // AlertDialogのNoボタンのコールバックリスナーを登録
        alertDialogBuilder.setNeutralButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        // AlertDialogのキャンセルができるように設定
        alertDialogBuilder.setCancelable(true);

        alertDialogBuilder.create().show();
    }



    /*private void deleteCheck() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());
        // AlertDialogのタイトル設定します

        alertDialogBuilder.setTitle("削除");

        // AlertDialogのメッセージ設定
        alertDialogBuilder.setMessage("本当に削除しますか？");

        // AlertDialogのYesボタンのコールバックリスナーを登録
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteItem();
                    }
                });
        // AlertDialogのNoボタンのコールバックリスナーを登録
        alertDialogBuilder.setNeutralButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        // AlertDialogのキャンセルができるように設定
        alertDialogBuilder.setCancelable(true);

        AlertDialog alertDialog = alertDialogBuilder.create();
        // AlertDialogの表示
        alertDialog.show();
    }*/

    private void deleteItem(int position) {
        // それぞれの要素を削除
        itemNames.remove(position);

        // ListView の更新
        adapter.notifyDataSetChanged();
    }
}