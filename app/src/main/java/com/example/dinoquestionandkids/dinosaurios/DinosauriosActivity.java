package com.example.dinoquestionandkids.dinosaurios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.dinoquestionandkids.R;
import com.example.dinoquestionandkids.dinosaurios.Dino;
import com.example.dinoquestionandkids.dinosaurios.DinoAdapter;

import java.util.ArrayList;

public class DinosauriosActivity extends AppCompatActivity implements DinoAdapter.ListClickItem{

    private RecyclerView recView;
    private ArrayList<Dino> miLista;
    private int REQ_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinosaurios);

        recView = (RecyclerView) findViewById(R.id.recView);
        miLista = new ArrayList<Dino>();
        //si cambiamos el layoutManager a horizontal, se pone en una sola linea la lista
        //si le pongo un gridLayout de dos columnas, cambia la forma de ver la lista
        // recView.setLayoutManager(new LinearLayoutManager(this, 2));
        recView.setLayoutManager(new GridLayoutManager(this, 3));
        final DinoAdapter adapter = new DinoAdapter(miLista, this);
        recView.setAdapter(adapter);
        cargarDinos();

    }

    public void cargarDinos() {
        Dino almeria = new Dino(R.drawable.inicio);
        miLista.add(almeria);
        Dino athmadrid = new Dino(R.drawable.inicio);
        miLista.add(athmadrid);
        Dino barcelona = new Dino(R.drawable.inicio);
        miLista.add(barcelona);
        Dino betis = new Dino(R.drawable.inicio);
        miLista.add(betis);
        Dino bilbao = new Dino(R.drawable.inicio);
        miLista.add(bilbao);
        Dino espania = new Dino(R.drawable.inicio);
        miLista.add(espania);
        Dino getafe = new Dino(R.drawable.inicio);
        miLista.add(getafe);
        Dino madrid = new Dino(R.drawable.inicio);
        miLista.add(madrid);
        Dino rsociedad = new Dino(R.drawable.inicio);
        miLista.add(rsociedad);
        Dino sevilla = new Dino(R.drawable.inicio);
        miLista.add(sevilla);
        Dino valencia = new Dino(R.drawable.inicio);
        miLista.add(valencia);
    }

    @Override
    public void onListClickItem(int posicion) {
        //AlertDialog.Builder miMensaje = new AlertDialog.Builder(this);
        //miMensaje.setMessage(miLista.get(posicion).getNombre()+", "+miLista.get(posicion).getDescripcion());
        //miMensaje.setPositiveButton("Aceptar",null);
        //miMensaje.show();
        switch (posicion) {
            case 0:  //startActivity(new Intent(DinosauriosActivity.this, Dino0Activity.class));
                break;
            case 1:  startActivity(new Intent(DinosauriosActivity.this, Dino1Activity.class));
                break;
            case 2:  //startActivity(new Intent(DinosauriosActivity.this, Dino2Activity.class));
                break;
            case 3:  //startActivity(new Intent(DinosauriosActivity.this, Dino3Activity.class));
                break;
            case 4:  //startActivity(new Intent(DinosauriosActivity.this, Dino4Activity.class));
                break;
            case 5:  //startActivity(new Intent(DinosauriosActivity.this, Dino5Activity.class));
                break;
            case 6:  //startActivity(new Intent(DinosauriosActivity.this, Dino6Activity.class));
                break;
            case 7:  //startActivity(new Intent(DinosauriosActivity.this, Dino7Activity.class));
                break;
        }
    }
}
