package com.example.seguros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Anadir_clientes extends AppCompatActivity {

    EditText EDdniCliente, EDnombreCliente, EDape1Cliente, EDape2Cliente;
    Button botonAnadirCliente;
    public SQLiteDatabase sql;
    public BaseDatosVictorPrueba bd;
    String dniComercial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_clientes);

         dniComercial = Comercial.dniComercial;

        EDdniCliente = (EditText) findViewById(R.id.editTextDNICliente);
        EDnombreCliente = (EditText) findViewById(R.id.editTextnombreCliente);
        EDape1Cliente = (EditText) findViewById(R.id.editTextApe1Cliente);
        EDape2Cliente = (EditText) findViewById(R.id.editTextApe2Cliente);
        botonAnadirCliente = (Button) findViewById(R.id.botonAnadirCliente);
    }

    public void anadirCliente(View v)
    {
        if(!EDdniCliente.getText().toString().isEmpty() && !EDnombreCliente.getText().toString().isEmpty()
        && !EDape1Cliente.getText().toString().isEmpty() && !EDape2Cliente.getText().toString().isEmpty()) {
            //Abrimos conexiones:
            bd = new BaseDatosVictorPrueba(this, BaseDatosVictorPrueba.db_nombre, null, BaseDatosVictorPrueba.db_version);
            //Ahora indicamos que abra la base de datos en modo lectura y escritura
            sql = bd.getWritableDatabase();

            //Recogemos los valores de los campos
            String dni = EDdniCliente.getText().toString();
            String nombre = EDnombreCliente.getText().toString();
            String ape1 = EDape1Cliente.getText().toString();
            String ape2 = EDape2Cliente.getText().toString();
            if( !bd.existeCliente(sql, dni)) {

                ContentValues nuevo_cliente = bd.guardar_cliente(dni, nombre, ape1, ape2, dniComercial);
                bd.insertar_valores(sql, Bd_estructura_victor_prueba.tb3, nuevo_cliente);

                EDdniCliente.setText("");
                EDnombreCliente.setText("");
                EDape1Cliente.setText("");
                EDape2Cliente.setText("");
                Toast.makeText(this, "El registro se añadió", Toast.LENGTH_SHORT).show();

                sql.close();
                bd.close();
            } else {
                Toast.makeText(this, "Este DNI ya existe! Elige otro!", Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();

        }
    }
}
