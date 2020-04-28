package com.example.dinoquestionandkids.juego;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dinoquestionandkids.R;
import com.example.dinoquestionandkids.informacion.InformacionActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JuegoActivity extends AppCompatActivity {

    private FirebaseAuth miAuth;
    private DatabaseReference miBD;
    private FirebaseUser user;
    private String nivel;
    private Button btnComenzarJuego, btnInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        miAuth = FirebaseAuth.getInstance();
        miBD = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        btnComenzarJuego = (Button) findViewById(R.id.btnComenzarJuego);
        btnInfo = (Button) findViewById(R.id.btnInfo);

        //poner icono en el actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        obtenerNivelUsuario();

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JuegoActivity.this, InformacionActivity.class));
            }
        });

        btnComenzarJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nivel.equalsIgnoreCase("1")){
                    startActivity(new Intent(JuegoActivity.this, Juego1Activity.class));
                    finish();
                }
                if(nivel.equalsIgnoreCase("2")){
                    startActivity(new Intent(JuegoActivity.this, Juego2Activity.class));
                    finish();
                }
                if(nivel.equalsIgnoreCase("3")){
                    startActivity(new Intent(JuegoActivity.this, Juego3Activity.class));
                    finish();
                }
                if(nivel.equalsIgnoreCase("4")){
                    startActivity(new Intent(JuegoActivity.this, Juego4Activity.class));
                    finish();
                }
                if(nivel.equalsIgnoreCase("5")){
                    startActivity(new Intent(JuegoActivity.this, Juego5Activity.class));
                    finish();
                }
                if(nivel.equalsIgnoreCase("6")){
                    startActivity(new Intent(JuegoActivity.this, Juego6Activity.class));
                    finish();
                }
            }
        });

    }

    private void obtenerNivelUsuario(){
        String id = miAuth.getCurrentUser().getUid();
        miBD.child((String) getResources().getText(R.string.usuarios)).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(user != null && dataSnapshot.exists()){
                    nivel = dataSnapshot.child("nivel").getValue().toString();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(JuegoActivity.this, "No se ha podido acceder a los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
