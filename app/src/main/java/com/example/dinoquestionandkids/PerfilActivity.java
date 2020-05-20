package com.example.dinoquestionandkids;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dinoquestionandkids.inicio.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PerfilActivity extends AppCompatActivity {

    private EditText etNombre;
    private EditText etCorreo;
    private TextView tvNombre;
    private TextView tvCorreo;
    private Button btnEditarPerfil;
    private Button btnGuardarPerfil;
    private Button btnBorrarPerfil;
    private String nombre, correo, inicioNombre, inicioCorreo;
    private FirebaseAuth miAuth;
    private FirebaseUser user;
    private DatabaseReference miBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        inicioNombre = getResources().getText(R.string.nombre_actual).toString();
        inicioCorreo = getResources().getText(R.string.correo_actual).toString();

        miAuth = FirebaseAuth.getInstance();
        miBD = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        cargarView();
        obtenetInfoUsuario();

        //TODO no funciona la actuslizacion ed datos ni el borrado!!!

        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etNombre.setEnabled(true);
                etCorreo.setEnabled(true);
            }
        });

        btnGuardarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = etNombre.getText().toString();
                correo = etCorreo.getText().toString();
                if(!nombre.isEmpty()){
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(nombre)
                            .build();
                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("ACTUALIZACION", "nombre de usuario actualizado correctamente");
                                    }
                                }
                            });
                }
                if(!correo.isEmpty()){
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    user.updateEmail(correo)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("ACTUALIZACION", "correo de usuario actualizado correctamente");
                                    }
                                }
                            });
                }
            }
        });

        btnBorrarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogo();
            }
        });
    }

    private void cargarView() {
        etNombre = (EditText) findViewById(R.id.etNombre);
        etCorreo = (EditText) findViewById(R.id.etCorreo);
        btnEditarPerfil = (Button) findViewById(R.id.btnEditarPerfil);
        btnGuardarPerfil = (Button) findViewById(R.id.btnGuardarPerfil);
        btnBorrarPerfil = (Button) findViewById(R.id.btnBorrarPerfil);
        tvNombre = (TextView) findViewById(R.id.tvNombre);
        tvCorreo = (TextView) findViewById(R.id.tvCorreo);
    }

    private void obtenetInfoUsuario(){
        String id = miAuth.getCurrentUser().getUid();
        Log.d("ID USUARIO",id);
        miBD.child((String) getResources().getText(R.string.usuarios)).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //hacemos la parte de acceso a los datos cuando se inicia con google
                if(user != null){
                    nombre = "Sin nombre de usuario";
                    correo = user.getEmail();
                    //Log.d("NOMBRE USUARIO",nombre);
                   //Log.d("CORREO USUARIO",correo);
                    tvNombre.setText(inicioNombre +" "+ nombre);
                    tvCorreo.setText(inicioCorreo +" "+ correo);
                }
                if (dataSnapshot.exists()){
                    nombre = dataSnapshot.child((String) getResources().getText(R.string.nombre)).getValue().toString();
                    correo = dataSnapshot.child((String) getResources().getText(R.string.correo)).getValue().toString();
                    //Log.d("NOMBRE USUARIO",nombre);
                    //Log.d("CORREO USUARIO",correo);
                    tvNombre.setText(inicioNombre +" "+ nombre);
                    tvCorreo.setText(inicioCorreo +" "+ correo);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PerfilActivity.this, getResources().getText(R.string.no_acceso_datos), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //crear dialogo
    private void mostrarDialogo(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(PerfilActivity.this);
        builder.setTitle(getResources().getText(R.string.pregunta_borrado_perfil));
        builder.setPositiveButton(getResources().getText(R.string.si), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //borramos el usuario
                user = FirebaseAuth.getInstance().getCurrentUser();
                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("BORRADO", "Usuario borrado correctamente");
                                }
                            }
                        });
                //salimos y vamos al inicio
                miAuth.signOut();
                startActivity(new Intent(PerfilActivity.this, MainActivity.class));
                finish();
            }
        }).setNegativeButton(getResources().getText(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        })
                .setCancelable(false)
                .show();
    }


}
