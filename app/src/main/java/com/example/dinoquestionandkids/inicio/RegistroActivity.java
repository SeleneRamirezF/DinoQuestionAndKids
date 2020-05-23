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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    //variables
    private EditText etNombre;
    private EditText etCorreo;
    private EditText etContrasena;
    private Button btnRegistro;
    private String nombre, correo, contrasena;
    private String puntos_maximos = "0", puntos = "0", nivel = "1", vidas = "3";
    private FirebaseAuth miAuth;
    private DatabaseReference miBD;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //instances de la base de datos para la autentificacion y para guardar los datos
        miAuth = FirebaseAuth.getInstance();
        miBD = FirebaseDatabase.getInstance().getReference();
        user = miAuth.getCurrentUser();

        cargarViews();

        //poner icono en el actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icono_barra);

        //creacion al vuelo de la accion de clicar el boton de registrar usuario
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //guardo los datos que se cargaron enteriormente
                nombre = etNombre.getText().toString();
                correo = etCorreo.getText().toString();
                contrasena = etContrasena.getText().toString();

                //confirmo que los campos estan rellenos
                if(!nombre.isEmpty() && !correo.isEmpty() && !contrasena.isEmpty()){
                    //confirmo que la contraseña tiene una longitud minima
                    if(contrasena.length() >= 6){
                        registrarUsuario();
                    }else{
                        Toast.makeText(RegistroActivity.this, getResources().getText(R.string.minimo_contraseña), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegistroActivity.this, getResources().getText(R.string.completarCampos), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //metodo para cargar los datos introducidos por el usuario
    private void cargarViews(){
        etNombre = (EditText) findViewById(R.id.etNombre);
        etCorreo = (EditText) findViewById(R.id.etCorreo);
        etContrasena = (EditText) findViewById(R.id.etContrasena);
        btnRegistro = (Button) findViewById(R.id.btnRegistro);
    }

    //este metodo realiza la comprobacion de si el usuario ya existe y si no existe lo crea
    private void registrarUsuario(){
        //usando el objeto creado antes de FirebaseAuth, mandamos los datos al registro
        miAuth.createUserWithEmailAndPassword(correo, contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Map<String, Object> map = new HashMap<>();
                    map.put((String)getResources().getText(R.string.nombre), nombre);
                    map.put((String)getResources().getText(R.string.correo), correo);
                    map.put((String)getResources().getText(R.string.contrasena), contrasena);
                    map.put((String)getResources().getText(R.string.puntos_maximos), puntos_maximos);
                    map.put((String)getResources().getText(R.string.puntos), puntos);
                    map.put((String)getResources().getText(R.string.nivel), nivel);
                    map.put((String)getResources().getText(R.string.vidas), vidas);

                    String id = miAuth.getCurrentUser().getUid();
                    //usando el objeto creado antes de DatabaseReference, mandamos los datos a la base de datos
                    //creamos la rama de usuarios y guardamos los datos por separado
                    miBD.child((String)getResources().getText(R.string.usuarios)).child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()) {
                                //nos vamos al menú
                                startActivity(new Intent (RegistroActivity.this, MenuActivity.class));
                                finish();
                            }else{
                                Toast.makeText(RegistroActivity.this, getResources().getText(R.string.no_datos), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Toast.makeText(RegistroActivity.this, getResources().getText(R.string.usuario_ok), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegistroActivity.this, getResources().getText(R.string.usuario_no_registrado), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
