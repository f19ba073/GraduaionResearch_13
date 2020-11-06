package com.example.graduaionresearch_13;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.*;

public class ProblemListActivity extends AppCompatActivity {
    private final List<Problem> problmes = new ArrayList<>(Arrays.asList(
            new Problem(1,"jvmとは","java仮想マシン",1),
            new Problem(2,"jdkとは","java開発環境",1),
            new Problem(3,"javacとは","コンパイル",1)
    ));
    private ProblemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.problem_list);

        ListView listView = (ListView) findViewById(R.id.problem_listView);
        adapter = new ProblemAdapter(getApplicationContext(), R.layout.problem_row, problmes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final String[] items = {"編集", "削除", "キャンセル"};
                new AlertDialog.Builder(ProblemListActivity.this)
                        .setTitle("Selector")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        edit(position);
                                        break;
                                    case 1:
                                        deleteCheck(position);
                                        break;
                                    case 2:
                                        break;
                                }
                            }
                        })
                        .show();
            }
        });
    }

    private void deleteCheck(final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProblemListActivity.this)
                .setTitle("削除").setMessage("本当に削除しますか？");

        // AlertDialogのYesボタンのコールバックリスナーを登録
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        delete(position);
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
    }


    private void delete(int position){
        adapter.delete(position);
        adapter.notifyDataSetChanged();
    }

    private void edit(int position){

    }
}