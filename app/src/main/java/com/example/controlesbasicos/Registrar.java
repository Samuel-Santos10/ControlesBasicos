package com.example.controlesbasicos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Registrar extends AppCompatActivity implements View.OnClickListener {

    EditText us, pas, nom, ap;
    Button reg, can;
    daoUsuario dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar);

        us = (EditText)findViewById(R.id.etRegUser);
        pas = (EditText)findViewById(R.id.etRegPass);
        nom = (EditText)findViewById(R.id.etRegNombre);
        ap = (EditText)findViewById(R.id.etRegApellido);

        reg = (Button)findViewById(R.id.btnRegRegistrar);
        can = (Button)findViewById(R.id.btnRegCancelar);

        reg.setOnClickListener(this);
        can.setOnClickListener(this);

        dao = new daoUsuario(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegRegistrar:
                Usuario u = new Usuario();
                u.setUsurio(us.getText().toString());
                u.setPassword(pas.getText().toString());
                u.setNombre(nom.getText().toString());
                u.setApellido(ap.getText().toString());
                if(!u.isNull()){
                    Toast.makeText(getApplicationContext(), "¡ERROR!: Uno o mas campos estan vacios!", Toast.LENGTH_SHORT).show();
                }else if(dao.insertUsuario(u)){
                    Toast.makeText(getApplicationContext(), "Registro exitiso!", Toast.LENGTH_SHORT).show();
                    Intent i2 = new Intent(Registrar.this, Main.class);
                    startActivity(i2);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Usuario ya registrado!", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btnRegCancelar:
                Intent i = new Intent(Registrar.this, Main.class);
                startActivity(i);
                finish();
                break;
        }
    }
}