package com.example.dinoquestionandkids;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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
    private FirebaseAuth miauth;
    private DatabaseReference miBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //instances de la base de datos para la autentificacion y para guardar los datos
        miauth = FirebaseAuth.getInstance();
        miBD = FirebaseDatabase.getInstance().getReference();

        cargarViews();

        //creacion al vuelo de la accion de clicar el boton de registrar usuario
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //guardo los datos que se cargaron enteriormente
                nombre = etNombre.getText().toString();
                correo = etCorreo.getText().toString();
                contrasena = etContrasena.getText().toString();

                //confirmo que los campor estan rellenos
                if(!nombre.isEmpty() && !correo.isEmpty() && !contrasena.isEmpty()){
                    //confirmo que la contraseña tiene una longitud minima
                    if(contrasena.length() >= 6){
                        registrarUsuario();
                    }else{
                        Toast.makeText(RegistroActivity.this, "La contraseña debe que tener un mínimo de 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegistroActivity.this, "Ningún campo puede estar vacio", Toast.LENGTH_SHORT).show();
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
        miauth.createUserWithEmailAndPassword(correo, contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Map<String, Object> map = new HashMap<>();
                    map.put("nombre", nombre);
                    map.put("correo", correo);
                    map.put("contraseña", contrasena);

                    String id = miauth.getCurrentUser().getUid();

                    //usando el objeto creado antes de DatabaseReference, mandamos los datos a la base de datos
                    //creamos la rama de usuarios y guardamos los datos por separado
                    miBD.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()) {
                                startActivity(new Intent (RegistroActivity.this, MenuActivity.class));
                                finish();
                            }else{
                                Toast.makeText(RegistroActivity.this, "No se han podido crear los datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Toast.makeText(RegistroActivity.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegistroActivity.this, "No se han podido registrar este usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
