package com.example.graduaionresearch_13;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // ツールバーをアクションバーとしてセット
        final Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ホーム");


        ImageView imageView1 = findViewById(R.id.image_view_1);
        int[] illust = {R.drawable.tree_seichou01,
                            R.drawable.tree_seichou02,
                            R.drawable.tree_seichou03,
                            R.drawable.tree_seichou04,
                            R.drawable.tree_seichou05};

        TreeInformation tree = TreeInformation.getInstance(getApplication());
        int percent = ((int)tree.getTreeRate()) / 20;
        imageView1.setImageResource(illust[percent]);

        final TextView textView = findViewById(R.id.tree_rate);
        textView.setText("成長率" + tree.getTreeRate() + "％");

        ProgressBar progressBar = findViewById(R.id.tree_rate_bar);
        progressBar.setMax(100);
        progressBar.setProgress((int)tree.getTreeRate());

        TextView treeCount = findViewById(R.id.tree_count_text);
        treeCount.setText(" × " + tree.getTreeCount());

        final Button start_button = findViewById(R.id.start_button);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), VocabularyBookActivity.class);
                startActivity(intent);
            }
        });
    }
}