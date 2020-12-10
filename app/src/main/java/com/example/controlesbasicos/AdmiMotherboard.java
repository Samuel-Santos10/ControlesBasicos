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

public class AdmiMotherboard extends AppCompatActivity {
    DB_PC miDB;
    Cursor misMotherboard;
    motherboards motherboard;
    ArrayList<motherboards> stringArrayList = new ArrayList<motherboards>();
    ArrayList<motherboards> copyStringArrayList = new ArrayList<motherboards>();
    ListView lvMother;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admi_motherboard);

        FloatingActionButton btnAgregarProductos = (FloatingActionButton)findViewById(R.id.btnAgregarProductos2);
        btnAgregarProductos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                agregarMotherboard("Nuevo", new String[]{});
            }
        });
        obtenerDatosMotherboard();
        buscarProductos();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_principal, menu);

        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)menuInfo;
        misMotherboard.moveToPosition((adapterContextMenuInfo.position));
        menu.setHeaderTitle(misMotherboard.getString(1));
    }

    void buscarProductos(){
        final TextView tempVal = (TextView)findViewById(R.id.etBuscarProductoM);
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
                        for (motherboards am : copyStringArrayList) {
                            String nombre = am.getNombre();
                            if (nombre.toLowerCase().contains(tempVal.getText().toString().trim().toLowerCase())) {
                                stringArrayList.add(am);
                            }
                        }
                    }
                    adaptadorImagenes1 adaptadorImg = new adaptadorImagenes1(getApplicationContext(), stringArrayList);
                    lvMother.setAdapter(adaptadorImg);
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
                agregarMotherboard("Nuevo", new String[]{});
                return true;

            case R.id.mnxModificar:
                String[] dataMotherboard = {
                        misMotherboard.getString(0), // Para el id producto
                        misMotherboard.getString(1), // Para el nombre
                        misMotherboard.getString(2), // para el marca
                        misMotherboard.getString(3), // Para la stock
                        misMotherboard.getString(4), // y este para el precio.
                        misMotherboard.getString(5) // Para la URL
                };
                agregarMotherboard("Modificar", dataMotherboard);
                return true;

            case R.id.mnxEliminar:
                AlertDialog eliminarMother =  eliminarProductos();
                eliminarMother.show();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    AlertDialog eliminarProductos(){
        AlertDialog.Builder confirmacion = new AlertDialog.Builder(AdmiMotherboard.this);
        confirmacion.setTitle(misMotherboard.getString(1));
        confirmacion.setMessage("Esta seguro de eliminar el Producto?");
        confirmacion.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                miDB.mantenimientoMotherboard("Eliminar",new String[]{misMotherboard.getString(0)});
                obtenerDatosMotherboard();
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

    void obtenerDatosMotherboard(){
        miDB = new DB_PC(getApplicationContext(), "", null, 1);
        misMotherboard = miDB.mantenimientoMotherboard( "Consultar", null);

        if(misMotherboard.moveToFirst()){ // si hay registros en la base de datos que mostrar
            mostrarDatosMotherboard();
        }else{
            Toast.makeText(getApplicationContext(),"No hay Productos que mostrar", Toast.LENGTH_SHORT).show();

            agregarMotherboard("Nuevo", new String[]{});
        }
    }

    void agregarMotherboard(String accion, String[] dataMotherboard){
        Bundle enviarParametros = new Bundle();
        enviarParametros.putString("accion", accion);
        enviarParametros.putStringArray("dataMotherboard", dataMotherboard);
        Intent agregarMother = new Intent(AdmiMotherboard.this, AgregarProductos_Mother.class);
        agregarMother.putExtras(enviarParametros);
        startActivity(agregarMother);
    }

    void mostrarDatosMotherboard() {
        stringArrayList.clear();
        lvMother = (ListView)findViewById(R.id.lvMother);
        do {
            motherboard = new motherboards(misMotherboard.getString(0), misMotherboard.getString(1),misMotherboard.getString(2), misMotherboard.getString(3), misMotherboard.getString(4), misMotherboard.getString(5));
            stringArrayList.add(motherboard);
        } while (misMotherboard.moveToNext());

        adaptadorImagenes1 adaptadorImg = new adaptadorImagenes1(getApplicationContext(), stringArrayList);
        lvMother.setAdapter(adaptadorImg);

        copyStringArrayList.clear(); // Para hacer una limpieza de la lista
        copyStringArrayList.addAll(stringArrayList); //Para crear una copia de la lista de productos

        registerForContextMenu(lvMother);
    }
}

class motherboards{
    String id;
    String nombre;
    String marca;
    String stock;
    String precio;
    String urlImg;

    public motherboards(String id, String nombre, String marca, String stock, String precio, String urlImg) {
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

