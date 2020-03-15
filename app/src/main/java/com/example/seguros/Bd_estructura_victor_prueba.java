package com.example.seguros;

import android.content.Context;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

public final class Bd_estructura_victor_prueba
{

    public static String tb1="seguro";
    public static String tb1_column1="id_seguro";
    public static String tb1_column2="tipo_seguro";
    public static String tb1_column3="cobertura";
    public static String tb1_column4="precio";
    public static String tb1_column5="activo";

    public static String tb2="poliza";
    public static String tb2_column3="id_poliza";
    public static String tb2_column1="id_seguro";
    public static String tb2_column2="id_cliente";
    public static String tb2_column4="riesgo";
    public static String tb2_column5="comentario";
    public static String tb2_column6="descuento";
    public static String tb2_column7="precio";
    public static String tb2_column8="fecha";
    public static String tb2_column9="nif_vendedor";
    public static String tb2_column10="activo";

    public static String tb3="cliente";
    public static String tb3_column1="nif_cliente";
    public static String tb3_column2="nombre";
    public static String tb3_column3="apellido1";
    public static String tb3_column4="apellido2";
    public static String tb3_column5="nif_vendedor";
    public static String tb3_column6="activo";

    public static String tb4="vendedor";
    public static String tb4_column1="nif_vendedor";
    public static String tb4_column2="nombre";
    public static String tb4_column3="apellido1";
    public static String tb4_column4="apellido2";
    public static String tb4_column5="activo";
    public static String tb4_column6="pass";




    protected static String crear_tb1= "CREATE TABLE " +tb1+ " (" +tb1_column1+ " INTEGER PRIMARY KEY AUTOINCREMENT," +tb1_column2+
            " TEXT," +tb1_column3+ " TEXT," +tb1_column4+ " REAL," +tb1_column5+ " INTEGER)";

    protected static String crear_tb4= "CREATE TABLE " +tb4+ " (" +tb4_column1+ " INTEGER PRIMARY KEY," +tb4_column2+
            " TEXT," +tb4_column3+ " TEXT," +tb4_column4+ " TEXT,"+ tb4_column6+ " TEXT ," +tb4_column5+ " INTEGER DEFAULT 1)";

    protected static String crear_tb3= "CREATE TABLE " +tb3+ " (" +tb3_column1+ " INTEGER PRIMARY KEY AUTOINCREMENT," +tb3_column2+
            " TEXT," +tb3_column3+ " TEXT," +tb3_column4+ " TEXT," +tb3_column5+ " INTEGER," +tb3_column6+ " INTEGER, " +
            " FOREIGN KEY (" +tb3_column5+ ") REFERENCES " +tb4+ "(" +tb4_column1+ "))";

    protected static String crear_tb2= "CREATE TABLE " +tb2+ " ("
            +tb2_column1+ " INTEGER,"
            +tb2_column2+ " INTEGER,"
            +tb2_column3+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
            +tb2_column4+ " INTEGER,"
            +tb2_column5+ " TEXT,"
            +tb2_column6+ " REAL,"
            +tb2_column7+ " REAL,"
            +tb2_column8+ " NUMERIC,"
            +tb2_column9+ " INTEGER,"
            +tb2_column10+" INTEGER," +
            "FOREIGN KEY (" +tb2_column1+ ") REFERENCES " +tb1+ " (" +tb1_column1+ "), " +
            "FOREIGN KEY (" +tb2_column2+ ") REFERENCES " +tb3+ " (" +tb3_column1+ "), " +
            "FOREIGN KEY (" +tb2_column9+ ") REFERENCES " +tb4+ " (" +tb4_column1+ "))";

}
