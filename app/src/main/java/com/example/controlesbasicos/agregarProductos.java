package com.example.controlesbasicos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class agregarProductos extends AppCompatActivity {

    //web

    String resp, Accion, id, rev;

    //

    DB miDB;
    String accion = "Nuevo";
    String idProducto = "0";
    ImageView imgFotoProducto;

    String urlCompletaImg;
    Button btnProductos;
    Intent takePictureIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_productos);

        imgFotoProducto = (ImageView)findViewById(R.id.imgPhotoProducto);

        //web

        try {
            FloatingActionButton btnMostrarProductos = (FloatingActionButton)findViewById(R.id.btnMostrarProductos);
            btnMostrarProductos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MostrarDatosProductos();
                    guardarDatosProductos();
                    mostrarDatosProducto();
                    tomarFotoProducto();
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
            mostrarDatosProducto();
            MostrarDatosProductos();

        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Error al agregar Productos: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        //

    }


    void  tomarFotoProducto(){
        imgFotoProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    //guardando la imagen
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    }catch (Exception ex){}
                    if (photoFile != null) {
                        try {
                            Uri photoURI = FileProvider.getUriForFile(agregarProductos.this, "com.example.ControlesBasicos.fileprovider", photoFile);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            startActivityForResult(takePictureIntent, 1);
                        }catch (Exception ex){
                            Toast.makeText(getApplicationContext(), "Error En La Toma De Foto: "+ ex.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1 && resultCode == RESULT_OK) {
                Bitmap imageBitmap = BitmapFactory.decodeFile(urlCompletaImg);
                imgFotoProducto.setImageBitmap(imageBitmap);
            }
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Error: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private File createImageFile() throws IOException {
        // Aqui se crea un nombre de archivo de imagen
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "imagen_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if( storageDir.exists()==false ){
            storageDir.mkdirs();
        }
        File image = File.createTempFile(
                imageFileName,  /* prefijo */
                ".jpg",         /* sufijo */
                storageDir      /* directorio */

        );
        // Guarda un archivo, ruta para usar con las intenciones VISTA DE ACCION
        urlCompletaImg = image.getAbsolutePath();
        return image;
    }

    //web

    private void guardarProducto() {
        TextView temval = findViewById(R.id.txtNombreProd);
        String nombre = temval.getText().toString();

        temval = findViewById(R.id.txtMarcaProd);
        String marca = temval.getText().toString();

        temval = findViewById(R.id.txtStockProd);
        String stock = temval.getText().toString();

        temval = findViewById(R.id.txtPrecioProd);
        String precio = temval.getText().toString();

        try {
            JSONObject datosProducto = new JSONObject();
            if (accion.equals("Modificar")) {
                datosProducto.put("_id", id);
                datosProducto.put("_rev", rev);
            }
            datosProducto.put("nombre", nombre);
            datosProducto.put("marca", marca);
            datosProducto.put("stock", stock);
            datosProducto.put("precio", precio);

            enviarDatosProductos objGuardarProducto = new enviarDatosProductos();
            objGuardarProducto.execute(datosProducto.toString());

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Error de codigo" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //

    //web

    private class enviarDatosProductos extends AsyncTask<String, String, String> {
        HttpURLConnection urlConnection;
        @Override
        protected String doInBackground(String... parametros) {
            StringBuilder stringBuilder = new StringBuilder();
            String jsonResponse = null;
            String jsonDatos = parametros[0];
            BufferedReader reader;

            try {
                URL url = new URL("Http://10.0.2.2:5984/db_tienda/");
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
                    mostrarListaProductos();
                } else {
                    Toast.makeText(getApplicationContext(), "Error al intentar guardar datos de producto", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error al guardar producto: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    //

    void  guardarDatosProductos(){
        btnProductos = (Button)findViewById(R.id.btnGuardarProducto);
        btnProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tempval = (TextView)findViewById(R.id.txtNombreProd);
                String nombre = tempval.getText().toString();

                tempval = (TextView)findViewById(R.id.txtMarcaProd);
                String marca = tempval.getText().toString();

                tempval = (TextView)findViewById(R.id.txtStockProd);
                String stock = tempval.getText().toString();

                tempval = (TextView)findViewById(R.id.txtPrecioProd);
                String precio = tempval.getText().toString();

                if(!nombre.isEmpty() && !marca.isEmpty() && !stock.isEmpty() && !precio.isEmpty()){

                    String[] data = {idProducto, nombre, marca, stock, precio, urlCompletaImg};

                    miDB = new DB(getApplicationContext(), "", null, 1);
                    miDB.mantenimientoProductos(accion, data);

                    Toast.makeText(getApplicationContext(),"Se ha insertado un producto con exito", Toast.LENGTH_SHORT).show();
                    mostrarListaProductos();
                }
                else {
                    Toast.makeText(getApplicationContext(), "ERROR: Ingrese los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnProductos = (Button)findViewById(R.id.btnMostrarProductos);
        btnProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarListaProductos();
            }
        });
        mostrarDatosProducto();
    }

    private void mostrarListaProductos(){
        Intent mostrarProductos = new Intent( agregarProductos.this, MainActivity.class);
        startActivity(mostrarProductos);
    }

    //web

    void MostrarDatosProductos() {
        try {
            Bundle recibirParametros = getIntent().getExtras();
            accion = recibirParametros.getString("accion");
            if (accion.equals("Modificar")) {
                JSONObject DataProducto = new JSONObject(recibirParametros.getString("DataProducto")).getJSONObject("value");

                TextView tempVal = (TextView) findViewById(R.id.txtNombreProd);
                tempVal.setText(DataProducto.getString("nombre"));

                tempVal = findViewById(R.id.txtMarcaProd);
                tempVal.setText(DataProducto.getString("marca"));

                tempVal =  findViewById(R.id.txtStockProd);
                tempVal.setText(DataProducto.getString("stock"));

                tempVal =  findViewById(R.id.txtPrecioProd);
                tempVal.setText(DataProducto.getString("precio"));

                id = DataProducto.getString("_id");
                rev = DataProducto.getString("_rev");
            }
        } catch (Exception ex) {
            //
        }
    }

    //

    void mostrarDatosProducto(){
        try {
            Bundle recibirParametros = getIntent().getExtras();
            accion = recibirParametros.getString("accion");

            if(accion.equals("Modificar")){
                String[] dataProducto= recibirParametros.getStringArray("dataProducto");

                idProducto= dataProducto[0];

                TextView tempval = (TextView)findViewById(R.id.txtNombreProd);
                tempval.setText(dataProducto[1]);

                tempval = (TextView)findViewById(R.id.txtMarcaProd);
                tempval.setText(dataProducto[2]);

                tempval = (TextView)findViewById(R.id.txtStockProd);
                tempval.setText(dataProducto[3]);

                tempval = (TextView)findViewById(R.id.txtPrecioProd);
                tempval.setText(dataProducto[4]);

                urlCompletaImg = dataProducto[5];
                Bitmap imageBitmap = BitmapFactory.decodeFile(urlCompletaImg);
                imgFotoProducto.setImageBitmap(imageBitmap);
            }

        }catch (Exception ex){

        }
    }
}