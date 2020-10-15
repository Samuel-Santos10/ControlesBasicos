package com.example.controlesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InformacionBasica extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomacion_basica);

       try {
           Button btnAtras = (Button)findViewById(R.id.btnRegresarMenu);
           btnAtras.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   VolverAlMenu();
               }
           });
           Button btnCompu = (Button)findViewById(R.id.btnCompu);
           btnCompu.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   irCompu();
               }
           });
           Button btnRam = (Button)findViewById(R.id.btnRam);
           btnRam.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   irRam();
               }
           });
           Button btnCPU = (Button)findViewById(R.id.btnCPU);
           btnCPU.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   irCPU();
               }
           });
           Button btnProcesador = (Button)findViewById(R.id.btnProcesador);
           btnProcesador.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   irProcesador();
               }
           });
           Button btnMotherboard = (Button)findViewById(R.id.btnMotherBoard);
           btnMotherboard.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   irMotherboard();
               }
           });
       }catch (Exception ex){

       }
    }
    void VolverAlMenu(){
        Intent Retroceder = new Intent(InformacionBasica.this, MainActivity.class);
        startActivity(Retroceder);
    }
    void irCompu(){
        Intent compu = new Intent(InformacionBasica.this, Compu.class);
        startActivity(compu);
    }
    void irRam(){
        Intent Ram = new Intent(InformacionBasica.this, Ram.class);
        startActivity(Ram);
    }
    void irCPU(){
        Intent CPU = new Intent(InformacionBasica.this, CPU.class);
        startActivity(CPU);
    }
    void irProcesador(){
        Intent procesador = new Intent(InformacionBasica.this, Procesador.class);
        startActivity(procesador);
    }
    void irMotherboard(){
        Intent motherboard = new Intent(InformacionBasica.this, Motherboard.class);
        startActivity(motherboard);
    }
}