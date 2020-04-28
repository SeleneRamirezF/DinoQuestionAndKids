package com.example.dinoquestionandkids.dinosaurios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dinoquestionandkids.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DinoInfoActivity extends AppCompatActivity {

    private ImageView ivDino;
    private TextView tvNombreDino;
    private EditText etDescripcionDino;
    private String posicion;
    private DatabaseReference Dinosaurios;
    private String texto0, texto00, textoLimpio0;
    private String texto1, textoLimpio1;
    private String texto2, textoLimpio2;
    private String texto3, textoLimpio3;
    private String texto4, textoLimpio4;
    private String texto5, textoLimpio5;
    private String texto6, textoLimpio6;
    private String texto7, textoLimpio7;
    private String texto8, textoLimpio8;
    private String texto9, textoLimpio9;
    private String texto10, textoLimpio10;
    private String texto11, textoLimpio11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinoinfo);

        Dinosaurios = FirebaseDatabase.getInstance().getReference();
        posicion = getIntent().getStringExtra("posicion");

        cargarViews();
        cargarDinosauriosDatabase();

        //poner icono en el actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        tvNombreDino.setKeyListener(null);
        etDescripcionDino.setFocusable(false);
    }

    private void cargarViews(){
        ivDino = (ImageView) findViewById(R.id.ivDino);
        tvNombreDino = (TextView) findViewById(R.id.tvNombreDino);
        etDescripcionDino = (EditText) findViewById(R.id.etDescripcionDino);
    }

    private void cargarDinosauriosDatabase(){
        Dinosaurios.child("Dinosaurios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    //extraccion de los datos
                    texto0 = dataSnapshot.child("Ankylosaurus").getKey();
                    texto00 = dataSnapshot.child("Ankylosaurus").getValue().toString();
                    textoLimpio0 = texto00.substring(6, texto00.length()-1);

                    texto1 = dataSnapshot.child("Carnotaurus").getKey();
                    texto00 = dataSnapshot.child("Carnotaurus").getValue().toString();
                    textoLimpio1 = texto00.substring(6, texto00.length()-1);

                    texto2 = dataSnapshot.child("Compsognathus").getKey();
                    texto00 = dataSnapshot.child("Compsognathus").getValue().toString();
                    textoLimpio2 = texto00.substring(6, texto00.length()-1);

                    texto3 = dataSnapshot.child("Diplodocus").getKey();
                    texto00 = dataSnapshot.child("Diplodocus").getValue().toString();
                    textoLimpio3 = texto00.substring(6, texto00.length()-1);

                    texto4 = dataSnapshot.child("Iguanodon").getKey();
                    texto00 = dataSnapshot.child("Iguanodon").getValue().toString();
                    textoLimpio4 = texto00.substring(6, texto00.length()-1);

                    texto5 = dataSnapshot.child("Microraptor").getKey();
                    texto00 = dataSnapshot.child("Microraptor").getValue().toString();
                    textoLimpio5 = texto00.substring(6, texto00.length()-1);

                    texto6 = dataSnapshot.child("Ornithomimus").getKey();
                    texto00 = dataSnapshot.child("Ornithomimus").getValue().toString();
                    textoLimpio6 = texto00.substring(6, texto00.length()-1);

                    texto7 = dataSnapshot.child("Pterodactylus").getKey();
                    texto00 = dataSnapshot.child("Pterodactylus").getValue().toString();
                    textoLimpio7 = texto00.substring(6, texto00.length()-1);

                    texto8 = dataSnapshot.child("Stegosaurus").getKey();
                    texto00 = dataSnapshot.child("Stegosaurus").getValue().toString();
                    textoLimpio8 = texto00.substring(6, texto00.length()-1);

                    texto9 = dataSnapshot.child("Triceratops").getKey();
                    texto00 = dataSnapshot.child("Triceratops").getValue().toString();
                    textoLimpio9 = texto00.substring(6, texto00.length()-1);

                    texto10 = dataSnapshot.child("Tyrannosaurus Rex").getKey();
                    texto00 = dataSnapshot.child("Tyrannosaurus Rex").getValue().toString();
                    textoLimpio10 = texto00.substring(6, texto00.length()-1);

                    texto11 = dataSnapshot.child("Velociraptor").getKey();
                    texto00 = dataSnapshot.child("Velociraptor").getValue().toString();
                    textoLimpio11 = texto00.substring(6, texto00.length()-1);

                    //eleccion de los datos que se tienen que mostrar
                    if(posicion.equalsIgnoreCase("0")){
                        tvNombreDino.setText(texto0);
                        etDescripcionDino.setText(textoLimpio0);
                        ivDino.setImageResource(R.drawable.ankylosaurus);
                    }else if(posicion.equalsIgnoreCase("1")){
                        tvNombreDino.setText(texto1);
                        etDescripcionDino.setText(textoLimpio1);
                        ivDino.setImageResource(R.drawable.carnotaurus);
                    }else if(posicion.equalsIgnoreCase("2")){
                        tvNombreDino.setText(texto2);
                        etDescripcionDino.setText(textoLimpio2);
                        ivDino.setImageResource(R.drawable.compsognathus);
                    }else if(posicion.equalsIgnoreCase("3")){
                        tvNombreDino.setText(texto3);
                        etDescripcionDino.setText(textoLimpio3);
                        ivDino.setImageResource(R.drawable.diplodocus);
                    }else if(posicion.equalsIgnoreCase("4")){
                        tvNombreDino.setText(texto4);
                        etDescripcionDino.setText(textoLimpio4);
                        ivDino.setImageResource(R.drawable.iguanodon);
                    }else if(posicion.equalsIgnoreCase("5")){
                        tvNombreDino.setText(texto5);
                        etDescripcionDino.setText(textoLimpio5);
                        ivDino.setImageResource(R.drawable.microraptor);
                    }else if(posicion.equalsIgnoreCase("6")){
                        tvNombreDino.setText(texto6);
                        etDescripcionDino.setText(textoLimpio6);
                        ivDino.setImageResource(R.drawable.ornithomimus);
                    }else if(posicion.equalsIgnoreCase("7")){
                        tvNombreDino.setText(texto7);
                        etDescripcionDino.setText(textoLimpio7);
                        ivDino.setImageResource(R.drawable.pterodactylus);
                    }else if(posicion.equalsIgnoreCase("8")){
                        tvNombreDino.setText(texto8);
                        etDescripcionDino.setText(textoLimpio8);
                        ivDino.setImageResource(R.drawable.stegosaurus);
                    }else if(posicion.equalsIgnoreCase("9")){
                        tvNombreDino.setText(texto9);
                        etDescripcionDino.setText(textoLimpio9);
                        ivDino.setImageResource(R.drawable.triceratops);
                    }else if(posicion.equalsIgnoreCase("10")){
                        tvNombreDino.setText(texto10);
                        etDescripcionDino.setText(textoLimpio10);
                        ivDino.setImageResource(R.drawable.tyrannosaurusrex);
                    }else if(posicion.equalsIgnoreCase("11")){
                        tvNombreDino.setText(texto11);
                        etDescripcionDino.setText(textoLimpio11);
                        ivDino.setImageResource(R.drawable.velociraptor);
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DinoInfoActivity.this, "No se ha podido acceder a los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
