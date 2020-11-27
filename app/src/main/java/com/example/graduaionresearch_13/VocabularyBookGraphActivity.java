package com.example.graduaionresearch_13;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.*;

public class VocabularyBookGraphActivity extends AppCompatActivity {
    private VocabularyBook currentVocabularyBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocabulary_book_graph);

        LineChart lineChart = (LineChart) findViewById(R.id.accuracy_rate_graph);

        //テストのため1を引数に指定
        lineChart.setData(createLineData(1));
    }

    private LineData createLineData(int book_id){
        List<VocabularyBookLog> logs;

        //getListを実装したらこのコードを使用
        //logs = VocabularyBookLog.getList(getApplication(), book_id);

        //サンプルデータ
        logs = new ArrayList<>();
        logs.add(new VocabularyBookLog(1, 70.0f, 1));
        logs.add(new VocabularyBookLog(2, 75.0f, 1));
        logs.add(new VocabularyBookLog(3, 78.5f, 1));
        logs.add(new VocabularyBookLog(4, 90.6f, 1));
        logs.add(new VocabularyBookLog(5, 85.8f, 1));

        Collections.sort(logs);
        List<Entry> entryList = new ArrayList<>(logs.size());
        for(int i = 0, n = logs.size(); i < n; i++){
            VocabularyBookLog log = logs.get(i);
            entryList.add(new Entry(i, log.getAccuracy_rate()));
        }

        LineDataSet lineDataSet         = new LineDataSet(entryList, "正解率");
        List<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet);
        return new LineData(iLineDataSets);
    }
}
