package com.example.dinoquestionandkids.juego;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dinoquestionandkids.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class PerderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perder);

        //poner icono en el actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //creo un hilo de espera para cerrar el activity
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // acciones que se ejecutan tras los milisegundos
                startActivity(new Intent(PerderActivity.this, JuegoActivity.class));
                finish();
            }
        }, 5000);
    }
}
