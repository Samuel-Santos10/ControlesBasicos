package com.example.controlesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class agregarAmigos extends AppCompatActivity {

    DB miDB;
    String accion = "Nuevo";
    String idAmigo = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_amigos);

        Button btnGuardarAmigos = (Button)findViewById(R.id.btnGuardarAmigos);
        btnGuardarAmigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tempval = (TextView)findViewById(R.id.txtNombreAmigo);
                String nombre = tempval.getText().toString();

                tempval = (TextView)findViewById(R.id.txtTelegonoAmigos);
                String telefono = tempval.getText().toString();

                tempval = (TextView)findViewById(R.id.txtDireccionAmigo);
                String direccion = tempval.getText().toString();

                tempval = (TextView)findViewById(R.id.txtEmailAmigo);
                String email = tempval.getText().toString();

                String[] data = {idAmigo, nombre, telefono, direccion, email};

                miDB = new DB(getApplicationContext(), "", null, 1);
                miDB.mantenimientoAmigos(accion, data);

                Toast.makeText(getApplicationContext(),"Se ha insertado un amigo con exito", Toast.LENGTH_SHORT).show();
                Intent mostrarAmigos = new Intent( agregarAmigos.this, MainActivity.class);
                startActivity(mostrarAmigos);
            }
        });
        mostrarDatosAmigo();
    }
    void mostrarDatosAmigo(){
        try {
            Bundle recibirParametros = getIntent().getExtras();
            accion = recibirParametros.getString("accion");

            if(accion.equals("Modificar")){
                String[] dataAmigo= recibirParametros.getStringArray("dataAmigo");

                idAmigo= dataAmigo[0];

                TextView tempval = (TextView)findViewById(R.id.txtNombreAmigo);
                tempval.setText(dataAmigo[1]);

                tempval = (TextView)findViewById(R.id.txtTelegonoAmigos);
                tempval.setText(dataAmigo[2]);

                tempval = (TextView)findViewById(R.id.txtDireccionAmigo);
                tempval.setText(dataAmigo[3]);

                tempval = (TextView)findViewById(R.id.txtEmailAmigo);
                tempval.setText(dataAmigo[4]);
            }

        }catch (Exception ex){

        }
    }
}