package com.example.seguros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;


public class Comercial extends AppCompatActivity {
    AutoCompleteTextView editText;
    LinearLayout linearlayoutScroll;
    //Creamos una variable estática para que se pueda acceder desde varias clases.
    static String dniComercial, dni_cliente_elegido;
    public SQLiteDatabase sql;
    public BaseDatosVictorPrueba bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comercial);
        SharedPreferences prefs = getSharedPreferences("fondoAplicaciones", Context.MODE_PRIVATE);
       ConstraintLayout fondo = (ConstraintLayout) findViewById(R.id.fondo);
        MainActivity main = new MainActivity();
        main.establecerFondo(fondo, prefs);
        //Obtenemos el nombre del comercial para poder trabajar con sus datos
        Bundle bundle = getIntent().getExtras();
        String comercial = bundle.getString("comercial");
        dniComercial = comercial;

        //Obetnemos el linearLayOut de nuestro ScrollView que será dinámico
        linearlayoutScroll = (LinearLayout) findViewById(R.id.linearLayOutScroll);

        ImageView flecha = (ImageView)findViewById(R.id.flecha);
    }

    public void cambiarActividadDatosCliente(String dni, String nombre, String ape1, String ape2)
    {
        Intent intento = new Intent(this, Cliente.class);
        intento.putExtra("dniCliente", dni);
        intento.putExtra("nombreCliente", nombre);
        intento.putExtra("ape1Cliente", ape1);
        intento.putExtra("ape2Cliente", ape2);
        startActivity(intento);
        overridePendingTransition (0,0);

    }
    //Este método hace que se despligue el autocompletTExtview
    public void pulsarFlecha(View v)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.autocompletetv_personal_de_victor,R.id.autoCompleteItem, dameArray());
        editText.setThreshold(1);//Esto es para que empiece a buscar por 1 caracter
        editText.setAdapter(adapter);

        editText.showDropDown();
    }

    public ArrayList<String> dameArray()
    {
        /*
        Tenemos que hacer una consulta a la base de datos
        */
        bd = new BaseDatosVictorPrueba(this, BaseDatosVictorPrueba.db_nombre, null, BaseDatosVictorPrueba.db_version);
        //Ahora indicamos que abra la base de datos en modo lectura y escritura
        sql = bd.getWritableDatabase();
        ArrayList<String> array = new ArrayList<String>();

        //Aqui hacemos una consulta a la base de datos.
        Cursor c = bd.listaClientes(sql, dniComercial);
        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
               String nombre = c.getString(0);
                String ape1 = c.getString(1);

                String ape2 = c.getString(2);
                String nif_vendedor = c.getString(3);
                array.add(nombre+", " + ape1+ ", " + ape2+": "+nif_vendedor);
            } while (c.moveToNext());
        }

        bd.close();
        sql.close();
        return array;

    }

    public void mostrarTodosClientes(View v)
    {
        Intent intento = new Intent(this, TodosClientes.class);
        startActivity(intento);
    }

    public void pasarActividadAnadirCliente(View v)
    {

        Intent intento = new Intent(this, Anadir_clientes.class);
        startActivity(intento);
        overridePendingTransition (0,0);

    }

    @Override
    public void onResume() {
        super.onResume();
        editText = findViewById(R.id.autoCompleteTextView99);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.autocompletetv_personal_de_victor,R.id.autoCompleteItem, dameArray());
        editText.setThreshold(1);//Esto es para que empiece a buscar por 1 caracter


        editText.setAdapter(adapter);

        //A este autocompletTV le añadimos un escuchador, el cual cambiará de actividad cuando hagamos clic en algun elemento
        editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Esta sentencia me devuelve el string seleccionado
                //String dniComercial = parent.getItemAtPosition(position).toString();

                String fila =  parent.getItemAtPosition(position).toString();

                String dnif = null;
                String nombref = null;
                String ape1f = null;
                String ape2f = null;

                String[] parts = fila.split(": ");
                String primera = parts[0];
                String parte0[] = primera.split(", ");
                nombref = parte0[0];
                ape1f = parte0[1];
                ape2f = parte0[2];
                dnif = parts[1];
                dni_cliente_elegido = dnif;
                cambiarActividadDatosCliente(dni_cliente_elegido, nombref, ape1f, ape2f);
            }
        });

    }
}
