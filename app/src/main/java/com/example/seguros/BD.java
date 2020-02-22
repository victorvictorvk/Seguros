package com.example.seguros;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BD extends SQLiteOpenHelper
{
    protected static final int db_version=1;
    protected static final String db_nombre= "seguros.db";
    protected static SQLiteDatabase db;

    public BD(Context context)
    {
        super(context, db_nombre, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(bd_estructura.crear_tb1);
        db.execSQL(bd_estructura.crear_tb4);
        db.execSQL(bd_estructura.crear_tb3);
        db.execSQL(bd_estructura.crear_tb2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

        onUpgrade(db, oldVersion, newVersion);
}

    public ContentValues guardar_seguro(String tipo, String cobertura, double precio)
    {
        ContentValues nuevo_seguro= new ContentValues();
        nuevo_seguro.put(bd_estructura.tb1_column2, tipo);
        nuevo_seguro.put(bd_estructura.tb1_column3, cobertura);
        nuevo_seguro.put(bd_estructura.tb1_column4, precio);
        nuevo_seguro.put(bd_estructura.tb1_column5, 1);
        return nuevo_seguro;
    }

    public ContentValues guardar_vendedor(String nif, String nombre, String apellido1, String apellido2)
    {
        ContentValues nuevo_vendedor= new ContentValues();
        nuevo_vendedor.put(bd_estructura.tb4_column1, nif);
        nuevo_vendedor.put(bd_estructura.tb4_column2, nombre);
        nuevo_vendedor.put(bd_estructura.tb4_column3, apellido1);
        nuevo_vendedor.put(bd_estructura.tb4_column4, apellido2);
        nuevo_vendedor.put(bd_estructura.tb4_column5, 1);
        return nuevo_vendedor;
    }

    public void insertar_valores(String tabla, ContentValues valores)
    {
        long id = db.insert(tabla, null, valores);
    }
}
