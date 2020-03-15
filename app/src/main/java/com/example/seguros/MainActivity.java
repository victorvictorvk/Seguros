package com.example.seguros;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
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
import android.widget.Toast;

import java.util.ArrayList;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {

    EditText edUsuario;
    EditText edPass;
    ConstraintLayout fondo;
    int mDefaultColor;
    static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edUsuario = (EditText) findViewById(R.id.editTextUsuario);
        edPass = (EditText) findViewById(R.id.editTextPass);
        fondo = (ConstraintLayout) findViewById(R.id.fondo);
        mDefaultColor = ContextCompat.getColor(MainActivity.this, R.color.colorPrimary);
        //Fondo f = new Fondo(Fondo.context, fondo);

        //f.establecerFondo();
        SharedPreferences prefs = getSharedPreferences("fondoAplicaciones", Context.MODE_PRIVATE);

        establecerFondo(fondo, prefs);
    }

    public void establecerFondo(ConstraintLayout fondoAestablecer, SharedPreferences prefs ) {
        //Leemos el fichero para saber qué color es el que hay que coger.

        String nombreFondo = prefs.getString("fondo", "nada");
       // mDefaultColor = prefs.getInt("numeroColores", 88888888);

        if (nombreFondo.equals("azul")) {
            fondoAestablecer.setBackgroundResource(R.mipmap.fondo_azul_3);

        } else if (nombreFondo.equals("amarillo")) {
            fondoAestablecer.setBackgroundResource(R.mipmap.fondo_amarillo);

        } else if (nombreFondo.equals("rojo")) {
            fondoAestablecer.setBackgroundResource(R.mipmap.fondo_rojo);
        } else if (nombreFondo.equals("personalizado")) {
            fondoAestablecer.setBackgroundColor(mDefaultColor);
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
                        /*
                    case R.id.SubMnuOpc4:
                        int color = openColorPicker();
                        editor.putInt("numeroColores", color);
                        edUsuario.setText(String.valueOf(color));
                        editor.putString("fondo", "personalizado");
                        break;

                         */
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
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
            }
            @Override
            //Establezco el fondo
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
        edUsuario.setText("");
        edPass.setText("");

    }


    public void entrarApp(View v) {
        //Abrimos conexiones a la BD
        SQLiteDatabase sql;
        BaseDatosVictorPrueba bd;
        bd = new BaseDatosVictorPrueba(this, BaseDatosVictorPrueba.db_nombre, null, BaseDatosVictorPrueba.db_version);
        sql = bd.getReadableDatabase();


        if (edUsuario.getText().toString().equals("admin")) {
            Intent intento = new Intent(this, Admin.class);
            startActivity(intento);
        } else if (bd.passOK(sql, edUsuario.getText().toString(), edPass.getText().toString())) {

            Intent intento = new Intent(this, Comercial.class);
            //Le pasamos el id_usuario al intento para poder visualizar o trabajar con sus datos.
            intento.putExtra("comercial", edUsuario.getText().toString());
            startActivity(intento);
            overridePendingTransition (0,0);
        } else {

            Toast.makeText(this, "Usuario o contraseña no encontrados.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onStop()
    {
        super.onStop();

    }
}


