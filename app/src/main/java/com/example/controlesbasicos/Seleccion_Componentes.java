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
            Button btnSelecMotherboard = (Button) findViewById(R.id.btnSelecMother);
            btnSelecMotherboard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    informacionMother();


                }
            });

            Button btnSelecGPU = (Button) findViewById(R.id.btnSelecGPU);
            btnSelecGPU.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    informacionGpu();
                }
            });

            Button btnSelecRAM = (Button) findViewById(R.id.btnSelecRAM);
            btnSelecRAM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    informacionRam();
                }
            });

            Button btnSelecDISCO = (Button) findViewById(R.id.btnSelecDisco);
            btnSelecDISCO.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    informacionDisco();
                }
            });

            Button btnSelecFuente = (Button) findViewById(R.id.btnSelecFuente);
            btnSelecFuente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    informacionFuente();
                }
            });
            //aqui ño
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Error al ingresar en la opcion.", Toast.LENGTH_SHORT).show();
        }

    }
    void informacionBasica(){
        Intent admiCpu = new Intent(Seleccion_Componentes.this, AdmiCPU.class);
        startActivity(admiCpu);
    }

    void informacionMother(){
        Intent admiMotherboard = new Intent(Seleccion_Componentes.this, AdmiMotherboard.class);
        startActivity(admiMotherboard);
    }
    void informacionGpu(){
        Intent admiGpu = new Intent(Seleccion_Componentes.this, AdmiGPU.class);
        startActivity(admiGpu);
    }

    void informacionRam(){
        Intent admiRam = new Intent(Seleccion_Componentes.this, AdmiRAM.class);
        startActivity(admiRam);
    }

    void informacionDisco(){
        Intent admiDisco = new Intent(Seleccion_Componentes.this, AdmiDisco.class);
        startActivity(admiDisco);
    }

    void informacionFuente(){
        Intent admiFuente = new Intent(Seleccion_Componentes.this, AdmiFuente.class);
        startActivity(admiFuente);
    }
}