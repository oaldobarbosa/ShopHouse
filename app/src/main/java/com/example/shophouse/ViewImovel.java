package com.example.shophouse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewImovel extends AppCompatActivity {

    ImageView imagem_imovel;
    TextView titulo_imovel, campo_descricao;

    String data1, data2;
    int imagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_imovel);
        //esconder toolbar
        getSupportActionBar().hide();


        //iniciando componentes
        IniciarComponentes();

        //get e set dados
        getData();
        setData();
    }

    private void IniciarComponentes() {
        imagem_imovel = findViewById(R.id.imagem_imovel);
        titulo_imovel = findViewById(R.id.titulo_imovel);
        campo_descricao = findViewById(R.id.campo_descricao);
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
        titulo_imovel.setText(data1);
        campo_descricao.setText(data2);
        imagem_imovel.setImageResource(imagem);

    }
}