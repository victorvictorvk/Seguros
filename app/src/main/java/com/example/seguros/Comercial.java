package com.example.seguros;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Comercial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comercial);

        //Obtenemos el nombre del comercial para poder trabajar con sus datos
        Bundle bundle = getIntent().getExtras();
        String comercial = bundle.getString("comercial");
    }
}
