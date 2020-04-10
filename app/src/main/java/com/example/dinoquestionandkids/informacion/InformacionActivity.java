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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InformacionActivity extends AppCompatActivity {

    private RecyclerView recView;
    ArrayList<Historia> miLista;
    private HistoriaAdapter adapter;
    private DatabaseReference miBDRef;
    private String origen, edades, geo, precambirco, fanerozioco, paleozoico, mesozoico, cenozoico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);
        recView = findViewById(R.id.recView);
        miLista = new ArrayList<>();

        recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        adapter = new HistoriaAdapter(miLista);
        recView.setAdapter(adapter);

        //TODO
        //obtener datos y mantenerlos actualizados





        cargarHistorias();
    }

    private void cargarHistorias() {
        Historia origenT = new Historia("Origen de la tierra", origen, R.drawable.inicio);
        miLista.add(origenT);
        Historia bbb = new Historia("b", "bb", R.drawable.inicio);
        miLista.add(bbb);
        Historia ccc = new Historia("c", "cc", R.drawable.inicio);
        miLista.add(ccc);
        Historia ddd = new Historia("d", "dd", R.drawable.inicio);
        miLista.add(ddd);
        Historia eee = new Historia("e", "ee", R.drawable.inicio);
        miLista.add(eee);
    }
}
