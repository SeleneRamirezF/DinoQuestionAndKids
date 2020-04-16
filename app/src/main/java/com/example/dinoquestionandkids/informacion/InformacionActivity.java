package com.example.dinoquestionandkids.informacion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.dinoquestionandkids.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InformacionActivity extends AppCompatActivity {

    private RecyclerView recView;
    private ArrayList<Historia> miLista;
    private HistoriaAdapter adapter;
    private DatabaseReference historia;
    private static final String TAG = "PRUEBA";
    private String origen, edades, geo, precambirco, fanerozioco, paleozoico, mesozoico, cenozoico;
    //private int contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);

        recView = findViewById(R.id.recView);
        miLista = new ArrayList<Historia>();

        historia = FirebaseDatabase.getInstance().getReference().child("Historia");

        recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        //recView.setLayoutManager(new LinearLayoutManager(this));
        //adapter = new HistoriaAdapter(miLista);
        adapter = new HistoriaAdapter(miLista);
        recView.setAdapter(adapter);

        //cargarEquipos();
        cargarHistoriasDatabase();

    }

    public void cargarEquipos() {
        Historia almeria = new Historia("Almería", "El equipo de Almeria", R.drawable.inicio);
        miLista.add(almeria);
        Historia athmadrid = new Historia("Atletico de Madrid", "Uno de los equipo de madrid", R.drawable.inicio);
        miLista.add(athmadrid);
        Historia barcelona = new Historia("Barcelona", "El equipo de Barcelona", R.drawable.inicio);
        miLista.add(barcelona);
        Historia betis = new Historia("Betis", "Uno de los equipos de Sevilla", R.drawable.inicio);
        miLista.add(betis);
        Historia bilbao = new Historia("Bilbao", "El equipo de Bilbao", R.drawable.inicio);
        miLista.add(bilbao);
        Historia espania = new Historia("España", "El equipo de España", R.drawable.inicio);
        miLista.add(espania);
        Historia getafe = new Historia("Getafe", "Uno de los equipos de madrid", R.drawable.inicio);
        miLista.add(getafe);
        Historia madrid = new Historia("Real madrid", "Uno de los equipos de Madrid", R.drawable.inicio);
        miLista.add(madrid);
    }

    private void cargarHistoriasDatabase(){
        historia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    /*
                    for (DataSnapshot ds : dataSnapshot.getValue()){
                        String texto = ds.getValue().toString();
                        miLista.add(new Historia("nombre",texto, R.drawable.inicio));
                    }
                    */
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        String texto = ds.getKey();
                        miLista.add(new Historia(ds.getValue().toString(),texto, R.drawable.inicio));

                        //Historia h = dataSnapshot.getValue(Historia.class);
                        //miLista.add(h);
                    }
                    //adapter = new HistoriaAdapter(miLista, R.layout.activity_informacion);
                    recView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
