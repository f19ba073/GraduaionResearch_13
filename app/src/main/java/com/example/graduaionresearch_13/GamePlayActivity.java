package com.example.graduaionresearch_13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.*;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class GamePlayActivity extends AppCompatActivity{

    private List<Problem> problems;
    private int problemsIndex;
    private List<Result> results;

    private Button nextTransitionButton;
    private Button endButton;
    private TextView problemTextView;
    private EditText editAnswerText;
    private ImageView correctOrWrongImage;

    private ListView resultListView;

    private OnClickNextProblem onClickNextProblem = new OnClickNextProblem();
    private OnClickResultCheck onClickResultCheck = new OnClickResultCheck();
    private View.OnClickListener onClickTransitionResult = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            initializeGameResult();
        }
    };

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

        results = new ArrayList<>(problems.size());

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

    //結果画面の初期化
    private void initializeGameResult(){
        setContentView(R.layout.game_result);

        resultListView = findViewById(R.id.result_listView);
        LayoutInflater inflater = this.getLayoutInflater();
        View header = inflater.inflate(R.layout.result_header, null);
        resultListView.addHeaderView(header);

        GameResultAdapter adapter = new GameResultAdapter(getApplicationContext(), R.layout.result_row, results);
        resultListView.setAdapter(adapter);
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
                results.add(new Result(
                        problems.get(problemsIndex),
                        true,
                        editedAnswer
                ));
            }else{
                correctOrWrongImage.setImageResource(R.drawable.ic_wrong);
                results.add(new Result(
                        problems.get(problemsIndex),
                        false,
                        editedAnswer
                ));
            }

            problemsIndex++;

            //問題が終わっていなければ次の問題に遷移するクリックイベントに切り替え
            //終わっていれば結果画面に遷移するクリックイベントに切り替え
            if(problemsIndex < problems.size()){
                nextTransitionButton.setOnClickListener(onClickNextProblem);
                nextTransitionButton.setText("次の問題へ");
            }else{
                nextTransitionButton.setOnClickListener(onClickTransitionResult);
                nextTransitionButton.setText("結果");
            }
        }
    }
}