package com.example.controlesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class agregarProductos extends AppCompatActivity {

    DB miDB;
    String accion = "Nuevo";
    String idProducto = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_productos);

        Button btnproductos = (Button)findViewById(R.id.btnGuardarProducto);
        btnproductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tempval = (TextView)findViewById(R.id.txtCodigoProd);
                String codigo = tempval.getText().toString();

                tempval = (TextView)findViewById(R.id.txtDescripcionProd);
                String descripcion = tempval.getText().toString();

                tempval = (TextView)findViewById(R.id.txtMedidaProd);
                String medida = tempval.getText().toString();

                tempval = (TextView)findViewById(R.id.txtPrecioProd);
                String precio = tempval.getText().toString();

                String[] data = {idProducto, codigo, descripcion, medida, precio};

                miDB = new DB(getApplicationContext(), "", null, 1);
                miDB.mantenimientoProductos(accion, data);

                Toast.makeText(getApplicationContext(),"Se ha insertado un producto con exito", Toast.LENGTH_SHORT).show();
                mostrarListaProductos();
            }
        });
        btnproductos = (Button)findViewById(R.id.btnMostrarProductos);
        btnproductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarListaProductos();
            }
        });
        mostrarDatosProducto();
    }

    void mostrarListaProductos(){
        Intent mostrarProductos = new Intent( agregarProductos.this, MainActivity.class);
        startActivity(mostrarProductos);
    }

    void mostrarDatosProducto(){
        try {
            Bundle recibirParametros = getIntent().getExtras();
            accion = recibirParametros.getString("accion");

            if(accion.equals("Modificar")){
                String[] dataProducto= recibirParametros.getStringArray("dataProducto");

                idProducto= dataProducto[0];

                TextView tempval = (TextView)findViewById(R.id.txtCodigoProd);
                tempval.setText(dataProducto[1]);

                tempval = (TextView)findViewById(R.id.txtDescripcionProd);
                tempval.setText(dataProducto[2]);

                tempval = (TextView)findViewById(R.id.txtMedidaProd);
                tempval.setText(dataProducto[3]);

                tempval = (TextView)findViewById(R.id.txtPrecioProd);
                tempval.setText(dataProducto[4]);
            }

        }catch (Exception ex){

        }
    }
}