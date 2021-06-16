package com.example.shophouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewImovelProp extends AppCompatActivity {

    private Button bt_editar, bt_deletar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_imovel_prop);

        //esconder toolbar
        getSupportActionBar().hide();

        //iniciar componentes
        IniciarComponentes();

        //redirecionar para tela de editar imóvel
        bt_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewImovelProp.this, FormEditarImovel.class);
                startActivity(intent);
            }
        });
    }

    private void IniciarComponentes(){
        bt_editar = findViewById(R.id.bt_editar);
        bt_deletar = findViewById(R.id.bt_deletar);

    }
}