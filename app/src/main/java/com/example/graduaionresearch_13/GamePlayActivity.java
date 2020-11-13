package com.example.graduaionresearch_13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.widget.TextView;

public class GamePlayActivity extends AppCompatActivity implements View.OnClickListener{
    private final List<Problem> problmes = new ArrayList<>(Arrays.asList(
            new Problem(1,"jvmとは","java仮想マシン",1),
            new Problem(2,"jdkとは","java開発環境",1),
            new Problem(3,"javacとは","コンパイル",1)
    ));
    private Button answer_button;
    private TextView textview1;
    private int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_play);
        index = 0;

        final Button start_button = findViewById(R.id.gamestart_button);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.game_production);
                answer_button = findViewById(R.id.nextproblem_button);
                answer_button.setOnClickListener(GamePlayActivity.this);
                textview1=(TextView)findViewById(R.id.textView);
            }
        });
    }

    @Override
    public void onClick(View V) {
        textview1.setText(problmes.get(index).getProblem());
        index++;
    }
}