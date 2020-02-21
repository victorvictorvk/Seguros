package com.example.seguros;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Admin extends AppCompatActivity {
    AutoCompleteTextView editText;
    LinearLayout linearlayoutScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminprincipal);
    }
}