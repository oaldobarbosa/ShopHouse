package com.example.shophouse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class ViewImovel extends AppCompatActivity {

    ImageView imagem_imovel;
    TextView titulo_imovel, campo_descricao, campo_endereco, campo_cidade, campo_estado, campo_telefone, campo_email;



    String img, titulo, descricao, endereco, cidade, estado, telefone, email;


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
        campo_endereco = findViewById(R.id.campo_endereco);
        campo_cidade = findViewById(R.id.campo_cidade);
        campo_estado = findViewById(R.id.campo_estado);
        campo_telefone = findViewById(R.id.campo_telefone);
        campo_email = findViewById(R.id.campo_email);
    }

    private void getData(){
        if (getIntent().hasExtra("img") && getIntent().hasExtra("titulo")
                && getIntent().hasExtra("descricao") && getIntent().hasExtra("endereco")
                && getIntent().hasExtra("cidade") && getIntent().hasExtra("estado")
                && getIntent().hasExtra("telefone") && getIntent().hasExtra("email")
        ){

            img = getIntent().getStringExtra("img");
            titulo = getIntent().getStringExtra("titulo");
            descricao = getIntent().getStringExtra("descricao");
            endereco = getIntent().getStringExtra("endereco");
            cidade = getIntent().getStringExtra("cidade");

            estado = getIntent().getStringExtra("estado");
            telefone = getIntent().getStringExtra("telefone");
            email = getIntent().getStringExtra("email");


        }else{
            Toast.makeText(this, "Nenhum dado", Toast.LENGTH_SHORT).show();
        }

    }

    private void setData(){



        Picasso.get().load(img).fit().into(imagem_imovel);

        titulo_imovel.setText(titulo);

        campo_descricao.setText(descricao);
        campo_endereco.setText(endereco);
        campo_cidade.setText(cidade);
        campo_estado.setText(estado);
        campo_telefone.setText(telefone);
        campo_email.setText(email);


    }
}