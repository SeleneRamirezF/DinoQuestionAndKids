package com.example.dinoquestionandkids.informacion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.dinoquestionandkids.R;
import com.example.dinoquestionandkids.juego.JuegoActivity;
import com.example.dinoquestionandkids.linea_temporal.LineaTemporalActivity;
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
    private int contador = 0;//7
    private MediaPlayer mp;
    private String activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);

        //codigo para poner sonido en loop (ciclado)
        mp = MediaPlayer.create(this,R.raw.informacion);
        mp.start();
        mp.setLooping(true);

        recView = (RecyclerView) findViewById(R.id.recView);
        miLista = new ArrayList<>();
        activity = getIntent().getStringExtra((String) getResources().getText(R.string.activity));
        //Log.d("ACTIVITY", activity);

        //poner icono en el actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        historia = FirebaseDatabase.getInstance().getReference().child((String) getResources().getText(R.string.hist));
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
                        //Log.d("PRUEBA", textoLimpio);
                        if(contador == 0){
                            miLista.add(new Historia(textoLimpio,texto, R.drawable.info0));
                        }else if(contador == 1){
                            miLista.add(new Historia(textoLimpio,texto, R.drawable.info1));
                        } else if(contador == 2){
                            miLista.add(new Historia(textoLimpio,texto, R.drawable.info2));
                        }else if(contador == 3){
                            miLista.add(new Historia(textoLimpio,texto, R.drawable.info3));
                        }else if(contador == 4){
                            miLista.add(new Historia(textoLimpio,texto, R.drawable.info4));
                        }else if(contador == 5){
                            miLista.add(new Historia(textoLimpio,texto, R.drawable.info5));
                        }else if(contador == 6){
                            miLista.add(new Historia(textoLimpio,texto, R.drawable.info6));
                        }else if(contador == 7){
                            miLista.add(new Historia(textoLimpio,texto, R.drawable.info7));
                        }
                        contador++;
                    }
                    contador = 0;
                    recView.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(InformacionActivity.this, getResources().getText(R.string.no_acceso_datos), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //anulación del boton de 'atras'
    @Override
    public void onBackPressed() {
        mp.stop();
        if(activity.equalsIgnoreCase((String) getResources().getText(R.string.linea))){
            startActivity(new Intent(InformacionActivity.this, LineaTemporalActivity.class));
        }else{
            startActivity(new Intent(InformacionActivity.this, JuegoActivity.class));
        }
    }


}
