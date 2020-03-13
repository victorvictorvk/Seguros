package com.example.seguros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ContratarSeguro extends AppCompatActivity {
    ListaSeguros claseListaSeguros;

    AutoCompleteTextView autoCompleteTVListaSegurosCliente;
    TextView tvDatosCliente;
    EditText eDNumeroRiesgo, eDComentario, eDDescuento;
    String numeroRiesgo, descuento, comentario, idSeguro, precioSeguro;
    ImageView  flechaSeguros;
    public SQLiteDatabase sql;
    public BaseDatosVictorPrueba bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contratar_seguro);

        tvDatosCliente =(TextView) findViewById(R.id.textViewDatosCliente);
        eDNumeroRiesgo = (EditText) findViewById(R.id.editTextNumeroRiesgo);
        eDComentario = (EditText) findViewById(R.id.editTextComentario);
        eDDescuento = (EditText) findViewById(R.id.editTextDescuentoContrato);
        comentario = eDComentario.getText().toString();
        numeroRiesgo = eDNumeroRiesgo.getText().toString();
        descuento = eDDescuento.getText().toString();
        flechaSeguros = (ImageView) findViewById(R.id.flechaSeguros);


        autoCompleteTVListaSegurosCliente = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView99);

        bd = new BaseDatosVictorPrueba(this, BaseDatosVictorPrueba.db_nombre, null, BaseDatosVictorPrueba.db_version);
        //Ahora indicamos que abra la base de datos en modo lectura y escritura
        sql = bd.getWritableDatabase();

        claseListaSeguros = new ListaSeguros(ListaSeguros.context, sql, bd);

        crearAdaptadorSeguros();

    }

    //Cuando pulsamos el botón para hacer la inserción en la BD
    public void contratarSeguro(View v)
    {

            //Abrimos conexiones:
            bd = new BaseDatosVictorPrueba(this, BaseDatosVictorPrueba.db_nombre, null, BaseDatosVictorPrueba.db_version);
            //Ahora indicamos que abra la base de datos en modo lectura y escritura
            sql = bd.getWritableDatabase();
// guardar_poliza(int idSeguro, int idCliente, int riesgo, String comentario, int descuento, Double precio, String nifVendedor)

        /*
            int precioSeguroint = Integer.valueOf(precioSeguro);
            //int descuentoint = (Integer.valueOf(descuento.trim())*precioSeguroint)/100;
        double descuentoint = (Double.valueOf(descuento)*precioSeguroint)/100;

        double numeroriesgoint = 1+ (Double.valueOf(numeroRiesgo)/10);



            String precioPersonal = String.valueOf((precioSeguroint*numeroriesgoint)-descuentoint);

         */
           // ContentValues nueva_poliza = bd.guardar_poliza();

        ContentValues nueva_poliza = bd.guardar_poliza(idSeguro, Comercial.dni_cliente_elegido, numeroRiesgo, comentario, descuento,  precioSeguro, Comercial.dniComercial);
            bd.insertar_valores(sql, Bd_estructura_victor_prueba.tb2,  nueva_poliza);
            Toast.makeText(this, "El registro se añadió", Toast.LENGTH_SHORT).show();

            bd.close();
            sql.close();

    }


    private void crearAdaptadorSeguros() {


        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                R.layout.autocompletetv_personal_de_victor,R.id.autoCompleteItem, claseListaSeguros.listaSeguros());
        autoCompleteTVListaSegurosCliente.setThreshold(1);//Esto es para que empiece a buscar por 1 caracter
        autoCompleteTVListaSegurosCliente.setAdapter(adapter2);

        autoCompleteTVListaSegurosCliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fila =  parent.getItemAtPosition(position).toString();

                String[] parts = fila.split(": ");
                String primera = parts[0];
                String parte0[] = primera.split(", ");
                precioSeguro = parte0[2];
                idSeguro = parts[1];
            }
        });
    }
    public void desplegarListaSeguros(View v) {
        ListaSeguros claseListaSeguros;

        claseListaSeguros = new ListaSeguros(ListaSeguros.context, sql, bd);

        //Creamos un adapter para poder mostrar el nombre de los comerciales e incluirlo en el desplegable
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.autocompletetv_personal_de_victor,R.id.autoCompleteItem, claseListaSeguros.listaSeguros());
        autoCompleteTVListaSegurosCliente.setThreshold(1);//Esto es para que empiece a buscar por 1 caracter
        autoCompleteTVListaSegurosCliente.setAdapter(adapter);
        autoCompleteTVListaSegurosCliente.showDropDown();
    }
}
