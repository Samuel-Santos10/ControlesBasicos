package com.example.controlesbasicos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.database.Cursor;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class MainActivity extends Activity {

    DB miDB;
    Cursor misProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        obtenerDatosProductos();
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
                return true;

            default:
                return super.onContextItemSelected(item);
        }
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
        ListView lvsProductos = (ListView) findViewById(R.id.lvsProductos);
        ArrayList<String> stringArrayList = new ArrayList<>();
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, stringArrayList);
        lvsProductos.setAdapter(stringArrayAdapter);
        do {
            stringArrayAdapter.add(misProductos.getString(1));
        } while (misProductos.moveToNext());
        stringArrayAdapter.notifyDataSetChanged();
        registerForContextMenu(lvsProductos);
    }

}