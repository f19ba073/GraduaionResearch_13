package com.example.graduaionresearch_13;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.*;

public class ProblemListActivity extends AppCompatActivity {

    //テストデータ
    private List<Problem> problmes = new ArrayList<>(Arrays.asList(
            new Problem(1,"jvmとは","java仮想マシン",1),
            new Problem(2,"jdkとは","java開発環境",1),
            new Problem(3,"javacとは","コンパイル",1)
    ));
    private ProblemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.problem_list);

        //データベースからデータ取得
        //problmes = Problem.getList(getApplication(), 1);
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

    //削除確認画面の出力
    private void showDeleteCheck(final int position) {
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

        alertDialogBuilder.create().show();
    }

    //編集画面の出力
    private void showEditDialog(final int position){
        LayoutInflater factory = LayoutInflater.from(this);
        final View inputView = factory.inflate(R.layout.problem_text_diarog, null);

        new AlertDialog.Builder(ProblemListActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("編集")
                .setView(inputView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        EditText problem = inputView.findViewById(R.id.dialog_edit_problem);
                        EditText answer = inputView.findViewById(R.id.dialog_edit_answer);
                        edit(position, problem.getText().toString(), answer.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                })
                .create().show();
    }

    private void delete(int position){
        adapter.delete(getApplication(), position);
        adapter.notifyDataSetChanged();
    }

    private void edit(int position, String problem, String answer){
        adapter.edit(getApplication(), position, problem, answer);
        adapter.notifyDataSetChanged();
    }
}