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

public class Ram extends AppCompatActivity {
    //Iniciamos variable
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ram);

        Button btnRegresar = (Button)findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VolverInfoBa();
            }
        });

        //asignamos la varible
        textView = findViewById(R.id.etmConcepto);

        //Iciamos el color de los dravable

        ColorDrawable leftBorder = new ColorDrawable(Color.RED);
        ColorDrawable topBorder = new ColorDrawable(Color.GREEN);
        ColorDrawable righBorder = new ColorDrawable(Color.BLUE);
        ColorDrawable bottonBoder = new ColorDrawable(Color.YELLOW);
        ColorDrawable background = new ColorDrawable(Color.WHITE);

        //Icializamos Dravables Array

        Drawable [] layers = new Drawable[] {
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
    void VolverInfoBa(){
        Intent Retroceder = new Intent(Ram.this, InformacionBasica.class);
        startActivity(Retroceder);
    }
}