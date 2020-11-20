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
import android.widget.ImageView;
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
    private ImageView correctWrongImage;

    private OnClickNextProblem onClickNextProblem = new OnClickNextProblem();
    private OnClickResultCheck onClickResultCheck = new OnClickResultCheck();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_play);
        problemsIndex = 0;

        Button start_button = findViewById(R.id.gamestart_button);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.game_production);
                initializeGameProduction();
            }
        });
    }

    private void initializeGameProduction(){
        nextTransitionButton = findViewById(R.id.next_transition_button);
        endButton = findViewById(R.id.end_button);
        problemTextView = findViewById(R.id.problem_text_view);
        editAnswerText = findViewById(R.id.edit_answer_text);
        correctWrongImage = findViewById(R.id.correct_wrong_image);

        setProblemWithIndex(problemsIndex);
        nextTransitionButton.setOnClickListener(onClickResultCheck);
    }

    //TODO 結果画面の初期化処理を実装
    private void initializeGameResult(){

    }

    private void setProblemWithIndex(int index){
        problemTextView.setText(problmes.get(index).getProblem());
    }

    private class OnClickNextProblem implements View.OnClickListener{
        @Override
        public void onClick(View v){
            editAnswerText.getEditableText().clear();
            setProblemWithIndex(problemsIndex);
            correctWrongImage.setImageDrawable(null);

            nextTransitionButton.setOnClickListener(onClickResultCheck);
            nextTransitionButton.setText("正解へ");
        }
    }

    private class OnClickResultCheck implements View.OnClickListener{
        @Override
        public void onClick(View v){
            String editedAnswer = editAnswerText.getText().toString();
            String correctAnswer = problmes.get(problemsIndex).getAnswer();

            if(editedAnswer.equals(correctAnswer)){
                //正解
                correctWrongImage.setImageResource(R.drawable.ic_correct);
            }else{
                //不正解
                correctWrongImage.setImageResource(R.drawable.ic_wrong);
            }

            nextTransitionButton.setOnClickListener(onClickNextProblem);
            nextTransitionButton.setText("次の問題へ");
            problemsIndex++;
        }
    }
}