package com.example.dinoquestionandkids;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //variables
    private Button btnAccesoRegistro;
    private Button btnAccesoLogin;
    private FirebaseAuth miauth;
    private SignInButton btnGoogle;
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cargarViews();
        miauth = FirebaseAuth.getInstance();

        //creacion al vuelo de la accion de clicar el boton de registrar usuario
        btnAccesoRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistroActivity.class));
            }
        });

        //creacion al vuelo de la accion de clicar el boton de login
        btnAccesoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        //funcionalidad boton google
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //proveedor de autenticación
                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        new AuthUI.IdpConfig.GoogleBuilder().build());
                //Crear e iniciar el intent de inicio de sesión
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                //para que google te de ha elegir entre diferentes cuentas para el registro
                                .setIsSmartLockEnabled(false)
                                .build(),
                        RC_SIGN_IN);
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
        if (miauth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this, Activity2.class));
            finish();
        }
    }

    //este metodo gestiona la respuesta del registro con google
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Intent i = new Intent(MainActivity.this, Activity2.class);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "ERROR: "+ response.getError().getErrorCode(), Toast.LENGTH_SHORT).show();
            }
        }
    }



}
