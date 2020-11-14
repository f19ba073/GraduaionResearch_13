package com.example.graduaionresearch_13;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.widget.EditText;
import android.widget.TextView;

public class GamePlayActivity extends AppCompatActivity{

    private final List<Problem> problmes = new ArrayList<>(Arrays.asList(
            new Problem(1,"jvmとは","java仮想マシン",1),
            new Problem(2,"jdkとは","java開発環境",1),
            new Problem(3,"javacとは","コンパイル",1)
    ));
    private int problemsIndex;

    private Button nextTransitionButton;
    private Button endButton;
    private TextView problemTextView;
    private EditText editAnswerText;

    private OnClickNextProblem onClickNextProblem = new OnClickNextProblem();
    private OnClickResultCheck onClickResultCheck = new OnClickResultCheck();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_play);
        problemsIndex = 0;

        final Button start_button = findViewById(R.id.gamestart_button);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.game_production);
                nextTransitionButton = findViewById(R.id.next_transition_button);
                endButton = findViewById(R.id.end_button);
                problemTextView = findViewById(R.id.problem_text_view);
                editAnswerText = findViewById(R.id.edit_answer_text);

                setProblemWithIndex(problemsIndex);
                nextTransitionButton.setOnClickListener(onClickResultCheck);
            }
        });
    }

    private void setProblemWithIndex(int index){
        problemTextView.setText(problmes.get(index).getProblem());
    }

    private class OnClickNextProblem implements View.OnClickListener{
        @Override
        public void onClick(View v){
            nextTransitionButton.setText("正解へ");
            setProblemWithIndex(problemsIndex);
            nextTransitionButton.setOnClickListener(onClickResultCheck);
        }
    }

    private class OnClickResultCheck implements View.OnClickListener{
        @Override
        public void onClick(View v){
            if(editAnswerText.getText().equals(problmes.get(problemsIndex))){
                //正解
                Log.d("answer","正解");
            }else{
                //不正解
                Log.d("answer","不正解");
            }

            nextTransitionButton.setText("次の問題へ");
            nextTransitionButton.setOnClickListener(onClickNextProblem);
            problemsIndex++;
        }
    }
}