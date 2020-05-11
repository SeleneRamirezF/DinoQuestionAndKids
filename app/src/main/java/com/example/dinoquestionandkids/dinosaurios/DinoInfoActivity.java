package com.example.dinoquestionandkids.dinosaurios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinoinfo);

        Dinosaurios = FirebaseDatabase.getInstance().getReference();
        posicion = getIntent().getStringExtra(getResources().getString(R.string.pos));

        cargarViews();
        cargarDinosauriosDatabase();

        //poner icono en el actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        tvNombreDino.setKeyListener(null);
        etDescripcionDino.setFocusable(false);

        ivDino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //eleccion de los sonidos que se tienen que escuchar
                if(posicion.equalsIgnoreCase("0")){
                    mp = MediaPlayer.create(DinoInfoActivity.this, R.raw.ankylosaurus);
                    mp.start();
                }else if(posicion.equalsIgnoreCase("1")){
                    mp = MediaPlayer.create(DinoInfoActivity.this, R.raw.carnotaurus);
                    mp.start();
                }else if(posicion.equalsIgnoreCase("2")){
                    mp = MediaPlayer.create(DinoInfoActivity.this, R.raw.compsognathus);
                    mp.start();
                }else if(posicion.equalsIgnoreCase("3")){
                    mp = MediaPlayer.create(DinoInfoActivity.this, R.raw.diplodocus);
                    mp.start();
                }else if(posicion.equalsIgnoreCase("4")){
                    mp = MediaPlayer.create(DinoInfoActivity.this, R.raw.iguanodon);
                    mp.start();
                }else if(posicion.equalsIgnoreCase("5")){
                    mp = MediaPlayer.create(DinoInfoActivity.this, R.raw.microraptor);
                    mp.start();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mp.stop();
                        }
                    }, 1250);
                }else if(posicion.equalsIgnoreCase("6")){
                    mp = MediaPlayer.create(DinoInfoActivity.this, R.raw.ornithomimus);
                    mp.start();
                }else if(posicion.equalsIgnoreCase("7")){
                    mp = MediaPlayer.create(DinoInfoActivity.this, R.raw.pterodactylus);
                    mp.start();
                }else if(posicion.equalsIgnoreCase("8")){
                    mp = MediaPlayer.create(DinoInfoActivity.this, R.raw.stegosaurus);
                    mp.start();
                }else if(posicion.equalsIgnoreCase("9")){
                    mp = MediaPlayer.create(DinoInfoActivity.this, R.raw.triceratops);
                    mp.start();
                }else if(posicion.equalsIgnoreCase("10")){
                    mp = MediaPlayer.create(DinoInfoActivity.this, R.raw.tyrannosaurus_rex);
                    mp.start();
                }else if(posicion.equalsIgnoreCase("11")){
                    mp = MediaPlayer.create(DinoInfoActivity.this, R.raw.velociraptor);
                    mp.start();
                }

            }
        });
    }

    private void cargarViews(){
        ivDino = (ImageView) findViewById(R.id.ivDino);
        tvNombreDino = (TextView) findViewById(R.id.tvNombreDino);
        etDescripcionDino = (EditText) findViewById(R.id.etDescripcionDino);
    }

    private void cargarDinosauriosDatabase(){
        Dinosaurios.child(getResources().getString(R.string.dinos)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    //extraccion de los datos
                    texto0 = dataSnapshot.child(getResources().getString(R.string.anky)).getKey();
                    texto00 = dataSnapshot.child(getResources().getString(R.string.anky)).getValue().toString();
                    textoLimpio0 = texto00.substring(6, texto00.length()-1);

                    texto1 = dataSnapshot.child(getResources().getString(R.string.carno)).getKey();
                    texto00 = dataSnapshot.child(getResources().getString(R.string.carno)).getValue().toString();
                    textoLimpio1 = texto00.substring(6, texto00.length()-1);

                    texto2 = dataSnapshot.child(getResources().getString(R.string.compso)).getKey();
                    texto00 = dataSnapshot.child(getResources().getString(R.string.compso)).getValue().toString();
                    textoLimpio2 = texto00.substring(6, texto00.length()-1);

                    texto3 = dataSnapshot.child(getResources().getString(R.string.diplo)).getKey();
                    texto00 = dataSnapshot.child(getResources().getString(R.string.diplo)).getValue().toString();
                    textoLimpio3 = texto00.substring(6, texto00.length()-1);

                    texto4 = dataSnapshot.child(getResources().getString(R.string.igua)).getKey();
                    texto00 = dataSnapshot.child(getResources().getString(R.string.igua)).getValue().toString();
                    textoLimpio4 = texto00.substring(6, texto00.length()-1);

                    texto5 = dataSnapshot.child(getResources().getString(R.string.micro)).getKey();
                    texto00 = dataSnapshot.child(getResources().getString(R.string.micro)).getValue().toString();
                    textoLimpio5 = texto00.substring(6, texto00.length()-1);

                    texto6 = dataSnapshot.child(getResources().getString(R.string.orni)).getKey();
                    texto00 = dataSnapshot.child(getResources().getString(R.string.orni)).getValue().toString();
                    textoLimpio6 = texto00.substring(6, texto00.length()-1);

                    texto7 = dataSnapshot.child(getResources().getString(R.string.ptero)).getKey();
                    texto00 = dataSnapshot.child(getResources().getString(R.string.ptero)).getValue().toString();
                    textoLimpio7 = texto00.substring(6, texto00.length()-1);

                    texto8 = dataSnapshot.child(getResources().getString(R.string.stego)).getKey();
                    texto00 = dataSnapshot.child(getResources().getString(R.string.stego)).getValue().toString();
                    textoLimpio8 = texto00.substring(6, texto00.length()-1);

                    texto9 = dataSnapshot.child(getResources().getString(R.string.trice)).getKey();
                    texto00 = dataSnapshot.child(getResources().getString(R.string.trice)).getValue().toString();
                    textoLimpio9 = texto00.substring(6, texto00.length()-1);

                    texto10 = dataSnapshot.child(getResources().getString(R.string.tyra)).getKey();
                    texto00 = dataSnapshot.child(getResources().getString(R.string.tyra)).getValue().toString();
                    textoLimpio10 = texto00.substring(6, texto00.length()-1);

                    texto11 = dataSnapshot.child(getResources().getString(R.string.velo)).getKey();
                    texto00 = dataSnapshot.child(getResources().getString(R.string.velo)).getValue().toString();
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
                Toast.makeText(DinoInfoActivity.this, getResources().getString(R.string.no_acceso_datos), Toast.LENGTH_SHORT).show();
            }
        });
    }
}