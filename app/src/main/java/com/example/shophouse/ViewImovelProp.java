package com.example.shophouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewImovelProp extends AppCompatActivity {

    ImageView imagem_imovel;
    TextView titulo_imovel, campo_descricao;

    String data1, data2;
    int imagem;

    private Button bt_editar, bt_deletar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_imovel_prop);

        //esconder toolbar
        getSupportActionBar().hide();

        //iniciar componentes
        IniciarComponentes();

        //get e set dados
        getData();
        setData();

        //redirecionar para tela de editar imóvel
        bt_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewImovelProp.this, FormEditarImovel.class);
                startActivity(intent);
            }
        });
    }



    private void getData(){
        if (getIntent().hasExtra("imagem") && getIntent().hasExtra("data1")
                && getIntent().hasExtra("data2")){

            data1 = getIntent().getStringExtra("data1");
            data2 = getIntent().getStringExtra("data2");
            imagem = getIntent().getIntExtra("imagem", 1);


        }else{
            Toast.makeText(this, "Nenhum dado", Toast.LENGTH_SHORT).show();
        }

    }

    private void setData(){

        imagem_imovel = findViewById(R.id.imagem_imovel);
        titulo_imovel = findViewById(R.id.titulo_imovel);
        campo_descricao = findViewById(R.id.campo_descricao);

        titulo_imovel.setText(data1);
        campo_descricao.setText(data2);
        imagem_imovel.setImageResource(imagem);

    }

    private void IniciarComponentes(){
        bt_editar = findViewById(R.id.bt_editar);
        bt_deletar = findViewById(R.id.bt_deletar);

    }
}