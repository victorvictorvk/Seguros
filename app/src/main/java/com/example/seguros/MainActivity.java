package com.example.seguros;

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

public class MainActivity extends AppCompatActivity {

    EditText edUsuario;
    EditText edPass;
    ConstraintLayout fondo;
    int mDefaultColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edUsuario = (EditText) findViewById(R.id.editTextUsuario);
        edPass = (EditText) findViewById(R.id.editTextPass);
        fondo = (ConstraintLayout) findViewById(R.id.fondo);
        mDefaultColor = ContextCompat.getColor(MainActivity.this, R.color.colorPrimary);

        establecerFondo();

    }

    public void establecerFondo() {
        //Leemos el fichero para saber qué color es el que hay que coger.
        SharedPreferences prefs = getSharedPreferences("fondoAplicaciones", Context.MODE_PRIVATE);
        ConstraintLayout fondo = (ConstraintLayout) findViewById(R.id.fondo);

        String nombreFondo = prefs.getString("fondo", "personalizado");
        mDefaultColor = prefs.getInt("numeroColores", 0);


        if (nombreFondo.equals("azul")) {
            fondo.setBackgroundResource(R.mipmap.fondo_azul_3);

        } else if (nombreFondo.equals("amarillo")) {
            fondo.setBackgroundResource(R.mipmap.fondo_amarillo);

        } else if (nombreFondo.equals("rojo")) {
            fondo.setBackgroundResource(R.mipmap.fondo_rojo);
        } else if (nombreFondo.equals("personalizado")) {
            fondo.setBackgroundColor(mDefaultColor);
        }
    }

    public void menuPopup(View v) {
        ImageView icono = (ImageView) findViewById(R.id.icono);
        PopupMenu menu = new PopupMenu(this, icono);
        menu.getMenuInflater().inflate(R.menu.menu_ociones, menu.getMenu());

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                SharedPreferences preferencias = getSharedPreferences("fondoAplicaciones", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                String nombreFondo = "azul";
                switch (item.getItemId()) {
                    case R.id.MnuOpc1:
                        System.exit(1);
                        break;

                    case R.id.SubMnuOpc1:
                        fondo.setBackgroundResource(R.mipmap.fondo_azul_3);
                        nombreFondo = "azul";
                        editor.putString("fondo", nombreFondo);
                        break;

                    case R.id.SubMnuOpc2:
                        fondo.setBackgroundResource(R.mipmap.fondo_amarillo);
                        nombreFondo = "amarillo";
                        editor.putString("fondo", nombreFondo);
                        break;

                    case R.id.SubMnuOpc3:
                        fondo.setBackgroundResource(R.mipmap.fondo_rojo);
                        nombreFondo = "rojo";
                        editor.putString("fondo", nombreFondo);
                        break;

                    case R.id.SubMnuOpc4:
                        editor.putInt("numeroColores", openColorPicker());
                        editor.putString("fondo", "personalizado");
                        break;
                }
                editor.commit();
                return true;
            }
        });
        menu.show();

    }

    public void mostar(View v) {
        SharedPreferences prefs = getSharedPreferences("fondoAplicaciones", Context.MODE_PRIVATE);
        ConstraintLayout fondo = (ConstraintLayout) findViewById(R.id.fondo);

        int colores = prefs.getInt("numeroColores", 0);

    }

    public int openColorPicker() {

        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, mDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            int mDefaultColor;
            //Pepito

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefaultColor = color;
                fondo.setBackgroundColor(mDefaultColor);
            }
        });
        colorPicker.show();
        return mDefaultColor;
    }

    //Usaremos este método para guardar la información en el caso en que pase a segundo plano la App
    public void onSaveInstanceState(Bundle estado) {
        estado.putString("comercial", edUsuario.getText().toString());
        estado.putString("pass", edPass.getText().toString());

        super.onSaveInstanceState(estado);
    }

    //Para recuperar los datos si volvemos a poner la App en primer plano
    public void onRestoreInstanceState(Bundle estado) {
        super.onRestoreInstanceState(estado);
        edUsuario.setText("hh");
        edPass.setText("hpass");

    }


    public void entrarApp(View v)
    {
        /*
        Descomentar cuando funcione la BD
        if (edUsuario.getText().toString().equals("admin"))
        {
            //Cambiar esto cuando tengamos conexion a base de datos.
            Intent intento = new Intent(this, Admin.class);
            //intento.putExtra("comercial", idCliente.getText().toString());
            startActivity(intento);
        } else {
            Intent intento = new Intent(this, Comercial.class);
            intento.putExtra("comercial", edUsuario.getText().toString());
            startActivity(intento);
        }
*/
        Intent intento = new Intent(this, Admin.class);
        //intento.putExtra("comercial", idCliente.getText().toString());
        startActivity(intento);
    }

    public void onStop()
    {
        super.onStop();

    }
}


