package com.example.controlesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText Usuario;
    private EditText Password;
    private Button Ingresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Usuario = (EditText)findViewById(R.id.etUsuario);
        Password = (EditText)findViewById(R.id.etPassword);
        Ingresar = (Button) findViewById(R.id.btnIngresar);

        Ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validacion(Usuario.getText().toString(), Password.getText().toString());
            }
        });

    }

    private  void  Validacion (String NombreUsurio, String PasswordUsuario){

        if ((NombreUsurio == "admin" && PasswordUsuario == "1234")){
            Intent intent = new Intent(Login.this, ControlProgramador.class);
            startActivity(intent);
        }
        else if ((NombreUsurio == "usuario" && PasswordUsuario == "1122")){
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        }

        else {
            Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
        }
    }
}