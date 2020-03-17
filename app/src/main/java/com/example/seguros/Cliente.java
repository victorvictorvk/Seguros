package com.example.seguros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Cliente extends AppCompatActivity {
    LinearLayout linearlayoutScroll;
    ScrollView listaPolizas;
    EditText  eDNombre, eDApe1, eDApe2;
    TextView tvDniCliente;
    static String dniCliente, nombreCliente, ape1Cliente, ape2Cliente;
    public SQLiteDatabase sql;
    public BaseDatosVVS bd;
    //static String id_seguro_escogido,nombre_seguro_escogido, id_poliza_escogida, comentario_escogido, n_reisgo_escogido, descuento_escogido, precio_escogido;
    //String id_seguro, id_poliza, comentario,n_riesgo, descuento , precio;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        SharedPreferences prefs = getSharedPreferences("fondoAplicaciones", Context.MODE_PRIVATE);
        ConstraintLayout fondo = (ConstraintLayout) findViewById(R.id.fondo);
        MainActivity main = new MainActivity();
        main.establecerFondo(fondo, prefs);
        listaPolizas = (ScrollView) findViewById(R.id.listaPolizas);
        linearlayoutScroll = (LinearLayout) findViewById(R.id.linearLayOutScroll);


        Bundle bundle = getIntent().getExtras();

        //Este lo necesitaremos para hacer las consultas
        dniCliente = bundle.getString("dniCliente");
        nombreCliente = bundle.getString("nombreCliente");
        ape1Cliente = bundle.getString("ape1Cliente");
        ape2Cliente = bundle.getString("ape2Cliente");

        tvDniCliente = (TextView) findViewById(R.id.textViewDniCliente);
         eDNombre =(EditText) findViewById(R.id.EDNombreCliente);
         eDApe1 =(EditText) findViewById(R.id.EDApe1Cliente);
        eDApe2 =(EditText) findViewById(R.id.EDApe2Cliente);
        //edDNI.setText(dniCliente);
        //Prueba
        tvDniCliente.setText(Comercial.dni_cliente_elegido);
        eDNombre.setText(nombreCliente);
        eDApe1.setText(ape1Cliente);
        eDApe2.setText(ape2Cliente);
    }
    private void establecerScrollViewPolizas() {
        bd = new BaseDatosVVS(this, BaseDatosVVS.db_nombre, null, BaseDatosVVS.db_version);
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
                btn = new Button(this);

                final String  id_seguro = c.getString(0);
                final String  id_poliza = c.getString(1);

                final String n_riesgo = c.getString(2);
                final String comentario = c.getString(3);
                final String descuento = c.getString(4);
                final String  precio = c.getString(5);

               final String nombre_seguro_escogido =   bd.dameNombreSeguro(sql, id_seguro);
                btn.setText(nombre_seguro_escogido + "; "+comentario + "; "+ precio+"€");

                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        seleccionarPoliza(nombre_seguro_escogido,comentario,n_riesgo,descuento, precio, id_poliza);
                        //seleccionarPoliza(v);
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

    private void seleccionarPoliza(String nombre_seguro_escogido, String comentario_escogido, String n_reisgo_escogido, String descuento_escogido, String precio_escogido, String poliza) {
        //Cambiar esto cuando tengamos conexion a base de datos.
        Intent intento = new Intent(this, DatosPoliza.class);
        intento.putExtra("nombre_seguro_escogido",nombre_seguro_escogido );
        intento.putExtra("comentario_escogido", comentario_escogido);
        intento.putExtra("n_reisgo_escogido", n_reisgo_escogido);
        intento.putExtra("descuento_escogido",descuento_escogido );
        intento.putExtra("precio_escogido", precio_escogido);
        intento.putExtra("poliza_escogida", poliza);

        startActivity(intento);
    }

    public void seleccionarPoliza(View v)
{
        Intent intento = new Intent(this, DatosPoliza.class);
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

        bton.setTextColor(Color.BLACK);
        bton.setBackgroundResource(R.drawable.roundedbutton);
        //Anchura de cada boton
        bton.setWidth(800);
        btn.setHeight(25);
        btn.setTextSize(12);
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
        //Aquí debemos hacer una comprobación para saber si existe algun seguro.
        bd = new BaseDatosVVS(this, BaseDatosVVS.db_nombre, null, BaseDatosVVS.db_version);
        //Ahora indicamos que abra la base de datos en modo lectura y escritura
        sql = bd.getWritableDatabase();
        Cursor cursor = bd.listaSeguros(sql);
        boolean existen = false;
        while(cursor.moveToNext()){
            existen = true;
            }
        if ( existen ){
        Intent intento = new Intent(this, ContratarSeguro.class);
        startActivity(intento);
        } else {
            Toast.makeText(this, "No existe ningún seguro. Dile al administrador que los cree.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        linearlayoutScroll.removeAllViews();
        establecerScrollViewPolizas();
    }

    public void modificarCliente(View v)
    {
        bd = new BaseDatosVVS(this, BaseDatosVVS.db_nombre, null, BaseDatosVVS.db_version);
        sql = bd.getWritableDatabase();
        if( !eDNombre.getText().toString().isEmpty()
                && !eDApe1.getText().toString().isEmpty() && !eDApe2.getText().toString().isEmpty()) {
            ContentValues valores = new ContentValues();
            valores.put(Bd_estructura_VVS.tb3_column2, eDNombre.getText().toString());
            valores.put(Bd_estructura_VVS.tb3_column3, eDApe1.getText().toString());
            valores.put(Bd_estructura_VVS.tb3_column4, eDApe2.getText().toString());

            sql.update(Bd_estructura_VVS.tb3, valores, Bd_estructura_VVS.tb3_column1 + " = " + Comercial.dni_cliente_elegido, null);

            bd.close();
            sql.close();
            Toast.makeText(this, "El registro se modificó", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();

        }
    }

    public void eliminarCliente(View v)
    {
        bd = new BaseDatosVVS(this, BaseDatosVVS.db_nombre, null, BaseDatosVVS.db_version);
        sql = bd.getWritableDatabase();

        boolean asociado = bd.estaAsociadoActivo(sql, Comercial.dni_cliente_elegido, Bd_estructura_VVS.tb2_column2,Bd_estructura_VVS.tb2_column10 );
        if(!asociado) {

            ContentValues valores = new ContentValues();

            valores.put(Bd_estructura_VVS.tb3_column2, nombreCliente);
            valores.put(Bd_estructura_VVS.tb3_column3, ape1Cliente);
            valores.put(Bd_estructura_VVS.tb3_column4, ape2Cliente);
            valores.put(Bd_estructura_VVS.tb3_column6, 0);

            sql.update(Bd_estructura_VVS.tb3, valores, Bd_estructura_VVS.tb3_column1+" = "+ Comercial.dni_cliente_elegido, null );

        bd.close();
        sql.close();
        Toast.makeText(this, "El registro se eliminó", Toast.LENGTH_SHORT).show();
        finish();
        } else
        {
            Toast.makeText(this, "No se puede eliminar porque dicho Cliente está asociado a una póliza", Toast.LENGTH_SHORT).show();
        }
    }
}
