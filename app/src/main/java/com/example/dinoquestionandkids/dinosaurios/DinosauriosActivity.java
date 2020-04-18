package com.example.dinoquestionandkids.dinosaurios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.dinoquestionandkids.R;

import java.util.ArrayList;

public class DinosauriosActivity extends AppCompatActivity implements DinoAdapter.ListClickItem{

    private RecyclerView recView;
    private ArrayList<Dino> miLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinosaurios);

        recView = (RecyclerView) findViewById(R.id.recView);
        miLista = new ArrayList<>();
        recView.setLayoutManager(new GridLayoutManager(this, 2));
        final DinoAdapter adapter = new DinoAdapter(miLista, this);
        recView.setAdapter(adapter);

        cargarDinos();

    }

    public void cargarDinos() {
        Dino dino1 = new Dino(R.drawable.inicio);
        miLista.add(dino1);
        Dino dino2 = new Dino(R.drawable.inicio);
        miLista.add(dino2);
        Dino dino3 = new Dino(R.drawable.inicio);
        miLista.add(dino3);
        Dino dino4 = new Dino(R.drawable.inicio);
        miLista.add(dino4);
        Dino dino5 = new Dino(R.drawable.inicio);
        miLista.add(dino5);
        Dino dino6 = new Dino(R.drawable.inicio);
        miLista.add(dino6);
        Dino dino7 = new Dino(R.drawable.inicio);
        miLista.add(dino7);
        Dino dino8 = new Dino(R.drawable.inicio);
        miLista.add(dino8);
        Dino dino9 = new Dino(R.drawable.inicio);
        miLista.add(dino9);
        Dino dino10 = new Dino(R.drawable.inicio);
        miLista.add(dino10);
        Dino dino11 = new Dino(R.drawable.inicio);
        miLista.add(dino11);
        Dino dino12 = new Dino(R.drawable.inicio);
        miLista.add(dino12);
    }

    @Override
    public void onListClickItem(int posicion) {
        Intent i = new Intent(DinosauriosActivity.this, DinoInfoActivity.class);
        i.putExtra("posicion", posicion);
        startActivity(i);
    }
}
