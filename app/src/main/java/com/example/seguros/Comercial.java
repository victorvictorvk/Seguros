package com.example.seguros;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Comercial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comercial);

        Bundle bundle = getIntent().getExtras();
        String comercial = bundle.getString("comercial");
    }
}
