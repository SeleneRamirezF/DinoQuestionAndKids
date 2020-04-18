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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);

        recView = (RecyclerView) findViewById(R.id.recView);
        miLista = new ArrayList<>();

        historia = FirebaseDatabase.getInstance().getReference().child("Historia");
        recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        adapter = new HistoriaAdapter(miLista);
        recView.setAdapter(adapter);

        cargarHistoriasDatabase();

    }

    private void cargarHistoriasDatabase(){
        historia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        String texto = ds.getKey();
                        String texto2 = ds.getValue().toString();
                        String textoLimpio = texto2.substring(6, texto2.length()-1);
                        miLista.add(new Historia(textoLimpio,texto, R.drawable.inicio));
                        //Log.d("PRUEBA", textoLimpio);
                    }
                    recView.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(InformacionActivity.this, "No se ha podido acceder a los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
