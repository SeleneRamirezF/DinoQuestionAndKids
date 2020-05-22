package com.example.dinoquestionandkids.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dinoquestionandkids.R;
import com.example.dinoquestionandkids.inicio.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class PerfilActivity extends AppCompatActivity {

    private EditText etNombre;
    private EditText etCorreo;
    private TextView tvNombre;
    private TextView tvCorreo;
    private Button btnEditarPerfil;
    private Button btnGuardarPerfil;
    private Button btnBorrarPerfil;
    private String nombre, correo, contrasena, inicioNombre, inicioCorreo;
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
        user = miAuth.getCurrentUser();

        cargarView();
        obtenetInfoUsuario();

        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                etNombre.setVisibility(View.VISIBLE);
                etNombre.setBackground(getDrawable(R.color.colorFondoTexto));
                etCorreo.setVisibility(View.VISIBLE);
                etCorreo.setBackground(getDrawable(R.color.colorFondoTexto));
            }
        });

        btnGuardarPerfil.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                nombre = etNombre.getText().toString();
                correo = etCorreo.getText().toString();
                if(!nombre.isEmpty()){
                   actualizarNombreUsuario();
                }
                if(!correo.isEmpty()){
                  actualizarCorreoUsuario();
                }
                if(nombre.isEmpty() && correo.isEmpty()){
                    Toast.makeText(PerfilActivity.this, getResources().getText(R.string.no_hay_datos), Toast.LENGTH_SHORT).show();
                }
                etNombre.setVisibility(View.GONE);
                etCorreo.setVisibility(View.GONE);
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
        //Log.d("ID USUARIO",id);
        miBD.child((String) getResources().getText(R.string.usuarios)).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //hacemos la parte de acceso a los datos cuando se inicia con google
                if(user != null){
                    nombre = (String) getResources().getText(R.string.sin_nombre);
                    correo = user.getEmail();
                    //Log.d("NOMBRE USUARIO",nombre);
                   //Log.d("CORREO USUARIO",correo);
                    tvNombre.setText(inicioNombre +" "+ nombre);
                    tvCorreo.setText(inicioCorreo +" "+ correo);
                }
                if (dataSnapshot.exists()){
                    nombre = dataSnapshot.child((String) getResources().getText(R.string.nombre)).getValue().toString();
                    correo = dataSnapshot.child((String) getResources().getText(R.string.correo)).getValue().toString();
                    contrasena = dataSnapshot.child((String) getResources().getText(R.string.contrasena)).getValue().toString();
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

    private void mostrarDialogo(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(PerfilActivity.this);
        builder.setTitle(getResources().getText(R.string.pregunta_borrado_perfil));
        builder.setPositiveButton(getResources().getText(R.string.si), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                borrarUsuario();
                borrarUsuarioDatabase();
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

    private void actualizarNombreUsuario(){
        String id = miAuth.getCurrentUser().getUid();
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombre);
        miBD.child((String)getResources().getText(R.string.usuarios)).child(id).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(PerfilActivity.this,getResources().getText(R.string.usuario_actualizado),Toast.LENGTH_SHORT).show();
                //Log.d("ACTUALIZACION", "nombre de usuario actualizado correctamente");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PerfilActivity.this,getResources().getText(R.string.usuario_no_actualizado),Toast.LENGTH_SHORT).show();
                //Log.d("ACTUALIZACION", "nombre de usuario no actualizado");
            }
        });
    }

    private void actualizarCorreoUsuario(){
        String id = miAuth.getCurrentUser().getUid();
        Map<String, Object> map = new HashMap<>();
        map.put("correo", correo);
        miBD.child((String)getResources().getText(R.string.usuarios)).child(id).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(PerfilActivity.this,getResources().getText(R.string.usuario_actualizado),Toast.LENGTH_SHORT).show();
                //Log.d("ACTUALIZACION", "correo de usuario actualizado correctamente");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PerfilActivity.this,getResources().getText(R.string.usuario_no_actualizado),Toast.LENGTH_SHORT).show();
                //Log.d("ACTUALIZACION", "correo de usuario no actualizado");
            }
        });
    }

    private void borrarUsuario(){
        AuthCredential credential = EmailAuthProvider
                .getCredential(correo, contrasena);
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(PerfilActivity.this,getResources().getText(R.string.usuario_eliminado),Toast.LENGTH_SHORT).show();
                                    //Log.d("BORRADO","Usuario eleminado correctamente");
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(PerfilActivity.this,getResources().getText(R.string.usuario_no_eliminado),Toast.LENGTH_SHORT).show();
                                //Log.e("BORRADO","Ocurrio un error durante la eliminación del usuario", e);
                            }
                        });
                    }
                });
    }

    private void borrarUsuarioDatabase(){
        String id = miAuth.getCurrentUser().getUid();
        DatabaseReference usuarioABorrar = FirebaseDatabase.getInstance().getReference((String) getResources().getText(R.string.usuarios)).child(id);
        usuarioABorrar.removeValue();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PerfilActivity.this, MenuActivity.class));

    }
}
