package com.example.seguros;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

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
        //Aquí se van llamando a la creación de las tablas
        db.execSQL(Bd_estructura.crear_tb1);
        db.execSQL(Bd_estructura.crear_tb4);
        db.execSQL(Bd_estructura.crear_tb3);
        db.execSQL(Bd_estructura.crear_tb2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

        onUpgrade(db, oldVersion, newVersion);
}

    public ContentValues guardar_seguro(String tipo, String cobertura, double precio)
    {
        ContentValues nuevo_seguro= new ContentValues();
        nuevo_seguro.put(Bd_estructura.tb1_column2, tipo);
        nuevo_seguro.put(Bd_estructura.tb1_column3, cobertura);
        nuevo_seguro.put(Bd_estructura.tb1_column4, precio);
        nuevo_seguro.put(Bd_estructura.tb1_column5, 1);
        return nuevo_seguro;
    }

    public ContentValues guardar_vendedor(String nif, String nombre, String apellido1, String apellido2)
    {
        ContentValues nuevo_vendedor= new ContentValues();
        nuevo_vendedor.put(Bd_estructura.tb4_column1, nif);
        nuevo_vendedor.put(Bd_estructura.tb4_column2, nombre);
        nuevo_vendedor.put(Bd_estructura.tb4_column3, apellido1);
        nuevo_vendedor.put(Bd_estructura.tb4_column4, apellido2);
        nuevo_vendedor.put(Bd_estructura.tb4_column5, 1);
        return nuevo_vendedor;
    }

    public ContentValues guardar_poliza(int idSeguro, int idCliente, int riesgo, String comentario, int descuento, Double precio, String nifVendedor)
    {
        ContentValues nueva_poliza= new ContentValues();
        Date fech= new Date();

        nueva_poliza.put(Bd_estructura.tb2_column2, idSeguro);
        nueva_poliza.put(Bd_estructura.tb2_column3, idCliente);
        nueva_poliza.put(Bd_estructura.tb2_column4, riesgo);
        nueva_poliza.put(Bd_estructura.tb2_column5, comentario);
        nueva_poliza.put(Bd_estructura.tb2_column6, descuento);
        nueva_poliza.put(Bd_estructura.tb2_column7, precio);
        nueva_poliza.put(Bd_estructura.tb2_column8, fech.toString());
        nueva_poliza.put(Bd_estructura.tb2_column9, nifVendedor);
        nueva_poliza.put(Bd_estructura.tb2_column10, 1);

        return nueva_poliza;
    }

    public ContentValues guardar_cliente(String nif, String nombre, String apellido1, String apellido2, String nifVendedor)
    {
        ContentValues nuevo_cliente= new ContentValues();
        nuevo_cliente.put(Bd_estructura.tb3_column1, nif);
        nuevo_cliente.put(Bd_estructura.tb3_column2, nombre);
        nuevo_cliente.put(Bd_estructura.tb3_column3, apellido1);
        nuevo_cliente.put(Bd_estructura.tb3_column4, apellido2);
        nuevo_cliente.put(Bd_estructura.tb3_column5, nifVendedor);
        nuevo_cliente.put(Bd_estructura.tb3_column5, 1);

        return nuevo_cliente;
    }

    public void insertar_valores(String tabla, ContentValues valores)
    {
        db.beginTransaction();
        long id=db.insert(tabla, null, valores);


    }

    @Override
    public synchronized void close()
    {
        super.close();
    }
}
