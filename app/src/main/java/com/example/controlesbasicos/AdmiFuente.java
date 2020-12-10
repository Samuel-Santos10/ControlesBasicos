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

public class AdmiFuente extends AppCompatActivity {

    DB_PC miDB;
    Cursor misFuente;
    fuentes fuente;
    ArrayList<fuentes> stringArrayList = new ArrayList<fuentes>();
    ArrayList<fuentes> copyStringArrayList = new ArrayList<fuentes>();
    ListView lvsFuente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admi_fuente);

        FloatingActionButton btnAgregarProductos = (FloatingActionButton)findViewById(R.id.btnAgregarProductos);
        btnAgregarProductos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                agregarFuente("Nuevo", new String[]{});
            }
        });
        obtenerDatosFuente();
        buscarProductos();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_principal, menu);

        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)menuInfo;
        misFuente.moveToPosition((adapterContextMenuInfo.position));
        menu.setHeaderTitle(misFuente.getString(1));
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
                        for (fuentes am : copyStringArrayList) {
                            String nombre = am.getNombre();
                            if (nombre.toLowerCase().contains(tempVal.getText().toString().trim().toLowerCase())) {
                                stringArrayList.add(am);
                            }
                        }
                    }
                    adaptadorImagen4 adaptadorImg = new adaptadorImagen4(getApplicationContext(), stringArrayList);
                    lvsFuente.setAdapter(adaptadorImg);
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
                agregarFuente("Nuevo", new String[]{});
                return true;

            case R.id.mnxModificar:
                String[] dataFuente = {
                        misFuente.getString(0), // Para el id producto
                        misFuente.getString(1), // Para el nombre
                        misFuente.getString(2), // para el marca
                        misFuente.getString(3), // Para la stock
                        misFuente.getString(4), // y este para el precio.
                        misFuente.getString(5) // Para la URL
                };
                agregarFuente("Modificar", dataFuente);
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
        AlertDialog.Builder confirmacion = new AlertDialog.Builder(AdmiFuente.this);
        confirmacion.setTitle(misFuente.getString(1));
        confirmacion.setMessage("Esta seguro de eliminar el Producto?");
        confirmacion.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                miDB.mantenimientoProductos("Eliminar",new String[]{misFuente.getString(0)});
                obtenerDatosFuente();
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

    void obtenerDatosFuente(){
        miDB = new DB_PC(getApplicationContext(), "", null, 1);
        misFuente = miDB.mantenimientoFuente( "Consultar", null);

        if(misFuente.moveToFirst()){ // si hay registros en la base de datos que mostrar
            mostrarDatosFuente();
        }else{
            Toast.makeText(getApplicationContext(),"No hay Productos que mostrar", Toast.LENGTH_SHORT).show();

            agregarFuente("Nuevo", new String[]{});
        }
    }

    void agregarFuente(String accion, String[] dataFuente){
        Bundle enviarParametros = new Bundle();
        enviarParametros.putString("accion", accion);
        enviarParametros.putStringArray("dataFuente", dataFuente);
        Intent agregarProductos = new Intent(AdmiFuente.this, AgregarProducto_Fuente.class);
        agregarProductos.putExtras(enviarParametros);
        startActivity(agregarProductos);
    }

    void mostrarDatosFuente() {
        stringArrayList.clear();
        lvsFuente = (ListView)findViewById(R.id.lvsFuente);
        do {
            fuente = new fuentes(misFuente.getString(0), misFuente.getString(1),misFuente.getString(2), misFuente.getString(3), misFuente.getString(4), misFuente.getString(5));
            stringArrayList.add(fuente);
        } while (misFuente.moveToNext());

        adaptadorImagen4 adaptadorImg = new adaptadorImagen4(getApplicationContext(), stringArrayList);
        lvsFuente.setAdapter(adaptadorImg);

        copyStringArrayList.clear(); // Para hacer una limpieza de la lista
        copyStringArrayList.addAll(stringArrayList); //Para crear una copia de la lista de productos

        registerForContextMenu(lvsFuente);
    }
}

class fuentes{
    String id;
    String nombre;
    String marca;
    String stock;
    String precio;
    String urlImg;

    public fuentes(String id, String nombre, String marca, String stock, String precio, String urlImg) {
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