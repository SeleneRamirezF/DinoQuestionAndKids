package com.example.dinoquestionandkids;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JuegoActivity extends AppCompatActivity {

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
    private ArrayList<String> listaNivel1, listaNivel2, listaNivel3,
            listaNivel4, listaNivel5, listaNivel6;
    private int contador = 0, contador1 = 0;
    private int  salto11 = 5, salto = 5,salto1 = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        cargarViews();

        miAuth = FirebaseAuth.getInstance();
        miBD = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        obtenerPreguntas();
        obtenetDatosUsuario();

        listaNivel1 = new ArrayList();
        listaNivel2 = new ArrayList();
        listaNivel3 = new ArrayList();
        listaNivel4 = new ArrayList();
        listaNivel5 = new ArrayList();
        listaNivel6 = new ArrayList();

        //obtenerPreguntas();

        //mostrarDatos();

        btnComprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contador++;
                contador1++;
                //mostrarDatos();
                obtenerPreguntas();
                obtenetDatosUsuario();
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
                    nivel = dataSnapshot.child("nivel").getValue().toString();
                }
                if (dataSnapshot.exists()){
                    etNombre.setText(dataSnapshot.child((String) getResources().getText(R.string.nombre)).getValue().toString());
                    etPuntos.setText(dataSnapshot.child("puntos").getValue().toString());
                    String vidas = dataSnapshot.child("vidas").getValue().toString();
                    if(vidas.equalsIgnoreCase("1")){
                        ivVidas.setImageResource(R.drawable.una_vida1);
                    }else if(vidas.equalsIgnoreCase("2")){
                        ivVidas.setImageResource(R.drawable.dos_vidas);
                    }else if(vidas.equalsIgnoreCase("3")){
                        ivVidas.setImageResource(R.drawable.tres_vidas);
                    }
                    nivel = dataSnapshot.child("nivel").getValue().toString();
                }
                etNivel.setText("NIVEL " + nivel);
                //Toast.makeText(JuegoActivity.this, "Estas en el nivel "+nivel, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(JuegoActivity.this, "No se ha podido acceder a los datos", Toast.LENGTH_SHORT).show();
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
                    for (DataSnapshot ds : dataSnapshot.child("nivel2").getChildren()){
                        pregunta = ds.getValue().toString();
                        listaNivel2.add(pregunta);
                    }
                    for (DataSnapshot ds : dataSnapshot.child("nivel3").getChildren()){
                        pregunta = ds.getValue().toString();
                        listaNivel3.add(pregunta);
                    }
                    for (DataSnapshot ds : dataSnapshot.child("nivel4").getChildren()){
                        pregunta = ds.getValue().toString();
                        listaNivel4.add(pregunta);
                    }
                    for (DataSnapshot ds : dataSnapshot.child("nivel5").getChildren()){
                        pregunta = ds.getValue().toString();
                        listaNivel5.add(pregunta);
                    }
                    for (DataSnapshot ds : dataSnapshot.child("nivel6").getChildren()){
                        pregunta = ds.getValue().toString();
                        listaNivel6.add(pregunta);
                    }
                    //mostrar los datos
                    if(nivel.equalsIgnoreCase("1")){
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

                    }else if(nivel.equalsIgnoreCase("2")){
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
                        //Log.d("Salto", String.valueOf(salto));
                        //Log.d("PRUEBA", listaNivel2.get(0));

                    }else if(nivel.equalsIgnoreCase("3")){
                        tvPregunta.setText(listaNivel3.get(contador));
                        if(contador <= 0){
                            rbUno.setText(listaNivel3.get(contador + 13));
                            rbDos.setText(listaNivel3.get(contador + 14));
                            rbTres.setText(listaNivel3.get(contador + 15));
                            rbCuatro.setText(listaNivel3.get(contador + 16));
                        }else{
                            rbUno.setText(listaNivel3.get(contador + salto + 11));
                            rbDos.setText(listaNivel3.get(contador + salto + 12));
                            rbTres.setText(listaNivel3.get(contador + salto + 13));
                            rbCuatro.setText(listaNivel3.get(contador + salto + 14));
                            //Log.d("SALTO1", String.valueOf(salto));
                            salto = salto + salto1;
                            //Log.d("SALTO2", String.valueOf(salto));
                        }
                        //Log.d("CONTADOR", String.valueOf(contador));
                        //Log.d("PRUEBA", String.valueOf(salto));
                    }else if(nivel.equalsIgnoreCase("4")){
                        tvPregunta.setText(listaNivel4.get(contador));
                        if(contador <= 0){
                            rbUno.setText(listaNivel4.get(contador + 13));
                            rbDos.setText(listaNivel4.get(contador + 14));
                            rbTres.setText(listaNivel4.get(contador + 15));
                            rbCuatro.setText(listaNivel4.get(contador + 16));
                        }else{
                            rbUno.setText(listaNivel4.get(contador + salto + 11));
                            rbDos.setText(listaNivel4.get(contador + salto + 12));
                            rbTres.setText(listaNivel4.get(contador + salto + 13));
                            rbCuatro.setText(listaNivel4.get(contador + salto + 14));
                            salto = salto + salto1;
                        }

                    }else if(nivel.equalsIgnoreCase("5")){
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

                    }else if(nivel.equalsIgnoreCase("6")){
                        tvPregunta.setText(listaNivel6.get(contador));
                        if(contador <= 0){
                            rbUno.setText(listaNivel6.get(contador + 13));
                            rbDos.setText(listaNivel6.get(contador + 14));
                            rbTres.setText(listaNivel6.get(contador + 15));
                            rbCuatro.setText(listaNivel6.get(contador + 16));
                        }else{
                            rbUno.setText(listaNivel6.get(contador + salto + 11));
                            rbDos.setText(listaNivel6.get(contador + salto + 12));
                            rbTres.setText(listaNivel6.get(contador + salto + 13));
                            rbCuatro.setText(listaNivel6.get(contador + salto + 14));
                            salto = salto + salto1;
                        }
                    }

                    if(contador1 >= 9){
                        contador1 = -1;
                        salto11 = 5;
                    }
                    if(contador >= 12){
                        contador = -1;
                        salto = 5;
                    }
                    

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(JuegoActivity.this, "No se ha podido acceder a los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
