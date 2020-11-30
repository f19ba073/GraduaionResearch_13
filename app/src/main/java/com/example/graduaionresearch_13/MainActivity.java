package com.example.graduaionresearch_13;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        ImageView imageView1 = findViewById(R.id.image_view_1);
        int[] illust = {R.drawable.faketree,
                            R.drawable.faketree,
                            R.drawable.faketree,
                            R.drawable.faketree,
                            R.drawable.faketree};

        int percent = 1;
        imageView1.setImageResource(illust[percent]);

        final TextView textView = findViewById(R.id.tree_rate);
        textView.setText("成長率" + TreeInformation.getInstance(getApplication()).getTreeRate() + "％");
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