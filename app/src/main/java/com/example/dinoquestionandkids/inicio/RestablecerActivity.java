package com.example.dinoquestionandkids.inicio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dinoquestionandkids.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RestablecerActivity extends AppCompatActivity {

    private EditText etCorreo;
    private Button btnRestablecer;
    private String correo;
    private FirebaseAuth miAuth;
    private ProgressDialog miDialogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restablecer);

        miAuth = FirebaseAuth.getInstance();
        miDialogo = new ProgressDialog(this);

        cargarViews();
        //poner icono en el actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icono_barra);

        btnRestablecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correo = etCorreo.getText().toString();
                if (!correo.isEmpty()){
                    miDialogo.setMessage(getResources().getText(R.string.enviando));
                    miDialogo.setCanceledOnTouchOutside(false);
                    miDialogo.show();
                    restablecerContrasena();
                    startActivity(new Intent(RestablecerActivity.this, MainActivity.class));
                }else{
                    Toast.makeText(RestablecerActivity.this, getResources().getText(R.string.introducir),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void cargarViews() {
        etCorreo = (EditText) findViewById(R.id.etCorreo);
        btnRestablecer = (Button) findViewById(R.id.btnRestablecer);
    }

    private void restablecerContrasena(){
        miAuth.setLanguageCode("es");
        miAuth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RestablecerActivity.this, getResources().getText(R.string.restablecer),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RestablecerActivity.this, getResources().getText(R.string.no_restablecer),Toast.LENGTH_SHORT).show();
                }
                miDialogo.dismiss();
            }
        });
    }
}
