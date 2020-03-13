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
import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity {
    AutoCompleteTextView aCTtVListaComerciales, autoCompleteTVListaSeguros;
    ImageView flechaComerciales, flechaSeguros;
    public SQLiteDatabase sql;
    public BaseDatosVictorPrueba bd;
    String dni, nombre, ape1, ape2;
    ArrayList<String> arrayMostrarComerciales;
    ArrayList<ArrayList<String>> arrayComerciales, arraySeguros;
    ListaSeguros claseListaSeguros;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        crearAdaptadorComercialesChapuza();
        bd = new BaseDatosVictorPrueba(this, BaseDatosVictorPrueba.db_nombre, null, BaseDatosVictorPrueba.db_version);
        //Ahora indicamos que abra la base de datos en modo lectura y escritura
        sql = bd.getWritableDatabase();
        claseListaSeguros = new ListaSeguros(ListaSeguros.context, sql, bd);
        crearAdaptadorSeguros();

        flechaComerciales = (ImageView)findViewById(R.id.flechaComerciales);
        flechaSeguros = (ImageView) findViewById(R.id.flechaSeguros);
    }

    private void crearAdaptadorComercialesChapuza() {
        aCTtVListaComerciales = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewListaComerciales);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.autocompletetv_personal_de_victor,R.id.autoCompleteItem, arrayChapucero());
        aCTtVListaComerciales.setThreshold(1);//Esto es para que empiece a buscar por 1 caracter
        aCTtVListaComerciales.setAdapter(adapter);

        //A este autocompletTV le añadimos un escuchador, el cual cambiará de actividad cuando hagamos clic en algun elemento
        aCTtVListaComerciales.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
                cambiarActividadComercial(dnif, nombref, ape1f, ape2f);
            }
        });
    }
    private void crearAdaptadorSeguros() {
        autoCompleteTVListaSeguros = (AutoCompleteTextView) findViewById(R.id.autoCompleteTVListaSeguros);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                R.layout.autocompletetv_personal_de_victor,R.id.autoCompleteItem, claseListaSeguros.listaSeguros());
        autoCompleteTVListaSeguros.setThreshold(1);//Esto es para que empiece a buscar por 1 caracter
        autoCompleteTVListaSeguros.setAdapter(adapter2);

        autoCompleteTVListaSeguros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fila =  parent.getItemAtPosition(position).toString();
                String idR = null;
                String tipoR = null;
                String cobR = null;
                String precioR = null;
                String[] parts = fila.split(": ");
                String primera = parts[0];
                String parte0[] = primera.split(", ");
                tipoR = parte0[0];
                cobR = parte0[1];
                precioR = parte0[2];
                idR = parts[1];
                cambiarActividadDatosSeguro(idR, tipoR, cobR, precioR);
            }
        });
    }

    private void crearAdaptadorComerciales() {
        aCTtVListaComerciales = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewListaComerciales);
        ArrayAdapter<ArrayList<String>> adapter = new ArrayAdapter<>(this,
                R.layout.autocompletetv_personal_de_victor,R.id.autoCompleteItem, listaComerciales());
        aCTtVListaComerciales.setThreshold(1);//Esto es para que empiece a buscar por 1 caracter
        aCTtVListaComerciales.setAdapter(adapter);

        //A este autocompletTV le añadimos un escuchador, el cual cambiará de actividad cuando hagamos clic en algun elemento
        aCTtVListaComerciales.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Esta sentencia me devuelve el string seleccionado
                //String dniComercial = parent.getItemAtPosition(position).toString();

                ArrayList<String> fila = (ArrayList<String>) parent.getItemAtPosition(position);
                //Obtengo la posicion
                String dnif = null;
                String nombref = null;
                String ape1f = null;
                String ape2f = null;

                for (int i = 0; i < fila.size(); i++)
                {
                    dnif = fila.get(0);
                    nombref = fila.get(1);
                    ape1f = fila.get(2);
                    ape2f = fila.get(3);

                }
                cambiarActividadComercial(dnif, nombref, ape1f, ape2f);
            }
        });
    }

    public void cambiarActividadComercial(String dni,String nombre, String ape1,String ape2)
    {
        Intent intento = new Intent(this, Datos_comercial.class);
        intento.putExtra("dniComercial", dni);
        intento.putExtra("nombreComercial", nombre);
        intento.putExtra("ape1Comercial", ape1);
        intento.putExtra("ape2Comercial", ape2);
        startActivity(intento);
    }

    public void cambiarActividadDatosSeguro(String idSeguro, String tipoS, String cobS, String precio) {

        Intent intento = new Intent(this, Datos_Seguros.class);
        intento.putExtra("idSeguro", idSeguro);

        intento.putExtra("tipoSeguro", tipoS);
        intento.putExtra("coberturaSeguro", cobS);
        intento.putExtra("precioSeguro", precio);
        startActivity(intento);
    }

    public void desplegarListaComercialesChapuza(View v)
    {
        //Creamos un adapter para poder mostrar el nombre de los comerciales e incluirlo en el desplegable
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.autocompletetv_personal_de_victor,R.id.autoCompleteItem, arrayChapucero());
        aCTtVListaComerciales.setThreshold(1);//Esto es para que empiece a buscar por 1 caracter
        aCTtVListaComerciales.setAdapter(adapter);
        aCTtVListaComerciales.showDropDown();
    }
    public void desplegarListaComerciales(View v)
    {
        //Creamos un adapter para poder mostrar el nombre de los comerciales e incluirlo en el desplegable
        ArrayAdapter<ArrayList<String>> adapter = new ArrayAdapter<>(this,
                R.layout.autocompletetv_personal_de_victor,R.id.autoCompleteItem, listaComerciales());
        aCTtVListaComerciales.setThreshold(1);//Esto es para que empiece a buscar por 1 caracter
        aCTtVListaComerciales.setAdapter(adapter);
        aCTtVListaComerciales.showDropDown();
    }

    public ArrayList<ArrayList<String>> listaComerciales() {

       // Abrimos la base de datos
        bd = new BaseDatosVictorPrueba(this, BaseDatosVictorPrueba.db_nombre, null, BaseDatosVictorPrueba.db_version);
        //Ahora indicamos que esa apertura se en modo lectura y escritura
        sql = bd.getReadableDatabase();
        //Creamos el array vacío
        arrayMostrarComerciales = new ArrayList<String>();

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
                arrayMostrarComerciales.add(nombre+" " + ape1+ " " + ape2+ " - "+dni);
            } while (c.moveToNext());
        }
        arrayComerciales = new ArrayList();

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                List<String> row = new ArrayList<>();

                    row.add(c.getString(0));
                    row.add(c.getString(1));
                    row.add(c.getString(2));
                    row.add(c.getString(3));

                arrayComerciales.add((ArrayList<String>) row);
            } while (c.moveToNext());
        }
        bd.close();
        sql.close();
        return arrayComerciales;
    }

    public ArrayList<String> arrayChapucero() {
        bd = new BaseDatosVictorPrueba(this, BaseDatosVictorPrueba.db_nombre, null, BaseDatosVictorPrueba.db_version);
        sql = bd.getReadableDatabase();
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
                array.add(nombre+", " + ape1+ ", " + ape2+ ": "+dni);
            } while (c.moveToNext());
        }

        bd.close();
        sql.close();
        return array;
    }

    public void desplegarListaSeguros(View v) {

        //Creamos un adapter para poder mostrar el nombre de los comerciales e incluirlo en el desplegable
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.autocompletetv_personal_de_victor,R.id.autoCompleteItem, listaSeguros());
        autoCompleteTVListaSeguros.setThreshold(1);//Esto es para que empiece a buscar por 1 caracter
        autoCompleteTVListaSeguros.setAdapter(adapter);
        autoCompleteTVListaSeguros.showDropDown();
    }
    /*
    tipoSeguro = c.getString(0);
    coberturaSeguro = c.getString(1);
    precioSeguro = c.getString(2);
    idSeguro = c.getString(3);


     */
    public ArrayList<String> listaSeguros() {

        bd = new BaseDatosVictorPrueba(this, BaseDatosVictorPrueba.db_nombre, null, BaseDatosVictorPrueba.db_version);
        sql = bd.getReadableDatabase();
        ArrayList<String> array = new ArrayList<>();
        //Aqui hacemos una consulta a la base de datos.
        Cursor c = bd.listaSeguros(sql);
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
        sql.close();
        return array;
    }


    public void verAnadirComerciales(View v)
    {
        Intent intento = new Intent(this, AnadirComercial.class);
        //intento.putExtra("comercial", idCliente.getText().toString());
        startActivity(intento);
    }

    public void verAnadirSeguros(View v)
    {
        Intent intento = new Intent(this, AnadirSeguros.class);
        //intento.putExtra("comercial", idCliente.getText().toString());
        startActivity(intento);
    }
}
