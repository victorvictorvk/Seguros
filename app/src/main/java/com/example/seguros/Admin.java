package com.example.seguros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class Admin extends AppCompatActivity {
    AutoCompleteTextView aCTtVListaComerciales;
    ImageView flechaComerciales, flechaSeguros;
    public SQLiteDatabase sql;
    public BaseDatosVictorPrueba bd;
    String dni, nombre, ape1, ape2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //Super importante, lo primero de todo Abrimos la base de datos
        bd = new BaseDatosVictorPrueba(this, BaseDatosVictorPrueba.db_nombre, null, BaseDatosVictorPrueba.db_version);
        //Ahora indicamos que esa apertura se en modo lectura y escritura
        sql = bd.getWritableDatabase();

        aCTtVListaComerciales = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewListaComerciales);
        //Creamos un adapter para poder mostrar el nombre de los comerciales e incluirlo en el desplegable
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.autocompletetv_personal_de_victor,R.id.autoCompleteItem, listaComerciales());
        aCTtVListaComerciales.setThreshold(1);//Esto es para que empiece a buscar por 1 caracter
        aCTtVListaComerciales.setAdapter(adapter);
        flechaComerciales = (ImageView)findViewById(R.id.flechaComerciales);

        //A este autocompletTV le añadimos un escuchador, el cual cambiará de actividad cuando hagamos clic en algun elemento
        aCTtVListaComerciales.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               //Seleccionamos el nombre del comercial
                //String dniComercial = parent.getItemAtPosition(position).toString();

                cambiarActividadComercial(dni);
            }
        });
    }

    public void cambiarActividadComercial(String dni)
    {
        Intent intento = new Intent(this, Datos_comercial.class);
        intento.putExtra("dniComercial", dni);
        intento.putExtra("nombreComercial", nombre);

        intento.putExtra("ape1Comercial", ape1);

        intento.putExtra("ape2Comercial", ape2);

        startActivity(intento);
    }

    public void desplegarListaComerciales(View v)
    {
        aCTtVListaComerciales.showDropDown();
    }

    public ArrayList<String> listaComerciales() {
        //Creamos el array vacío
        ArrayList<String> array = new ArrayList<String>();

        //Aqui hacemos una consulta a la base de datos.
        Cursor c = bd.listaComerciales(sql);
        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                //Estos datos serán globales en toda la clase puesto que es con el que trabajaremos
                dni = c.getString(0);
                 nombre = c.getString(1);
                 ape1 = c.getString(2);

                 ape2 = c.getString(3);
                array.add(nombre+" " + ape1+ " " + ape2+ " - "+dni);
            } while (c.moveToNext());
        }
        return array;
    }

    public void verAnadirComerciales(View v)
    {
        Intent intento = new Intent(this, AnadirComercial.class);
        //intento.putExtra("comercial", idCliente.getText().toString());
        startActivity(intento);
    }
}
