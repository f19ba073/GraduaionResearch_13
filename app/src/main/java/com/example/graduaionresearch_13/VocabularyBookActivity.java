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
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VocabularyBookActivity extends AppCompatActivity {

    private VocabularyBookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocabulary_book_list);

        // ツールバーをアクションバーとしてセット
        final Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("単語帳一覧");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // ListViewのインスタンスを生成
        ListView listView = (ListView) findViewById(R.id.listView);

        // BaseAdapter を継承したadapterのインスタンスを生成
        // レイアウトファイル list.xml を activity_main.xml に inflate するためにadapterに引数として渡す
        adapter = new VocabularyBookAdapter(this.getApplicationContext(),
                R.layout.vocabularybook_row, VocabularyBook.getList(getApplication()));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                //リスト自体をクリックされた場合
                if(R.id.menu != id){
                    screenTransition(adapter.getItem(position), GamePlayActivity.class);
                    return;
                }

                //メニューボタンをクリックされた場合
                final String[] menus = {"編集", "削除", "タイトル編集","統計","キャンセル"};
                new AlertDialog.Builder(VocabularyBookActivity.this)
                        .setTitle("Selector")
                        .setItems(menus, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        screenTransition(adapter.getItem(position),
                                                ProblemListActivity.class);
                                        break;
                                    case 1:
                                        showDeleteCheck(position);
                                        break;
                                    case 2:
                                        showTitleEdit(position);
                                        break;
                                    case 3:
                                        screenTransition(adapter.getItem(position),
                                                VocabularyBookGraphActivity.class);
                                        break;
                                    case 4:
                                        break;
                                }
                            }
                        }).setCancelable(true)
                        .show();
            }
        });
        listView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.add_vocabulary);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater factory = LayoutInflater.from(getApplication());
                final View inputView = factory.inflate(R.layout.vocabulary_book_title_edit_diarog, null);

                createEditDialogTemplate()
                        .setTitle("新規")
                        .setView(inputView)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                EditText newTitle = inputView.findViewById(R.id.dialog_edit_title);
                                addList(newTitle.getText().toString());
                            }
                        })
                        .create().show();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.update(getApplication());
    }

    //ツールバーバックボタン
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    //別画面に遷移
    private void screenTransition(VocabularyBook vocabularyBook, Class nextScreen){
        Intent intent = new Intent(getApplication(), nextScreen);
        intent.putExtra("VocabularyBook", vocabularyBook);
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
        adapter.delete(getApplication(), position);

        // ListView の更新
        adapter.notifyDataSetChanged();
    }

    //編集画面の出力
    private void showTitleEdit(final int position){
        LayoutInflater factory = LayoutInflater.from(this);
        final View inputView = factory.inflate(R.layout.vocabulary_book_title_edit_diarog, null);

        createEditDialogTemplate()
                .setTitle("編集")
                .setView(inputView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        EditText newTitle = inputView.findViewById(R.id.dialog_edit_title);
                        edit(position, newTitle.getText().toString());
                    }
                }).create().show();
    }

    private AlertDialog.Builder createEditDialogTemplate(){
        AlertDialog.Builder builder =
                new AlertDialog.Builder(VocabularyBookActivity.this)
                .setIcon(R.drawable.ic_edit)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {}
                });

        return builder;
    }

    private void edit(int position, String newTitle){
        if(newTitle.isEmpty()){
            return;
        }
        adapter.edit(getApplication(), position, newTitle);
        adapter.notifyDataSetChanged();
    }

    private void addList(String newTitle){
        if(newTitle.isEmpty()){
            return;
        }
        VocabularyBook createNewBook =  adapter.add(getApplication(), newTitle);
        adapter.notifyDataSetChanged();
        screenTransition(createNewBook, ProblemListActivity.class);
    }

}