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
    String  comentario, idSeguro;
    static String precioSeguro_escogido_crear_poliza;
    double numeroRiesgo,descuento;
    ImageView  flechaSeguros;
    public SQLiteDatabase sql;
    public BaseDatosVictorPrueba bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contratar_seguro);

        tvDatosCliente =(TextView) findViewById(R.id.textViewDatosCliente);
        eDNumeroRiesgo = (EditText) findViewById(R.id.editTextNumeroRiesgoContratar);
        eDComentario = (EditText) findViewById(R.id.editTextComentario);
        eDDescuento = (EditText) findViewById(R.id.editTextDescuentoContrato);

        flechaSeguros = (ImageView) findViewById(R.id.flechaSeguros);


        autoCompleteTVListaSegurosCliente = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView99);

        bd = new BaseDatosVictorPrueba(this, BaseDatosVictorPrueba.db_nombre, null, BaseDatosVictorPrueba.db_version);
        //Ahora indicamos que abra la base de datos en modo lectura y escritura
        sql = bd.getWritableDatabase();

        claseListaSeguros = new ListaSeguros(ListaSeguros.context, sql, bd);

        crearAdaptadorSeguros();
        bd.close();
        sql.close();


    }

    //Cuando pulsamos el botón para hacer la inserción en la BD
    public void contratarSeguro(View v)
    {
        //Importante recoger aquí los datos!!! que si no, no los lee
            comentario = eDComentario.getText().toString();
            numeroRiesgo = Double.valueOf(eDNumeroRiesgo.getText().toString());
            descuento = Double.valueOf(eDDescuento.getText().toString());

            bd = new BaseDatosVictorPrueba(this, BaseDatosVictorPrueba.db_nombre, null, BaseDatosVictorPrueba.db_version);
            sql = bd.getWritableDatabase();
            double precioSeguroCalcular = Double.valueOf(precioSeguro_escogido_crear_poliza);


             double numeroRiesgoCalcular = 1+((numeroRiesgo)/10);
            double descuentoCalcular = (descuento*precioSeguroCalcular)/100;

            String precioPersonal = String.valueOf((numeroRiesgoCalcular*precioSeguroCalcular)-descuentoCalcular);


           // ContentValues nueva_poliza = bd.guardar_poliza();
// guardar_poliza(int idSeguro, int idCliente, int riesgo, String comentario, int descuento, Double precio, String nifVendedor)

        ContentValues nueva_poliza = bd.guardar_poliza(idSeguro, Comercial.dni_cliente_elegido, String.valueOf(numeroRiesgo), comentario, String.valueOf(descuento),  precioPersonal, Comercial.dniComercial);
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
                precioSeguro_escogido_crear_poliza = parte0[2];
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
