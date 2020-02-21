package com.example.seguros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class Cliente extends AppCompatActivity {
    LinearLayout linearlayoutScroll;
    ScrollView listaPolizas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        listaPolizas = (ScrollView) findViewById(R.id.listaPolizas);
        linearlayoutScroll = (LinearLayout) findViewById(R.id.linearLayOutScroll);

        establecerScrollView();

    }
    private void establecerScrollView() {

        //Consulta
        //ArrayList
        for (int i = 1; i <= 7; i++) {

            linearlayoutScroll.setOrientation(LinearLayout.VERTICAL);

            // TextView textView = new TextView(this);
            Button btn = new Button(this);
            btn.setText("Seguro "+ i);



            //textView.setText("TextView " + String.valueOf(i));
            //setTextViewAttributes(textView);
            //layoutScroll.addView(textView);

            //Asociamos un escuchador al botón

            final String j = String.valueOf(i);
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    seleccionarPoliza(v);
                }
            });

            setAtributosBotones(btn);
            linearlayoutScroll.addView(btn);
        }
        addLineSeperator();
    }
public void seleccionarPoliza(View v)
{
    //Cambiar esto cuando tengamos conexion a base de datos.
        Intent intento = new Intent(this, Poliza.class);
    //intento.putExtra("comercial", idCliente.getText().toString());
    startActivity(intento);
}

    private void setAtributosBotones(Button bton) {
        //Creamos un objeto parametro para establecer dimensiones,colores y margenes a cada boton
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(convertDpToPixel(4),
                convertDpToPixel(4),
                0, 0
        );

        bton.setTextColor(Color.WHITE);
        bton.setBackgroundColor(Color.BLUE);
        //Anchura de cada boton
        bton.setWidth(800);
        bton.setLayoutParams(params);
    }

    private int convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

    private void addLineSeperator() {
        LinearLayout lineLayout = new LinearLayout(this);
        lineLayout.setBackgroundColor(Color.GRAY);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                2);
        params.setMargins(0, convertDpToPixel(10), 0, convertDpToPixel(10));
        lineLayout.setLayoutParams(params);
        linearlayoutScroll.addView(lineLayout);
    }
}
