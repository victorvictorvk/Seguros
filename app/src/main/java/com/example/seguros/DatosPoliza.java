package com.example.seguros;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class DatosPoliza extends AppCompatActivity {
    EditText eDNumeroSeguro, eDComentario, eDNumeroRiesgo, eDDescuento;
    TextView precioCliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_poliza);
        eDNumeroSeguro = (EditText) findViewById(R.id.editTextNumeroPoliza);
        eDComentario =(EditText) findViewById(R.id.editTextComentarios);
        eDNumeroRiesgo = (EditText) findViewById(R.id.editTextNumeroRiesgoContratar) ;
        eDDescuento = (EditText) findViewById(R.id.editTextDescuentoPoliza) ;
        precioCliente = (TextView) findViewById(R.id.textViewPrecioCliente);

        eDNumeroSeguro.setText(Cliente.tipo_seguro_escogido);
        eDComentario.setText(Cliente.comentario_escogido);
        eDNumeroRiesgo.setText(Cliente.n_reisgo_escogido);
        eDDescuento.setText(Cliente.descuento_escogido+"%");
        precioCliente.setText(Cliente.precio_escogido+" euros");

    }
}
