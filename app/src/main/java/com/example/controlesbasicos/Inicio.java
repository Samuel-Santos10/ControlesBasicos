package com.example.controlesbasicos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Inicio extends AppCompatActivity implements View.OnClickListener {

    Button btnEditar, btnEliminar, btnMostrar, btnSalir, btnModificar;
    TextView nombre;
    int id = 0;
    Usuario u;
    daoUsuario dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        nombre = (TextView)findViewById(R.id.tvNombreUsuario);
        btnEditar = (Button)findViewById(R.id.btnEditar);
        btnEliminar = (Button)findViewById(R.id.btnEliminar);
        btnMostrar = (Button)findViewById(R.id.btnMostrar);
        btnSalir = (Button)findViewById(R.id.btnSalir);
        btnModificar = (Button)findViewById(R.id.btnModificarPartes);

        btnEditar.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);
        btnMostrar.setOnClickListener(this);
        btnSalir.setOnClickListener(this);
        btnModificar.setOnClickListener(this);

        Bundle b = getIntent().getExtras();
        id = b.getInt("Id");
        dao = new daoUsuario(this);
        u = dao.getUsuarioById(id);
        nombre.setText(u.getNombre() + " " + u.getApellido());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEditar:
                Intent a = new Intent(Inicio.this, Editar.class);
                a.putExtra("Id", id);
                startActivity(a);
                break;
            case R.id.btnEliminar:
                // Dialogo para eliminar usuarios
                AlertDialog.Builder b = new AlertDialog.Builder(this);
                b.setMessage("Estas seguro que deseas eliminar tu cuenta");
                b.setCancelable(false);
                b.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (dao.deleteUsuario(id)){
                            Toast.makeText(Inicio.this, "¡Eliminacion exitosa!", Toast.LENGTH_SHORT).show();
                            Intent a = new Intent(Inicio.this, Main.class);
                            startActivity(a);
                            finish();
                        }else {
                            Toast.makeText(Inicio.this, "¡ERROR: No se elimino tu cuenta!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                b.show();
                break;
            case  R.id.btnMostrar:
                Intent c = new Intent(Inicio.this, Mostrar.class);
                startActivity(c);
                break;
            case  R.id.btnSalir:
                Intent i2 = new Intent(Inicio.this, Main.class);
                startActivity(i2);
                finish();
                break;

            case  R.id.btnModificarPartes:
                Intent i5 = new Intent(Inicio.this, agregarProducto_sqlite.class);
                startActivity(i5);
                break;
        }
    }

}