package com.example.dinoquestionandkids.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dinoquestionandkids.inicio.MainActivity;
import com.example.dinoquestionandkids.R;
import com.example.dinoquestionandkids.dinosaurios.DinosauriosActivity;
import com.example.dinoquestionandkids.juego.JuegoActivity;
import com.example.dinoquestionandkids.linea_temporal.LineaTemporalActivity;
import com.example.dinoquestionandkids.puzzle.PuzzleActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuActivity extends AppCompatActivity {

    private Button btnCerrarSesion;
    private FirebaseAuth miAuth;
    private TextView tvNombre;
    private TextView tvCorreo;
    private DatabaseReference miBD;
    private Button btn1, btn2, btn3, btn4;
    private FirebaseUser user;
    private MediaPlayer mp;
    private Toolbar toolbar;
    private int cont = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //codigo para poner sonido en loop (ciclado)
        mp = MediaPlayer.create(this,R.raw.main);
        mp.start();
        mp.setLooping(true);

        cargarViews();
        //menu barra superior
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //poner icono en el actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icono_barra);

        //inicializacion variables base de datos
        miAuth = FirebaseAuth.getInstance();
        miBD = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                terminarMusica();
                miAuth.signOut();
                startActivity(new Intent(MenuActivity.this, MainActivity.class));
                finish();
            }
        });
        obtenetInfoUsuario();
        //hacemos la parte de acceso a los datos cuando se inicia con google
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount != null){
            cont = 1;
            tvNombre.setText(signInAccount.getDisplayName());
            tvCorreo.setText(signInAccount.getEmail());
            btn3.setEnabled(false);
            btn4.setEnabled(false);
        }
        //acciones de los botones
        //linea temporal
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                terminarMusica();
                startActivity(new Intent(MenuActivity.this, LineaTemporalActivity.class));
            }
        });
        //dinosaurios
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                terminarMusica();
                startActivity(new Intent(MenuActivity.this, DinosauriosActivity.class));
            }
        });
        //juego
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                terminarMusica();
                startActivity(new Intent(MenuActivity.this, JuegoActivity.class));
            }
        });
        //puzzle
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                terminarMusica();
                startActivity(new Intent(MenuActivity.this, PuzzleActivity.class));
            }
        });

    }

    private void cargarViews(){
        btnCerrarSesion = (Button) findViewById(R.id.btnCerrarSesion);
        tvNombre = (TextView) findViewById(R.id.tvNombre);
        tvCorreo = (TextView) findViewById(R.id.tvCorreo);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
    }
    //metodo para obtener los datos del usuario
    private void obtenetInfoUsuario(){
        String id = miAuth.getCurrentUser().getUid();
        miBD.child((String) getResources().getText(R.string.usuarios)).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    cont = 0;
                    btn3.setEnabled(true);
                    btn4.setEnabled(true);
                    String nombre = dataSnapshot.child((String) getResources().getText(R.string.nombre)).getValue().toString();
                    String correo = dataSnapshot.child((String) getResources().getText(R.string.correo)).getValue().toString();
                    tvNombre.setText(nombre);
                    tvCorreo.setText(correo);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MenuActivity.this, "No se ha podido conectar con la base de datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void terminarMusica(){
        mp.stop();
    }

    //acciones del boton de 'atras'
    private boolean puedoSalirApp = false;
    @Override
    public void onBackPressed() {
        if (!puedoSalirApp) {
            puedoSalirApp = true;
            Toast.makeText(this, "Pulse otra vez para salir", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    puedoSalirApp = false;
                }
            }, 2000);
        } else {
            mp.stop();
            super.onBackPressed();
        }
    }

    //metodos para crear y usar la barra superior para opciones
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflar el menú, esto agrega elementos a la barra de acción si está presente.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ajustes) {
            mostrarDialogo();
        }
        if (id == R.id.acercade) {
            terminarMusica();
            startActivity(new Intent(MenuActivity.this, AcercadeActivity.class));
        }
        if(id == R.id.compartir){
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT,getResources().getString(R.string.app_name));
                String aux = (String) getResources().getText(R.string.descarga);
                //direccion de prueba
                aux = aux + getResources().getText(R.string.descarga_url);
                i.putExtra(Intent.EXTRA_TEXT, aux);
                startActivity(i);
            }catch(Exception e){
                Log.d((String) getResources().getText(R.string.error),e.getMessage());
            }
        }
        if(id == R.id.editarPerfil){
            if(cont == 1){
                mostrarDialogo2();
            }else{
                mp.stop();
                startActivity(new Intent(MenuActivity.this, PerfilActivity.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }
    //crear dialogo
    private void mostrarDialogo(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
        builder.setTitle(getResources().getText(R.string.pregunta_sonido));
        builder.setPositiveButton(getResources().getText(R.string.si), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mp.start();
            }
        }).setNegativeButton(getResources().getText(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mp.pause();
            }
        })
                .setCancelable(false)
                .show();
    }

    private void mostrarDialogo2(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
        builder.setTitle("Estas como invitado, no tienes acceso a esta opción");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setCancelable(false).show();
    }

}
