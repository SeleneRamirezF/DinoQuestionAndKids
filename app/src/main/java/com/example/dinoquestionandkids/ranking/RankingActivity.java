package com.example.dinoquestionandkids.ranking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dinoquestionandkids.R;
import com.example.dinoquestionandkids.menu.MenuActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class RankingActivity extends AppCompatActivity {

    private RecyclerView recView;
    private RankingAdapter adapter;
    private ArrayList<Ranking> miLista;
    private DatabaseReference usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        //poner icono en el actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icono_barra);

        miLista = new ArrayList<>();
        recView = (RecyclerView) findViewById(R.id.recView);

        usuarios = FirebaseDatabase.getInstance().getReference().child((String) getResources().getText(R.string.usuarios));
        recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        adapter = new RankingAdapter(miLista);
        recView.setAdapter(adapter);

        obtenetDatosUsuario();
    }

    private void obtenetDatosUsuario(){
        usuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        String nombre = ds.child((String) getResources().getText(R.string.nombre)).getValue().toString();
                        String puntos_maximos = ds.child((String)getResources().getText(R.string.puntos_maximos)).getValue().toString();
                        //Log.d("Nombre: ", nombre);
                        //Log.d("Puntuacion : ", puntos_maximos);
                        miLista.add(new Ranking("Puntuacion de "+nombre, puntos_maximos));
                    }
                    Collections.sort(miLista);
                    recView.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RankingActivity.this, getResources().getText(R.string.no_acceso_datos), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBackPressed() {
        startActivity(new Intent(RankingActivity.this, MenuActivity.class));
    }

}
