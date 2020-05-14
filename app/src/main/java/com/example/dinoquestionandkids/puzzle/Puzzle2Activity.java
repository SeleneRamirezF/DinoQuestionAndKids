package com.example.dinoquestionandkids.puzzle;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import androidx.appcompat.app.AlertDialog;

import com.example.dinoquestionandkids.BaseActivity;
import com.example.dinoquestionandkids.R;


public class Puzzle2Activity extends BaseActivity {

    private static DetectarGestosGridView miGridView;
    private static final int COLUMNAS = 3;
    private static final int DIMENSIONES = COLUMNAS * COLUMNAS;
    private static int anchoColumna, altoColumna;
    public static final String arriba = "up";
    public static final String abajo = "down";
    public static final String izquierda = "left";
    public static final String derecha = "right";
    private static String[] listaPiezas;
    private static int n;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle2);

        pantallaCompleta();

        mp = MediaPlayer.create(this, R.raw.juego_puzzle);
        mp.start();

        n = getIntent().getIntExtra((String)getResources().getText(R.string.boton), -1);

        //poner icono en el actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        inicio();
        colocacionPiezas();
        setDimensiones();
        comprobarSiResuelto();
    }
    private void inicio() {
        miGridView = (DetectarGestosGridView) findViewById(R.id.grid);
        miGridView.setNumColumns(COLUMNAS);
        listaPiezas = new String[DIMENSIONES];
        for (int i = 0; i < DIMENSIONES; i++) {
            listaPiezas[i] = String.valueOf(i);
        }
    }
    private void colocacionPiezas() {
        int indice;
        String cadenaTemp;
        Random random = new Random();
        for (int i = listaPiezas.length - 1; i > 0; i--) {
            indice = random.nextInt(i + 1);
            cadenaTemp = listaPiezas[indice];
            listaPiezas[indice] = listaPiezas[i];
            listaPiezas[i] = cadenaTemp;
        }
    }
    private void setDimensiones() {
        ViewTreeObserver vto = miGridView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                miGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int displayAncho = miGridView.getMeasuredWidth();
                int displayAlto = miGridView.getMeasuredHeight();
                int statusbarAlto = getDimensionAlto(getApplicationContext());
                int requiredAlto = displayAlto - statusbarAlto;
                anchoColumna = displayAncho / COLUMNAS;
                altoColumna = requiredAlto / COLUMNAS;
                colocarPiezas(getApplicationContext());
            }
        });
    }

    private int getDimensionAlto(Context context) {
        int resultado = 0;
        int id = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (id > 0) {
            resultado = context.getResources().getDimensionPixelSize(id);
        }
        return resultado;
    }

    private static void colocarPiezas(Context context) {
        ArrayList<Button> botones = new ArrayList<>();
        Button boton;
        if(n == 1){
            for (int i = 0; i < listaPiezas.length; i++) {
                boton = new Button(context);
                if (listaPiezas[i].equals("0"))
                    boton.setBackgroundResource(R.drawable.diplo_fila_1_col_1);
                else if (listaPiezas[i].equals("1"))
                    boton.setBackgroundResource(R.drawable.diplo_fila_1_col_2);
                else if (listaPiezas[i].equals("2"))
                    boton.setBackgroundResource(R.drawable.diplo_fila_1_col_3);
                else if (listaPiezas[i].equals("3"))
                    boton.setBackgroundResource(R.drawable.diplo_fila_2_col_1);
                else if (listaPiezas[i].equals("4"))
                    boton.setBackgroundResource(R.drawable.diplo_fila_2_col_2);
                else if (listaPiezas[i].equals("5"))
                    boton.setBackgroundResource(R.drawable.diplo_fila_2_col_3);
                else if (listaPiezas[i].equals("6"))
                    boton.setBackgroundResource(R.drawable.diplo_fila_3_col_1);
                else if (listaPiezas[i].equals("7"))
                    boton.setBackgroundResource(R.drawable.diplo_fila_3_col_2);
                else if (listaPiezas[i].equals("8"))
                    boton.setBackgroundResource(R.drawable.diplo_fila_3_col_3);
                botones.add(boton);
            }
        }else if(n == 2){
            for (int i = 0; i < listaPiezas.length; i++) {
                boton = new Button(context);
                if (listaPiezas[i].equals("0"))
                    boton.setBackgroundResource(R.drawable.stego_fila_1_col_1);
                else if (listaPiezas[i].equals("1"))
                    boton.setBackgroundResource(R.drawable.stego_fila_1_col_2);
                else if (listaPiezas[i].equals("2"))
                    boton.setBackgroundResource(R.drawable.stego_fila_1_col_3);
                else if (listaPiezas[i].equals("3"))
                    boton.setBackgroundResource(R.drawable.stego_fila_2_col_1);
                else if (listaPiezas[i].equals("4"))
                    boton.setBackgroundResource(R.drawable.stego_fila_2_col_2);
                else if (listaPiezas[i].equals("5"))
                    boton.setBackgroundResource(R.drawable.stego_fila_2_col_3);
                else if (listaPiezas[i].equals("6"))
                    boton.setBackgroundResource(R.drawable.stego_fila_3_col_1);
                else if (listaPiezas[i].equals("7"))
                    boton.setBackgroundResource(R.drawable.stego_fila_3_col_2);
                else if (listaPiezas[i].equals("8"))
                    boton.setBackgroundResource(R.drawable.stego_fila_3_col_3);
                botones.add(boton);
            }
        }else if(n == 3){
            for (int i = 0; i < listaPiezas.length; i++) {
                boton = new Button(context);
                if (listaPiezas[i].equals("0"))
                    boton.setBackgroundResource(R.drawable.raptor_fila_1_col_1);
                else if (listaPiezas[i].equals("1"))
                    boton.setBackgroundResource(R.drawable.raptor_fila_1_col_2);
                else if (listaPiezas[i].equals("2"))
                    boton.setBackgroundResource(R.drawable.raptor_fila_1_col_3);
                else if (listaPiezas[i].equals("3"))
                    boton.setBackgroundResource(R.drawable.raptor_fila_2_col_1);
                else if (listaPiezas[i].equals("4"))
                    boton.setBackgroundResource(R.drawable.raptor_fila_2_col_2);
                else if (listaPiezas[i].equals("5"))
                    boton.setBackgroundResource(R.drawable.raptor_fila_2_col_3);
                else if (listaPiezas[i].equals("6"))
                    boton.setBackgroundResource(R.drawable.raptor_fila_3_col_1);
                else if (listaPiezas[i].equals("7"))
                    boton.setBackgroundResource(R.drawable.raptor_fila_3_col_2);
                else if (listaPiezas[i].equals("8"))
                    boton.setBackgroundResource(R.drawable.raptor_fila_3_col_3);
                botones.add(boton);
            }
        }else if(n == 4){
            for (int i = 0; i < listaPiezas.length; i++) {
                boton = new Button(context);
                if (listaPiezas[i].equals("0"))
                    boton.setBackgroundResource(R.drawable.trice_fila_1_col_1);
                else if (listaPiezas[i].equals("1"))
                    boton.setBackgroundResource(R.drawable.trice_fila_1_col_2);
                else if (listaPiezas[i].equals("2"))
                    boton.setBackgroundResource(R.drawable.trice_fila_1_col_3);
                else if (listaPiezas[i].equals("3"))
                    boton.setBackgroundResource(R.drawable.trice_fila_2_col_1);
                else if (listaPiezas[i].equals("4"))
                    boton.setBackgroundResource(R.drawable.trice_fila_2_col_2);
                else if (listaPiezas[i].equals("5"))
                    boton.setBackgroundResource(R.drawable.trice_fila_2_col_3);
                else if (listaPiezas[i].equals("6"))
                    boton.setBackgroundResource(R.drawable.trice_fila_3_col_1);
                else if (listaPiezas[i].equals("7"))
                    boton.setBackgroundResource(R.drawable.trice_fila_3_col_2);
                else if (listaPiezas[i].equals("8"))
                    boton.setBackgroundResource(R.drawable.trice_fila_3_col_3);
                botones.add(boton);
            }
        }else if(n == 5){
            for (int i = 0; i < listaPiezas.length; i++) {
                boton = new Button(context);
                if (listaPiezas[i].equals("0"))
                    boton.setBackgroundResource(R.drawable.diplo2_fila_1_col_1);
                else if (listaPiezas[i].equals("1"))
                    boton.setBackgroundResource(R.drawable.diplo2_fila_1_col_2);
                else if (listaPiezas[i].equals("2"))
                    boton.setBackgroundResource(R.drawable.diplo2_fila_1_col_3);
                else if (listaPiezas[i].equals("3"))
                    boton.setBackgroundResource(R.drawable.diplo2_fila_2_col_1);
                else if (listaPiezas[i].equals("4"))
                    boton.setBackgroundResource(R.drawable.diplo2_fila_2_col_2);
                else if (listaPiezas[i].equals("5"))
                    boton.setBackgroundResource(R.drawable.diplo2_fila_2_col_3);
                else if (listaPiezas[i].equals("6"))
                    boton.setBackgroundResource(R.drawable.diplo2_fila_3_col_1);
                else if (listaPiezas[i].equals("7"))
                    boton.setBackgroundResource(R.drawable.diplo2_fila_3_col_2);
                else if (listaPiezas[i].equals("8"))
                    boton.setBackgroundResource(R.drawable.diplo2_fila_3_col_3);
                botones.add(boton);
            }
        }else if(n == 6){
            for (int i = 0; i < listaPiezas.length; i++) {
                boton = new Button(context);
                if (listaPiezas[i].equals("0"))
                    boton.setBackgroundResource(R.drawable.raptor2_fila_1_col_1);
                else if (listaPiezas[i].equals("1"))
                    boton.setBackgroundResource(R.drawable.raptor2_fila_1_col_2);
                else if (listaPiezas[i].equals("2"))
                    boton.setBackgroundResource(R.drawable.raptor2_fila_1_col_3);
                else if (listaPiezas[i].equals("3"))
                    boton.setBackgroundResource(R.drawable.raptor2_fila_2_col_1);
                else if (listaPiezas[i].equals("4"))
                    boton.setBackgroundResource(R.drawable.raptor2_fila_2_col_2);
                else if (listaPiezas[i].equals("5"))
                    boton.setBackgroundResource(R.drawable.raptor2_fila_2_col_3);
                else if (listaPiezas[i].equals("6"))
                    boton.setBackgroundResource(R.drawable.raptor2_fila_3_col_1);
                else if (listaPiezas[i].equals("7"))
                    boton.setBackgroundResource(R.drawable.raptor2_fila_3_col_2);
                else if (listaPiezas[i].equals("8"))
                    boton.setBackgroundResource(R.drawable.raptor2_fila_3_col_3);
                botones.add(boton);
            }
        }
        miGridView.setAdapter(new PuzzleAdapter(botones, anchoColumna, altoColumna));
    }

    private static void intercambioPiezas(Context context, int posicionActual, int intercambio) {
        String nuevaPosicion = listaPiezas[posicionActual + intercambio];
        listaPiezas[posicionActual + intercambio] = listaPiezas[posicionActual];
        listaPiezas[posicionActual] = nuevaPosicion;
        colocarPiezas(context);
    }

    public static void movimientoPiezas(Context context, String direccion, int posicion) {
        //Piezas esquina superior izquierda
        if (posicion == 0) {
            if (direccion.equals(derecha)) intercambioPiezas(context, posicion, 1);
            else if (direccion.equals(abajo)) intercambioPiezas(context, posicion, COLUMNAS);
            else Toast.makeText(context, "Movimiento no válido", Toast.LENGTH_SHORT).show();
        //Piezas centro superior
        } else if (posicion > 0 && posicion < COLUMNAS - 1) {
            if (direccion.equals(izquierda)) intercambioPiezas(context, posicion, -1);
            else if (direccion.equals(abajo)) intercambioPiezas(context, posicion, COLUMNAS);
            else if (direccion.equals(derecha)) intercambioPiezas(context, posicion, 1);
            else Toast.makeText(context, "Movimiento no válido", Toast.LENGTH_SHORT).show();
        //Piezas esquina superior derecha
        } else if (posicion == COLUMNAS - 1) {
            if (direccion.equals(izquierda)) intercambioPiezas(context, posicion, -1);
            else if (direccion.equals(abajo)) intercambioPiezas(context, posicion, COLUMNAS);
            else Toast.makeText(context, "Movimiento no válido", Toast.LENGTH_SHORT).show();
        //Piezas lado izquierdo
        } else if (posicion > COLUMNAS - 1 && posicion < DIMENSIONES - COLUMNAS &&
                posicion % COLUMNAS == 0) {
            if (direccion.equals(arriba)) intercambioPiezas(context, posicion, -COLUMNAS);
            else if (direccion.equals(derecha)) intercambioPiezas(context, posicion, 1);
            else if (direccion.equals(abajo)) intercambioPiezas(context, posicion, COLUMNAS);
            else Toast.makeText(context, "Movimiento no válido", Toast.LENGTH_SHORT).show();
        //Piezas lado derecho y esquina inferior derecha
        } else if (posicion == COLUMNAS * 2 - 1 || posicion == COLUMNAS * 3 - 1) {
            if (direccion.equals(arriba)) intercambioPiezas(context, posicion, -COLUMNAS);
            else if (direccion.equals(izquierda)) intercambioPiezas(context, posicion, -1);
            else if (direccion.equals(abajo)) {
                //Tolera solo las piezas del lado derecho para intercambiar hacia abajo en
                // lugar de las piezas de la esquina inferior derecha.
                if (posicion <= DIMENSIONES - COLUMNAS - 1) intercambioPiezas(context, posicion,
                        COLUMNAS);
                else Toast.makeText(context, "Movimiento no válido", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(context, "Movimiento no válido", Toast.LENGTH_SHORT).show();
        //Piezas esquina inferior izquierda
        }else if(posicion == DIMENSIONES - COLUMNAS){
            if (direccion.equals(arriba)) intercambioPiezas(context, posicion, -COLUMNAS);
            else if (direccion.equals(derecha)) intercambioPiezas(context, posicion, 1);
            else Toast.makeText(context, "Movimiento no válido", Toast.LENGTH_SHORT).show();
        //Piezas del centro inferior
        }else if(posicion < DIMENSIONES - 1 && posicion > DIMENSIONES - COLUMNAS){
            if (direccion.equals(arriba)) intercambioPiezas(context, posicion, -COLUMNAS);
            else if (direccion.equals(izquierda)) intercambioPiezas(context, posicion, -1);
            else if (direccion.equals(derecha)) intercambioPiezas(context, posicion, 1);
            else Toast.makeText(context, "Movimiento no válido", Toast.LENGTH_SHORT).show();
        //Piezas centrales
        }else{
            if (direccion.equals(arriba)) intercambioPiezas(context, posicion, -COLUMNAS);
            else if (direccion.equals(izquierda)) intercambioPiezas(context, posicion, -1);
            else if (direccion.equals(derecha)) intercambioPiezas(context, posicion, 1);
            else intercambioPiezas(context, posicion, COLUMNAS);
        }
    }
    private static boolean resuelto() {
        boolean resuelto = false;
        for(int i = 0; i < listaPiezas.length; i++){
            if(listaPiezas[i].equals(String.valueOf(i))){
                resuelto = true;
            }else{
                resuelto = false;
                break;
            }
        }return resuelto;
    }
    @Override
    public void onBackPressed() {
        mp.stop();
        startActivity(new Intent(Puzzle2Activity.this, PuzzleActivity.class));
    }
    private void mostrasDialogo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Puzzle2Activity.this);
        builder.setTitle(getResources().getText(R.string.puzzle_fin));
        builder.setMessage(getResources().getText(R.string.puzzle_elejir)).setPositiveButton(getResources().getText(R.string.si), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mp.stop();
                startActivity(new Intent(Puzzle2Activity.this, PuzzleActivity.class));
            }
        }).setNegativeButton(getResources().getText(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Puzzle2Activity.this, getResources().getText(R.string.puzzle_disfruta), Toast.LENGTH_SHORT).show();
            }
        }).setCancelable(false).show();
    }
    public void comprobarSiResuelto(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if(resuelto()){
                    mostrasDialogo();
                }else{
                    comprobarSiResuelto();
                }
            }
        }, 1000);
    }
}
