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
import android.widget.Toast;

public class Datos_Seguros extends AppCompatActivity {
    public SQLiteDatabase sql;
    public BaseDatosVVS bd;
    EditText edTipoSeguro, etCobertura,edPrecio;
    String idSeguro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos__seguros);
        SharedPreferences prefs = getSharedPreferences("fondoAplicaciones", Context.MODE_PRIVATE);
        ConstraintLayout fondo = (ConstraintLayout) findViewById(R.id.fondo);
        MainActivity main = new MainActivity();
        main.establecerFondo(fondo, prefs);
        bd = new BaseDatosVVS(this, BaseDatosVVS.db_nombre, null, BaseDatosVVS.db_version);
        //Ahora indicamos que esa apertura se en modo lectura y escritura
        sql = bd.getWritableDatabase();

        //Obtenemos los datos
        // del comercial para poder trabajar con sus datos
        Bundle bundle = getIntent().getExtras();
        //Este lo necesitaremos para hacer las consultas
        idSeguro = bundle.getString("idSeguro");
        String tipoSeguro = bundle.getString("tipoSeguro");
        String coberturaSeguro = bundle.getString("coberturaSeguro");
        String precio = bundle.getString("precioSeguro");
        edTipoSeguro = (EditText) findViewById(R.id.editTextTipoSeguro);
        etCobertura = (EditText) findViewById(R.id.editTextCobertura);
        edPrecio = (EditText) findViewById(R.id.editTextprecio);
        edTipoSeguro.setText(tipoSeguro);
        etCobertura.setText(coberturaSeguro);
        edPrecio.setText(precio);
        bd.close();
        sql.close();
    }

    public void modificarDatos(View v)
    {
        bd = new BaseDatosVVS(this, BaseDatosVVS.db_nombre, null, BaseDatosVVS.db_version);
        sql = bd.getWritableDatabase();
        String tipoDeSeguro = edTipoSeguro.getText().toString();
        String coberturaSeguro = etCobertura.getText().toString();
        if(!tipoDeSeguro.isEmpty() && !coberturaSeguro.isEmpty() && !edPrecio.getText().toString().isEmpty()) {
            ContentValues valores = new ContentValues();
            valores.put(Bd_estructura_VVS.tb1_column2, edTipoSeguro.getText().toString());
            valores.put(Bd_estructura_VVS.tb1_column3, etCobertura.getText().toString());
            valores.put(Bd_estructura_VVS.tb1_column4, edPrecio.getText().toString());

            sql.update(Bd_estructura_VVS.tb1, valores, Bd_estructura_VVS.tb1_column1 + " = " + idSeguro, null);

            bd.close();
            sql.close();
            Toast.makeText(this, "El registro se modificó", Toast.LENGTH_SHORT).show();
        } else
            {
                Toast.makeText(this, "Para modificar, rellena todos los campos", Toast.LENGTH_SHORT).show();
            }
    }

    public void eliminarDatos(View v)
    {
        bd = new BaseDatosVVS(this, BaseDatosVVS.db_nombre, null, BaseDatosVVS.db_version);
        sql = bd.getWritableDatabase();
        //Comprobamos si este seguro está estaAsociado a alguna poliza

        boolean asociado = bd.estaAsociado(sql, idSeguro, Bd_estructura_VVS.tb2_column1);
        if(!asociado) {
            ContentValues valores = new ContentValues();
            valores.put(Bd_estructura_VVS.tb1_column2, edTipoSeguro.getText().toString());
            valores.put(Bd_estructura_VVS.tb1_column3, etCobertura.getText().toString());
            valores.put(Bd_estructura_VVS.tb1_column4, edPrecio.getText().toString());
            valores.put(Bd_estructura_VVS.tb1_column5, 0);

            sql.update(Bd_estructura_VVS.tb1, valores, Bd_estructura_VVS.tb1_column1 + " = " + idSeguro, null);

            bd.close();
            sql.close();
            Toast.makeText(this, "El registro se eliminó", Toast.LENGTH_SHORT).show();
            edTipoSeguro.setText("");
            etCobertura.setText("");
            edPrecio.setText("");
            finish();
        } else
        {
            Toast.makeText(this, "No se puede eliminar porque dicho seguro está asociado a una póliza", Toast.LENGTH_SHORT).show();

        }
    }
}
