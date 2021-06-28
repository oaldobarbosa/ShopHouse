package com.example.shophouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class FormCadastro extends AppCompatActivity {

    private EditText edit_nome, edit_email, edit_telefone, edit_endereco, edit_cidade, edit_estado, edit_senha;
    private Button bt_cadastrar;
    String[] mensagens = {"Preencha todos os campos", "cadastro realizado com sucesso"};
    String usuarioAtualId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);

        //esconder toolbar
        getSupportActionBar().hide();

        //iniciando componentes
        IniciarComponentes();

        //evento click bt cadastrar
        bt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = edit_nome.getText().toString();
                String email = edit_email.getText().toString();
                String telefone = edit_telefone.getText().toString();
                String endereco = edit_endereco.getText().toString();
                String cidade = edit_cidade.getText().toString();
                String estado = edit_estado.getText().toString();
                String senha = edit_senha.getText().toString();

                if ( nome.isEmpty() || email.isEmpty() || telefone.isEmpty() || endereco.isEmpty() ||
                        cidade.isEmpty() || estado.isEmpty() || senha.isEmpty() ){
                    //preencher tood os campos
                    Snackbar snackbar = Snackbar.make(v, mensagens[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else{
                    CadastrarUsuario(v);
                }
            }
        });
    }

    private void CadastrarUsuario(View v) {
        String email = edit_email.getText().toString();
        String senha = edit_senha.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    //salvado dados do user

                    SalvarDadosUsuario();

                    //realizado com sucesso
                    Snackbar snackbar = Snackbar.make(v, mensagens[1], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();

                }else{
                    String erro;
                    try {
                        throw task.getException();

                    }catch (FirebaseAuthWeakPasswordException e){
                        erro = "digite uma senha com no minimo 6 caracteres";

                    }catch (FirebaseAuthUserCollisionException e){

                        erro = "Já existe uma conta com o email";
                    }catch (FirebaseAuthInvalidCredentialsException e){

                        erro = "Email com formato inválido";

                    }catch (Exception e){
                        erro = "Erro ao cadastrar Usuário";
                    }

                    //exibir mensagem de erro
                    Snackbar snackbar = Snackbar.make(v, erro, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();


                }
            }
        });
    }

    //salvar dados do usuario
    private void SalvarDadosUsuario(){
        //capturando informacoes
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
                Log.d("db", "Sucesso ao salvar dados");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.d("db_erro", "Erro ao salvar dados" + e.toString());

            }
        });

    }

    private void IniciarComponentes(){
        edit_nome = findViewById(R.id.edit_nome);
        edit_email = findViewById(R.id.edit_email);
        edit_telefone = findViewById(R.id.edit_telefone) ;
        edit_endereco = findViewById(R.id.edit_endereco);
        edit_cidade = findViewById(R.id.edit_cidade);
        edit_estado = findViewById(R.id.edit_estado);
        edit_senha = findViewById(R.id.edit_senha);
        bt_cadastrar = findViewById(R.id.bt_cadastrar);

    }
}