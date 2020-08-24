package com.example.controlesbasicos;

import android.widget.Spinner;

public class ValoresTodos {

    Spinner val;
    double valores[][] = {
            new double[]{1, 8.75, 105.83, 74.94, 24.58, 22.08, 6.91, 7.68, 0.88,34.80}, //Valores de monedas
            new double[] {1, 100, 39.3701, 3.28084, 1.1963081929167, 1.09361, 0.001, 0.000621371, 1000, 1e+6},//Valores de Longitud
            new double[] {1, 1000, 0.001, 0.264172, 0.0353147, 4.16667, 61.0237, 35.1951, 56.3121, 168.936 }, //Valores de Volumen
            new double[] {1, 1000, 1000000 , 1000000000, 2.20462, 35.274, 0.001, 0.157473, 1e-6, 10}, //Valores de Masa
            new double[] {1, 1000, 1000000, 0.001, 1e-6, 1e-9, 1e-12, 8e+6, 2e+6, 8000}, //Valores de Almacenamiento
            new double[] {1, 1000000000, 1000000, 1000, 0.0166666667, 0.0002777778, 0.0000115741, 0.0000016534, 0.0000003802570, 0.00000003168808}, //Valores de Tiempo
            new double[]{1, 0.001, 0.000125, 0.000001, 0.000000125, 0.000000001, 0.000000000125, 0.000000000001, 0.000000000000125}  //Valores de Transferencia de Datos
    };
}
