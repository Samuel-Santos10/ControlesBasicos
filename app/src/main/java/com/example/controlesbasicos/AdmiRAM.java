package com.example.controlesbasicos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AdmiRAM extends AppCompatActivity {

    DB_PC miDB;
    Cursor misRam;
    ram rams;
    ArrayList<ram> stringArrayList = new ArrayList<ram>();
    ArrayList<ram> copyStringArrayList = new ArrayList<ram>();
    ListView lvsRam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admi_r_a_m);

        FloatingActionButton btnAgregarProductos = (FloatingActionButton)findViewById(R.id.btnAgregarProductos);
        btnAgregarProductos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                agregarRam("Nuevo", new String[]{});
            }
        });
        obtenerDatosRam();
        buscarProductos();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_principal, menu);

        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)menuInfo;
        misRam.moveToPosition((adapterContextMenuInfo.position));
        menu.setHeaderTitle(misRam.getString(1));
    }

    void buscarProductos(){
        final TextView tempVal = (TextView)findViewById(R.id.etBuscarProducto);
        tempVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    stringArrayList.clear();
                    if (tempVal.getText().toString().trim().length() < 1) { //aqui no hay texto para mostrar
                        stringArrayList.addAll(copyStringArrayList);
                    } else { // Aqui hacemos la busqueda
                        for (ram am : copyStringArrayList) {
                            String nombre = am.getNombre();
                            if (nombre.toLowerCase().contains(tempVal.getText().toString().trim().toLowerCase())) {
                                stringArrayList.add(am);
                            }
                        }
                    }
                    adaptadorImagen3 adaptadorImg = new adaptadorImagen3(getApplicationContext(), stringArrayList);
                    lvsRam.setAdapter(adaptadorImg);
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), "Error: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnxAgregar:
                agregarRam("Nuevo", new String[]{});
                return true;

            case R.id.mnxModificar:
                String[] dataRam = {
                        misRam.getString(0), // Para el id producto
                        misRam.getString(1), // Para el nombre
                        misRam.getString(2), // para el marca
                        misRam.getString(3), // Para la stock
                        misRam.getString(4), // y este para el precio.
                        misRam.getString(5) // Para la URL
                };
                agregarRam("Modificar", dataRam);
                return true;

            case R.id.mnxEliminar:
                AlertDialog eliminarProduct =  eliminarProductos();
                eliminarProduct.show();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    AlertDialog eliminarProductos(){
        AlertDialog.Builder confirmacion = new AlertDialog.Builder(AdmiRAM.this);
        confirmacion.setTitle(misRam.getString(1));
        confirmacion.setMessage("Esta seguro de eliminar el Producto?");
        confirmacion.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                miDB.mantenimientoRam("Eliminar",new String[]{misRam.getString(0)});
                obtenerDatosRam();
                Toast.makeText(getApplicationContext(), "Producto eliminado exitosamente.",Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });
        confirmacion.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Sea eliminado el producto.",Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });
        return confirmacion.create();
    }

    void obtenerDatosRam(){
        miDB = new DB_PC(getApplicationContext(), "", null, 1);
        misRam = miDB.mantenimientoRam( "Consultar", null);

        if(misRam.moveToFirst()){ // si hay registros en la base de datos que mostrar
            mostrarDatosRam();
        }else{
            Toast.makeText(getApplicationContext(),"No hay Productos que mostrar", Toast.LENGTH_SHORT).show();

            agregarRam("Nuevo", new String[]{});
        }
    }

    void agregarRam(String accion, String[] dataRam){
        Bundle enviarParametros = new Bundle();
        enviarParametros.putString("accion", accion);
        enviarParametros.putStringArray("dataRam", dataRam);
        Intent agregarRam = new Intent(AdmiRAM.this, AgregarProducto_Ram.class);
        agregarRam.putExtras(enviarParametros);
        startActivity(agregarRam);
    }

    void mostrarDatosRam() {
        stringArrayList.clear();
        lvsRam = (ListView)findViewById(R.id.lvsRam);
        do {
            rams = new ram(misRam.getString(0), misRam.getString(1),misRam.getString(2), misRam.getString(3), misRam.getString(4), misRam.getString(5));
            stringArrayList.add(rams);
        } while (misRam.moveToNext());

        adaptadorImagen3 adaptadorImg = new adaptadorImagen3(getApplicationContext(), stringArrayList);
        lvsRam.setAdapter(adaptadorImg);

        copyStringArrayList.clear(); // Para hacer una limpieza de la lista
        copyStringArrayList.addAll(stringArrayList); //Para crear una copia de la lista de productos

        registerForContextMenu(lvsRam);
    }
}

class ram{
    String id;

    String nombre;
    String marca;
    String stock;
    String precio;
    String urlImg;

    public ram(String id, String nombre, String marca, String stock, String precio, String urlImg) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.stock = stock;
        this.precio = precio;
        this.urlImg = urlImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
    }
