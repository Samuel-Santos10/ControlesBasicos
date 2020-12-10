package com.example.controlesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Usuario_ventana extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_ventana);

        try {
            ImageButton btnInfoBasica = (ImageButton)findViewById(R.id.btnInfoBasica);
            btnInfoBasica.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    informacionBasica();
                }
            });
            ImageButton btnPcPersonalizar = (ImageButton)findViewById(R.id.btnPcPersonalizada);
            btnPcPersonalizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pcPersonalizada();
                }
            });

            ImageButton btnNovedades = (ImageButton)findViewById(R.id.btnNovedades);
            btnNovedades.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    novedades();
                }
            });
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Error al ingresar en la opcion.", Toast.LENGTH_SHORT).show();
        }

    }
    void informacionBasica(){
        Intent inforBasica = new Intent(Usuario_ventana.this, InformacionBasica.class);
        startActivity(inforBasica);
    }
    void pcPersonalizada(){
        Intent personalizar = new Intent(Usuario_ventana.this, Seleccion_Componentes.class);
        startActivity(personalizar);
    }
    void novedades(){
        Intent Novedades = new Intent(Usuario_ventana.this, com.example.controlesbasicos.Novedades.class);
        startActivity(Novedades);
    }
}