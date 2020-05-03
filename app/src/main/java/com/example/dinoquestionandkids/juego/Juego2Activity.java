package com.example.dinoquestionandkids.juego;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dinoquestionandkids.MenuActivity;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Juego2Activity extends AppCompatActivity {

    private FirebaseAuth miAuth;
    private DatabaseReference miBD;
    private FirebaseUser user;
    private EditText etNombre;
    private EditText etPuntos;
    private TextView etNivel;
    private ImageView ivVidas;
    private TextView tvPregunta;
    private RadioButton rbUno, rbDos, rbTres, rbCuatro;
    private Button btnComprobar, btnBonus;
    private String nivel, pregunta;
    private ArrayList<String> listaNivel2;
    private int contador = 0;
    private int  salto = 5,salto1 = 3;
    private int puntos, vidas, nuevoNivel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego2);

        cargarViews();

        //poner icono en el actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        miAuth = FirebaseAuth.getInstance();
        miBD = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        obtenerPreguntas();
        obtenetDatosUsuario();

        listaNivel2 = new ArrayList();

        comprobarBonus();
        btnBonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vidas <= 2){
                    vidas = vidas+1;
                    actualizarDatosUsuario(puntos, vidas, 2);
                    obtenetDatosUsuario();
                    btnBonus.setEnabled(false);
                }
            }
        });

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

                if(nivel.equalsIgnoreCase("2")){
                    switch (contador){
                        case 4:
                        case 11:
                        case 12:
                            if(rbTres.isChecked()){
                                Toast.makeText(Juego2Activity.this, "CORRECTA", Toast.LENGTH_SHORT).show();
                                puntos = puntos+3;
                                gestionDatos();
                                rbTres.setChecked(false);
                            }else{
                                Toast.makeText(Juego2Activity.this, "INCORRECTA", Toast.LENGTH_SHORT).show();
                                vidas--;
                                gestionDatos();
                                rbDos.setChecked(false);
                                rbUno.setChecked(false);
                                rbCuatro.setChecked(false);
                            }
                            break;
                        case 1:
                        case 3:
                        case 7:
                        case 13:
                            if(rbUno.isChecked()){
                                Toast.makeText(Juego2Activity.this, "CORRECTA", Toast.LENGTH_SHORT).show();
                                puntos = puntos+3;
                                gestionDatos();
                                rbUno.setChecked(false);
                            }else{
                                Toast.makeText(Juego2Activity.this, "INCORRECTA", Toast.LENGTH_SHORT).show();
                                vidas--;
                                gestionDatos();
                                rbDos.setChecked(false);
                                rbTres.setChecked(false);
                                rbCuatro.setChecked(false);
                            }
                            break;
                        case 2:
                        case 9:
                        case 6:
                        case 10:
                            if(rbDos.isChecked()){
                                Toast.makeText(Juego2Activity.this, "CORRECTA", Toast.LENGTH_SHORT).show();
                                puntos = puntos+3;
                                gestionDatos();
                                rbDos.setChecked(false);
                            }else{
                                Toast.makeText(Juego2Activity.this, "INCORRECTA", Toast.LENGTH_SHORT).show();
                                vidas--;
                                gestionDatos();
                                rbUno.setChecked(false);
                                rbTres.setChecked(false);
                                rbCuatro.setChecked(false);
                            }
                            break;
                        case 5:
                        case 8:
                            if(rbCuatro.isChecked()){
                                Toast.makeText(Juego2Activity.this, "CORRECTA", Toast.LENGTH_SHORT).show();
                                puntos = puntos+3;
                                gestionDatos();
                                rbCuatro.setChecked(false);
                            }else{
                                Toast.makeText(Juego2Activity.this, "INCORRECTA", Toast.LENGTH_SHORT).show();
                                vidas--;
                                gestionDatos();
                                rbUno.setChecked(false);
                                rbDos.setChecked(false);
                                rbTres.setChecked(false);
                            }
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
        btnBonus = (Button) findViewById(R.id.btnBonus);
    }

    private void obtenetDatosUsuario(){
        String id = miAuth.getCurrentUser().getUid();
        miBD.child((String) getResources().getText(R.string.usuarios)).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(user != null){
                    etNombre.setText(user.getDisplayName());
                    etPuntos.setText(dataSnapshot.child("puntos").getValue().toString());
                    puntos = Integer.parseInt(dataSnapshot.child("puntos").getValue().toString());
                    nivel = dataSnapshot.child("nivel").getValue().toString();
                }
                if (dataSnapshot.exists()){
                    etNombre.setText(dataSnapshot.child((String) getResources().getText(R.string.nombre)).getValue().toString());
                    etPuntos.setText(dataSnapshot.child("puntos").getValue().toString());
                    String vida = dataSnapshot.child("vidas").getValue().toString();
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
                    nivel = dataSnapshot.child("nivel").getValue().toString();
                }
                etNivel.setText("NIVEL " + nivel);
                //Toast.makeText(JuegoActivity.this, "Estas en el nivel "+nivel, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Juego2Activity.this, "No se ha podido acceder a los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void obtenerPreguntas(){
        miBD.child("Preguntas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    //extraccion de los datos
                    for (DataSnapshot ds : dataSnapshot.child("nivel2").getChildren()){
                        pregunta = ds.getValue().toString();
                        listaNivel2.add(pregunta);
                    }
                    //mostrar los datos
                        tvPregunta.setText(listaNivel2.get(contador));
                        if(contador <= 0){
                            rbUno.setText(listaNivel2.get(contador + 13));
                            rbDos.setText(listaNivel2.get(contador + 14));
                            rbTres.setText(listaNivel2.get(contador + 15));
                            rbCuatro.setText(listaNivel2.get(contador + 16));
                        }else{
                            rbUno.setText(listaNivel2.get(contador + salto + 11));
                            rbDos.setText(listaNivel2.get(contador + salto + 12));
                            rbTres.setText(listaNivel2.get(contador + salto + 13));
                            rbCuatro.setText(listaNivel2.get(contador + salto + 14));
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
                Toast.makeText(Juego2Activity.this, "No se ha podido acceder a los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void gestionDatos(){

        actualizarDatosUsuario(puntos, vidas, nuevoNivel);

        if(vidas == 0){
            //mandar un mensaje permanente para informar de que ha perdido, quitar toast
            Toast.makeText(Juego2Activity.this, "HAS PERDIDO, INTENTALO OTRA VEZ", Toast.LENGTH_SHORT).show();
            actualizarDatosUsuario(0, 3, 1);

            startActivity(new Intent(Juego2Activity.this, PerderActivity.class));
            finish();

        }
        if(puntos >= 37){
            Log.d("pasa por aqui", "Siiiiiii");
            contador = 0;
            salto = 5;
            nuevoNivel = 3;
            actualizarDatosUsuario(puntos, vidas, nuevoNivel);
            Toast.makeText(Juego2Activity.this, "PASAS DE NIVEL, ENHORABUENA!!!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Juego2Activity.this, Juego3Activity.class));
            finish();
        }

    }

    private void actualizarDatosUsuario(int puntos, int vidas, int nuevoNivel){

        //actualizar datos
        String id = miAuth.getCurrentUser().getUid();
        Map<String, Object> map = new HashMap<>();
        map.put("puntos", puntos);
        map.put("vidas", vidas);
        map.put("nivel", nuevoNivel);

        miBD.child("Usuarios").child(id).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Log.d("ACTUALIZACIÓN DATOS", "OK");
                //Toast.makeText(Juego2Activity.this, "ACTUALIZACIÓN DATOS CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ACTUALIZACIÓN DATOS", "ERROR");
            }
        });
    }

    private void comprobarBonus(){
        //acciones sobre el boton de bonus
        if(vidas <= 2){
            btnBonus.setEnabled(true);
        }else{
            btnBonus.setEnabled(false);
        }
    }

    @Override
    public void onBackPressed() {
    }


}
