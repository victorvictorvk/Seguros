package com.example.seguros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import java.util.ArrayList;

public class Admin extends AppCompatActivity {
    AutoCompleteTextView aCTtVListaComerciales;
    ImageView flechaComerciales, flechaSeguros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        /*
        aCTtVListaComerciales = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewListaComerciales);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.autocompletetv_personal_de_victor,R.id.autoCompleteItem, listaComerciales());
        aCTtVListaComerciales.setThreshold(1);//Esto es para que empiece a buscar por 1 caracter
        aCTtVListaComerciales.setAdapter(adapter);

        flechaComerciales = (ImageView)findViewById(R.id.flechaComerciales);

         */
    }

    public void desplegarListaComerciales(View v)
    {
        aCTtVListaComerciales.showDropDown();
    }

    public ArrayList listaComerciales() {
        //Creamos el array vacío
        ArrayList<String> array = new ArrayList<>();
        BaseDatosVictorPrueba bd = new BaseDatosVictorPrueba(this, "baseDatosVictorPrueba", null, 1);

        Cursor c = bd.listaComerciales();

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                String nombre = c.getString(0);
                array.add(nombre);
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
