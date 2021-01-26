package com.example.graduaionresearch_13;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.*;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class GamePlayActivity extends AppCompatActivity{

    private List<Problem> problems;
    private int problemsIndex;
    private List<Result> results;
    private VocabularyBook currentVocabularyBook;
    private CountDownTimer timer;
    private int correctCount;

    private TextView tenth_minute;
    private TextView first_minute;
    private TextView tenth_second;
    private TextView first_second;

    private Button nextTransitionButton;
    private TextView problemTextView;
    private EditText editAnswerText;
    private ImageView correctOrWrongImage;
    private TextView timerText;

    private ListView resultListView;

    private OnClickNextProblem onClickNextProblem = new OnClickNextProblem();
    private OnClickResultCheck onClickResultCheck = new OnClickResultCheck();
    private View.OnClickListener onClickTransitionResult = new OnClickTransitionResult();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_start);

        //単語帳一覧から渡されたVocabularyBookオブジェクト受け取り
        Intent intent = getIntent();
        currentVocabularyBook = (VocabularyBook)intent.getSerializableExtra("VocabularyBook");

        //DBから問題の一覧を取得してシャッフル
        problems = Problem.getList(getApplication(), currentVocabularyBook.getBook_id());
        Collections.shuffle(problems);
        problemsIndex = 0;
        results = new ArrayList<>(problems.size());

        setTitle(currentVocabularyBook.getBook_name());
        setToolBar();
        setTimeCount(currentVocabularyBook.getTime_limit());

        Button start_button = findViewById(R.id.gamestart_button);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeGamePlay();
            }
        });
    }

    //ツールバーバックボタン
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    //問題回答画面の初期化
    private void initializeGamePlay(){
        currentVocabularyBook.setTime_limit(parseTime());
        currentVocabularyBook.update(getApplication());

        setContentView(R.layout.game_play);

        setToolBar();

        //xmlからコンポーネント読み込み
        nextTransitionButton = findViewById(R.id.next_transition_button);
        problemTextView      = findViewById(R.id.problem_text_view);
        editAnswerText       = findViewById(R.id.edit_answer_text);
        correctOrWrongImage  = findViewById(R.id.correct_wrong_image);
        timerText            = findViewById(R.id.time_text);

        //タイマーの初期化
        timer = new CountDownTimer(currentVocabularyBook.getTime_limit(), 10){
            private SimpleDateFormat dataFormat =
                    new SimpleDateFormat("mm:ss.SSS", Locale.US);

            @Override
            public void onTick(long millisUntilFinished) {
                timerText.setText(dataFormat.format(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                timerText.setText(dataFormat.format(0));
                setResult("時間切れ", false);
                problemsIndex++;
                transitionNext();
            }
        };

        //正解数初期化
        correctCount = 0;

        setProblemWithIndex(problemsIndex);
        nextTransitionButton.setOnClickListener(onClickResultCheck);

        timer.start();
    }

    //結果画面の初期化
    private void initializeGameResult(){
        setContentView(R.layout.game_result);

        setToolBar();

        resultListView = findViewById(R.id.result_listView);
        LayoutInflater inflater = this.getLayoutInflater();
        View header = inflater.inflate(R.layout.result_header, null);
        resultListView.addHeaderView(header);

        GameResultAdapter adapter =
                new GameResultAdapter(getApplicationContext(), R.layout.result_row, results);
        resultListView.setAdapter(adapter);
    }

    private void setToolBar(){
        final Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(currentVocabularyBook.getBook_name());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setProblemWithIndex(int index){
        problemTextView.setText(problems.get(index).getProblem());
    }

    //正解、不正解の表示と結果の記録
    private void setResult(String editedAnswer, boolean isCorrect){
        if(isCorrect){
            correctCount++;
            correctOrWrongImage.setImageResource(R.drawable.ic_correct);
        }else{
            correctOrWrongImage.setImageResource(R.drawable.ic_wrong);
        }

        results.add(new Result(
                problems.get(problemsIndex),
                isCorrect,
                editedAnswer
        ));
    }

    //問題が終わっていなければ次の問題に遷移するクリックイベントに切り替え
    //終わっていれば結果画面に遷移するクリックイベントに切り替え
    private void transitionNext(){
        if(problemsIndex < problems.size()){
            nextTransitionButton.setOnClickListener(onClickNextProblem);
            nextTransitionButton.setText("次の問題へ");
        }else{
            nextTransitionButton.setOnClickListener(onClickTransitionResult);
            nextTransitionButton.setText("結果");
        }
    }

    //制限時間入力画面に時間を設定する
    private void setTimeCount(int millis){
        tenth_minute = findViewById(R.id.tenth_minute_text);
        first_minute = findViewById(R.id.first_minute_text);
        tenth_second = findViewById(R.id.tenth_second_text);
        first_second = findViewById(R.id.first_second_text);

        //分と秒に直して表示
        millis /= 1000;
        tenth_minute.setText("" + millis / 600);
        millis %= 600;
        first_minute.setText("" + millis / 60);
        millis %= 60;
        tenth_second.setText("" + millis / 10);
        millis %= 10;
        first_second.setText("" + millis);

        setTimeUpClickLister(R.id.tenth_minute_up, tenth_minute, 0, 1);
        setTimeUpClickLister(R.id.first_minute_up, first_minute, 0, 9);
        setTimeUpClickLister(R.id.tenth_second_up, tenth_second, 0, 5);
        setTimeUpClickLister(R.id.first_second_up, first_second, 0, 9);
        setTimeDownClickLister(R.id.tenth_minute_down, tenth_minute, 0, 1);
        setTimeDownClickLister(R.id.first_minute_down, first_minute, 0, 9);
        setTimeDownClickLister(R.id.tenth_second_down, tenth_second, 0, 5);
        setTimeDownClickLister(R.id.first_second_down, first_second, 0, 9);
    }

    //入力された制限時間をミリ秒に変換
    private int parseTime(){
        int millis = 0;
        millis += Integer.parseInt(tenth_minute.getText().toString()) * 600;
        millis += Integer.parseInt(first_minute.getText().toString()) * 60;
        millis += Integer.parseInt(tenth_second.getText().toString()) * 10;
        millis += Integer.parseInt(first_minute.getText().toString());
        millis *= 1000;

        return millis;
    }

    //制限時間入力画面のクリックイベント設定
    private void setTimeUpClickLister(int id, final TextView textView, final int min, final int max){
        findViewById(id).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(textView.getText().toString());
                textView.setText("" + increment(value, min, max));
            }
        });
    }

    //制限時間入力画面のクリックイベント設定
    private void setTimeDownClickLister(int id, final TextView textView, final int min, final int max){
        findViewById(id).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(textView.getText().toString());
                textView.setText("" + decrement(value, min, max));
            }
        });
    }

    private int increment(int value, int min, int max){
        value++;
        if(value > max){
            value = min;
        }
        return value;
    }

    private int decrement(int value, int min, int max){
        value--;
        if(value < min){
            value = max;
        }
        return value;
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

            timer.start();
        }
    }

    private class OnClickResultCheck implements View.OnClickListener{
        @Override
        public void onClick(View v){
            timer.cancel();
            String editedAnswer = editAnswerText.getText().toString();
            String correctAnswer = problems.get(problemsIndex).getAnswer();

            //正誤判定
            setResult(editedAnswer, editedAnswer.equals(correctAnswer));

            problemsIndex++;
            transitionNext();
        }
    }

    private class OnClickTransitionResult implements View.OnClickListener{
        @Override
        public void onClick(View v){

            //木の成長率計算
            TreeInformation tree = TreeInformation.getInstance(getApplication());
            tree.addTreeRate(getApplication(),
                    TreeInformation.TREE_RATE_PLUS_POINT * correctCount);

            //成長率が100%に達していたら成長率を初期化して、木の本数をインクリメント
            if(tree.getTreeRate() > TreeInformation.MAX_TREE_RATE){
                tree.setTreeRate(getApplication(), 0);
                tree.incrementTreeCount(getApplication());
            }

            //正解率を計算してDBに登録
            VocabularyBookLog.createNewLog(getApplication(),
                    (float)correctCount / results.size() * 100,
                    currentVocabularyBook.getBook_id());

            initializeGameResult();
        }
    }
}