package com.example.controlesbasicos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ControlProgramador extends AppCompatActivity {

    JSONArray datosJSON;
    JSONObject jsonObject;
    Integer posicion;
    ArrayList<String> arrayList = new ArrayList<String>();
    ArrayList<String> copyStringArrayList = new ArrayList<String>();
    ArrayAdapter<String> stringArrayAdapter;
    utilidadescomunes uc;
    detectarInternet di;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_programador);

        di = new detectarInternet(getApplicationContext());
        if (di.hayConexionInternet()) {
            conexionServidor objObtenerProductos = new conexionServidor();
            objObtenerProductos.execute(uc.url_consulta, "GET");
        } else  {
            Toast.makeText(getApplicationContext(), "No hay conexion a internet.", Toast.LENGTH_LONG).show();
        }

        FloatingActionButton btnAgregarProductos = (FloatingActionButton) findViewById(R.id.btnAgregarProductos);
        btnAgregarProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (di.hayConexionInternet()) {
                    agregarNuevosProductos("Nuevo", jsonObject);
                } else  {
                    Toast.makeText(getApplicationContext(), "No hay conexion a internet.", Toast.LENGTH_LONG).show();
                }
            }
        });
        buscarProductos();
    }


    void buscarProductos() {
        final TextView tempVal = findViewById(R.id.etBuscarProductos);
        tempVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                arrayList.clear();
                if (tempVal.getText().toString().trim().length() < 1) { // No tenemos texto el cual mostrar
                    arrayList.addAll(copyStringArrayList);
                } else { // Realizamos la busqueda
                    for (String producto : copyStringArrayList) {
                        if (producto.toLowerCase().contains(tempVal.getText().toString().trim().toLowerCase())) {
                            arrayList.add(producto);
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_principal, menu);
        try {
            AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
            posicion = adapterContextMenuInfo.position;
            menu.setHeaderTitle(datosJSON.getJSONObject(posicion).getString("descripcion"));
        } catch (Exception ex) {
            ///
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnxAgregar:
                agregarNuevosProductos("Nuevo", jsonObject);
                return true;

            case R.id.mnxModificar:
                try {
                    agregarNuevosProductos("Modificar", datosJSON.getJSONObject(posicion));
                } catch (Exception ex) {
                }
                return true;

            case R.id.mnxEliminar:
                AlertDialog eliminarProduct = eliminarProducto();
                eliminarProduct.show();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    private void mostrarDatosProductos() {
        ListView lvTienda = findViewById(R.id.lvTienda);
        try {
            arrayList.clear();
            stringArrayAdapter = new ArrayAdapter<>(ControlProgramador.this, android.R.layout.simple_list_item_1, arrayList);
            lvTienda.setAdapter(stringArrayAdapter);

            for (int i = 0; i < datosJSON.length(); i++) {
                stringArrayAdapter.add(datosJSON.getJSONObject(i).getJSONObject("value").getString("descripcion"));
            }
            copyStringArrayList.clear();
            copyStringArrayList.addAll(arrayList);

            stringArrayAdapter.notifyDataSetChanged();
            registerForContextMenu(lvTienda);
        } catch (Exception ex) {
            Toast.makeText(ControlProgramador.this, "Error al mostrar los productos: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void agregarNuevosProductos(String accion, JSONObject jsonObject) {
        try {
            Bundle enviarParametros = new Bundle();
            enviarParametros.putString("accion", accion);
            enviarParametros.putString("dataProducto", jsonObject.toString());

            Intent agregarProductos = new Intent(ControlProgramador.this, Agregar_Productos.class);
            agregarProductos.putExtras(enviarParametros);
            startActivity(agregarProductos);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error al llamar agregar producto: " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    AlertDialog eliminarProducto() {
        AlertDialog.Builder confirmacion = new AlertDialog.Builder(ControlProgramador.this);
        try {
            confirmacion.setTitle(datosJSON.getJSONObject(posicion).getJSONObject("value").getString("descripcion"));
            confirmacion.setMessage("¿Esta seguro de eliminar el producto?");
            confirmacion.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    try {
                        conexionServidor objEliminarAmigo = new conexionServidor();
                        objEliminarAmigo.execute(uc.url_mto +
                                datosJSON.getJSONObject(posicion).getJSONObject("value").getString("_id") + "?rev=" +
                                datosJSON.getJSONObject(posicion).getJSONObject("value").getString("_rev"), "DELETE");

                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), "Error al intentar eliminar el Producto: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    dialogInterface.dismiss();
                }
            });
            confirmacion.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(getApplicationContext(), "Eliminacion cancelada por el producto.", Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            });
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Error al mostrar la confirmacion " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return confirmacion.create();
    }

    private class conexionServidor extends AsyncTask<String, String, String> {
        HttpURLConnection urlConnection;

        @Override
        protected String doInBackground(String... parametros) {
            StringBuilder result = new StringBuilder();
            try {
                String uri = parametros[0];
                String metodo = parametros[1];

                URL url = new URL(uri);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod(metodo);

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String linea;
                while ((linea = reader.readLine()) != null) {
                    result.append(linea);
                }
            } catch (Exception ex) {
                //
            }
            return result.toString();
        }

        @SuppressLint("NewApi")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                jsonObject = new JSONObject(s);
                if (jsonObject.isNull("rows")) {
                    if (jsonObject.getBoolean("ok")) {
                        Toast.makeText(ControlProgramador.this, "Producto eliminado con exito", Toast.LENGTH_SHORT).show();
                        datosJSON.remove(posicion);
                    } else {
                        Toast.makeText(ControlProgramador.this, "Error no se pudo eliminar el registro de Producto", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    datosJSON = jsonObject.getJSONArray("rows");
                }
                mostrarDatosProductos();
            } catch (Exception ex) {
                Toast.makeText(ControlProgramador.this, "Error la parsear los datos: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}