package com.example.controlesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Compu extends AppCompatActivity {

    //Iniciamos variable
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compu);

        Button btnAtras = (Button)findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atras();
            }
        });
        //asignamos la varible
        textView = findViewById(R.id.etmConcepto5);

        //Iciamos el color de los dravable

        ColorDrawable leftBorder = new ColorDrawable(Color.RED);
        ColorDrawable topBorder = new ColorDrawable(Color.GREEN);
        ColorDrawable righBorder = new ColorDrawable(Color.BLUE);
        ColorDrawable bottonBoder = new ColorDrawable(Color.YELLOW);
        ColorDrawable background = new ColorDrawable(Color.WHITE);

        //Icializamos Dravables Array

        Drawable[] layers = new Drawable[] {
                leftBorder, //Red color
                topBorder, // Green Color
                righBorder, //Blue Color
                bottonBoder, //Yellow Color
                background, // White Color
        };
        //inicializamos LayerDravable
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        //Drav left Border
        layerDrawable.setLayerInset(0,0,0,15,0);

        layerDrawable.setLayerInset(1,15,0,0,15);

        layerDrawable.setLayerInset(2,15,15,0,0);

        layerDrawable.setLayerInset(3,15,15,15,0);

        layerDrawable.setLayerInset(4,15,15,15,15);

        //set Background
        textView.setBackground(layerDrawable);
    }
    void atras(){
        Intent volver = new Intent(Compu.this, InformacionBasica.class);
        startActivity(volver);
    }
}