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
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class FormEditar extends AppCompatActivity {
    private EditText edit_nome, edit_telefone, edit_endereco, edit_cidade, edit_estado;//edit_senha
    private Button bt_salvarAlteracoes;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioAtualId;
    String[] mensagens = {"preencha todos os campos", "alterado com sucesso", "erro ao alterar os dados"};

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
                String nome = edit_nome.getText().toString();
                String telefone = edit_telefone.getText().toString();
                String endereco = edit_endereco.getText().toString();
                String cidade = edit_cidade.getText().toString();
                String estado = edit_estado.getText().toString();
                //String senha = edit_senha.getText().toString();

                if(nome.isEmpty() || telefone.isEmpty() || endereco.isEmpty() ||
                        cidade.isEmpty() || estado.isEmpty()){

                    //preencher tood os campos
                    Snackbar snackbar = Snackbar.make(v, mensagens[0], Snackbar.LENGTH_SHORT);

                    //mandar a snackbar pro topo
                    View view = snackbar.getView();
                    FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
                    params.gravity = Gravity.TOP;
                    view.setLayoutParams(params);

                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();

                }else{
                    AlterarDados(v);
                }



            }
        });
    }

    //funcao para alterar
    private void AlterarDados(View v) {

        String nome = edit_nome.getText().toString();
        String telefone = edit_telefone.getText().toString();
        String endereco = edit_endereco.getText().toString();
        String cidade = edit_cidade.getText().toString();
        String estado = edit_estado.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> usuarios = new HashMap<>();
        usuarios.put("nome", nome);
        usuarios.put("telefone", telefone);
        usuarios.put("endereco", endereco);
        usuarios.put("cidade", cidade);
        usuarios.put("estado", estado);

        usuarioAtualId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioAtualId);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                //realizado com sucesso
                msfToast("Dados Alterados com Sucesso!");

                TelaPerfil();

                Log.d("db", "Sucesso ao editar dados");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                //realizado com sucesso
                Snackbar snackbar = Snackbar.make(v, mensagens[2], Snackbar.LENGTH_SHORT);

                //mandar a snackbar pro topo
                View view = snackbar.getView();
                FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
                params.gravity = Gravity.TOP;
                view.setLayoutParams(params);

                snackbar.setBackgroundTint(Color.WHITE);
                snackbar.setTextColor(Color.BLACK);
                snackbar.show();
                Log.d("db_erro", "Erro ao editar dados");

            }
        });




    }

    private void TelaPerfil() {
        //redirecionar pagina de pefil
        Intent intent = new Intent(FormEditar.this, PerfilUsuario.class);
        startActivity(intent);
        finish();

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
                    edit_telefone.setText(documentSnapshot.getString("telefone"));
                    edit_endereco.setText(documentSnapshot.getString("endereco"));
                    edit_cidade.setText(documentSnapshot.getString("cidade"));
                    edit_estado.setText(documentSnapshot.getString("estado"));
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

        //mascara telefone
        SimpleMaskFormatter smf = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(edit_telefone, smf);
        edit_telefone.addTextChangedListener(mtw);

        bt_salvarAlteracoes = findViewById(R.id.bt_salvarAlteracoes);

    }

    //toast messagem
    private void msfToast(String s) {
        Toast.makeText(getApplicationContext(), s , Toast.LENGTH_SHORT).show();
    }


}