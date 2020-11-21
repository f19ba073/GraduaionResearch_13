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

    private List<Problem> problems;
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

        //単語帳一覧から渡されたVocabularyBookオブジェクト受け取り
        Intent intent = getIntent();
        currentVocabularyBook = (VocabularyBook)intent.getSerializableExtra("VocabularyBook");
        problems = Problem.getList(getApplication(), currentVocabularyBook.getBook_id());
        setTitle(currentVocabularyBook.getBook_name());

        Button start_button = findViewById(R.id.gamestart_button);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeGamePlay();
            }
        });
    }

    //問題回答画面の初期化
    private void initializeGamePlay(){
        setContentView(R.layout.game_play);

        //xmlからコンポーネント読み込み
        nextTransitionButton = findViewById(R.id.next_transition_button);
        endButton            = findViewById(R.id.end_button);
        problemTextView      = findViewById(R.id.problem_text_view);
        editAnswerText       = findViewById(R.id.edit_answer_text);
        correctOrWrongImage  = findViewById(R.id.correct_wrong_image);

        setProblemWithIndex(problemsIndex);
        nextTransitionButton.setOnClickListener(onClickResultCheck);
    }

    //TODO 結果画面の初期化処理を実装
    private void initializeGameResult(){
        setContentView(R.layout.game_result);
    }

    private void setProblemWithIndex(int index){
        problemTextView.setText(problems.get(index).getProblem());
    }

    private class OnClickNextProblem implements View.OnClickListener{
        @Override
        public void onClick(View v){

            //問題文、回答領域、〇×表示の初期化
            setProblemWithIndex(problemsIndex);
            editAnswerText.getEditableText().clear();
            correctOrWrongImage.setImageDrawable(null);

            //正誤判定のためクリックイベント切り替え
            nextTransitionButton.setOnClickListener(onClickResultCheck);
            nextTransitionButton.setText("正解へ");
        }
    }

    private class OnClickResultCheck implements View.OnClickListener{
        @Override
        public void onClick(View v){
            String editedAnswer = editAnswerText.getText().toString();
            String correctAnswer = problems.get(problemsIndex).getAnswer();

            //正誤判定
            if(editedAnswer.equals(correctAnswer)){
                correctOrWrongImage.setImageResource(R.drawable.ic_correct);
            }else{
                correctOrWrongImage.setImageResource(R.drawable.ic_wrong);
            }

            //次の問題に遷移できるようにクリックイベント切り替え
            nextTransitionButton.setOnClickListener(onClickNextProblem);
            nextTransitionButton.setText("次の問題へ");
            problemsIndex++;
        }
    }
}