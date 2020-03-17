package com.example.seguros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Datos_comercial extends AppCompatActivity {
    public SQLiteDatabase sql;
    public BaseDatosVVS bd;
    EditText edNombre, edApe1, edApe2;
    TextView edDNI;
    String dni, nombre, ape1, ape2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_comercial);
        SharedPreferences prefs = getSharedPreferences("fondoAplicaciones", Context.MODE_PRIVATE);
        ConstraintLayout fondo = (ConstraintLayout) findViewById(R.id.fondo);
        MainActivity main = new MainActivity();
        main.establecerFondo(fondo, prefs);
        //Super importante, lo primero  Abrimos la base de datos
        bd = new BaseDatosVVS(this, BaseDatosVVS.db_nombre, null, BaseDatosVVS.db_version);
        //Ahora indicamos que esa apertura se en modo lectura y escritura
        sql = bd.getWritableDatabase();

        //Obtenemos los datos
        // del comercial para poder trabajar con sus datos
        Bundle bundle = getIntent().getExtras();
         dni = bundle.getString("dniComercial");
         nombre = bundle.getString("nombreComercial");

         ape1 = bundle.getString("ape1Comercial");

         ape2 = bundle.getString("ape2Comercial");

        edDNI = (TextView) findViewById(R.id.editTextDNI);

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
    public void modificarDatos(View v)
    {
        String dni = edDNI.getText().toString();
        String nombre = edNombre.getText().toString();
        String apellido1 = edApe1.getText().toString();
        String apellido2 = edApe2.getText().toString();
        bd = new BaseDatosVVS(this, BaseDatosVVS.db_nombre, null, BaseDatosVVS.db_version);
        sql = bd.getWritableDatabase();

        if( !dni.isEmpty() && !nombre.isEmpty() && !apellido1.isEmpty() && !apellido2.isEmpty())
        {


        ContentValues valores = new ContentValues();
        valores.put(Bd_estructura_VVS.tb4_column2, edNombre.getText().toString());
        valores.put(Bd_estructura_VVS.tb4_column3, edApe1.getText().toString());
        valores.put(Bd_estructura_VVS.tb4_column4, edApe2.getText().toString());
        String[] argumentosParaActualizar = {dni};

            sql.update(Bd_estructura_VVS.tb4, valores,Bd_estructura_VVS.tb4_column1+" = "+ dni, null );

            // sql.delete(Bd_estructura_VVS.tb4, Bd_estructura_VVS.tb4_column1+" = ?", argumentosParaActualizar);
            bd.close();
            sql.close();
            Toast.makeText(this, "El registro se modificó", Toast.LENGTH_SHORT).show();

            } else
        {
            Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminarDatos(View v)
    {
        bd = new BaseDatosVVS(this, BaseDatosVVS.db_nombre, null, BaseDatosVVS.db_version);
        sql = bd.getWritableDatabase();
        //Tenemos que mirar en la tabla clientes si está activo algun cliente
        boolean asociado = bd.estaClienteActivo(sql, dni, Bd_estructura_VVS.tb3_column5, Bd_estructura_VVS.tb3_column6);
        if(!asociado) {
            ContentValues valores = new ContentValues();
            valores.put(Bd_estructura_VVS.tb4_column2, edNombre.getText().toString());
            valores.put(Bd_estructura_VVS.tb4_column3, edApe1.getText().toString());
            valores.put(Bd_estructura_VVS.tb4_column4, edApe2.getText().toString());
            //Inactivamos el comercial pero no lo borramos
            valores.put(Bd_estructura_VVS.tb4_column5, 0);
            sql.update(Bd_estructura_VVS.tb4, valores,Bd_estructura_VVS.tb4_column1+" = "+ dni, null );
            bd.close();
            sql.close();
            Toast.makeText(this, "El registro se eliminó", Toast.LENGTH_SHORT).show();
            finish();
            edNombre.setText("");
            edApe1.setText("");
            edApe2.setText("");
            edDNI.setText("");
        } else
        {
            Toast.makeText(this, "No se puede eliminar porque este comercial tiene clientes", Toast.LENGTH_SHORT).show();
        }
    }

}
