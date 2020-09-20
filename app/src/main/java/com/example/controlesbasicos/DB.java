package com.example.controlesbasicos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {

    static String nameDB = "db_amigos"; // aqui estamos declarando la instancia de la base de datos
    // Creacion de tabla y sus campos

    static String tblAmigos = "CREATE TABLE amigos(idamigos integer primary key autoincrement, nombre text, telefono text, direccion text, email text)";

    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nameDB, factory, version); // nameDB = La creacion de la base de datos en SQLite
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tblAmigos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    //Creamos una estructura switch para los diferentes procesos que tendra
    public Cursor mantenimientoAmigos(String accion, String[] data){
        SQLiteDatabase sqLiteDatabaseReadable = getReadableDatabase();
        SQLiteDatabase sqLiteDatabasewritable = getWritableDatabase();
        Cursor cursor = null;
        switch (accion){
            case "Consultar":
                cursor = sqLiteDatabaseReadable.rawQuery("SELECT * FROM amigos ORDER BY nombre ASC", null);
                break;
            case "Nuevo":
                sqLiteDatabasewritable.execSQL("INSERT INTO amigos(nombre,telefono,direccion,email) VALUES ('"+ data[1] +"','"+data[2]+"','"+data[3]+"','"+data[4]+"')");
                break;
            case "Modificar":

                break;
            case "Eliminar":

                break;
            default:
                break;
        }
        return cursor;
    }
}
