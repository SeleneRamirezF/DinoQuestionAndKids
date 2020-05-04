package com.example.dinoquestionandkids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PuzzleActivity extends AppCompatActivity {

    private Button btnPuzzle1;
    private Button btnPuzzle2;
    private Button btnPuzzle3;
    private Button btnPuzzle4;
    private Button btnPuzzle5;
    private Button btnPuzzle6;
    private int boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        btnPuzzle1 = findViewById(R.id.btnPuzzle1);
        btnPuzzle2 = findViewById(R.id.btnPuzzle2);
        btnPuzzle3 = findViewById(R.id.btnPuzzle3);
        btnPuzzle4 = findViewById(R.id.btnPuzzle4);
        btnPuzzle5 = findViewById(R.id.btnPuzzle5);
        btnPuzzle6 = findViewById(R.id.btnPuzzle6);


        btnPuzzle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boton = 1;
                Intent i = new Intent(PuzzleActivity.this, Puzzle2Activity.class);
                i.putExtra("boton", boton);
                startActivity(i);
            }
        });

        btnPuzzle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boton = 2;
                Intent i = new Intent(PuzzleActivity.this, Puzzle2Activity.class);
                i.putExtra("boton", boton);
                startActivity(i);
            }
        });

        btnPuzzle3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boton = 3;
                Intent i = new Intent(PuzzleActivity.this, Puzzle2Activity.class);
                i.putExtra("boton", boton);
                startActivity(i);
            }
        });

        btnPuzzle4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boton = 4;
                Intent i = new Intent(PuzzleActivity.this, Puzzle2Activity.class);
                i.putExtra("boton", boton);
                startActivity(i);
            }
        });

        btnPuzzle5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boton = 5;
                Intent i = new Intent(PuzzleActivity.this, Puzzle2Activity.class);
                i.putExtra("boton", boton);
                startActivity(i);
            }
        });

        btnPuzzle6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boton = 6;
                Intent i = new Intent(PuzzleActivity.this, Puzzle2Activity.class);
                i.putExtra("boton", boton);
                startActivity(i);
            }
        });

    }
}
