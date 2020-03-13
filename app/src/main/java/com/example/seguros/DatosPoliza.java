package com.example.seguros;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class DatosPoliza extends AppCompatActivity {
    EditText eDNumeroSeguro, eDComentario, eDNumeroRiesgo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_poliza);
        eDNumeroSeguro = (EditText) findViewById(R.id.editTextNumeroPoliza);
        eDNumeroSeguro.setText(Cliente.tipo_seguro_escogido);
        eDNumeroRiesgo = (EditText) findViewById(R.id.editTextNumeroRiesgo) ;
        eDComentario =(EditText) findViewById(R.id.editTextComentarios);
        eDComentario.setText(Cliente.comentario_escogido);
        eDNumeroRiesgo.setText(Comercial.dni_cliente_elegido);
    }
}
