package com.example.seguros;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Comercial extends AppCompatActivity {

    LinearLayout linearlayoutScroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comercial);

        //Obtenemos el nombre del comercial para poder trabajar con sus datos
        Bundle bundle = getIntent().getExtras();
        String comercial = bundle.getString("comercial");

        //Obetnemos el linearLayOut de nuestro ScrollView que será dinámico
        linearlayoutScroll = (LinearLayout) findViewById(R.id.linearLayOutScroll);

        establecerScrollView();
    }

    private void establecerScrollView() {

        //Consulta
        //ArrayList
        for (int i = 1; i <= 20; i++) {

            linearlayoutScroll.setOrientation(LinearLayout.VERTICAL);

               // TextView textView = new TextView(this);
                Button btn = new Button(this);
                btn.setText("Jose Luis numero "+ i);
                //textView.setText("TextView " + String.valueOf(i));
                //setTextViewAttributes(textView);
                //layoutScroll.addView(textView);
            final String j = String.valueOf(i);
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {


                    Toast.makeText(Comercial.this,
                            ("Soy el boton " +j+""), Toast.LENGTH_LONG).show();
                }
            });

            setAtributosBotones(btn);
            linearlayoutScroll.addView(btn);
        }
        addLineSeperator();
    }

    private void setAtributosBotones(Button bton) {
        //Creamos un objeto parametro para establecer dimensiones,colores y margenes a cada boton
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(convertDpToPixel(16),
                convertDpToPixel(16),
                0, 0
        );

        bton.setTextColor(Color.WHITE);
        bton.setBackgroundColor(Color.BLUE);
        //Anchura de cada boton
        bton.setWidth(690);
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
