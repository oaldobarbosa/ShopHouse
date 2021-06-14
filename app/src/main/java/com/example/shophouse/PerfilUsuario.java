package com.example.shophouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PerfilUsuario extends AppCompatActivity {

    private TextView campo_nome, campo_email, campo_telefone, campo_endereco, campo_cidade, campo_estado;
    private Button bt_editarDados, bt_deslogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        //esconder toolbar
        getSupportActionBar().hide();

        //iniciando Componentes
        IniciarComponentes();

        //deslogar
        bt_deslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //codigo firebase para colocar
                Intent intent = new Intent(PerfilUsuario.this, FormLogin.class);
                startActivity(intent);
                finish();

            }
        });


        //redirecionar para tela de editar dados
        bt_editarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilUsuario.this, FormEditar.class);
                startActivity(intent);

            }
        });
    }

    private void IniciarComponentes(){

        campo_nome = findViewById(R.id.campo_nome);
        campo_email = findViewById(R.id.campo_email);
        campo_telefone = findViewById(R.id.campo_telefone);
        campo_endereco = findViewById(R.id.campo_endereco);
        campo_cidade = findViewById(R.id.campo_cidade);
        campo_estado = findViewById(R.id.campo_estado);

        //buttons
        bt_editarDados = findViewById(R.id.bt_editarDados);
        bt_deslogar = findViewById(R.id.bt_deslogar);

    }
}