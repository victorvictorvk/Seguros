package com.example.seguros;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class bd extends SQLiteOpenHelper
{
    protected static final int db_version=1;
    protected static final String db_nombre= "seguros.db";

    public bd(Context context)
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

   
}
