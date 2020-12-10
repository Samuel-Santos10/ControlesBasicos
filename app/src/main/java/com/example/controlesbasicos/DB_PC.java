package com.example.controlesbasicos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB_PC extends SQLiteOpenHelper {

    static String nameDB = "db_tienda"; // aqui estamos declarando la instancia de la base de datos
    // Creacion de tabla y sus campos

    static String tblProductos = "CREATE TABLE productos(idproducto integer primary key autoincrement, nombre text, marca text, stock int, precio real, url text)"; // este es de CPU
    static   String tblMotherboard = "CREATE TABLE motherboard(idplaca integer primary key autoincrement, nombre text, marca text, stock int, precio real, url text)";
    static String tblDisco = "CREATE TABLE Disco(iddisco integer primary key autoincrement, nombre text, marca text, stock int, precio real, url text)";
    static String tblRam = "CREATE TABLE Ram(idRam integer primary key autoincrement, nombre text, marca text, stock int, precio real, url text)";
    static String tblFuente = "CREATE TABLE Fuente(idFuente integer primary key autoincrement, nombre text, marca text, stock int, precio real, url text)";
    static String tblGpu = "CREATE TABLE Gpu(idGpu integer primary key autoincrement, nombre text, marca text, stock int, precio real, url text)";


    public DB_PC(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nameDB, factory, version); // nameDB = La creacion de la base de datos en SQLite
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tblProductos);
        db.execSQL(tblMotherboard);
        db.execSQL(tblDisco);
        db.execSQL(tblRam);
        db.execSQL(tblFuente);
        db.execSQL(tblGpu);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // CPU


    //Creamos una estructura switch para los diferentes procesos que tendra
    public Cursor mantenimientoProductos(String accion, String[] data){
        SQLiteDatabase sqLiteDatabaseReadable = getReadableDatabase();
        SQLiteDatabase sqLiteDatabasewritable = getWritableDatabase();
        Cursor cursor = null;
        switch (accion){
            case "Consultar":
                cursor = sqLiteDatabaseReadable.rawQuery("SELECT * FROM productos ORDER BY nombre ASC", null);
                break;
            case "Nuevo":
                sqLiteDatabasewritable.execSQL("INSERT INTO productos(nombre,marca,stock,precio,url) VALUES('"+ data[1] +"','"+data[2]+"','"+data[3]+"','"+data[4]+"','"+data[5]+"')");
                break;
            case "Modificar":
                sqLiteDatabasewritable.execSQL("UPDATE productos SET nombre='"+ data[1] +"',marca='"+data[2]+"',stock='"+data[3]+"',precio='"+data[4]+"', url='"+data[5]+"' WHERE idproducto='"+data[0]+"'");
                break;
            case "Eliminar":
                sqLiteDatabasewritable.execSQL("DELETE FROM productos WHERE idproducto='"+ data[0] +"'");
                break;
            default:
                break;
        }
        return cursor;
    }

    // MOTHERBOARD

    //Creamos otra estructura swich pero esta vez para la motherboard
    public Cursor mantenimientoMotherboard(String accion, String[] data){
        SQLiteDatabase sqLiteDatabaseReadable = getReadableDatabase();
        SQLiteDatabase sqLiteDatabasewritable = getWritableDatabase();
        Cursor cursor = null;
        switch (accion){
            case "Consultar":
                cursor = sqLiteDatabaseReadable.rawQuery("SELECT * FROM motherboard ORDER BY nombre ASC", null);
                break;
            case "Nuevo":
                sqLiteDatabasewritable.execSQL("INSERT INTO motherboard(nombre,marca,stock,precio,url) VALUES('"+ data[1] +"','"+data[2]+"','"+data[3]+"','"+data[4]+"','"+data[5]+"')");
                break;
            case "Modificar":
                sqLiteDatabasewritable.execSQL("UPDATE motherboard SET nombre='"+ data[1] +"',marca='"+data[2]+"',stock='"+data[3]+"',precio='"+data[4]+"', url='"+data[5]+"' WHERE idplaca='"+data[0]+"'");
                break;
            case "Eliminar":
                sqLiteDatabasewritable.execSQL("DELETE FROM motherboard WHERE idplaca='"+ data[0] +"'");
                break;
            default:
                break;
        }
        return cursor;
    }

    //DISCO


    public Cursor mantenimientoDisco(String accion, String[] data){
        SQLiteDatabase sqLiteDatabaseReadable = getReadableDatabase();
        SQLiteDatabase sqLiteDatabasewritable = getWritableDatabase();
        Cursor cursor = null;
        switch (accion){
            case "Consultar":
                cursor = sqLiteDatabaseReadable.rawQuery("SELECT * FROM Disco ORDER BY nombre ASC", null);
                break;
            case "Nuevo":
                sqLiteDatabasewritable.execSQL("INSERT INTO Disco(nombre,marca,stock,precio,url) VALUES('"+ data[1] +"','"+data[2]+"','"+data[3]+"','"+data[4]+"','"+data[5]+"')");
                break;
            case "Modificar":
                sqLiteDatabasewritable.execSQL("UPDATE Disco SET nombre='"+ data[1] +"',marca='"+data[2]+"',stock='"+data[3]+"',precio='"+data[4]+"', url='"+data[5]+"' WHERE iddisco='"+data[0]+"'");
                break;
            case "Eliminar":
                sqLiteDatabasewritable.execSQL("DELETE FROM Disco WHERE iddisco='"+ data[0] +"'");
                break;
            default:
                break;
        }
        return cursor;
    }

    //RAM

    public Cursor mantenimientoRam(String accion, String[] data){
        SQLiteDatabase sqLiteDatabaseReadable = getReadableDatabase();
        SQLiteDatabase sqLiteDatabasewritable = getWritableDatabase();
        Cursor cursor = null;
        switch (accion){
            case "Consultar":
                cursor = sqLiteDatabaseReadable.rawQuery("SELECT * FROM Ram ORDER BY nombre ASC", null);
                break;
            case "Nuevo":
                sqLiteDatabasewritable.execSQL("INSERT INTO Ram(nombre,marca,stock,precio,url) VALUES('"+ data[1] +"','"+data[2]+"','"+data[3]+"','"+data[4]+"','"+data[5]+"')");
                break;
            case "Modificar":
                sqLiteDatabasewritable.execSQL("UPDATE Ram SET nombre='"+ data[1] +"',marca='"+data[2]+"',stock='"+data[3]+"',precio='"+data[4]+"', url='"+data[5]+"' WHERE idRam='"+data[0]+"'");
                break;
            case "Eliminar":
                sqLiteDatabasewritable.execSQL("DELETE FROM Ram WHERE idRam='"+ data[0] +"'");
                break;
            default:
                break;
        }
        return cursor;
    }


    //FUENTE

    public Cursor mantenimientoFuente(String accion, String[] data){
        SQLiteDatabase sqLiteDatabaseReadable = getReadableDatabase();
        SQLiteDatabase sqLiteDatabasewritable = getWritableDatabase();
        Cursor cursor = null;
        switch (accion){
            case "Consultar":
                cursor = sqLiteDatabaseReadable.rawQuery("SELECT * FROM Fuente ORDER BY nombre ASC", null);
                break;
            case "Nuevo":
                sqLiteDatabasewritable.execSQL("INSERT INTO Fuente(nombre,marca,stock,precio,url) VALUES('"+ data[1] +"','"+data[2]+"','"+data[3]+"','"+data[4]+"','"+data[5]+"')");
                break;
            case "Modificar":
                sqLiteDatabasewritable.execSQL("UPDATE Fuente SET nombre='"+ data[1] +"',marca='"+data[2]+"',stock='"+data[3]+"',precio='"+data[4]+"', url='"+data[5]+"' WHERE idFuente='"+data[0]+"'");
                break;
            case "Eliminar":
                sqLiteDatabasewritable.execSQL("DELETE FROM Fuente WHERE idFuente='"+ data[0] +"'");
                break;
            default:
                break;
        }
        return cursor;
    }

    //GPU

    public Cursor mantenimientoGpu(String accion, String[] data){
        SQLiteDatabase sqLiteDatabaseReadable = getReadableDatabase();
        SQLiteDatabase sqLiteDatabasewritable = getWritableDatabase();
        Cursor cursor = null;
        switch (accion){
            case "Consultar":
                cursor = sqLiteDatabaseReadable.rawQuery("SELECT * FROM Gpu ORDER BY nombre ASC", null);
                break;
            case "Nuevo":
                sqLiteDatabasewritable.execSQL("INSERT INTO Gpu(nombre,marca,stock,precio,url) VALUES('"+ data[1] +"','"+data[2]+"','"+data[3]+"','"+data[4]+"','"+data[5]+"')");
                break;
            case "Modificar":
                sqLiteDatabasewritable.execSQL("UPDATE Gpu SET nombre='"+ data[1] +"',marca='"+data[2]+"',stock='"+data[3]+"',precio='"+data[4]+"', url='"+data[5]+"' WHERE idGpu='"+data[0]+"'");
                break;
            case "Eliminar":
                sqLiteDatabasewritable.execSQL("DELETE FROM Gpu WHERE idGpu='"+ data[0] +"'");
                break;
            default:
                break;
        }
        return cursor;
    }
}
