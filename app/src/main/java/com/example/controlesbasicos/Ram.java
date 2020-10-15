package com.example.controlesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Ram extends AppCompatActivity {

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
    }
    void VolverInfoBa(){
        Intent Retroceder = new Intent(Ram.this, InformacionBasica.class);
        startActivity(Retroceder);
    }
}