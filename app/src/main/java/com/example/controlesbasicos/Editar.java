package com.example.controlesbasicos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Editar extends AppCompatActivity implements View.OnClickListener {

    EditText EdiUser, EdiPass, EdiNombre, EdiApellido;
    Button btnActualizar, btnCancelar;
    int id = 0;
    Usuario u;
    daoUsuario dao;
    Intent x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar);

        EdiUser = (EditText)findViewById(R.id.etEdiUser);
        EdiPass = (EditText)findViewById(R.id.etEdiPass);
        EdiNombre = (EditText)findViewById(R.id.etEdiNombre);
        EdiApellido = (EditText)findViewById(R.id.etEdiApellido);

        btnActualizar = (Button)findViewById(R.id.btnEdiActualizar);
        btnCancelar = (Button)findViewById(R.id.btnEdiCancelar);

        btnActualizar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);


        Bundle b = getIntent().getExtras();
        id = b.getInt("Id");
        dao = new daoUsuario(this);
        u = dao.getUsuarioById(id);
        EdiUser.setText(u.getUsurio());
        EdiPass.setText(u.getPassword());
        EdiNombre.setText(u.getNombre());
        EdiApellido.setText(u.getApellido());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEdiActualizar:
                u.setUsurio(EdiUser.getText().toString());
                u.setPassword(EdiPass.getText().toString());
                u.setNombre(EdiNombre.getText().toString());
                u.setApellido(EdiApellido.getText().toString());
                if(!u.isNull()){
                    Toast.makeText(this, "¡ERROR!: ¡¡Uno o mas campos estan vacios!!", Toast.LENGTH_SHORT).show();
                }else if(dao.updateUsuario(u)){
                    Toast.makeText(this, "Actualizacion exitosa!", Toast.LENGTH_SHORT).show();
                    Intent i2 = new Intent(Editar.this, Inicio.class);
                    i2.putExtra("Id", u.getId());
                    startActivity(i2);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "No se puede actualizar!", Toast.LENGTH_SHORT).show();
                }
            break;
            case R.id.btnEdiCancelar:
                Intent i2 = new Intent(Editar.this, Inicio.class);
                i2.putExtra("Id", u.getId());
                startActivity(i2);
                finish();
                break;
        }
    }
}