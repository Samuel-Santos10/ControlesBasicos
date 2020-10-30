package com.example.controlesbasicos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
         Intent inforBasica = new Intent(MainActivity.this, InformacionBasica.class);
         startActivity(inforBasica);
     }
    void pcPersonalizada(){
        Intent personalizar = new Intent(MainActivity.this, PcPersonalizada.class);
        startActivity(personalizar);
    }
    void novedades(){
        Intent Novedades = new Intent(MainActivity.this, com.example.controlesbasicos.Novedades.class);
        startActivity(Novedades);
    }
}