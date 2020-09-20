package com.example.controlesbasicos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
    Cursor misAmigos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        obtenerDatosAmigos();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_amigos, menu);

        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)menuInfo;
        misAmigos.moveToPosition((adapterContextMenuInfo.position));
        menu.setHeaderTitle(misAmigos.getString(1));
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnxAgregar:
                agregarAmigo("Nuevo", new String[]{});
                return true;

            case R.id.mnxModificar:
                String[] dataAmigo = {
                        misAmigos.getString(0), // Para el id amigo
                        misAmigos.getString(1), // Para el nombre
                        misAmigos.getString(2), // para el telefono
                        misAmigos.getString(3), // Para la dirección
                        misAmigos.getString(4), // y este para el email.
                };
                agregarAmigo("Modificar", dataAmigo);
                return true;

            case R.id.mnxEliminar:
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    void obtenerDatosAmigos(){
        miDB = new DB(getApplicationContext(), "", null, 1);
        misAmigos = miDB.mantenimientoAmigos( "Consultar", null);

        if(misAmigos.moveToFirst()){ // si hay registros en la base de datos que mostrar
            mostrarDatosAmigos();
        }else{
            Toast.makeText(getApplicationContext(),"No hay Amigos que mostrar", Toast.LENGTH_SHORT).show();

            agregarAmigo("Nuevo", new String[]{});
        }
    }

    void agregarAmigo(String accion, String[] dataAmigo){
        Bundle enviarParametros = new Bundle();
        enviarParametros.putString("accion", accion);
        enviarParametros.putStringArray("dataAmigo", dataAmigo);
        Intent agregarAmigos = new Intent(MainActivity.this, com.example.controlesbasicos.agregarAmigos.class);
        agregarAmigos.putExtras(enviarParametros);
        startActivity(agregarAmigos);
    }

    void mostrarDatosAmigos() {
        ListView lvsAmigos = (ListView) findViewById(R.id.lvsAmigos);
        ArrayList<String> stringArrayList = new ArrayList<>();
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, stringArrayList);
        lvsAmigos.setAdapter(stringArrayAdapter);
        do {
            stringArrayAdapter.add(misAmigos.getString(1));
        } while (misAmigos.moveToNext());
        stringArrayAdapter.notifyDataSetChanged();
        registerForContextMenu(lvsAmigos);
    }

}