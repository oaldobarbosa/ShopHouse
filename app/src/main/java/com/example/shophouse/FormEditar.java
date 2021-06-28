package com.example.shophouse;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class FormEditar extends AppCompatActivity {
    private EditText edit_nome, edit_telefone, edit_endereco, edit_cidade, edit_estado, edit_senha;
    private Button bt_salvarAlteracoes;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioAtualId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_editar);

        //esconder toolbar
        getSupportActionBar().hide();

        IniciarComponentes();

        //click bt salvar alteraçoes

        bt_salvarAlteracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        usuarioAtualId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioAtualId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable  DocumentSnapshot documentSnapshot, @Nullable  FirebaseFirestoreException error) {

                if (documentSnapshot != null){
                    edit_nome.setText(documentSnapshot.getString("nome"));
                }

            }
        });
    }

    private void IniciarComponentes() {

        edit_nome = findViewById(R.id.edit_nome);
        edit_telefone = findViewById(R.id.edit_telefone);
        edit_endereco = findViewById(R.id.edit_endereco);
        edit_cidade = findViewById(R.id.edit_cidade);
        edit_estado = findViewById(R.id.edit_estado);
        edit_senha = findViewById(R.id.edit_senha);

        bt_salvarAlteracoes = findViewById(R.id.bt_salvarAlteracoes);

    }


}