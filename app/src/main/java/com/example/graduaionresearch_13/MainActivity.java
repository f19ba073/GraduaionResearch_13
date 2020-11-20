package com.example.graduaionresearch_13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        int[] percent = {0,20,40,60,80};
        ImageView imageView1 = findViewById(R.id.image_view_1);

        switch(percent[0]/20) {
            case 0:
                imageView1.setImageResource(R.drawable.faketree);
                break;
            case 1:
                imageView1.setImageResource(R.drawable.genkinaki);
                break;
            case 2:
                imageView1.setImageResource(R.drawable.faketree);
                break;
            case 3:
                imageView1.setImageResource(R.drawable.genkinaki);
                break;
            case 4:
                imageView1.setImageResource(R.drawable.faketree);
                break;
        }

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