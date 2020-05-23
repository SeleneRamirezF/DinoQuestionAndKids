package com.example.dinoquestionandkids.juego;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dinoquestionandkids.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Juego5Activity extends AppCompatActivity {

    private FirebaseAuth miAuth;
    private DatabaseReference miBD;
    private FirebaseUser user;
    private EditText etNombre;
    private EditText etPuntos;
    private TextView etNivel;
    private ImageView ivVidas;
    private TextView tvPregunta;
    private RadioButton rbUno, rbDos, rbTres, rbCuatro;
    private RadioGroup group;
    private Button btnComprobar;
    private String nivel, pregunta;
    private ArrayList<String> listaNivel5;
    private int contador = 0;
    private int  salto = 5,salto1 = 3;
    private int puntos, vidas, nuevoNivel;
    private MediaPlayer mp, mpAcierto, mpFallo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego5);

        //poner sonido ciclado
        mp = MediaPlayer.create(this, R.raw.juego_puzzle);
        mp.start();
        mp.setLooping(true);
        mpAcierto = MediaPlayer.create(this, R.raw.juego_acierto);
        mpFallo = MediaPlayer.create(this, R.raw.juego_fallo);

        group = findViewById(R.id.group);

        cargarViews();

        //poner icono en el actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icono_barra);

        miAuth = FirebaseAuth.getInstance();
        miBD = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        obtenerPreguntas();
        obtenetDatosUsuario();

        listaNivel5 = new ArrayList();

        btnComprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contador++;
                obtenerPreguntas();
                obtenetDatosUsuario();

                nuevoNivel = Integer.parseInt(nivel);

                /* las listas estan rellenas asi que cada vez que se pulse
                se comprobará el nivel y se comparará con el resultado
                según que boton se pulde */

                if(nivel.equalsIgnoreCase("5")){
                    switch (contador){
                        case 2:
                        case 3:
                        case 10:
                        case 11:
                        case 12:
                            if(rbTres.isChecked()){
                                Toast.makeText(Juego5Activity.this,  getResources().getText(R.string.correcta), Toast.LENGTH_SHORT).show();
                                mpAcierto.start();
                                puntos = puntos+6;
                                gestionDatos();
                            }else{
                                Toast.makeText(Juego5Activity.this, getResources().getText(R.string.incorrecta), Toast.LENGTH_SHORT).show();
                                mpFallo.start();
                                vidas--;
                                gestionDatos();
                            }
                            group.clearCheck();
                            break;
                        case 5:
                        case 6:
                        case 9:
                            if(rbUno.isChecked()){
                                Toast.makeText(Juego5Activity.this,  getResources().getText(R.string.correcta), Toast.LENGTH_SHORT).show();
                                mpAcierto.start();
                                puntos = puntos+6;
                                gestionDatos();
                            }else{
                                Toast.makeText(Juego5Activity.this, getResources().getText(R.string.incorrecta), Toast.LENGTH_SHORT).show();
                                mpFallo.start();
                                vidas--;
                                gestionDatos();
                            }
                            group.clearCheck();
                            break;
                        case 1:
                        case 7:
                        case 13:
                            if(rbDos.isChecked()){
                                Toast.makeText(Juego5Activity.this,  getResources().getText(R.string.correcta), Toast.LENGTH_SHORT).show();
                                mpAcierto.start();
                                puntos = puntos+6;
                                gestionDatos();
                            }else{
                                Toast.makeText(Juego5Activity.this, getResources().getText(R.string.incorrecta), Toast.LENGTH_SHORT).show();
                                mpFallo.start();
                                vidas--;
                                gestionDatos();
                            }
                            group.clearCheck();
                            break;
                        case 4:
                        case 8:
                            if(rbCuatro.isChecked()){
                                Toast.makeText(Juego5Activity.this,  getResources().getText(R.string.correcta), Toast.LENGTH_SHORT).show();
                                mpAcierto.start();
                                puntos = puntos+6;
                                gestionDatos();
                            }else{
                                Toast.makeText(Juego5Activity.this, getResources().getText(R.string.incorrecta), Toast.LENGTH_SHORT).show();
                                mpFallo.start();
                                vidas--;
                                gestionDatos();
                            }
                            group.clearCheck();
                            break;
                    }
                }
            }
        });
    }

    private void cargarViews(){
        etNombre = (EditText) findViewById(R.id.etNombre);
        etPuntos = (EditText) findViewById(R.id.etPuntos);
        ivVidas = (ImageView) findViewById(R.id.ivVidas);
        tvPregunta = (TextView) findViewById(R.id.tvPregunta);
        rbUno = (RadioButton) findViewById(R.id.rbUno);
        rbDos = (RadioButton) findViewById(R.id.rbDos);
        rbTres = (RadioButton) findViewById(R.id.rbTres);
        rbCuatro = (RadioButton) findViewById(R.id.rbCuatro);
        btnComprobar = (Button) findViewById(R.id.btnComprobar);
        etNivel = (TextView) findViewById(R.id.etNivel);
    }

    private void obtenetDatosUsuario(){
        String id = miAuth.getCurrentUser().getUid();
        miBD.child((String) getResources().getText(R.string.usuarios)).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(user != null){
                    etNombre.setText(user.getDisplayName());
                    etPuntos.setText(dataSnapshot.child((String) getResources().getText(R.string.puntos)).getValue().toString());
                    puntos = Integer.parseInt(dataSnapshot.child((String) getResources().getText(R.string.puntos)).getValue().toString());
                    nivel = dataSnapshot.child((String) getResources().getText(R.string.nivel)).getValue().toString();
                }
                if (dataSnapshot.exists()){
                    etNombre.setText(dataSnapshot.child((String) getResources().getText(R.string.nombre)).getValue().toString());
                    etPuntos.setText(dataSnapshot.child((String) getResources().getText(R.string.puntos)).getValue().toString());
                    String vida = dataSnapshot.child((String) getResources().getText(R.string.vidas)).getValue().toString();
                    if(vida.equalsIgnoreCase("1")){
                        ivVidas.setImageResource(R.drawable.una_vida1);
                        vidas = 1;
                    }else if(vida.equalsIgnoreCase("2")){
                        ivVidas.setImageResource(R.drawable.dos_vidas);
                        vidas = 2;
                    }else if(vida.equalsIgnoreCase("3")){
                        ivVidas.setImageResource(R.drawable.tres_vidas);
                        vidas = 3;
                    }
                    nivel = dataSnapshot.child((String) getResources().getText(R.string.nivel)).getValue().toString();
                }
                etNivel.setText(getResources().getText(R.string.nivel_tipo2) + " " + nivel);
                //Toast.makeText(JuegoActivity.this, "Estas en el nivel "+nivel, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Juego5Activity.this, getResources().getText(R.string.no_acceso_datos), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void obtenerPreguntas(){
        miBD.child((String) getResources().getText(R.string.preguntas)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    //extraccion de los datos
                    for (DataSnapshot ds : dataSnapshot.child((String) getResources().getText(R.string.n5)).getChildren()){
                        pregunta = ds.getValue().toString();
                        listaNivel5.add(pregunta);
                    }
                    //mostrar los datos
                        tvPregunta.setText(listaNivel5.get(contador));
                        if(contador <= 0){
                            rbUno.setText(listaNivel5.get(contador + 13));
                            rbDos.setText(listaNivel5.get(contador + 14));
                            rbTres.setText(listaNivel5.get(contador + 15));
                            rbCuatro.setText(listaNivel5.get(contador + 16));
                        }else{
                            rbUno.setText(listaNivel5.get(contador + salto + 11));
                            rbDos.setText(listaNivel5.get(contador + salto + 12));
                            rbTres.setText(listaNivel5.get(contador + salto + 13));
                            rbCuatro.setText(listaNivel5.get(contador + salto + 14));
                            salto = salto + salto1;
                        }
                    if(contador >= 12){
                        contador = -1;
                        salto = 5;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Juego5Activity.this, getResources().getText(R.string.no_acceso_datos), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void gestionDatos(){

        actualizarDatosUsuario(puntos, vidas, nuevoNivel);

        if(vidas == 0){
            //mandar un mensaje permanente para informar de que ha perdido, quitar toast
            Toast.makeText(Juego5Activity.this, getResources().getText(R.string.perder), Toast.LENGTH_SHORT).show();
            actualizarDatosUsuario(0, 3, 1);
            actualizarPuntosMaximos(puntos);
            pararMusica();
            startActivity(new Intent(Juego5Activity.this, PerderActivity.class));
            finish();

        }
        if(puntos >= 187){
            contador = 0;
            salto = 5;
            nuevoNivel = 6;
            actualizarDatosUsuario(puntos, vidas, nuevoNivel);
            Toast.makeText(Juego5Activity.this, getResources().getText(R.string.ganar), Toast.LENGTH_SHORT).show();
            pararMusica();
            startActivity(new Intent(Juego5Activity.this, Juego6Activity.class));
            finish();
        }
    }

    private void actualizarDatosUsuario(int puntos, int vidas, int nuevoNivel){

        //actualizar datos
        String id = miAuth.getCurrentUser().getUid();
        Map<String, Object> map = new HashMap<>();
        map.put((String) getResources().getText(R.string.puntos), puntos);
        map.put((String)getResources().getText(R.string.vidas), vidas);
        map.put((String)getResources().getText(R.string.nivel), nuevoNivel);

        miBD.child((String)getResources().getText(R.string.usuarios)).child(id).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Log.d("ACTUALIZACIÓN DATOS", "OK");
                //Toast.makeText(Juego5Activity.this, "ACTUALIZACIÓN DATOS CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Log.d("ACTUALIZACIÓN DATOS", "ERROR");
            }
        });
    }

    private void actualizarPuntosMaximos(int puntos_maximos){
        //actualizar datos
        String id = miAuth.getCurrentUser().getUid();
        Map<String, Object> map = new HashMap<>();
        map.put((String)getResources().getText(R.string.puntos_maximos), puntos_maximos);

        miBD.child((String)getResources().getText(R.string.usuarios)).child(id).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Log.d("ACTUALIZACIÓN PUNTUACION MAXIMA", "OK");
                //Toast.makeText(Juego5Activity.this, "ACTUALIZACIÓN PUNTUACION MAXIMA CORRECTA", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Log.d("ACTUALIZACIÓN PUNTUACION MAXIMA", "ERROR");
            }
        });
    }

    @Override
    public void onBackPressed() {
    }

    private void pararMusica(){
        mp.stop();
    }

}

