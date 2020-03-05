package com.example.seguros;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AnadirSeguros extends AppCompatActivity {

    private EditText tipoSeguro, cobertura, precio;
    private Button botonAnadirSeguro;
    public SQLiteDatabase sql;
    public BaseDatosVictorPrueba bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_seguro);
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
            //Abrimos coneciones:
            bd = new BaseDatosVictorPrueba(this, BaseDatosVictorPrueba.db_nombre, null, BaseDatosVictorPrueba.db_version);
            //Ahora indicamos que abra la base de datos en modo lectura y escritura
            sql = bd.getWritableDatabase();


            //Este double lo meto después de comprobar si están vacíos los campos, dado que como le dé al botón
            //de insertar seguro, estando vacío este campo, nos da un error.
            double numero = Double.parseDouble(precio.getText().toString());

            //Cremos un objeto de tipo ContentValues que le llamamos registro y le metemos todos los datos que queremos introducir
            //En la clase BASE DATOS tebemos un metodo que nos devuelve un content.
            ContentValues nuevo_seguro = bd.guardar_seguro(tipoDeSeguro, coberturaSeguro, numero);

            /*
            ContentValues nuevo_seguro= new ContentValues();
            nuevo_seguro.put(Bd_estructura_victor_prueba.tb1_column2, tipoDeSeguro);
            nuevo_seguro.put(Bd_estructura_victor_prueba.tb1_column3, coberturaSeguro);
            nuevo_seguro.put(Bd_estructura_victor_prueba.tb1_column4, numero);
            nuevo_seguro.put(Bd_estructura_victor_prueba.tb1_column5, 1);

             */

            //Llamamos al metodo que inserta el content en la base de datos.
            bd.insertar_valores(sql, Bd_estructura_victor_prueba.tb1,  nuevo_seguro);
            //sql.insert(Bd_estructura_victor_prueba.tb4, null, registronuevo);

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
