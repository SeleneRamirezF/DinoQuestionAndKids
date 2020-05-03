package com.example.dinoquestionandkids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PuzzleActivity extends AppCompatActivity {

    private Button btnPuzzle1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        btnPuzzle1 = findViewById(R.id.btnPuzzle1);

        btnPuzzle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(PuzzleActivity.this, Puzzle2Activity.class));
            }
        });

    }
}
