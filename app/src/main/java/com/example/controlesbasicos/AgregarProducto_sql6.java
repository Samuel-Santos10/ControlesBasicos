package com.example.controlesbasicos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AgregarProducto_sql6 extends AppCompatActivity {
    DB_PC miDB;
    String accion = "Nuevo";
    String idGpu = "0";
    ImageView imgFotoProducto;
    //Galeria
    ImageView imgGaleriaProducto;

    String urlCompletaImg;
    Button btnProductos;
    Intent takePictureIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto_sql6);

        imgFotoProducto = (ImageView)findViewById(R.id.imgPhotoProducto);
        //galeria
        imgGaleriaProducto = (ImageView)findViewById(R.id.imgGaleriaProducto);

        btnProductos = (Button) findViewById(R.id.btnMostrarProductos2);
        btnProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarListaProductos();
            }
        });
        guardarDatosProductos();
        mostrarDatosProducto();
        tomarFotoProducto();
        TomarGaleriaProducto();
    }

    //Galeria
    void TomarGaleriaProducto(){
        imgGaleriaProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGaleri = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentGaleri,100);
            }
        });
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
                            Uri photoURI = FileProvider.getUriForFile(AgregarProducto_sql6.this, "com.example.ControlesBasicos.fileprovider", photoFile);
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
            //Galeria
            else if(requestCode==100 && resultCode==RESULT_OK){
                Bitmap imageBitmap = BitmapFactory.decodeFile(urlCompletaImg);
                imgGaleriaProducto.setImageURI(data.getData());
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

    void  guardarDatosProductos(){
        btnProductos = (Button)findViewById(R.id.btnGuardarProducto2);
        btnProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tempval = (TextView)findViewById(R.id.etNombre);
                String nombre = tempval.getText().toString();

                tempval = (TextView)findViewById(R.id.etMarca);
                String marca = tempval.getText().toString();

                tempval = (TextView)findViewById(R.id.etStock);
                String stock = tempval.getText().toString();

                tempval = (TextView)findViewById(R.id.etPrecio);
                String precio = tempval.getText().toString();

                if(!nombre.isEmpty() && !marca.isEmpty() && !stock.isEmpty() && !precio.isEmpty()){

                    String[] data = {idGpu, nombre, marca, stock, precio, urlCompletaImg};

                    miDB = new DB_PC(getApplicationContext(), "", null, 1);
                    miDB.mantenimientoGpu(accion, data);

                    Toast.makeText(getApplicationContext(),"Se ha insertado un producto con exito", Toast.LENGTH_SHORT).show();
                    mostrarListaProductos();
                }else if(!nombre.isEmpty() && !marca.isEmpty() && !stock.isEmpty() && !precio.isEmpty()){

                    String[] data = {idGpu, nombre, marca, stock, precio, urlCompletaImg};

                    miDB = new DB_PC(getApplicationContext(), "", null, 1);
                    miDB.mantenimientoGpu(accion, data);

                    Toast.makeText(getApplicationContext(),"Se ha insertado un producto con exito", Toast.LENGTH_SHORT).show();
                    mostrarListaProductos();
                }
                else {
                    Toast.makeText(getApplicationContext(), "ERROR: Ingrese los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnProductos = (Button)findViewById(R.id.btnMostrarProductos2);
        btnProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarListaProductos();
            }
        });
        mostrarDatosProducto();
    }

    void mostrarListaProductos(){
        Intent mostrarProductos = new Intent( AgregarProducto_sql6.this, AdmiGPU.class);
        startActivity(mostrarProductos);
    }

    void mostrarDatosProducto(){
        try {
            Bundle recibirParametros = getIntent().getExtras();
            accion = recibirParametros.getString("accion");

            if(accion.equals("Modificar")){
                String[] dataGpu= recibirParametros.getStringArray("dataGpu");

                idGpu= dataGpu[0];

                TextView tempval = (TextView)findViewById(R.id.etNombre);
                tempval.setText(dataGpu[1]);

                tempval = (TextView)findViewById(R.id.etMarca);
                tempval.setText(dataGpu[2]);

                tempval = (TextView)findViewById(R.id.etStock);
                tempval.setText(dataGpu[3]);

                tempval = (TextView)findViewById(R.id.etPrecio);
                tempval.setText(dataGpu[4]);

                urlCompletaImg = dataGpu[5];
                Bitmap imageBitmap = BitmapFactory.decodeFile(urlCompletaImg);
                imgFotoProducto.setImageBitmap(imageBitmap);
                imgGaleriaProducto.setImageBitmap(imageBitmap);
            }

        }catch (Exception ex){

        }
    }

}