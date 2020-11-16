package com.example.controlesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class PantalladeCarga extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallade_carga);
        //agregar animacion
        Animation animacion1 = AnimationUtils.loadAnimation(this,R.anim.dezplazamiento_arriba);

        Animation animacion3 = AnimationUtils.loadAnimation(this,R.anim.desplazamiento_derecha);


        ImageView logoimagen2 = findViewById(R.id.logo2);
        ImageView logoimagen3 = findViewById(R.id.logo3);


        logoimagen2.setAnimation(animacion3);
        logoimagen3.setAnimation(animacion1);

        new Handler() .postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(PantalladeCarga.this, Usuario_Programador.class);
                startActivity(intent);
                finish();
            }
        },4000);
    }
}