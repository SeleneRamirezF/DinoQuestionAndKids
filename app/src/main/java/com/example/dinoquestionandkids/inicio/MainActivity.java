package com.example.dinoquestionandkids.inicio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dinoquestionandkids.R;
import com.example.dinoquestionandkids.menu.MenuActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //variables
    private Button btnAccesoRegistro;
    private Button btnAccesoLogin;
    private FirebaseAuth miAuth;
    private SignInButton btnGoogle;
    private final static int RC_SIGN_IN = 123;
    private MediaPlayer mp;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //codigo para poner sonido en loop (ciclado)
        mp = MediaPlayer.create(this,R.raw.main);
        mp.start();
        mp.setLooping(true);

        cargarViews();
        miAuth = FirebaseAuth.getInstance();
        //creacion al vuelo de la accion de clicar el boton de registrar usuario
        btnAccesoRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                startActivity(new Intent(MainActivity.this, RegistroActivity.class));
            }
        });

        //creacion al vuelo de la accion de clicar el boton de login
        btnAccesoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //proveedor de autenticación
                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        new AuthUI.IdpConfig.GoogleBuilder()
                                .build());
                //Crear e iniciar el intent de inicio de sesión
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                //para que google te de ha elegir entre diferentes cuentas para el registro
                                .setIsSmartLockEnabled(false)
                                .build(), RC_SIGN_IN);
            }
        });
    }
    //metodo para cargar los datos introducidos por el usuario
    private void cargarViews(){
        btnAccesoRegistro = (Button) findViewById(R.id.btnAccesoRegistro);
        btnAccesoLogin = (Button) findViewById(R.id.btnAccesoLogin);
        btnGoogle = (SignInButton) findViewById(R.id.btnGoogle);
    }
    //metodo para que la aplicacion reconozca si ya se inicio sesión con anterioridad y asi entrar directamente a la app
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = miAuth.getCurrentUser();
        if (user != null){
            mp.stop();
            startActivity(new Intent(MainActivity.this, MenuActivity.class));
            finish();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                user = miAuth.getCurrentUser();
                //Log.d("DATOS GOOGLE", user.getUid());
                //Log.d("DATOS GOOGLE", user.getEmail());
                mp.stop();
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
                finish();
            } else {
                Toast.makeText(MainActivity.this, getResources().getText(R.string.error) + " " + response.getError(), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "Registrate para poder acceder", Toast.LENGTH_SHORT).show();
            }
        }
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

}
