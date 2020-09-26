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

public class agregarProductos extends AppCompatActivity {

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

        btnProductos = (Button) findViewById(R.id.btnMostrarProductos);
        btnProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarListaProductos();
            }
        });
        guardarDatosProductos();
        mostrarDatosProducto();
        tomarFotoProducto();
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

    void  guardarDatosProductos(){
        btnProductos = (Button)findViewById(R.id.btnGuardarProducto);
        btnProductos.setOnClickListener(new View.OnClickListener() {
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

                if(!codigo.isEmpty() && !descripcion.isEmpty() && !medida.isEmpty() && !precio.isEmpty()){

                    String[] data = {idProducto, codigo, descripcion, medida, precio, urlCompletaImg};

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

                urlCompletaImg = dataProducto[5];
                Bitmap imageBitmap = BitmapFactory.decodeFile(urlCompletaImg);
                imgFotoProducto.setImageBitmap(imageBitmap);
            }

        }catch (Exception ex){

        }
    }
}