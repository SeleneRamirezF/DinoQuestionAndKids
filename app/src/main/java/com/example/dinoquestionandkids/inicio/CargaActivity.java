package com.example.dinoquestionandkids.inicio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.dinoquestionandkids.MainActivity;
import com.example.dinoquestionandkids.R;

public class CargaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(CargaActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 1500);
    }
}
