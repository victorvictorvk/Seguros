package com.example.seguros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seguros.Entidades.Seguro;

import java.util.ArrayList;
import java.util.Date;


public class ContratarSeguro extends AppCompatActivity {
    ListaSeguros claseListaSeguros;
    TextView tvDatosCliente;
    EditText eDNumeroRiesgo, eDComentario, eDDescuento;
    String  comentario, idSeguro;
    static String precioSeguro_escogido_crear_poliza;
    double numeroRiesgo,descuento;
    ImageView  flechaSeguros;
    public SQLiteDatabase sql;
    public BaseDatosVictorPrueba bd;
    ArrayList<String> listaSeguros;
    ArrayList<Seguro> segurosList;
    Spinner comboSeguros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contratar_seguro);
        SharedPreferences prefs = getSharedPreferences("fondoAplicaciones", Context.MODE_PRIVATE);
        ConstraintLayout fondo = (ConstraintLayout) findViewById(R.id.fondo);
        MainActivity main = new MainActivity();
        main.establecerFondo(fondo, prefs);
        tvDatosCliente =(TextView) findViewById(R.id.textViewDatosCliente);
        eDNumeroRiesgo = (EditText) findViewById(R.id.editTextNumeroRiesgoContratar);
        eDComentario = (EditText) findViewById(R.id.editTextComentario);
        eDDescuento = (EditText) findViewById(R.id.editTextDescuentoContrato);
        comboSeguros =(Spinner) findViewById(R.id.spinnerSeguros);

        flechaSeguros = (ImageView) findViewById(R.id.flechaSeguros);

        consultarListaSeguros();
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this,android.R.layout.simple_spinner_item,
                listaSeguros);
        comboSeguros.setAdapter(adaptador);

    }

    //Cuando pulsamos el botón para hacer la inserción en la BD
    public void contratarSeguro(View v)
    {
        //Importante recoger aquí los datos!!! que si no, no los lee
        comentario = eDComentario.getText().toString();

        if(!comentario.isEmpty() && !eDNumeroRiesgo.getText().toString().isEmpty() && !eDDescuento.getText().toString().isEmpty())
        {


        int idCombo = (int) comboSeguros.getSelectedItemId();

            numeroRiesgo = Double.valueOf(eDNumeroRiesgo.getText().toString());
            descuento = Double.valueOf(eDDescuento.getText().toString());

            if(numeroRiesgo <10 && numeroRiesgo>0 ) {
                if(descuento <100 && descuento>0 ) {

                    bd = new BaseDatosVictorPrueba(this, BaseDatosVictorPrueba.db_nombre, null, BaseDatosVictorPrueba.db_version);
                    sql = bd.getWritableDatabase();
                    int precio_seguro = segurosList.get(idCombo).getPrecio();

                    double precioSeguroCalcular = Double.valueOf(precio_seguro);


                    double numeroRiesgoCalcular = 1 + ((numeroRiesgo) / 10);
                    double descuentoCalcular = (descuento * precioSeguroCalcular) / 100;

                    String precioPersonal = String.valueOf((numeroRiesgoCalcular * precioSeguroCalcular) - descuentoCalcular);

                    int idSeguro = segurosList.get(idCombo).getId_seguro();
                    // ContentValues nueva_poliza = bd.guardar_poliza();
// guardar_poliza(int idSeguro, int idCliente, int riesgo, String comentario, int descuento, Double precio, String nifVendedor)
/*
        ContentValues nueva_poliza = bd.guardar_poliza( idSeguro, Comercial.dni_cliente_elegido, String.valueOf(numeroRiesgo), comentario, String.valueOf(descuento),  precioPersonal, Comercial.dniComercial);
            bd.insertar_valores(sql, Bd_estructura_victor_prueba.tb2,  nueva_poliza);
 */
                    ContentValues values = new ContentValues();
                    values.put(Bd_estructura_victor_prueba.tb2_column1, idSeguro);
                    values.put(Bd_estructura_victor_prueba.tb2_column1, idSeguro);
                    values.put(Bd_estructura_victor_prueba.tb2_column2, Comercial.dni_cliente_elegido);
                    values.put(Bd_estructura_victor_prueba.tb2_column4, String.valueOf(numeroRiesgo));
                    values.put(Bd_estructura_victor_prueba.tb2_column5, comentario);
                    values.put(Bd_estructura_victor_prueba.tb2_column6, String.valueOf(descuento));
                    values.put(Bd_estructura_victor_prueba.tb2_column7, precioPersonal);
                    values.put(Bd_estructura_victor_prueba.tb2_column8, String.valueOf(new Date()));
                    values.put(Bd_estructura_victor_prueba.tb2_column9, Comercial.dniComercial);
                    values.put(Bd_estructura_victor_prueba.tb2_column10, 1);

                    Long idResultante = sql.insert(Bd_estructura_victor_prueba.tb2, Bd_estructura_victor_prueba.tb2_column3, values);

                    Toast.makeText(this, "El registro se añadió", Toast.LENGTH_SHORT).show();
                    bd.close();
                    sql.close();
                } else {
                    Toast.makeText(this, "EL % de riesgo tiene que estar en 1 y 99", Toast.LENGTH_SHORT).show();
                }
                } else {
                Toast.makeText(this, "EL número de riesgo tiene que estar en 1 y 9", Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();

        }
    }

    public void consultarListaSeguros(){
        bd = new BaseDatosVictorPrueba(this, BaseDatosVictorPrueba.db_nombre, null, BaseDatosVictorPrueba.db_version);
        sql = bd.getReadableDatabase();
        Seguro s = null;
        segurosList = new ArrayList<Seguro>();
        Cursor c = sql.rawQuery("Select * from "+ Bd_estructura_victor_prueba.tb1, null);

        while(c.moveToNext())
        {
            s = new Seguro();
            s.setId_seguro(c.getInt(0));
            s.setTipo_seguro(c.getString(1));
            s.setCobertura(c.getString(2));
            s.setPrecio(c.getInt(3));
            s.setActivo(c.getInt(4));

            Log.i("id_seguro", s.getId_seguro().toString());
            Log.i("tipo_seguro", s.getTipo_seguro());
            Log.i("cobertura_seguro", s.getCobertura());
            Log.i("precio_seguro", s.getPrecio().toString());
            Log.i("activo_seguro", s.getActivo().toString());

            segurosList.add(s);
        }
        obtenerLista();
    }

    private void obtenerLista() {
        listaSeguros = new ArrayList<String>();
        for (int i = 0; i < segurosList.size(); i++){
            listaSeguros.add(segurosList.get(i).getId_seguro()+" - " +segurosList.get(i).getTipo_seguro()
            +" - "+ segurosList.get(i).getPrecio() );
        }
    }
}
