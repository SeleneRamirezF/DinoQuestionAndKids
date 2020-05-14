package com.example.dinoquestionandkids.puzzle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class PuzzleAdapter extends BaseAdapter {
    private ArrayList<Button> misBotones = null;
    private int anchoColumnas, altoColumnas;

    public PuzzleAdapter(ArrayList<Button> botones, int anchoColumnas, int altoColumnas) {
        misBotones = botones;
        this.anchoColumnas = anchoColumnas;
        this.altoColumnas = altoColumnas;
    }

    @Override
    public int getCount() {
        return misBotones.size();
    }

    @Override
    public Object getItem(int posicion) {return (Object) misBotones.get(posicion);}

    @Override
    public long getItemId(int posicion) {
        return posicion;
    }

    @Override
    public View getView(int posicion, View convertView, ViewGroup parent) {
        Button boton;
        if (convertView == null) {
            boton = misBotones.get(posicion);
        } else {
            boton = (Button) convertView;
        }
        android.widget.AbsListView.LayoutParams params = new android.widget.AbsListView.LayoutParams(anchoColumnas, altoColumnas);
        boton.setLayoutParams(params);
        return boton;
    }
}
