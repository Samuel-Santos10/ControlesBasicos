package com.example.controlesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CPU extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_p_u);

        Button btnRegresar = (Button)findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VolverInfoBa();
            }
        });
    }
    void VolverInfoBa(){
        Intent Retroceder = new Intent(CPU.this, InformacionBasica.class);
        startActivity(Retroceder);
    }
}