package com.example.dinoquestionandkids.juego;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dinoquestionandkids.menu.MenuActivity;
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
    private Button btnComenzarJuego, btnInfo, btnSonido;
    private MediaPlayer mp;
    private int contador = 0;
    private int contador2 = (int)(Math.random()*5+1);
    private ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        //poner sonido ciclado
        mp = MediaPlayer.create(this, R.raw.juego_inicio);
        mp.start();
        mp.setLooping(true);

        //poner imagen
        imagen = findViewById(R.id.imagen);
        ponerImagen();

        miAuth = FirebaseAuth.getInstance();
        miBD = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        btnComenzarJuego = (Button) findViewById(R.id.btnComenzarJuego);
        btnInfo = (Button) findViewById(R.id.btnInfo);
        btnSonido = findViewById(R.id.btnSonido);

        //poner icono en el actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icono_barra);

        obtenerNivelUsuario();

        btnSonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contador == 0){
                    contador = 10;
                    btnSonido.setBackgroundResource(R.drawable.ic_volumen);
                    mp.pause();
                }else if(contador == 10){
                    contador = 0;
                    btnSonido.setBackgroundResource(R.drawable.ic_volumen_si);
                    mp.start();
                }
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pararMusica();
                Intent i = new Intent(JuegoActivity.this, InformacionActivity.class);
                i.putExtra((String)getResources().getText(R.string.activity), (String)getResources().getText(R.string.juegoActivity));
                startActivity(i);
            }
        });

        btnComenzarJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pararMusica();
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

    private void ponerImagen() {
        if (contador2 == 0){
            imagen.setImageResource(R.drawable.stegosaurus);
        }else if (contador2 == 1){
            imagen.setImageResource(R.drawable.compsognathus);
        }else if (contador2 == 2){
            imagen.setImageResource(R.drawable.tyrannosaurusrex);
        }else if (contador2 == 3){
            imagen.setImageResource(R.drawable.iguanodon);
        }else if (contador2 == 4){
            imagen.setImageResource(R.drawable.microraptor);
        }else if (contador2 == 5){
            imagen.setImageResource(R.drawable.carnotaurus);
        }
    }

    private void obtenerNivelUsuario(){
        String id = miAuth.getCurrentUser().getUid();
        miBD.child((String) getResources().getText(R.string.usuarios)).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(user != null && dataSnapshot.exists()){
                    nivel = dataSnapshot.child((String)getResources().getText(R.string.nivel)).getValue().toString();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(JuegoActivity.this, getResources().getText(R.string.no_acceso_datos), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //acciones boton de 'atras'
    @Override
    public void onBackPressed() {
        pararMusica();
        startActivity(new Intent(JuegoActivity.this, MenuActivity.class));
        finish();
    }

    private void pararMusica(){
        mp.stop();
    }
}
