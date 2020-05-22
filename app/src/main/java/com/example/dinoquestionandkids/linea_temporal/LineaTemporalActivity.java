package com.example.dinoquestionandkids.linea_temporal;

import android.content.Intent;
import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dinoquestionandkids.menu.MenuActivity;
import com.example.dinoquestionandkids.R;
import com.example.dinoquestionandkids.informacion.InformacionActivity;
import com.example.dinoquestionandkids.ui.main.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LineaTemporalActivity extends AppCompatActivity {

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    private LinearLayout puntos;
    private TextView[] puntosSlide;
    private FloatingActionButton btnInfo;

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

        //boton con acceso al activity de la información adicional
        btnInfo = (FloatingActionButton) findViewById(R.id.btnInfo);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //nos manda a un activity de informacion adicional
                Intent i = new Intent(LineaTemporalActivity.this, InformacionActivity.class);
                i.putExtra((String) getResources().getText(R.string.activity), getResources().getText(R.string.linea));
                startActivity(i);
            }
        });


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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LineaTemporalActivity.this, MenuActivity.class));

    }
}