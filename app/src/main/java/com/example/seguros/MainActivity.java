package com.example.seguros;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    EditText edUsuario;
    EditText edPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edUsuario = (EditText) findViewById(R.id.editTextUsuario);
        edPass = (EditText) findViewById(R.id.editTextPass);

        establecerFondo();

    }
    public void establecerFondo(){
        SharedPreferences prefs = getSharedPreferences("fondoAplicacion", Context.MODE_PRIVATE);
        ConstraintLayout fondo = (ConstraintLayout) findViewById(R.id.fondo);

        String nombreFondo = prefs.getString("fondo", "rojo");
        if( nombreFondo.equals("azul"))
        {
            fondo.setBackgroundResource(R.mipmap.fondo_azul_3);

        }else if( nombreFondo.equals("amarillo")) {
            fondo.setBackgroundResource(R.mipmap.fondo_amarillo);

        }else {
            fondo.setBackgroundResource(R.mipmap.fondo_rojo);

        }
    }
    public void menuPopup(View v)
    {
        ImageView icono = (ImageView) findViewById(R.id.icono);
        PopupMenu menu = new PopupMenu(this,icono);
        menu.getMenuInflater().inflate(R.menu.menu_ociones, menu.getMenu());

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                ConstraintLayout fondo = (ConstraintLayout) findViewById(R.id.fondo);
                String nombreFondo  = "azul";
                switch (item.getItemId()){
                    case R.id.MnuOpc1:
                        System.exit(1);
                    break;

                    case R.id.SubMnuOpc1:
                        fondo.setBackgroundResource(R.mipmap.fondo_azul_3);
                        nombreFondo = "azul";
                        break;
                    case R.id.SubMnuOpc2:
                        fondo.setBackgroundResource(R.mipmap.fondo_amarillo);
                        nombreFondo = "amarillo";

                        break;
                    case R.id.SubMnuOpc3:
                        fondo.setBackgroundResource(R.mipmap.fondo_rojo);
                        nombreFondo = "rojo";

                        break;

                }

                SharedPreferences preferencias = getSharedPreferences("fondoAplicacion", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("fondo", nombreFondo);
                editor.commit();
                return true;
            }
        });
        menu.show();

    }

    //Usaremos este método para guardar la información en el caso en que pase a segundo plano la App
    public void onSaveInstanceState (Bundle estado)
    {
        estado.putString("comercial",edUsuario.getText().toString());
        estado.putString("pass",edPass.getText().toString());

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
        Intent intento = new Intent(this, Comercial.class);
        intento.putExtra("comercial", edUsuario.getText().toString());
        startActivity(intento);
    }



}
