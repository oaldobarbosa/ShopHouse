package com.example.shophouse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FormEditarImovel extends AppCompatActivity {

    private EditText edit_titulo, edit_descricao, edit_endereco, edit_telefone, edit_email;
    private TextView text_estado, text_cidade;
    private ImageView iv_imagemSelecionada;
    private Button bt_salvar, bt_selecionarImagem;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String id_imovel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_editar_imovel);
        //esconder toolbar
        getSupportActionBar().hide();

        IniciarComponentes();

        Intent intent = getIntent();
        id_imovel = intent.getStringExtra("id_imovel");

        bt_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titulo = edit_titulo.getText().toString();
                String descricao = edit_descricao.getText().toString();
                String endereco = edit_endereco.getText().toString();
                String telefone = edit_telefone.getText().toString();
                String email = edit_email.getText().toString();

                if (titulo.isEmpty() || descricao.isEmpty() || endereco.isEmpty() || telefone.isEmpty() || email.isEmpty() ){

                    msfToast("Preencha Todos os Campos!");

                }else{

                    EditarDadosImovel();

                }//fim else
            }
        });
    }



    private void EditarDadosImovel() {


        String titulo = edit_titulo.getText().toString();
        String descricao = edit_descricao.getText().toString();
        String endereco = edit_endereco.getText().toString();
        String telefone = edit_telefone.getText().toString();
        String email = edit_email.getText().toString();

        //instancia user
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> imoveis = new HashMap<>();

        imoveis.put("titulo", titulo);
        imoveis.put("descricao", descricao);
        imoveis.put("endereco", endereco);
        imoveis.put("telefone", telefone);
        imoveis.put("email", email);

        DocumentReference documentReference = db.collection("Imoveis").document(id_imovel);

        documentReference.update(imoveis).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                msfToast("Dados Alterados com Sucesso!");
                Intent intent = new Intent(FormEditarImovel.this, Dashboard.class);
                finish();
                Log.d("db", "Sucesso ao salvar dados");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.d("db_erro", "Erro ao salvar dados" + e.toString());

            }
        });

    }

    private void IniciarComponentes() {

        edit_titulo = findViewById(R.id.edit_titulo);
        edit_descricao = findViewById(R.id.edit_descricao);
        edit_endereco = findViewById(R.id.edit_endereco);

        text_estado = findViewById(R.id.text_estado);
        text_cidade = findViewById(R.id.text_cidade);

        edit_telefone = findViewById(R.id.edit_telefone);

        //mascara telefone
        SimpleMaskFormatter smf = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(edit_telefone, smf);
        edit_telefone.addTextChangedListener(mtw);


        edit_email = findViewById(R.id.edit_email);

        iv_imagemSelecionada = findViewById(R.id.iv_imagemSelecionada);
        bt_selecionarImagem = findViewById(R.id.bt_selecionarImagem);

        bt_salvar = findViewById(R.id.bt_salvar);


    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle extras = getIntent().getExtras();
        String id_imovel = extras.getString("id_imovel");


        DocumentReference documentReference = db.collection("Imoveis").document(id_imovel);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                if (documentSnapshot != null){
                    edit_titulo.setText(documentSnapshot.getString("titulo"));
                    edit_descricao.setText(documentSnapshot.getString("descricao"));
                    edit_endereco.setText(documentSnapshot.getString("endereco"));
                    text_estado.setText(documentSnapshot.getString("estado"));
                    text_cidade.setText(documentSnapshot.getString("cidade"));
                    edit_telefone.setText(documentSnapshot.getString("telefone"));
                    edit_email.setText(documentSnapshot.getString("email"));

                    Picasso.get().load(documentSnapshot.getString("img")).into(iv_imagemSelecionada);

                }
            }
        });

    }

    //mensagem
    private void msfToast(String s) {
        Toast.makeText(getApplicationContext(), s , Toast.LENGTH_SHORT).show();
    }
}