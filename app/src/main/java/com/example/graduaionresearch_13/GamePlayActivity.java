package com.example.graduaionresearch_13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GamePlayActivity extends AppCompatActivity {
    private final List<Problem> problmes = new ArrayList<>(Arrays.asList(
            new Problem(1,"jvmとは","java仮想マシン",1),
            new Problem(2,"jdkとは","java開発環境",1),
            new Problem(3,"javacとは","コンパイル",1)
    ));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_play);

        final Button start_button = findViewById(R.id.gamestart_button);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.game_production);
            }
        });
    }
}