package com.example.dinoquestionandkids.juego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;

import com.example.dinoquestionandkids.R;

public class GanarActivity extends AppCompatActivity {

    private MediaPlayer mpGanar;
    private Vibrator vibrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganar);

        mpGanar = MediaPlayer.create(this, R.raw.ganar);
        mpGanar.start();

        vibrar = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibrar.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));

        //poner icono en el actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icono_barra);

        //creo un hilo de espera para cerrar el activity
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // acciones que se ejecutan tras los milisegundos
                startActivity(new Intent(GanarActivity.this, JuegoActivity.class));
                finish();
            }
        }, 5000);
    }
}
