package com.example.seguros;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;


import java.util.Date;

public class BaseDatosVVS extends SQLiteOpenHelper
{
    protected static final int db_version=1;
    protected static final String db_nombre= "seguros.db";
    protected static SQLiteDatabase db;

    public BaseDatosVVS(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(Bd_estructura_VVS.crear_tb1);
        db.execSQL(Bd_estructura_VVS.crear_tb4);
        db.execSQL(Bd_estructura_VVS.crear_tb3);
        db.execSQL(Bd_estructura_VVS.crear_tb2);
        db.execSQL(Bd_estructura_VVS.crear_tb5);

    }

    public void insertar_valores_admin(SQLiteDatabase sql) {
        //Ahora indicamos que abra la base de datos en modo lectura y escritura
        ContentValues valores_admin = new ContentValues();
        valores_admin.put(Bd_estructura_VVS.tb5_column1, "admin");
        valores_admin.put(Bd_estructura_VVS.tb5_column2, "1234");
        long numero  =   sql.insert(Bd_estructura_VVS.tb5, null, valores_admin);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(db, oldVersion, newVersion);
    }

    public ContentValues guardar_seguro(String tipo, String cobertura, double precio)
    {
        ContentValues nuevo_seguro= new ContentValues();
        //El id_Seguro no lo declaramos aquí, poruqe ya tiene puesto autoincrement
        nuevo_seguro.put(Bd_estructura_VVS.tb1_column2, tipo);
        nuevo_seguro.put(Bd_estructura_VVS.tb1_column3, cobertura);
        nuevo_seguro.put(Bd_estructura_VVS.tb1_column4, precio);
        nuevo_seguro.put(Bd_estructura_VVS.tb1_column5, 1);
        return nuevo_seguro;
    }

    public ContentValues guardar_vendedor(String nif, String nombre, String apellido1, String apellido2, String pass)
    {
        ContentValues nuevo_vendedor= new ContentValues();
        nuevo_vendedor.put(Bd_estructura_VVS.tb4_column1, nif);
        nuevo_vendedor.put(Bd_estructura_VVS.tb4_column2, nombre);
        nuevo_vendedor.put(Bd_estructura_VVS.tb4_column3, apellido1);
        nuevo_vendedor.put(Bd_estructura_VVS.tb4_column4, apellido2);
        nuevo_vendedor.put(Bd_estructura_VVS.tb4_column5, 1);
        nuevo_vendedor.put(Bd_estructura_VVS.tb4_column6, pass);
        return nuevo_vendedor;
    }

    public void insertar_valores(SQLiteDatabase sql, String tabla, ContentValues valores)
    {
         long numero  =   sql.insert(tabla, null, valores);
    }

    //Metodo para devolver una consulta de un solo comercial
    public Cursor comercial(String dni)
    {
        Cursor cursor = db.rawQuery("Select "+ Bd_estructura_VVS.tb4_column2 + ", " + Bd_estructura_VVS.tb4_column3 + ", "
                + Bd_estructura_VVS.tb4_column4 + " from  " + Bd_estructura_VVS.tb4+ " where " + Bd_estructura_VVS.tb4_column1+
                " = " +dni, null);
        return cursor;
    }

    //Metodo para devolver una consulta de una lista de comerciales
    public Cursor listaComerciales(SQLiteDatabase sql)
    {
        //Creamos un array con los campos que queremos seleccionar de la tabla.
        String[] columnasARecuperar = new String [] {Bd_estructura_VVS.tb4_column1, Bd_estructura_VVS.tb4_column2, Bd_estructura_VVS.tb4_column3, Bd_estructura_VVS.tb4_column4};
        Cursor cursor = sql.query(Bd_estructura_VVS.tb4, columnasARecuperar, Bd_estructura_VVS.tb4_column5+" = 1", null, null, null, null);

        return cursor;
    }

    //Metodo para devolver una consulta de una lista de seguros
    public Cursor listaSeguros(SQLiteDatabase sql)
    {
        //Creamos un array con los campos que queremos seleccionar de la tabla.
        //En esta consulta necesitaremos el campo id para futuras acciones
        String[] columnasARecuperar = new String [] {Bd_estructura_VVS.tb1_column1, Bd_estructura_VVS.tb1_column2, Bd_estructura_VVS.tb1_column3, Bd_estructura_VVS.tb1_column4};
        Cursor cursor = sql.query(Bd_estructura_VVS.tb1, columnasARecuperar, Bd_estructura_VVS.tb1_column5+" = 1", null, null, null, null);

        return cursor;
    }



    public Cursor consultaSQLComercialesPass(SQLiteDatabase sql)
    {
        //Creamos un array con los campos que queremos seleccionar de la tabla.
        //En esta consulta necesitaremos el campo id para futuras acciones
        String[] columnasARecuperar = new String [] {Bd_estructura_VVS.tb4_column1, Bd_estructura_VVS.tb4_column6 };
        Cursor cursor = sql.query(Bd_estructura_VVS.tb4, columnasARecuperar, Bd_estructura_VVS.tb4_column5+" = 1", null, null, null, null);

        return cursor;
    }

    public boolean passOK(SQLiteDatabase sql, String dni, String pass)
    {
        //Obtenemos todos los dni de los comercial con sus claves
        Cursor c = consultaSQLComercialesPass(sql);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                //Estos datos serán globales en toda la clase puesto que es con el que trabajaremos
              String dniTabla = c.getString(0);
              String passTabla = c.getString(1);
              if( dniTabla.equals(dni) && passTabla.equals(pass))
              {
                  return true;
              }

            } while (c.moveToNext());
        }
        return false;
    }

    public boolean esAdmin(String dni, SQLiteDatabase sql, String pass) {
        //Obtenemos todos los dni de los comercial con sus claves
        Cursor c = consultaSQLAdmin(sql);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                //Estos datos serán globales en toda la clase puesto que es con el que trabajaremos
                String dniTabla = c.getString(0);
                String passTabla = c.getString(1);
                if( dniTabla.equals(dni) && passTabla.equals(pass))
                {
                    return true;
                }

            } while (c.moveToNext());
        }
        return false;
    }

    private Cursor consultaSQLAdmin(SQLiteDatabase sql) {
        String[] columnasARecuperar = new String [] {Bd_estructura_VVS.tb5_column1, Bd_estructura_VVS.tb5_column2 };
        Cursor cursor = sql.query(Bd_estructura_VVS.tb5, columnasARecuperar, null, null, null, null, null);

        return cursor;
    }

    public ContentValues guardar_cliente(String nif, String nombre, String apellido1, String apellido2, String nifVendedor)
    {
        ContentValues nuevo_cliente= new ContentValues();
        nuevo_cliente.put(Bd_estructura.tb3_column1, nif);
        nuevo_cliente.put(Bd_estructura.tb3_column2, nombre);
        nuevo_cliente.put(Bd_estructura.tb3_column3, apellido1);
        nuevo_cliente.put(Bd_estructura.tb3_column4, apellido2);
        nuevo_cliente.put(Bd_estructura.tb3_column5, nifVendedor);
        nuevo_cliente.put(Bd_estructura.tb3_column6, 1);

        return nuevo_cliente;

    }

    public ContentValues guardar_poliza(Integer idSeguro, String idCliente, String riesgo, String comentario, String descuento, String precio, String nifVendedor)
    {


        ContentValues nueva_poliza= new ContentValues();
        Date fech= new Date();


        //El identificador de la póliza, no lo declaramos aquí porque está puesto autoincrement (tb2_column3)
        nueva_poliza.put(Bd_estructura.tb2_column1, idSeguro);
        nueva_poliza.put(Bd_estructura.tb2_column2, idCliente);
        nueva_poliza.put(Bd_estructura.tb2_column4, riesgo);
        nueva_poliza.put(Bd_estructura.tb2_column5, comentario);
        nueva_poliza.put(Bd_estructura.tb2_column6, descuento);
        nueva_poliza.put(Bd_estructura.tb2_column7, precio);
        nueva_poliza.put(Bd_estructura.tb2_column8, fech.toString());
        nueva_poliza.put(Bd_estructura.tb2_column9, nifVendedor);
        nueva_poliza.put(Bd_estructura.tb2_column10, 1);

        return nueva_poliza;
    }

    public Cursor listaClientes(SQLiteDatabase sql, String dniComercial) {

        //Creamos un array con los campos que queremos seleccionar de la tabla.
        //En esta consulta necesitaremos el campo id para futuras acciones
        String[] columnasARecuperar = new String [] {Bd_estructura_VVS.tb3_column2, Bd_estructura_VVS.tb3_column3,
                Bd_estructura_VVS.tb3_column4, Bd_estructura_VVS.tb3_column1 };
        Cursor cursor = sql.query(Bd_estructura_VVS.tb3, columnasARecuperar, Bd_estructura_VVS.tb3_column5 +" = "+ dniComercial
                +" and "+Bd_estructura_VVS.tb3_column6 + "= 1 ", null, null, null, null);

        return cursor;
    }

    public Cursor listaPolizas(SQLiteDatabase sql) {


        //Creamos un array con los campos que queremos seleccionar de la tabla.
        //En esta consulta necesitaremos el campo id para futuras acciones
        String[] columnasARecuperar = new String [] {Bd_estructura_VVS.tb2_column1, Bd_estructura_VVS.tb2_column3,
                Bd_estructura_VVS.tb2_column4, Bd_estructura_VVS.tb2_column5,
                Bd_estructura_VVS.tb2_column6, Bd_estructura_VVS.tb2_column7 };
        Cursor cursor = sql.query(Bd_estructura_VVS.tb2, columnasARecuperar, Bd_estructura_VVS.tb2_column2 +" = "+ Comercial.dni_cliente_elegido
              +" and "+ Bd_estructura_VVS.tb2_column10 +" = 1" , null, null, null, null);

        return cursor;
    }
    public boolean estaAsociado(SQLiteDatabase sql, String idSeguro, String columna) {

        //Consultamos si existe este id en la tabla polizas
        String[] columnasARecuperar = new String [] {columna };
        Cursor cursor = sql.query(Bd_estructura_VVS.tb2, columnasARecuperar, null
                , null, null, null, null);

        while(cursor.moveToNext()){
            if(cursor.getString(0).equals(idSeguro)){
                return true;
            }
        }
        return false;
    }

    public boolean estaSeguro(SQLiteDatabase sql, String idSeguro, String columnaIdseguro, String colActivo) {

        //Consultamos si existe este id en la tabla polizas
        String[] columnasARecuperar = new String [] {columnaIdseguro, colActivo };
        Cursor cursor = sql.query(Bd_estructura_VVS.tb2, columnasARecuperar, null
                , null, null, null, null);

        while(cursor.moveToNext()){
            if(cursor.getString(0).equals(idSeguro)&& cursor.getString(1).equals("1") ){
                return true;
            }
        }
        return false;
    }

    public boolean estaAsociadoActivo(SQLiteDatabase sql, String idSeguro, String columna, String colActivo) {

        //Consultamos si existe este id en la tabla polizas
        String[] columnasARecuperar = new String [] {columna, colActivo };
        Cursor cursor = sql.query(Bd_estructura_VVS.tb2, columnasARecuperar, null
                , null, null, null, null);

        while(cursor.moveToNext()){
            if(cursor.getString(0).equals(idSeguro) && cursor.getString(1).equals("1") ){
                return true;
            }
        }
        return false;
    }

    public boolean estaClienteActivo(SQLiteDatabase sql, String dniComercial, String columnadniComercial, String colActivo) {

        //Consultamos si existe este id en la tabla polizas
        String[] columnasARecuperar = new String [] {columnadniComercial, colActivo };
        Cursor cursor = sql.query(Bd_estructura_VVS.tb3, columnasARecuperar, null
                , null, null, null, null);

        while(cursor.moveToNext()){
            if(cursor.getString(0).equals(dniComercial) && cursor.getString(1).equals("1") ){
                return true;
            }
        }
        return false;
    }

    public String dameNombreSeguro(SQLiteDatabase sql, String id_seguro) {
        String[] columnasARecuperar = new String [] {Bd_estructura_VVS.tb1_column2 };
        Cursor cursor = sql.query(Bd_estructura_VVS.tb1, columnasARecuperar, Bd_estructura_VVS.tb1_column1 +" = "+ id_seguro, null, null, null, null);
        String nombreSeguro = null;
        while(cursor.moveToNext()){
             nombreSeguro = cursor.getString(0);
        }
        return nombreSeguro;
    }

    public boolean existeCliente(SQLiteDatabase sql, String dni) {
        String[] columnasARecuperar = new String [] {Bd_estructura_VVS.tb3_column1 };
        Cursor cursor = sql.query(Bd_estructura_VVS.tb3, columnasARecuperar, null, null, null, null, null);
        while(cursor.moveToNext()){
            if(cursor.getString(0).equals(dni)){
                return true;
            }
        }
        return false;
    }

    public boolean existeComercial(SQLiteDatabase sql, String dni) {
        String[] columnasARecuperar = new String [] {Bd_estructura_VVS.tb4_column1 };
        Cursor cursor = sql.query(Bd_estructura_VVS.tb4, columnasARecuperar, null, null, null, null, null);
        while(cursor.moveToNext()){
            if(cursor.getString(0).equals(dni)){
                return true;
            }
        }
        return false;
    }



}
