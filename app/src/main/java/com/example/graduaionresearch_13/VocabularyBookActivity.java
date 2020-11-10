package com.example.graduaionresearch_13;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VocabularyBookActivity extends AppCompatActivity {

    private ListViewAdapter adapter;

    // 要素の削除、順番変更のためArrayListを定義
    private List<String> itemNames;
    // タップされたitemの位置
    private int tappedPosition = 0;
    //サンプルデータ
    List<VocabularyBook> scenes = new ArrayList<>(Arrays.asList(
            new VocabularyBook(1,"現国"),
            new VocabularyBook(2,"数学Ⅲ"),
            new VocabularyBook(3,"世界史")
    ));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocabulary_book_list);

        // ListViewのインスタンスを生成
        ListView listView = (ListView) findViewById(R.id.listView);

        // BaseAdapter を継承したadapterのインスタンスを生成
        // レイアウトファイル list.xml を activity_main.xml に inflate するためにadapterに引数として渡す
        adapter = new ListViewAdapter(this.getApplicationContext(),
                R.layout.row, scenes);

        // ListViewにadapterをセット
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if(R.id.menu != id){
                    return;
                }
                final String[] items = {"編集", "削除", "タイトル編集","キャンセル"};
                new AlertDialog.Builder(VocabularyBookActivity.this)
                        .setTitle("Selector")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        screenTransition(position);
                                        break;
                                    case 1:
                                        showDeleteCheck(position);
                                        break;
                                    case 2:
                                        showTitleEdit(position);
                                        break;
                                    case 3:
                                        break;
                                }
                            }
                        }).setCancelable(true)
                        .show();
            }
        });

    }
    //問題一覧画面に遷移
    private void screenTransition(int position){
        Intent intent = new Intent(getApplication(), ProblemListActivity.class);
        intent.putExtra("VocabularyBook",adapter.getItem(position));
        startActivity(intent);
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

    //削除処理
    private void deleteItem(int position) {
        // それぞれの要素を削除
        adapter.delete(position);

        // ListView の更新
        adapter.notifyDataSetChanged();
    }

    //編集画面の出力
    private void showTitleEdit(final int position){
        LayoutInflater factory = LayoutInflater.from(this);
        final View inputView = factory.inflate(R.layout.vb_titleedit_diarog, null);

        new android.app.AlertDialog.Builder(VocabularyBookActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("編集")
                .setView(inputView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        EditText title = inputView.findViewById(R.id.dialog_edit_title);
                        edit(position, title.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                })
                .create().show();
    }

    private void edit(int position, String title){
        adapter.edit(getApplication(), position, title);
        adapter.notifyDataSetChanged();
    }
}