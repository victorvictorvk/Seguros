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

public class AnadirSeguros extends AppCompatActivity {

    private EditText tipoSeguro, cobertura, precio;
    private Button botonAnadirSeguro;
    public SQLiteDatabase sql;
    public BaseDatosVVS bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_seguro);
        SharedPreferences prefs = getSharedPreferences("fondoAplicaciones", Context.MODE_PRIVATE);
        ConstraintLayout fondo = (ConstraintLayout) findViewById(R.id.fondo);
        MainActivity main = new MainActivity();
        main.establecerFondo(fondo, prefs);
        tipoSeguro = (EditText) findViewById(R.id.editTextTipoSeguro);
        cobertura = (EditText) findViewById(R.id.editTextCobertura);
        precio = (EditText) findViewById(R.id.editTextPrecio);
        botonAnadirSeguro = (Button)findViewById(R.id.buttonAnadirSeguro);
    }

    public void anadirSeguro(View v)
    {

        String tipoDeSeguro = tipoSeguro.getText().toString();
        String coberturaSeguro = cobertura.getText().toString();

        if(!tipoDeSeguro.isEmpty() && !coberturaSeguro.isEmpty() && !precio.getText().toString().isEmpty())
        {
            //Abrimos conexiones:
            bd = new BaseDatosVVS(this, BaseDatosVVS.db_nombre, null, BaseDatosVVS.db_version);
            //Ahora indicamos que abra la base de datos en modo lectura y escritura
            sql = bd.getWritableDatabase();


            //Este double lo meto después de comprobar si están vacíos los campos, dado que como le dé al botón
            //de insertar seguro, estando vacío este campo, nos da un error.
            double numero = Double.parseDouble(precio.getText().toString());

            //Cremos un objeto de tipo ContentValues que le llamamos registro y le metemos todos los datos que queremos introducir
            //En la clase BASE DATOS tebemos un metodo que nos devuelve un content.
            ContentValues nuevo_seguro = bd.guardar_seguro(tipoDeSeguro, coberturaSeguro, numero);

            bd.insertar_valores(sql, Bd_estructura_VVS.tb1,  nuevo_seguro);
            //sql.insert(Bd_estructura_VVS.tb4, null, registronuevo);

            tipoSeguro.setText("");
            cobertura.setText("");
            precio.setText("");

            sql.close();
            bd.close();

            Toast.makeText(this, "El registro se añadió", Toast.LENGTH_SHORT).show();

        } else
        {
            Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}
