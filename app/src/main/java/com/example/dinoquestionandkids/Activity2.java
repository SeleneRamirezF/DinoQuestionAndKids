package com.example.dinoquestionandkids;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Activity2 extends AppCompatActivity {

    private Button btnCerrarSesion;
    private FirebaseAuth miAuth;
    private TextView tvNombre;
    private TextView tvCorreo;
    private DatabaseReference miBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        cargarViews();
        miAuth = FirebaseAuth.getInstance();
        miBD = FirebaseDatabase.getInstance().getReference();

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miAuth.signOut();
                startActivity(new Intent(Activity2.this, MainActivity.class));
                finish();
            }
        });

        obtenetInfoUsuario();
    }

    private void cargarViews(){
        btnCerrarSesion = (Button) findViewById(R.id.btnCerrarSesion);
        tvNombre = (TextView) findViewById(R.id.tvNombre);
        tvCorreo = (TextView) findViewById(R.id.tvCorreo);
    }

    private void obtenetInfoUsuario(){
        String id = miAuth.getCurrentUser().getUid();
        miBD.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
