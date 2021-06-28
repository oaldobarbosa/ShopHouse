package com.example.shophouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class FormCadastroImovel extends AppCompatActivity {

    private EditText edit_titulo, edit_descricao, edit_endereco, edit_estado, edit_cidade, edit_telefone, edit_email;
    private Button bt_selecionarImagem, bt_cadastrarImovel;
    String[] mensagens = {"preencha todos os campos", "imovel cadastrado com sucesso"};
    String usuarioAtualId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro_imovel);

        //esconder toolbar
        getSupportActionBar().hide();

        //iniciando componentes
        IniciarComponentes();

        //ao clicar no bt_cadastrarImovel
        bt_cadastrarImovel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titulo = edit_titulo.getText().toString();
                String descricao = edit_descricao.getText().toString();
                String endereco = edit_endereco.getText().toString();
                String estado = edit_estado.getText().toString();
                String cidade = edit_cidade.getText().toString();
                String telefone = edit_telefone.getText().toString();
                String email = edit_email.getText().toString();

                if (titulo.isEmpty() || descricao.isEmpty() || endereco.isEmpty() || estado.isEmpty() || cidade.isEmpty() ||
                telefone.isEmpty() || email.isEmpty()){

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

                    SalvarDadosImovel();

                    //realizado com sucesso
                    Snackbar snackbar = Snackbar.make(v, mensagens[1], Snackbar.LENGTH_SHORT);

                    //mandar a snackbar pro topo
                    View view = snackbar.getView();
                    FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
                    params.gravity = Gravity.TOP;
                    view.setLayoutParams(params);

                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();

                }//fim else

            }
        });

    }

    private void SalvarDadosImovel(){

        String titulo = edit_titulo.getText().toString();
        String descricao = edit_descricao.getText().toString();
        String endereco = edit_endereco.getText().toString();
        String estado = edit_estado.getText().toString();
        String cidade = edit_cidade.getText().toString();
        String telefone = edit_telefone.getText().toString();
        String email = edit_email.getText().toString();

        //instancia user
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //capturar usuario atual
        usuarioAtualId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> imoveis = new HashMap<>();

        imoveis.put("id_user", usuarioAtualId);
        imoveis.put("titulo", titulo);
        imoveis.put("descricao", descricao);
        imoveis.put("endereco", endereco);
        imoveis.put("estado", estado);
        imoveis.put("cidade", cidade);
        imoveis.put("telefone", telefone);
        imoveis.put("email", email);

        DocumentReference documentReference = db.collection("Imoveis").document();

        documentReference.set(imoveis).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    private void IniciarComponentes() {

        edit_titulo = findViewById(R.id.edit_titulo);
        edit_descricao = findViewById(R.id.edit_descricao);
        edit_endereco = findViewById(R.id.edit_endereco);
        edit_estado = findViewById(R.id.edit_estado);
        edit_cidade = findViewById(R.id.edit_cidade);
        edit_telefone = findViewById(R.id.edit_telefone);
        edit_email = findViewById(R.id.edit_email);

        bt_selecionarImagem = findViewById(R.id.bt_selecionarImagem);
        bt_cadastrarImovel = findViewById(R.id.bt_cadastrarImovel);

    }
}