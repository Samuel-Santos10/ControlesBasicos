package com.example.controlesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

public class Agregar_Productos extends AppCompatActivity {

    String resp, accion, id, rev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar__productos);

        try {
            FloatingActionButton btnMostrarProductos = (FloatingActionButton)findViewById(R.id.btnMostrarProductos);
            btnMostrarProductos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mostrarProductos();
                }
            });
            // Boton Guardar
            Button btnGuardaProducto = findViewById(R.id.btnGuardarProducto);
            btnGuardaProducto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    guardarProducto();
                }
            });
            mostrarDatosProductos();
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Error al agregar Productos: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    void mostrarDatosProductos() {
        try {
            Bundle recibirParametros = getIntent().getExtras();
            accion = recibirParametros.getString("accion");
            if (accion.equals("Modificar")) {
                JSONObject dataProducto = new JSONObject(recibirParametros.getString("dataProducto")).getJSONObject("value");

                TextView tempVal = (TextView) findViewById(R.id.txtcodigo);
                tempVal.setText(dataProducto.getString("codigo"));

                tempVal = findViewById(R.id.txtdescripcion);
                tempVal.setText(dataProducto.getString("descripcion"));

                tempVal =  findViewById(R.id.txtmarca);
                tempVal.setText(dataProducto.getString("marca"));

                tempVal =  findViewById(R.id.txtprecentacion);
                tempVal.setText(dataProducto.getString("precentacion"));

                tempVal =  findViewById(R.id.txtPrecioProd);
                tempVal.setText(dataProducto.getString("precio"));

                id = dataProducto.getString("_id");
                rev = dataProducto.getString("_rev");
            }
        } catch (Exception ex) {
            //
        }
    }
    private void mostrarProductos() {
        Intent mostrarProductos = new Intent(Agregar_Productos.this, MainActivity.class);
        startActivity(mostrarProductos);
    }

    private void guardarProducto() {
        TextView temval = findViewById(R.id.txtcodigo);
        String codigo = temval.getText().toString();

        temval = findViewById(R.id.txtdescripcion);
        String descripcion = temval.getText().toString();

        temval = findViewById(R.id.txtmarca);
        String marca = temval.getText().toString();

        temval = findViewById(R.id.txtprecentacion);
        String precentacion = temval.getText().toString();

        temval = findViewById(R.id.txtPrecioProd);
        String precio = temval.getText().toString();

        try {
            JSONObject datosProducto = new JSONObject();
            if (accion.equals("Modificar")) {
                datosProducto.put("_id", id);
                datosProducto.put("_rev", rev);
            }
            datosProducto.put("codigo", codigo);
            datosProducto.put("descripcion", descripcion);
            datosProducto.put("marca", marca);
            datosProducto.put("precentacion", precentacion);
            datosProducto.put("precio", precio);

            enviarDatosProductos objGuardarProducto = new enviarDatosProductos();
            objGuardarProducto.execute(datosProducto.toString());

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Error de codigo" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private class enviarDatosProductos extends AsyncTask<String, String, String> {
        HttpURLConnection urlConnection;
        @Override
        protected String doInBackground(String... parametros) {
            StringBuilder stringBuilder = new StringBuilder();
            String jsonResponse = null;
            String jsonDatos = parametros[0];
            BufferedReader reader;

            try {
                URL url = new URL("Http://10.0.2.2:5984/db_pc/");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");

                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                writer.write(jsonDatos);
                writer.close();

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                resp = reader.toString();

                String inputLine;
                StringBuffer stringBuffer = new StringBuffer();
                while ((inputLine = reader.readLine()) != null) {
                    stringBuffer.append(inputLine + "\n");
                }
                if (stringBuffer.length() == 0) {
                    return null;
                }
                jsonResponse = stringBuffer.toString();
                return jsonResponse;
            } catch (Exception ex) {
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getBoolean("ok")) {
                    Toast.makeText(getApplicationContext(), "Datos de producto guardado con exito", Toast.LENGTH_SHORT).show();
                    mostrarProductos();
                } else {
                    Toast.makeText(getApplicationContext(), "Error al intentar guardar datos de producto", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error al guardar producto: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}