package com.example.controlesbasicos;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class Inicio extends AppCompatActivity {

    TabHost tbhConversores;
    ValoresTodos traer=new ValoresTodos() ;

    EditText IngreseCantidadTV;
    TextView ResultadoTV;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);


        IngreseCantidadTV = findViewById(R.id.IngreseCantidadTV);
        ResultadoTV = findViewById(R.id.ResultadoTV);
        tbhConversores = findViewById(R.id.tbhConversores);
        tbhConversores.setup();

        tbhConversores.addTab(tbhConversores.newTabSpec("Monedas").setContent(R.id.tabMonedas).setIndicator("",getDrawable(R.drawable.ic_money)));
        tbhConversores.addTab(tbhConversores.newTabSpec("Masa").setContent(R.id.tabMasa).setIndicator("",getDrawable(R.drawable.weight_kilogram_i)));
        tbhConversores.addTab(tbhConversores.newTabSpec("Volumen").setContent(R.id.tabVolumen).setIndicator("",getDrawable(R.drawable.cube_icon)));
        tbhConversores.addTab(tbhConversores.newTabSpec("Longitud").setContent(R.id.tabLongitud).setIndicator("",getDrawable(R.drawable.length)));
        tbhConversores.addTab(tbhConversores.newTabSpec("Almacenamiento").setContent(R.id.tabAlmacenamiento).setIndicator("",getDrawable(R.drawable.usb_pendrive)));
        tbhConversores.addTab(tbhConversores.newTabSpec("Tiempo").setContent(R.id.tabTiempo).setIndicator("",getDrawable(R.drawable.ic_baseline_access_time_24)));
        tbhConversores.addTab(tbhConversores.newTabSpec("TransDatos").setContent(R.id.tabTDatos).setIndicator("",getDrawable(R.drawable.ic_transdatos)));

        tbhConversores.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {

                IngreseCantidadTV.getText().clear();
                ResultadoTV.setText("");
            }
        });
    }


    public void Convertir(View view){
        try {
            TextView tmpVal = (TextView) findViewById(R.id.IngreseCantidadTV);
            double cantidad = Double.parseDouble(tmpVal.getText().toString());



            int de = 0, a = 0;
            double resp = 0;
            switch (tbhConversores.getCurrentTabTag()){

                case "Monedas":
                    traer.val   = (Spinner) findViewById(R.id.MonedaActualSP);
                    de = traer.val .getSelectedItemPosition();
                    traer.val  = (Spinner) findViewById(R.id.MonedaCambiarSP);
                    a = traer.val .getSelectedItemPosition();
                    resp = traer .valores  [0][a] / traer .valores [0][de] * cantidad;
                    break;

                case "Longitud":
                    traer.val = (Spinner) findViewById(R.id.longitudActualSP );
                    de = traer.val.getSelectedItemPosition();
                    traer.val  = (Spinner) findViewById(R.id.LongitudCambiarSP );
                    a = traer .val.getSelectedItemPosition();
                    resp = traer.valores  [1][a] / traer.valores [1][de] * cantidad;
                    break;

                case "Volumen":
                    traer.val = (Spinner) findViewById(R.id.volumenactualsp);
                    de = traer.val.getSelectedItemPosition();
                    traer.val = (Spinner) findViewById(R.id.volumencambiosp);
                    a = traer.val.getSelectedItemPosition();
                    resp = traer.valores[2][a] / traer.valores[2][de] * cantidad;
                    break;

                case "Masa":
                    traer.val = (Spinner) findViewById(R.id.MasaActualsp);
                    de = traer.val.getSelectedItemPosition();
                    traer.val = (Spinner) findViewById(R.id.MasaCambiosp);
                    a = traer.val.getSelectedItemPosition();
                    resp = traer.valores[3][a] / traer.valores[3][de] * cantidad;
                    break;

                case "Almacenamiento":
                    traer.val = (Spinner) findViewById(R.id.spnDe );
                    de = traer.val.getSelectedItemPosition();
                    traer.val  = (Spinner) findViewById(R.id.spnA );
                    a = traer .val.getSelectedItemPosition();
                    resp = traer.valores  [4][a] / traer.valores [4][de] * cantidad;
                    break;

                case "Tiempo":
                    traer.val = (Spinner) findViewById(R.id.spnTime );
                    de = traer.val.getSelectedItemPosition();
                    traer.val  = (Spinner) findViewById(R.id.spntimen );
                    a = traer .val.getSelectedItemPosition();
                    resp = traer.valores  [5][a] / traer.valores [5][de] * cantidad;
                    break;

            }

            tmpVal = (TextView) findViewById(R.id.ResultadoTV);
            tmpVal.setText(String.format("Por la cantidad de: "+ cantidad  + " Usted recivira " + resp  ));
        }catch (Exception err){
            TextView temp = (TextView) findViewById(R.id.ResultadoTV);
            temp.setText("Porfavor Ingrese solo Numeros.");

            Toast.makeText(getApplicationContext(),"Por Favor Ingrese Solamente Numeros",Toast.LENGTH_LONG).show();


        }
    }
}