package com.example.shophouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import static android.content.ContentValues.TAG;

public class ViewImovelProp extends AppCompatActivity {

    ImageView imagem_imovel;
    TextView titulo_imovel, campo_descricao, campo_endereco, campo_cidade, campo_estado, campo_telefone, campo_email;
    String img, titulo, descricao, endereco, cidade, estado, telefone, email, id_imovel;

    private Button bt_editar, bt_deletar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_imovel_prop);

        //esconder toolbar
        getSupportActionBar().hide();

        //iniciar componentes
        IniciarComponentes();

        //get e set dados na view
        getData();
        setData();

        //redirecionar para tela de editar imóvel
        bt_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewImovelProp.this, FormEditarImovel.class);
                intent.putExtra("id_imovel", id_imovel);
                startActivity(intent);
                finish();
            }
        });

        //deletar imovel
        bt_deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("Imoveis").document(id_imovel).delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully deleted!");
                            msfToast("Imóvel Deletado com Sucesso!");
                            Intent intent = new Intent(ViewImovelProp.this, Dashboard.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error deleting document", e);
                        }
                    });
            }
        });
    }//fim oncreate

    private void getData(){
        if (getIntent().hasExtra("img") && getIntent().hasExtra("titulo")
                && getIntent().hasExtra("descricao") && getIntent().hasExtra("endereco")
                && getIntent().hasExtra("cidade") && getIntent().hasExtra("estado")
                && getIntent().hasExtra("telefone") && getIntent().hasExtra("email")
                && getIntent().hasExtra("id_imovel")
        ){
            img = getIntent().getStringExtra("img");
            titulo = getIntent().getStringExtra("titulo");
            descricao = getIntent().getStringExtra("descricao");
            endereco = getIntent().getStringExtra("endereco");
            cidade = getIntent().getStringExtra("cidade");
            estado = getIntent().getStringExtra("estado");
            telefone = getIntent().getStringExtra("telefone");
            email = getIntent().getStringExtra("email");
            id_imovel = getIntent().getStringExtra("id_imovel");
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

    private void IniciarComponentes(){
        imagem_imovel = findViewById(R.id.imagem_imovel);
        titulo_imovel = findViewById(R.id.titulo_imovel);
        campo_descricao = findViewById(R.id.campo_descricao);
        campo_endereco = findViewById(R.id.campo_endereco);
        campo_cidade = findViewById(R.id.campo_cidade);
        campo_estado = findViewById(R.id.campo_estado);
        campo_telefone = findViewById(R.id.campo_telefone);
        campo_email = findViewById(R.id.campo_email);
        bt_editar = findViewById(R.id.bt_editar);
        bt_deletar = findViewById(R.id.bt_deletar);
    }

    //toast messagem
    private void msfToast(String s) {
        Toast.makeText(getApplicationContext(), s , Toast.LENGTH_SHORT).show();
    }
}