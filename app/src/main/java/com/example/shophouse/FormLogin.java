package com.example.shophouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FormLogin extends AppCompatActivity {

    private EditText edit_email, edit_senha;
    private Button bt_entrar, bt_cadastrese;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);

        //esconder toolbar
        getSupportActionBar().hide();

        //iniciando componentes
        IniciarComponentes();

        //telacadastro
        bt_cadastrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormLogin.this, FormCadastro.class);
                startActivity(intent);
            }
        });


    }

    /*
    @Override
    protected void onStart() {
        super.onStart();

    }
     */

    /*redirecionar para tela principal
    private void TelaPrincipal(){
        Intent intent = new Intent(FormLogin.this, TelaPrincipal.class);
        startActivity(intent);
        finish();
    }

     */

    private void IniciarComponentes(){

        //edit texts
        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);

        //buttons
        bt_entrar = findViewById(R.id.bt_entrar);
        bt_cadastrese = findViewById(R.id.bt_cadastrese);


    }
}