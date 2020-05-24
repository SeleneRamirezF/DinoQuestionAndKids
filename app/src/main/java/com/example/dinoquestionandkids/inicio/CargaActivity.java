package com.example.dinoquestionandkids.inicio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.dinoquestionandkids.R;

public class CargaActivity extends AppCompatActivity {

    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga);

        //codigo para poner sonido
        mp = MediaPlayer.create(this,R.raw.carga);
        mp.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mp.stop();
            }
        }, 2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(CargaActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 1700);
    }
    //acciones del boton de 'atras'
    private boolean canExitApp = false;
    @Override
    public void onBackPressed() {
        if (!canExitApp) {
            canExitApp = true;
            Toast.makeText(this, "Pulse otra vez para salir", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    canExitApp = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
        }
    }
}
