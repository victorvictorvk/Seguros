package com.example.seguros;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListaSeguros extends AppCompatActivity {

    public SQLiteDatabase sqlt;
    public BaseDatosVVS bd;
    static Context context;

   public ListaSeguros(Context c, SQLiteDatabase sql, BaseDatosVVS bd){
       this.context = c;

       this.bd = bd;
       this.sqlt = bd.getWritableDatabase();
   }

    public ArrayList<String> listaSeguros() {

        ArrayList<String> array = new ArrayList<>();
        //Aqui hacemos una consulta a la base de datos.
        Cursor c = bd.listaSeguros(sqlt);
        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                String id_seguro = c.getString(0);
                String tipoSeguro = c.getString(1);
                String cobertura = c.getString(2);
                String precio = c.getString(3);

                array.add(tipoSeguro+", "+cobertura+", "+precio+": "+id_seguro);
            } while (c.moveToNext());
        }
        bd.close();
        sqlt.close();
        return array;
    }
}
