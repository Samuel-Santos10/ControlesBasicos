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
    ValoresTodos misvalores=new ValoresTodos() ;

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

        tbhConversores.addTab(tbhConversores.newTabSpec("Monedas").setContent(R.id.tabMulticonver).setIndicator("",getDrawable(R.drawable.ic_money)));
        tbhConversores.addTab(tbhConversores.newTabSpec("Area").setContent(R.id.tabArea).setIndicator("",getDrawable(R.drawable.length)));

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

               /* case "Monedas":
                    traer.val   = (Spinner) findViewById(R.id.);
                    de = traer.val .getSelectedItemPosition();
                    traer.val  = (Spinner) findViewById(R.id.MonedaCambiarSP);
                    a = traer.val .getSelectedItemPosition();
                    resp = traer .valores  [0][a] / traer .valores [0][de] * cantidad;
                    break;*/

                case "Area":
                    misvalores.val   = (Spinner) findViewById(R.id.AreaActualSP);
                    de = misvalores.val .getSelectedItemPosition();
                    misvalores.val  = (Spinner) findViewById(R.id.AreaCambiarSP);
                    a = misvalores.val .getSelectedItemPosition();
                    resp = misvalores.datos [1][a] / misvalores.datos [1][de];
                    break;


            }

            tmpVal = (TextView) findViewById(R.id.ResultadoTV);
            tmpVal.setText(String.format("Por la cantidad de: "+ cantidad  + " Usted recivira " + resp  ));
        }catch (Exception err){
            TextView temp = (TextView) findViewById(R.id.ResultadoTV);

            Toast.makeText(getApplicationContext(),"Error: Ingrese la cantidad",Toast.LENGTH_LONG).show();

        }
    }
}