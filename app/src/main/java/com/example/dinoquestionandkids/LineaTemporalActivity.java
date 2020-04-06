package com.example.dinoquestionandkids;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dinoquestionandkids.ui.main.SectionsPagerAdapter;

public class LineaTemporalActivity extends AppCompatActivity{

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    private LinearLayout puntos;
    private TextView[] puntosSlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linea_temporal);

        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        //acciones puntos de posicionamiento
        puntos = (LinearLayout) findViewById(R.id.idPuntos);
        agregarPuntos(0);
        viewPager.addOnPageChangeListener(viewListener);
    }

    //metodo que construye los puntos de posicionamiento
    private void agregarPuntos(int posicion) {
        puntosSlide = new TextView[8];
        puntos.removeAllViews();
        for (int i=0; i<puntosSlide.length; i++){
            puntosSlide[i] = new TextView(this);
            puntosSlide[i].setText(Html.fromHtml("&#8226;"));
            puntosSlide[i].setTextSize(45);
            puntosSlide[i].setTextColor(getResources().getColor(R.color.colorBlancoTransparente));
            puntos.addView(puntosSlide[i]);
        }
        if (puntosSlide.length > 0){
            puntosSlide[posicion].setTextColor(getResources().getColor(R.color.colorBlanco));
        }
    }

    //listener para el movimiento del dedo por la pantalla
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            agregarPuntos(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}