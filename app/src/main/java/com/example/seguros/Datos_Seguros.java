package com.example.seguros;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;

public class Datos_Seguros extends AppCompatActivity {
    public SQLiteDatabase sql;
    public BaseDatosVictorPrueba bd;
    EditText edTipoSeguro, etCobertura,edPrecio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos__seguros);

        bd = new BaseDatosVictorPrueba(this, BaseDatosVictorPrueba.db_nombre, null, BaseDatosVictorPrueba.db_version);
        //Ahora indicamos que esa apertura se en modo lectura y escritura
        sql = bd.getWritableDatabase();

        //Obtenemos los datos
        // del comercial para poder trabajar con sus datos
        Bundle bundle = getIntent().getExtras();

        //Este lo necesitaremos para hacer las consultas
        String idSeguro = bundle.getString("idSeguro");


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
}
