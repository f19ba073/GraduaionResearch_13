package com.example.graduaionresearch_13;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.*;

public class VocabularyBookGraphActivity extends AppCompatActivity {
    private VocabularyBook currentVocabularyBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocabulary_book_graph);

        Intent intent = getIntent();
        currentVocabularyBook = (VocabularyBook) (intent.getSerializableExtra("VocabularyBook"));

        final Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(currentVocabularyBook.getBook_name());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int book_id = currentVocabularyBook.getBook_id();
        List<VocabularyBookLog> list = VocabularyBookLog.getList(getApplication(), book_id);
        drawGraph(list);
    }

    private void drawGraph(List<VocabularyBookLog> list){
        //グラフの設定
        LineChart lineChart = (LineChart) findViewById(R.id.accuracy_rate_graph);
        lineChart.getDescription().setEnabled(false);
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.setDrawGridBackground(true);
        lineChart.setGridBackgroundColor(0xFFDDDDD);
        lineChart.setClickable(false);

        //X,Y軸線の色が場所によって変わってしまうため非表示
        //解決出来たら軸線の表示をしたい
        //X軸の設定
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //Y軸の設定
        lineChart.getAxisRight().setEnabled(false);
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setDrawGridLines(false);
        yAxis.setAxisMaximum(100.0f);
        yAxis.setAxisMinimum(0.0f);

        //テストのため1を引数に指定
        lineChart.setData(createLineData(list));
        lineChart.animateY(2500);
    }

    private LineData createLineData(List<VocabularyBookLog> logs){
        Collections.sort(logs);

        //正解率のデータをEntry型に変換
        List<Entry> entryList = new ArrayList<>(logs.size());
        for(int i = 0, n = logs.size(); i < n; i++){
            VocabularyBookLog log = logs.get(i);
            entryList.add(new Entry(i, log.getAccuracy_rate()));
        }

        //Entryのリストを集約して折れ線グラフに変換
        LineDataSet lineDataSet = new LineDataSet(entryList, "正解率");

        //折れ線グラフ
        lineDataSet.setDrawValues(false);
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setColor(Color.GREEN);
        lineDataSet.setLineWidth(2f);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(Color.GREEN);

        List<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet);

        return new LineData(iLineDataSets);
    }
}
