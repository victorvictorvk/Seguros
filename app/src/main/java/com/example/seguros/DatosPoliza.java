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

public class DatosPoliza extends AppCompatActivity {
    EditText eDNombreSeguro, eDComentario, eDNumeroRiesgo, eDDescuento;
    TextView precioCliente;
    public SQLiteDatabase sql;
    public BaseDatosVVS bd;
    String poliza;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_poliza);
        SharedPreferences prefs = getSharedPreferences("fondoAplicaciones", Context.MODE_PRIVATE);
        ConstraintLayout fondo = (ConstraintLayout) findViewById(R.id.fondo);
        MainActivity main = new MainActivity();
        main.establecerFondo(fondo, prefs);
        eDNombreSeguro = (EditText) findViewById(R.id.editTextNombreSeguro);
        eDComentario =(EditText) findViewById(R.id.editTextComentarios);
        eDNumeroRiesgo = (EditText) findViewById(R.id.editTextNumeroRiesgoContratar) ;
        eDDescuento = (EditText) findViewById(R.id.editTextDescuentoPoliza) ;
        precioCliente = (TextView) findViewById(R.id.textViewPrecioCliente);

        Bundle bundle = getIntent().getExtras();

        eDNombreSeguro.setText(bundle.getString("nombre_seguro_escogido"));
        eDComentario.setText(bundle.getString("comentario_escogido"));
        eDNumeroRiesgo.setText(bundle.getString("n_reisgo_escogido"));
        eDDescuento.setText(bundle.getString("descuento_escogido")+"%");
        precioCliente.setText(bundle.getString("precio_escogido")+" euros");
        poliza = bundle.getString("poliza_escogida");
    }
    public void modificarPoliza(View v)
    {
        bd = new BaseDatosVVS(this, BaseDatosVVS.db_nombre, null, BaseDatosVVS.db_version);
        sql = bd.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(Bd_estructura_VVS.tb2_column4, eDNumeroRiesgo.getText().toString());
        valores.put(Bd_estructura_VVS.tb2_column5, eDComentario.getText().toString());
        valores.put(Bd_estructura_VVS.tb2_column6, eDDescuento.getText().toString());
        sql.update(Bd_estructura_VVS.tb2, valores, Bd_estructura_VVS.tb2_column3+" = "+ poliza, null );
        bd.close();
        sql.close();
        Toast.makeText(this, "La póliza se modificó", Toast.LENGTH_SHORT).show();
    }
    public void eliminarPoliza(View v)
    {
        bd = new BaseDatosVVS(this, BaseDatosVVS.db_nombre, null, BaseDatosVVS.db_version);
        sql = bd.getWritableDatabase();
        sql.delete(Bd_estructura_VVS.tb2, Bd_estructura_VVS.tb2_column3+" = "+ poliza, null );
        bd.close();
        sql.close();
        Toast.makeText(this, "La póliza se eliminó", Toast.LENGTH_SHORT).show();
        finish();
    }
}
