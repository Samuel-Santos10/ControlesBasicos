package com.example.controlesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Novedades extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novedades);

        Button btnAtras = (Button)findViewById(R.id.btnRegresarMenu);
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VolverAlMenu();
            }
        });
    }
    void VolverAlMenu(){
        Intent Retroceder = new Intent(Novedades.this, MainActivity.class);
        startActivity(Retroceder);
    }
}