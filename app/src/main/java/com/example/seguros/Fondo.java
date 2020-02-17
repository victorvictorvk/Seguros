package com.example.seguros;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import yuku.ambilwarna.AmbilWarnaDialog;
public class Fondo extends AppCompatActivity{

    int mDefaultColor;

    public void establecerFondo(){
        //Leemos el fichero para saber qué color es el que hay que coger.
        SharedPreferences prefs = getSharedPreferences("fondoAplicaciones", Context.MODE_PRIVATE);
        ConstraintLayout fondo = (ConstraintLayout) findViewById(R.id.fondo);

        String nombreFondo = prefs.getString("fondo", "personalizado");
        mDefaultColor = prefs.getInt("numeroColores", 0);


        if( nombreFondo.equals("azul"))
        {
            fondo.setBackgroundResource(R.mipmap.fondo_azul_3);

        }else if( nombreFondo.equals("amarillo")) {
            fondo.setBackgroundResource(R.mipmap.fondo_amarillo);

        }else if( nombreFondo.equals("rojo")){
            fondo.setBackgroundResource(R.mipmap.fondo_rojo);
        }else if( nombreFondo.equals("personalizado"))
        {
            fondo.setBackgroundColor(mDefaultColor);
        }


    }
}
