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

public class AdmiDisco extends AppCompatActivity {

    DB_PC miDB;
    Cursor misDiscos;
    discos Disco;
    ArrayList<discos> stringArrayList = new ArrayList<discos>();
    ArrayList<discos> copyStringArrayList = new ArrayList<discos>();
    ListView lvsdiscos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admi_disco);

        FloatingActionButton btnAgregarProductos = (FloatingActionButton)findViewById(R.id.btnAgregarProductos);
        btnAgregarProductos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                agregardiscos("Nuevo", new String[]{});
            }
        });
        obtenerDatosDiscos();
        buscarDiscos();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_principal, menu);

        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)menuInfo;
        misDiscos.moveToPosition((adapterContextMenuInfo.position));
        menu.setHeaderTitle(misDiscos.getString(1));
    }

    void buscarDiscos(){
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
                        for (discos am : copyStringArrayList) {
                            String nombre = am.getNombre();
                            if (nombre.toLowerCase().contains(tempVal.getText().toString().trim().toLowerCase())) {
                                stringArrayList.add(am);
                            }
                        }
                    }
                    adaptadorImagenes2 adaptadorImg = new adaptadorImagenes2(getApplicationContext(), stringArrayList);
                    lvsdiscos.setAdapter(adaptadorImg);
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
                agregardiscos("Nuevo", new String[]{});
                return true;

            case R.id.mnxModificar:
                String[] dataDiscos = {
                        misDiscos.getString(0), // Para el id producto
                        misDiscos.getString(1), // Para el nombre
                        misDiscos.getString(2), // para el marca
                        misDiscos.getString(3), // Para la stock
                        misDiscos.getString(4), // y este para el precio.
                        misDiscos.getString(5) // Para la URL
                };
                agregardiscos("Modificar", dataDiscos);
                return true;

            case R.id.mnxEliminar:
                AlertDialog eliminardiscos =  eliminardiscos();
                eliminardiscos.show();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    AlertDialog eliminardiscos(){
        AlertDialog.Builder confirmacion = new AlertDialog.Builder(AdmiDisco.this);
        confirmacion.setTitle(misDiscos.getString(1));
        confirmacion.setMessage("Esta seguro de eliminar el Producto?");
        confirmacion.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                miDB.mantenimientoDisco("Eliminar",new String[]{misDiscos.getString(0)});
                obtenerDatosDiscos();
                Toast.makeText(getApplicationContext(), "Producto eliminado exitosamente.",Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });
        confirmacion.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Sea eliminado el disco.",Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });
        return confirmacion.create();
    }

    void obtenerDatosDiscos(){
        miDB = new DB_PC(getApplicationContext(), "", null, 1);
        misDiscos = miDB.mantenimientoDisco( "Consultar", null);

        if(misDiscos.moveToFirst()){ // si hay registros en la base de datos que mostrar
            mostrarDatosProductos();
        }else{
            Toast.makeText(getApplicationContext(),"No hay Discos que mostrar", Toast.LENGTH_SHORT).show();

            agregardiscos("Nuevo", new String[]{});
        }
    }

    void agregardiscos(String accion, String[] dataDiscos){
        Bundle enviarParametros = new Bundle();
        enviarParametros.putString("accion", accion);
        enviarParametros.putStringArray("dataDiscos", dataDiscos);
        Intent agregarDisco = new Intent(AdmiDisco.this, AgregarProductos_Disco.class);
        agregarDisco.putExtras(enviarParametros);
        startActivity(agregarDisco);
    }

    void mostrarDatosProductos() {
        stringArrayList.clear();
        lvsdiscos = (ListView)findViewById(R.id.lvsDisco);
        do {
            Disco = new discos(misDiscos.getString(0), misDiscos.getString(1),misDiscos.getString(2), misDiscos.getString(3), misDiscos.getString(4), misDiscos.getString(5));
            stringArrayList.add(Disco);
        } while (misDiscos.moveToNext());

        adaptadorImagenes2 adaptadorImg = new adaptadorImagenes2(getApplicationContext(), stringArrayList);
        lvsdiscos.setAdapter(adaptadorImg);

        copyStringArrayList.clear(); // Para hacer una limpieza de la lista
        copyStringArrayList.addAll(stringArrayList); //Para crear una copia de la lista de productos

        registerForContextMenu(lvsdiscos);
    }
}

class discos{
    String id;
    String nombre;
    String marca;
    String stock;
    String precio;
    String urlImg;

    public discos(String id, String nombre, String marca, String stock, String precio, String urlImg) {
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
