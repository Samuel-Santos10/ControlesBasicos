package com.example.controlesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Usuario_Programador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario__programador);

        try {

            ImageButton btnSelecUsuario = (ImageButton) findViewById(R.id.btnUsuario);
            btnSelecUsuario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IniciUsuario();
                }
            });

            ImageButton btnProgra = (ImageButton)findViewById(R.id.btnProgramador);
            btnProgra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IniciProgramador();
                }
            });

        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Error al ingresar en la opcion.", Toast.LENGTH_SHORT).show();
        }
    }

    void IniciUsuario(){
        Intent iniUsuario = new Intent(Usuario_Programador.this, MainActivity.class);
        startActivity(iniUsuario);
    }

    void IniciProgramador(){
        Intent admiCpu = new Intent(Usuario_Programador.this, Main.class);
        startActivity(admiCpu);
    }

}