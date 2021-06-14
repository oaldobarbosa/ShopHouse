package com.example.shophouse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FormEditar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_editar);

        //esconder toolbar
        getSupportActionBar().hide();
    }
}