package com.example.seguros;

import android.content.Context;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

public final class bd_estructura
{

        public static String tb1="seguro";
        public static String tb1_colum1="id_seguro";
        public static String tb1_column2="tipo_seguro";
        public static String tb1_column3="cobertura";
        public static String tb1_column4="precio";
        public static String tb1_column5="activo";

        public static String tb2="poliza";
        public static String tb2_colum1="id_seguro";
        public static String tb2_column3="id_cliente";
        public static String tb2_column2="id_poliza";
        public static String tb2_column4="riesgo";
        public static String tb2_column5="comentario";
        public static String tb2_column6="descuento";
        public static String tb2_column7="precio";
        public static String tb2_column8="fecha";
        public static String tb2_column9="nif_vendedor";
        public static String tb2_column10="activo";

        public static String tb3="cliente";
        public static String tb3_colum1="nif_cliente";
        public static String tb3_column2="nombre";
        public static String tb3_column3="apellido1";
        public static String tb3_column4="apellido2";
        public static String tb3_column5="nif_vendedor";
        public static String tb3_column6="activo";

        public static String tb4="vendedor";
        public static String tb4_colum1="nif_vendedor";
        public static String tb4_column2="nombre";
        public static String tb4_column3="apellido1";
        public static String tb4_column4="apellido2";
        public static String tb4_column5="activo";

        protected static String crear_tb1= "CREATE TABLE " +tb1+ " (" +tb1_colum1+ " INTEGER PRIMARY KEY," +tb1_column2+
                "TEXT," +tb1_column3+ "TEXT," +tb1_column4+ "REAL," +tb1_column5+ "INTERGER)";

        protected static String crear_tb3= "CREATE TABLE " +tb3+ " (" +tb3_colum1+ " INTEGER PRIMARY KEY," +tb3_column2+
                "TEXT," +tb3_column3+ "TEXT," +tb3_column4+ "TEXT," +tb3_column5+ "INTEGER," +tb3_column6+ "INTERGER,";

        protected static String crear_tb4= "CREATE TABLE " +tb4+ " (" +tb4_colum1+ " INTEGER PRIMARY KEY," +tb4_column2+
                "TEXT," +tb4_column3+ "TEXT," +tb4_column4+ "TEXT," +tb4_column5+ "INTERGER)";

}
