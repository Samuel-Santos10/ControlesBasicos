package com.example.controlesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ModificarComponentes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_componentes);
        try {
            Button btnAddCPU = (Button) findViewById(R.id.btnAddCPU);
            btnAddCPU.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddCpu();
                }
            });

            Button btnAddMotherboard = (Button) findViewById(R.id.btnAddMother);
            btnAddMotherboard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddMother();


                }
            });

            Button btnAddGPU = (Button) findViewById(R.id.btnAddGPU);
            btnAddGPU.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddGpu();
                }
            });

            Button btnAddRAM = (Button) findViewById(R.id.btnAddRAM);
            btnAddRAM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddRam();
                }
            });

            Button btnAddDISCO = (Button) findViewById(R.id.btnAddDisco);
            btnAddDISCO.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddDisco();
                }
            });

            Button btnAddFuente = (Button) findViewById(R.id.btnAddFuente);
            btnAddFuente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddFuente();
                }
            });

            Button btnAtras = (Button)findViewById(R.id.btnRegresarMenu);
            btnAtras.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    VolverAlMenu();
                }
            });

        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Error al ingresar en la opcion.", Toast.LENGTH_SHORT).show();
        }

    }
    void AddCpu(){
        Intent Cpu = new Intent(ModificarComponentes.this, agregarProducto_sqlite.class);
        startActivity(Cpu);
    }

    void AddMother(){
        Intent Motherboard = new Intent(ModificarComponentes.this, AgregarProductos_Mother.class);
        startActivity(Motherboard);
    }
    void AddGpu(){
        Intent Gpu = new Intent(ModificarComponentes.this, AgregarProducto_Gpu.class);
        startActivity(Gpu);
    }

    void AddRam(){
        Intent Ram = new Intent(ModificarComponentes.this, AgregarProducto_Ram.class);
        startActivity(Ram);
    }

    void AddDisco(){
        Intent Disco = new Intent(ModificarComponentes.this, AgregarProductos_Disco.class);
        startActivity(Disco);
    }

    void AddFuente(){
        Intent Fuente = new Intent(ModificarComponentes.this, AgregarProducto_Fuente.class);
        startActivity(Fuente);
    }

    void VolverAlMenu(){
        Intent Retroceder = new Intent(ModificarComponentes.this, ModificarComponentes.class);
        startActivity(Retroceder);
    }
}