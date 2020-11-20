package com.example.graduaionresearch_13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.*;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class GamePlayActivity extends AppCompatActivity{

    private List<Problem> problmes = new ArrayList<>(Arrays.asList(
            new Problem(1,"jvmとは","java仮想マシン",1),
            new Problem(2,"jdkとは","java開発環境",1),
            new Problem(3,"javacとは","コンパイル",1)
    ));
    private int problemsIndex;

    private Button nextTransitionButton;
    private Button endButton;
    private TextView problemTextView;
    private EditText editAnswerText;
    private ImageView correctOrWrongImage;

    private OnClickNextProblem onClickNextProblem = new OnClickNextProblem();
    private OnClickResultCheck onClickResultCheck = new OnClickResultCheck();

    private VocabularyBook currentVocabularyBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_start);
        problemsIndex = 0;

        //画面遷移を実装する場合の処理
        Intent intent = getIntent();
        currentVocabularyBook = (VocabularyBook)intent.getSerializableExtra("VocabularyBook");
        problmes = Problem.getList(getApplication(), currentVocabularyBook.getBook_id());
        setTitle(currentVocabularyBook.getBook_name());

        Button start_button = findViewById(R.id.gamestart_button);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeGameProduction();
            }
        });
    }

    private void initializeGameProduction(){
        setContentView(R.layout.game_play);
        nextTransitionButton = findViewById(R.id.next_transition_button);
        endButton = findViewById(R.id.end_button);
        problemTextView = findViewById(R.id.problem_text_view);
        editAnswerText = findViewById(R.id.edit_answer_text);
        correctOrWrongImage = findViewById(R.id.correct_wrong_image);

        setProblemWithIndex(problemsIndex);
        nextTransitionButton.setOnClickListener(onClickResultCheck);
    }

    //TODO 結果画面の初期化処理を実装
    private void initializeGameResult(){
        setContentView(R.layout.game_result);
    }

    private void setProblemWithIndex(int index){
        problemTextView.setText(problmes.get(index).getProblem());
    }

    private class OnClickNextProblem implements View.OnClickListener{
        @Override
        public void onClick(View v){
            editAnswerText.getEditableText().clear();
            setProblemWithIndex(problemsIndex);
            correctOrWrongImage.setImageDrawable(null);

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
                correctOrWrongImage.setImageResource(R.drawable.ic_correct);
            }else{
                //不正解
                correctOrWrongImage.setImageResource(R.drawable.ic_wrong);
            }

            nextTransitionButton.setOnClickListener(onClickNextProblem);
            nextTransitionButton.setText("次の問題へ");
            problemsIndex++;
        }
    }
}