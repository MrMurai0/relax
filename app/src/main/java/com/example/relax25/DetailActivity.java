package com.example.relax25;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView detailTextView = findViewById(R.id.detailTextView);
        TextView dataNotesTextView = findViewById(R.id.Data_Notes);


        String item = getIntent().getStringExtra("ITEM");


        detailTextView.setText("Заметка");
        dataNotesTextView.setText(item);
    }
}
