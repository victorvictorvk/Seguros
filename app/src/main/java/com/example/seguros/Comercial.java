package com.example.seguros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class Comercial extends AppCompatActivity {
    AutoCompleteTextView editText;
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

        ImageView flecha = (ImageView)findViewById(R.id.flecha);
        editText = findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.autocompletetv_personal_de_victor,R.id.autoCompleteItem, dameArray());
        editText.setThreshold(1);//Esto es para que empiece a buscar por 1 caracter


        editText.setAdapter(adapter);

        Button botonBuscar = findViewById(R.id.botonBuscar);
        if (botonBuscar != null) {
            botonBuscar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text =  editText.getText().toString();
                    Toast.makeText(Comercial.this, text, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //Este método hace que se pespligue el autocompletTExtview
    public void pulsarFlecha(View v)
    {
        editText.showDropDown();
    }

    public String[] dameArray()
    {
        /*
        ArrayList<Character> array = new ArrayList();
        for (char i = 'a'; i <=20; i++)
        {
            array.add(i);
        }
        return array;
        */
         String[] paises = new String[]{
                "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Albania", "Algeria", "Andorra", "Angola",
                 "Aldzfga", "Adfgria", "Aertra", "Aerta", "Akfh", "Aaert", "Avsg", "Astu",
                 "Albania", "Algeria", "Andorra", "Angola", "Albania", "Algeria", "Andorra", "Angola",
                 "Aldzfga", "Adfgria", "Aertra", "Aerta", "Akfh", "Aaert", "Avsg", "Astu"
        };
        return paises;
    }

    public void mostrarTodosClientes(View v)
    {
        //Cambiar esto cuando tengamos conexion a base de datos.
        Intent intento = new Intent(this, TodosClientes.class);
        //intento.putExtra("comercial", idCliente.getText().toString());
        startActivity(intento);
    }
}
