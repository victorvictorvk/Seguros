package com.example.seguros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Datos_comercial extends AppCompatActivity {
    public SQLiteDatabase sql;
    public BaseDatosVictorPrueba bd;
    EditText edDNI, edNombre, edApe1, edApe2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_comercial);
        SharedPreferences prefs = getSharedPreferences("fondoAplicaciones", Context.MODE_PRIVATE);
        ConstraintLayout fondo = (ConstraintLayout) findViewById(R.id.fondo);
        MainActivity main = new MainActivity();
        main.establecerFondo(fondo, prefs);
        //Super importante, lo primero  Abrimos la base de datos
        bd = new BaseDatosVictorPrueba(this, BaseDatosVictorPrueba.db_nombre, null, BaseDatosVictorPrueba.db_version);
        //Ahora indicamos que esa apertura se en modo lectura y escritura
        sql = bd.getWritableDatabase();

        //Obtenemos los datos
        // del comercial para poder trabajar con sus datos
        Bundle bundle = getIntent().getExtras();
        String dni = bundle.getString("dniComercial");
        String nombre = bundle.getString("nombreComercial");

        String ape1 = bundle.getString("ape1Comercial");

        String ape2 = bundle.getString("ape2Comercial");

        edDNI = (EditText) findViewById(R.id.editTextDNI);

        edNombre = (EditText) findViewById(R.id.editTextNombre);
        edApe1 = (EditText) findViewById(R.id.editTextApe1);
        edApe2 = (EditText) findViewById(R.id.editTextApe2);

        edNombre.setText(nombre);
        edApe1.setText(ape1);
        edApe2.setText(ape2);
        edDNI.setText(dni);

        bd.close();
        sql.close();


    }
}
