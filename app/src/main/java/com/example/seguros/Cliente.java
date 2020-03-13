package com.example.seguros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;

public class Cliente extends AppCompatActivity {
    LinearLayout linearlayoutScroll;
    ScrollView listaPolizas;
    EditText edDNI, eDNombre, eDApe1, eDApe2;
    static String dniCliente, nombreCliente, ape1Cliente, ape2Cliente;
    public SQLiteDatabase sql;
    public BaseDatosVictorPrueba bd;
    static String tipo_seguro_escogido, id_poliza_escogida, comentario_escogido, n_reisgo_escogido, descuento_escogido;
    String tipo_seguro, id_poliza, comentario,n_riesgo, descuento ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        listaPolizas = (ScrollView) findViewById(R.id.listaPolizas);
        linearlayoutScroll = (LinearLayout) findViewById(R.id.linearLayOutScroll);

        establecerScrollViewPolizas();

        Bundle bundle = getIntent().getExtras();

        //Este lo necesitaremos para hacer las consultas
        dniCliente = bundle.getString("dniCliente");
        nombreCliente = bundle.getString("nombreCliente");
        ape1Cliente = bundle.getString("ape1Cliente");
        ape2Cliente = bundle.getString("ape2Cliente");

         edDNI = (EditText) findViewById(R.id.EDDNICliente);
         eDNombre =(EditText) findViewById(R.id.EDNombreCliente);
        eDApe1 =(EditText) findViewById(R.id.EDApe1Cliente);
        eDApe2 =(EditText) findViewById(R.id.EDApe2Cliente);
        //edDNI.setText(dniCliente);
        //Prueba
        edDNI.setText(Comercial.dni_cliente_elegido);
        eDNombre.setText(nombreCliente);
        eDApe1.setText(ape1Cliente);
        eDApe2.setText(ape2Cliente);


    }
    private void establecerScrollViewPolizas() {
        bd = new BaseDatosVictorPrueba(this, BaseDatosVictorPrueba.db_nombre, null, BaseDatosVictorPrueba.db_version);
        //Ahora indicamos que abra la base de datos en modo lectura y escritura
        sql = bd.getWritableDatabase();
        //Consulta
        //ArrayList
        //Aqui hacemos una consulta a la base de datos.
        Cursor c = bd.listaPolizas(sql);
        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                linearlayoutScroll.setOrientation(LinearLayout.VERTICAL);
                Button btn = new Button(this);

                tipo_seguro = c.getString(0);
                  id_poliza = c.getString(1);

                  n_riesgo = c.getString(2);
                  comentario = c.getString(3);
                  descuento = c.getString(4);

                btn.setText(tipo_seguro + ";"+id_poliza + ";"+n_riesgo + ";"+comentario + ";"+descuento );
                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        tipo_seguro_escogido = tipo_seguro;
                        id_poliza_escogida = id_poliza;
                        comentario_escogido = comentario;
                        n_reisgo_escogido=  n_riesgo;
                                descuento_escogido = descuento;
                        seleccionarPoliza(v);
                    }
                });

                setAtributosBotones(btn);
                linearlayoutScroll.addView(btn);
            } while (c.moveToNext());
            addLineSeperator();

        }
        bd.close();
        sql.close();

        /*
        NO BORRAR
        for (int i = 1; i <= 7; i++) {
            linearlayoutScroll.setOrientation(LinearLayout.VERTICAL);
            Button btn = new Button(this);
            btn.setText("Seguro "+ i);

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

         */
    }
public void seleccionarPoliza(View v)
{
    //Cambiar esto cuando tengamos conexion a base de datos.
        Intent intento = new Intent(this, DatosPoliza.class);
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
    public void pasarActividadContratarSeguro (View v) {
        Intent intento = new Intent(this, ContratarSeguro.class);
        startActivity(intento);
    }
}
