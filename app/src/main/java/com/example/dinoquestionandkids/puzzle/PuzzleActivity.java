package com.example.dinoquestionandkids.puzzle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dinoquestionandkids.menu.MenuActivity;
import com.example.dinoquestionandkids.R;

public class PuzzleActivity extends AppCompatActivity {

    private Button btnPuzzle1;
    private Button btnPuzzle2;
    private Button btnPuzzle3;
    private Button btnPuzzle4;
    private Button btnPuzzle5;
    private Button btnPuzzle6;
    private int boton;
    private Button info_puzzle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        //poner icono en el actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icono_barra);

        cargarViews();

        info_puzzle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogo();
            }
        });

        btnPuzzle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boton = 1;
                Intent i = new Intent(PuzzleActivity.this, Puzzle2Activity.class);
                i.putExtra((String) getResources().getText(R.string.boton), boton);
                startActivity(i);
            }
        });

        btnPuzzle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boton = 2;
                Intent i = new Intent(PuzzleActivity.this, Puzzle2Activity.class);
                i.putExtra((String) getResources().getText(R.string.boton), boton);
                startActivity(i);
            }
        });

        btnPuzzle3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boton = 3;
                Intent i = new Intent(PuzzleActivity.this, Puzzle2Activity.class);
                i.putExtra((String) getResources().getText(R.string.boton), boton);
                startActivity(i);
            }
        });

        btnPuzzle4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boton = 4;
                Intent i = new Intent(PuzzleActivity.this, Puzzle2Activity.class);
                i.putExtra((String) getResources().getText(R.string.boton), boton);
                startActivity(i);
            }
        });

        btnPuzzle5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boton = 5;
                Intent i = new Intent(PuzzleActivity.this, Puzzle2Activity.class);
                i.putExtra((String) getResources().getText(R.string.boton), boton);
                startActivity(i);
            }
        });

        btnPuzzle6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boton = 6;
                Intent i = new Intent(PuzzleActivity.this, Puzzle2Activity.class);
                i.putExtra((String) getResources().getText(R.string.boton), boton);
                startActivity(i);
            }
        });

    }

    private void mostrarDialogo(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(PuzzleActivity.this);
        builder.setIcon(R.drawable.resolver_puzzle);
        builder.setTitle(getResources().getText(R.string.info_puzzle));
        builder.setPositiveButton(getResources().getText(R.string.listo), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setCancelable(false).show();
    }

    private void cargarViews() {
        btnPuzzle1 = (Button) findViewById(R.id.btnPuzzle1);
        btnPuzzle2 = (Button) findViewById(R.id.btnPuzzle2);
        btnPuzzle3 = (Button) findViewById(R.id.btnPuzzle3);
        btnPuzzle4 = (Button) findViewById(R.id.btnPuzzle4);
        btnPuzzle5 = (Button) findViewById(R.id.btnPuzzle5);
        btnPuzzle6 = (Button) findViewById(R.id.btnPuzzle6);
        info_puzzle = (Button) findViewById(R.id.info_puzzle);
    }

    public void onBackPressed() {
        startActivity(new Intent(PuzzleActivity.this, MenuActivity.class));
        finish();
    }
}
