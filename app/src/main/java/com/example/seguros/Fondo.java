package com.example.seguros;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;

public class Fondo extends AppCompatActivity{

    ConstraintLayout fondo;
    static Context context;

    public Fondo(Context c, ConstraintLayout fondo) {
        this.context = c;

        this.fondo = fondo;
    }

    public void establecerFondo(){
        //Leemos el fichero para saber qué color es el que hay que coger.
        SharedPreferences prefs = getSharedPreferences("fondoAplicaciones", Context.MODE_PRIVATE);

        String nombreFondo = prefs.getString("fondo", "nada");

        if( nombreFondo.equals("azul"))
        {
            fondo.setBackgroundResource(R.mipmap.fondo_azul_3);

        }else if( nombreFondo.equals("amarillo")) {
            fondo.setBackgroundResource(R.mipmap.fondo_amarillo);

        }else if( nombreFondo.equals("rojo")){
            fondo.setBackgroundResource(R.mipmap.fondo_rojo);
        }


    }
}
