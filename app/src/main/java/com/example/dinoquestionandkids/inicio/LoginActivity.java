package com.example.dinoquestionandkids.inicio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dinoquestionandkids.menu.MenuActivity;
import com.example.dinoquestionandkids.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    //variables
    private EditText etCorreo;
    private EditText etContrasena;
    private Button btnLogin;
    private Button btnRestablecer;
    private String correo;
    private String contrasena;
    private FirebaseAuth miAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cargarViews();

        ///poner icono en el actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icono_barra);


        miAuth = FirebaseAuth.getInstance();

        //creacion al vuelo de la accion de clipar el boton de login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correo = etCorreo.getText().toString();
                contrasena = etContrasena.getText().toString();
                //conprobamos que los campos esten rellenos
                if (!correo.isEmpty() && !contrasena.isEmpty()){
                    loginUsuario();
                }else{
                    Toast.makeText(LoginActivity.this, getResources().getText(R.string.completarCampos), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRestablecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RestablecerActivity.class));
            }
        });
    }

    private void cargarViews(){
        etCorreo = (EditText) findViewById(R.id.etCorreo);
        etContrasena = (EditText) findViewById(R.id.etContrasena);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRestablecer = (Button) findViewById(R.id.btnReestablecer);
    }

    //metodo para verificar si el usuario introducido existe
    private void loginUsuario(){
        //mandamos los datos introducidos y comprobamos
        miAuth.signInWithEmailAndPassword(correo, contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, getResources().getText(R.string.datos_erroneos), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

