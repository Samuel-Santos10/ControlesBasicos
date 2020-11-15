package com.example.controlesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Seleccion_Componentes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion__componentes);

        try {
            Button btnSelecCPU = (Button) findViewById(R.id.btnSelecCPU);
            btnSelecCPU.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    informacionBasica();
                }
            });

        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Error al ingresar en la opcion.", Toast.LENGTH_SHORT).show();
        }

    }
    void informacionBasica(){
        Intent admiCpu = new Intent(Seleccion_Componentes.this, AdmiCPU.class);
        startActivity(admiCpu);
    }
}