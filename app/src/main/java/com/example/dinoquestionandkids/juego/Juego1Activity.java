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

public class Juego1Activity extends AppCompatActivity {

    private FirebaseAuth miAuth;
    private DatabaseReference miBD;
    private FirebaseUser user;
    private EditText etNombre;
    private EditText etPuntos;
    private TextView etNivel;
    private ImageView ivVidas;
    private TextView tvPregunta;
    private RadioButton rbUno, rbDos, rbTres, rbCuatro;
    private Button btnComprobar;
    private String nivel, pregunta;
    private ArrayList<String> listaNivel1;
    private int contador1 = 0;
    private int  salto11 = 5,salto1 = 3;
    private int puntos, vidas, nuevoNivel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego1);

        cargarViews();

        //poner icono en el actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        miAuth = FirebaseAuth.getInstance();
        miBD = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        obtenerPreguntas();
        obtenetDatosUsuario();

        listaNivel1 = new ArrayList();

        btnComprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contador1++;
                obtenerPreguntas();
                obtenetDatosUsuario();

                nuevoNivel = Integer.parseInt(nivel);

                /* las listas estan rellenas asi que cada vez que se pulse
                se comprobará el nivel y se comparará con el resultado
                según que boton se pulde */

                if(nivel.equalsIgnoreCase("1")){
                    switch (contador1){
                        case 1:
                        case 9:
                        case 10:
                            if(rbTres.isChecked()){
                                Toast.makeText(Juego1Activity.this, "CORRECTA", Toast.LENGTH_SHORT).show();
                                puntos++;
                                gestionDatos();
                                rbTres.setChecked(false);
                            }else{
                                Toast.makeText(Juego1Activity.this, "INCORRECTA", Toast.LENGTH_SHORT).show();
                                vidas--;
                                rbUno.setChecked(false);
                                rbDos.setChecked(false);
                                rbCuatro.setChecked(false);
                                gestionDatos();
                            }
                            break;
                        case 2:
                        case 6:
                            if(rbUno.isChecked()){
                                Toast.makeText(Juego1Activity.this, "CORRECTA", Toast.LENGTH_SHORT).show();
                                puntos++;
                                gestionDatos();
                                rbUno.setChecked(false);
                            }else{
                                Toast.makeText(Juego1Activity.this, "INCORRECTA", Toast.LENGTH_SHORT).show();
                                vidas--;
                                rbTres.setChecked(false);
                                rbDos.setChecked(false);
                                rbCuatro.setChecked(false);
                                gestionDatos();
                            }
                            break;
                        case 3:
                        case 4:
                        case 8:
                            if(rbDos.isChecked()){
                                Toast.makeText(Juego1Activity.this, "CORRECTA", Toast.LENGTH_SHORT).show();
                                puntos++;
                                gestionDatos();
                                rbDos.setChecked(false);
                            }else{
                                Toast.makeText(Juego1Activity.this, "INCORRECTA", Toast.LENGTH_SHORT).show();
                                vidas--;
                                rbUno.setChecked(false);
                                rbTres.setChecked(false);
                                rbCuatro.setChecked(false);
                                gestionDatos();
                            }
                            break;
                        case 5:
                        case 7:
                            if(rbCuatro.isChecked()){
                                Toast.makeText(Juego1Activity.this, "CORRECTA", Toast.LENGTH_SHORT).show();
                                puntos++;
                                gestionDatos();
                                rbCuatro.setChecked(false);
                            }else{
                                Toast.makeText(Juego1Activity.this, "INCORRECTA", Toast.LENGTH_SHORT).show();
                                vidas--;
                                rbDos.setChecked(false);
                                rbUno.setChecked(false);
                                rbTres.setChecked(false);
                                gestionDatos();
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
                Toast.makeText(Juego1Activity.this, "No se ha podido acceder a los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void obtenerPreguntas(){
        miBD.child("Preguntas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    //extraccion de los datos
                    for (DataSnapshot ds : dataSnapshot.child("nivel1").getChildren()){
                        pregunta = ds.getValue().toString();
                        listaNivel1.add(pregunta);
                    }
                    //mostrar los datos
                        tvPregunta.setText(listaNivel1.get(contador1));
                        if(contador1 <= 0){
                            rbUno.setText(listaNivel1.get(contador1 + 10));
                            rbDos.setText(listaNivel1.get(contador1 + 11));
                            rbTres.setText(listaNivel1.get(contador1 + 12));
                            rbCuatro.setText(listaNivel1.get(contador1 + 13));
                        }else{
                            rbUno.setText(listaNivel1.get(contador1 + salto11 + 8));
                            rbDos.setText(listaNivel1.get(contador1 + salto11 + 9));
                            rbTres.setText(listaNivel1.get(contador1 + salto11 + 10));
                            rbCuatro.setText(listaNivel1.get(contador1 + salto11 + 11));
                            salto11 = salto11 + salto1;
                        }
                    if(contador1 >= 9){
                        contador1 = 0;
                        salto11 = 5;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Juego1Activity.this, "No se ha podido acceder a los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void gestionDatos(){
        actualizarDatosUsuario(puntos, vidas, nuevoNivel);
        if(vidas == 0){
            //mandar un mensaje permanente para informar de que ha perdido, quitar toast
            Toast.makeText(Juego1Activity.this, "HAS PERDIDO, INTENTALO OTRA VEZ", Toast.LENGTH_SHORT).show();
            actualizarDatosUsuario(0, 3, 1);
            startActivity(new Intent(Juego1Activity.this, PerderActivity.class));
            finish();
        }
        if(puntos == 7){
            contador1 = 0;
            salto11 = 5;
            nuevoNivel = 2;
            actualizarDatosUsuario(puntos, vidas, nuevoNivel);
            Toast.makeText(Juego1Activity.this, "PASAS DE NIVEL, ENHORABUENA!!!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Juego1Activity.this, Juego2Activity.class));
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
                //Toast.makeText(Juego1Activity.this, "ACTUALIZACIÓN DATOS CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ACTUALIZACIÓN DATOS", "ERROR");
            }
        });
    }
    //anulación del boton de 'atras'
    @Override
    public void onBackPressed() {
    }
}
