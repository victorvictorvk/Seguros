package com.example.seguros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AnadirComercial extends AppCompatActivity {

    private EditText dniComercial, nombreComercial, ape1Comercial, ape2Comercial, pass;
    private Button botonAnadirComercial;
    public SQLiteDatabase sql;
    public BaseDatosVVS bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_comercial);
        SharedPreferences prefs = getSharedPreferences("fondoAplicaciones", Context.MODE_PRIVATE);
        ConstraintLayout fondo = (ConstraintLayout) findViewById(R.id.fondo);
        MainActivity main = new MainActivity();
        main.establecerFondo(fondo, prefs);
        dniComercial = (EditText) findViewById(R.id.editTextDNI);
        nombreComercial = (EditText) findViewById(R.id.editTextNombreComercial);
        ape1Comercial = (EditText) findViewById(R.id.editTextApe1Comercial);
        ape2Comercial = (EditText) findViewById(R.id.editTextApe2Comercial);
        pass = (EditText) findViewById(R.id.editTextPass);
        botonAnadirComercial = (Button)findViewById(R.id.botonAnadirComercial);

        /*
        //Abrimos la base de datos
        bd = new BaseDatosVVS(this, BaseDatosVVS.db_nombre, null, BaseDatosVVS.db_version);
        //Ahora indicamos que abra la base de datos en modo lectura y escritura

        sql = bd.getWritableDatabase();

         */
    }

    public void anadirComercial(View v)
    {
            String dni = dniComercial.getText().toString();
            String nombre = nombreComercial.getText().toString();
            String apellido1 = ape1Comercial.getText().toString();
            String apellido2 = ape2Comercial.getText().toString();
            String passComercial  = pass.getText().toString();

            if( !dni.isEmpty() && !nombre.isEmpty() && !apellido1.isEmpty() && !apellido2.isEmpty())
            {
                //Cremos un objeto de tipo ContentValues que le llamamos registro y le metemos todos los datos que queremos introducir
                //En la clase BASE DATOS tebemos un metodo que nos devuelve un content.
                //ContentValues registro = bd.guardar_vendedor(dni, nombre, apellido1, apellido2, passComercial);

                bd = new BaseDatosVVS(this, BaseDatosVVS.db_nombre, null, BaseDatosVVS.db_version);
                //Ahora indicamos que abra la base de datos en modo lectura y escritura
                sql = bd.getWritableDatabase();
                if(!bd.existeComercial(sql, dni))
                {
                ContentValues registronuevo  = new ContentValues();
                registronuevo.put(Bd_estructura_VVS.tb4_column1, dni);
                registronuevo.put(Bd_estructura_VVS.tb4_column2, nombre);
                registronuevo.put(Bd_estructura_VVS.tb4_column3, apellido1);
                registronuevo.put(Bd_estructura_VVS.tb4_column4, apellido2);
                registronuevo.put(Bd_estructura_VVS.tb4_column6, passComercial);

                registronuevo.put(Bd_estructura_VVS.tb4_column5, 1);
                sql.insert(Bd_estructura_VVS.tb4, null, registronuevo);

                dniComercial.setText("");
                nombreComercial.setText("");
                ape1Comercial.setText("");
                ape2Comercial.setText("");

                sql.close();
                bd.close();

                Toast.makeText(this, "El registro se añadió", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Este DNI ya existe! Elige otro!", Toast.LENGTH_SHORT).show();
                }
            } else
            {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
            }
    }

        public void anadirComercialV2(View v)
        {
            String dni = dniComercial.getText().toString();
            String nombre = nombreComercial.getText().toString();
            String apellido1 = ape1Comercial.getText().toString();
            String apellido2 = ape2Comercial.getText().toString();
            String passComercial  = pass.getText().toString();

            if(!dni.isEmpty() && !nombre.isEmpty() && !apellido1.isEmpty() && !apellido2.isEmpty())
            {
                //Cremos un objeto de tipo ContentValues que le llamamos registro y le metemos todos los datos que queremos introducir
                //En la clase BASE DATOS tebemos un metodo que nos devuelve un content.
                ContentValues registronuevo = bd.guardar_vendedor(dni, nombre, apellido1, apellido2, passComercial);
                //registronuevo.put(Bd_estructura_VVS.tb4_column5, 1);
                //Llamamos al metodo que inserta el content en la base de datos.
                bd.insertar_valores(sql, Bd_estructura_VVS.tb4,  registronuevo);
                //sql.insert(Bd_estructura_VVS.tb4, null, registronuevo);
                sql.close();
                dniComercial.setText("");
                nombreComercial.setText("");
                ape1Comercial.setText("");
                ape2Comercial.setText("");

                Toast.makeText(this, "El registro se añadió", Toast.LENGTH_SHORT).show();

            } else
            {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
            }
        }
}
