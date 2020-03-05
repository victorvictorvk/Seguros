package com.example.seguros;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;


import androidx.annotation.Nullable;

public class BaseDatosVictorPrueba extends SQLiteOpenHelper
{
    protected static final int db_version=1;
    protected static final String db_nombre= "seguros.db";
    protected static SQLiteDatabase db;

    public BaseDatosVictorPrueba(Context context,String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(Bd_estructura_victor_prueba.crear_tb1);
        db.execSQL(Bd_estructura_victor_prueba.crear_tb4);
        db.execSQL(Bd_estructura_victor_prueba.crear_tb3);
        db.execSQL(Bd_estructura_victor_prueba.crear_tb2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(db, oldVersion, newVersion);
    }

    public ContentValues guardar_seguro(String tipo, String cobertura, double precio)
    {
        ContentValues nuevo_seguro= new ContentValues();
        nuevo_seguro.put(Bd_estructura_victor_prueba.tb1_column2, tipo);
        nuevo_seguro.put(Bd_estructura_victor_prueba.tb1_column3, cobertura);
        nuevo_seguro.put(Bd_estructura_victor_prueba.tb1_column4, precio);
        nuevo_seguro.put(Bd_estructura_victor_prueba.tb1_column5, 1);
        return nuevo_seguro;
    }

    public ContentValues guardar_vendedor(String nif, String nombre, String apellido1, String apellido2, String pass)
    {
        ContentValues nuevo_vendedor= new ContentValues();
        nuevo_vendedor.put(Bd_estructura_victor_prueba.tb4_column1, nif);
        nuevo_vendedor.put(Bd_estructura_victor_prueba.tb4_column2, nombre);
        nuevo_vendedor.put(Bd_estructura_victor_prueba.tb4_column3, apellido1);
        nuevo_vendedor.put(Bd_estructura_victor_prueba.tb4_column4, apellido2);
        nuevo_vendedor.put(Bd_estructura_victor_prueba.tb4_column5, 1);
        nuevo_vendedor.put(Bd_estructura_victor_prueba.tb4_column6, pass);
        return nuevo_vendedor;
    }

    public void insertar_valores(SQLiteDatabase sql, String tabla, ContentValues valores)
    {
    long numero  =   sql.insert(tabla, null, valores);
    }

    //Metodo para devolver una consulta de un solo comercial
    public Cursor comercial(String dni)
    {
        Cursor cursor = db.rawQuery("Select "+ Bd_estructura_victor_prueba.tb4_column2 + ", " + Bd_estructura_victor_prueba.tb4_column3 + ", "
                + Bd_estructura_victor_prueba.tb4_column4 + " from  " + Bd_estructura_victor_prueba.tb4+ " where " +Bd_estructura_victor_prueba.tb4_column1+
                " = " +dni, null);
        return cursor;
    }

    //Metodo para devolver una consulta de una lista de comerciales
    public Cursor listaComerciales(SQLiteDatabase sql)
    {
        //Creamos un array con los campos que queremos seleccionar de la tabla.
        String[] columnasARecuperar = new String [] {Bd_estructura_victor_prueba.tb4_column1,Bd_estructura_victor_prueba.tb4_column2, Bd_estructura_victor_prueba.tb4_column3, Bd_estructura_victor_prueba.tb4_column4};
        Cursor cursor = sql.query(Bd_estructura_victor_prueba.tb4, columnasARecuperar, null, null, null, null, null);

        return cursor;
    }

    //Metodo para devolver una consulta de una lista de seguros
    public Cursor listaSeguros(SQLiteDatabase sql)
    {
        //Creamos un array con los campos que queremos seleccionar de la tabla.
        //En esta consulta necesitaremos el campo id para futuras acciones
        String[] columnasARecuperar = new String [] {Bd_estructura_victor_prueba.tb1_column1, Bd_estructura_victor_prueba.tb1_column2, Bd_estructura_victor_prueba.tb1_column3,Bd_estructura_victor_prueba.tb1_column4};
        Cursor cursor = sql.query(Bd_estructura_victor_prueba.tb1, columnasARecuperar, null, null, null, null, null);

        return cursor;
    }
}
