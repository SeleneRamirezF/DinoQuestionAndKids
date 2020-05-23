package com.example.dinoquestionandkids.ranking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dinoquestionandkids.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RankingActivity extends AppCompatActivity {

    private RecyclerView recView;
    private RankingAdapter adapter;
    private ArrayList<Ranking> miLista;
    private DatabaseReference usuarios;

    private int[] puntos;
    int contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

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
                        String puntos_maximos = ds.child((String) getResources().getText(R.string.puntos_maximos)).getValue().toString();
                        Log.d("Nombre: ", nombre);
                        Log.d("Puntuacion : ", puntos_maximos);

                       // puntos[contador] = Integer.parseInt(puntos_maximos);
                       // ordenar(puntos);

                        miLista.add(new Ranking(nombre+": ", puntos_maximos));
                    }
                    recView.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RankingActivity.this, getResources().getText(R.string.no_acceso_datos), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void ordenar(int[] a){
        //int a[] = {5,3,2,7,10,1};
        for (int x = 0; x < a.length; x++) {
            for (int i = 0; i < a.length-x-1; i++) {
                if(a[i] < a[i+1]){
                    int tmp = a[i+1];
                    a[i+1] = a[i];
                    a[i] = tmp;
                }
            }
        }
    }


}
