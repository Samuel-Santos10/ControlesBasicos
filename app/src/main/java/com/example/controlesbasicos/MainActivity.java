package com.example.controlesbasicos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DB miDB;
    Cursor misProductos;
    ArrayList<String> stringArrayList = new ArrayList<String>();
    ArrayList<String> copyStringArrayList = new ArrayList<String>();
    ArrayAdapter<String> stringArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton btnAgregarProductos = (FloatingActionButton)findViewById(R.id.btnAgregarProductos);
        btnAgregarProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarProducto("Nuevo", new String[]{});
            }
        });
        obtenerDatosProductos();
        buscarProductos();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_productos, menu);

        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)menuInfo;
        misProductos.moveToPosition((adapterContextMenuInfo.position));
        menu.setHeaderTitle(misProductos.getString(1));
    }

    void buscarProductos(){
        final TextView tempVal = (TextView)findViewById(R.id.etBuscarProducto);
        tempVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                stringArrayList.clear();
                if( tempVal.getText().toString().trim().length()<1 ){ //en este nos indica que no hay texto para mostrar
                    stringArrayList.addAll(copyStringArrayList);
                } else{ // aqui realizamos la busqueda
                    for (String amigo : copyStringArrayList){
                        if(amigo.toLowerCase().contains(tempVal.getText().toString().trim().toLowerCase())){
                            stringArrayList.add(amigo);
                        }
                    }
                }
                stringArrayAdapter.notifyDataSetChanged();
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
                agregarProducto("Nuevo", new String[]{});
                return true;

            case R.id.mnxModificar:
                String[] dataProducto = {
                        misProductos.getString(0), // Para el id producto
                        misProductos.getString(1), // Para el codigo
                        misProductos.getString(2), // para el descripcion
                        misProductos.getString(3), // Para la medida
                        misProductos.getString(4), // y este para el precio.
                };
                agregarProducto("Modificar", dataProducto);
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
        AlertDialog.Builder confirmacion = new AlertDialog.Builder(MainActivity.this);
        confirmacion.setTitle(misProductos.getString(1));
        confirmacion.setMessage("Esta seguro de eliminar el Producto?");
        confirmacion.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                miDB.mantenimientoProductos("Eliminar",new String[]{misProductos.getString(0)});
                obtenerDatosProductos();
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

    void obtenerDatosProductos(){
        miDB = new DB(getApplicationContext(), "", null, 1);
        misProductos = miDB.mantenimientoProductos( "Consultar", null);

        if(misProductos.moveToFirst()){ // si hay registros en la base de datos que mostrar
            mostrarDatosProductos();
        }else{
            Toast.makeText(getApplicationContext(),"No hay Productos que mostrar", Toast.LENGTH_SHORT).show();

            agregarProducto("Nuevo", new String[]{});
        }
    }

    void agregarProducto(String accion, String[] dataProducto){
        Bundle enviarParametros = new Bundle();
        enviarParametros.putString("accion", accion);
        enviarParametros.putStringArray("dataProducto", dataProducto);
        Intent agregarProductos = new Intent(MainActivity.this, agregarProductos.class);
        agregarProductos.putExtras(enviarParametros);
        startActivity(agregarProductos);
    }

    void mostrarDatosProductos() {
        stringArrayList.clear();
        ListView lvsProductos = (ListView) findViewById(R.id.lvsProductos);
        stringArrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, stringArrayList);
        lvsProductos.setAdapter(stringArrayAdapter);
        do {
            stringArrayAdapter.add(misProductos.getString(1));
        } while (misProductos.moveToNext());

        copyStringArrayList.clear(); // Para hacer una limpieza de la lista
        copyStringArrayList.addAll(stringArrayList); //Para crear una copia de la lista de productos

        stringArrayAdapter.notifyDataSetChanged();
        registerForContextMenu(lvsProductos);
    }

}