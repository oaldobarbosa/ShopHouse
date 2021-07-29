package com.example.shophouse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import static android.content.ContentValues.TAG;

public class PerfilUsuario extends AppCompatActivity {

    private TextView campo_nome, campo_email, campo_telefone, campo_endereco, campo_cidade, campo_estado;
    private Button bt_editarDados, bt_excluirConta;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioAtualId;

    String iduser = usuarioAtualId;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        //esconder toolbar
        getSupportActionBar().hide();

        //iniciando Componentes
        IniciarComponentes();


        //redirecionar para tela de editar dados
        bt_editarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilUsuario.this, FormEditar.class);
                startActivity(intent);
                finish();

            }
        });


    }


    public void ClickExcluirConta(View view) {
        ExcluirConta(this);
    }
    
    private void ExcluirConta(Activity activity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //set title
        builder.setTitle("Excluir Conta");
        builder.setMessage("Voce deseja realmente excluir a conta?");

        //bt sim
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //codigo firebase
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                usuarioAtualId = FirebaseAuth.getInstance().getCurrentUser().getUid();


                db.collection("Usuarios").document(usuarioAtualId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void unused) {
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    msfToast("Conta Excluída com sucesso");
                                    ChamarTelaLogin();
                                    Log.d(TAG, "User account deleted.");

                                }else{
                                    msfToast("Não Foi Possível Excluir Conta");
                                }
                            }
                        });
                    }

                });

            }
        });

        //bt nao
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //fechar dialogo
                dialog.dismiss();
            }
        });
        //show dialog
        builder.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //current user
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        usuarioAtualId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioAtualId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                if (documentSnapshot != null){
                    campo_nome.setText(documentSnapshot.getString("nome"));
                    campo_email.setText(email);
                    campo_telefone.setText(documentSnapshot.getString("telefone"));
                    campo_endereco.setText(documentSnapshot.getString("endereco"));
                    campo_cidade.setText(documentSnapshot.getString("cidade"));
                    campo_estado.setText(documentSnapshot.getString("estado"));

                }
            }
        });
    }

    private void IniciarComponentes(){

        campo_nome = findViewById(R.id.campo_nome);
        campo_email = findViewById(R.id.campo_email);
        campo_telefone = findViewById(R.id.campo_telefone);
        campo_endereco = findViewById(R.id.campo_endereco);
        campo_cidade = findViewById(R.id.campo_cidade);
        campo_estado = findViewById(R.id.campo_estado);
        bt_editarDados = findViewById(R.id.bt_editarDados);
        bt_excluirConta = findViewById(R.id.bt_excluirConta);

    }

    public void ChamarTelaLogin() {
        Intent intent = new Intent(PerfilUsuario.this, FormLogin.class);
        startActivity(intent);
        finish();
    }

    private void msfToast(String s) {
        Toast.makeText(getApplicationContext(), s , Toast.LENGTH_SHORT).show();
    }

}