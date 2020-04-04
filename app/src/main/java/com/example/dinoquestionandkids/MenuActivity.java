package com.example.dinoquestionandkids;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuActivity extends AppCompatActivity {

    private Button btnCerrarSesion;
    private FirebaseAuth miAuth;
    private TextView tvNombre;
    private TextView tvCorreo;
    private DatabaseReference miBD;
    private Button btn1, btn2, btn3, btn4;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        cargarViews();
        miAuth = FirebaseAuth.getInstance();
        miBD = FirebaseDatabase.getInstance().getReference();
        //inicio con google
        user = FirebaseAuth.getInstance().getCurrentUser();

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miAuth.signOut();
                startActivity(new Intent(MenuActivity.this, MainActivity.class));
                finish();
            }
        });

        obtenetInfoUsuario();

        //acciones de los botones
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, LineaTemporalActivity.class));
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, DinosauriosActivity.class));
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, JuegoActivity.class));
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, PuzzleActivity.class));
            }
        });

    }

    private void cargarViews(){
        btnCerrarSesion = (Button) findViewById(R.id.btnCerrarSesion);
        tvNombre = (TextView) findViewById(R.id.tvNombre);
        tvCorreo = (TextView) findViewById(R.id.tvCorreo);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
    }

    //metodo para obtener los datos del usuario
    private void obtenetInfoUsuario(){
        String id = miAuth.getCurrentUser().getUid();
        miBD.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //hacemos la parte de acceso a los datos cuando se inicia con google
                if(user != null){
                    tvNombre.setText(user.getDisplayName());
                    tvCorreo.setText(user.getEmail());
                }
                if (dataSnapshot.exists()){
                    String nombre = dataSnapshot.child("nombre").getValue().toString();
                    String correo = dataSnapshot.child("correo").getValue().toString();
                    tvNombre.setText(nombre);
                    tvCorreo.setText(correo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
