package com.example.shophouse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FormCadastroImovel extends AppCompatActivity {

    private EditText edit_titulo, edit_descricao, edit_endereco, edit_estado, edit_cidade, edit_telefone, edit_email;
    private Button bt_cadastrarImovel;
    String[] mensagens = {"Preencha Todos os Campos", "Selecione uma Imagem"};
    String usuarioAtualId;

    //criando referencia na unha
    private StorageReference storageRef;
    private ImageView iv_imagemSelecionada;
    private Button bt_selecionarImagem;


    //private Spinnner
    private Spinner select_estado;
    private Spinner select_cidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro_imovel);
        storageRef = FirebaseStorage.getInstance().getReference();

        getSupportActionBar().hide();
        //iniciando componentes
        IniciarComponentes();

        //spinner estado
        ArrayAdapter<String> estadoAdapterSpinner = new ArrayAdapter<>(FormCadastroImovel.this, android.R.layout.
                simple_list_item_1, getResources().getStringArray(R.array.estados));
        estadoAdapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_estado.setAdapter(estadoAdapterSpinner);

        //spinner cidade
        ArrayAdapter<String> cidadeAdapterSpinner = new ArrayAdapter<>(FormCadastroImovel.this, android.R.layout.
                simple_list_item_1, getResources().getStringArray(R.array.cidades));
        cidadeAdapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_cidade.setAdapter(cidadeAdapterSpinner);

        //ao clicar no bt_cadastrarImovel
        bt_cadastrarImovel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titulo = edit_titulo.getText().toString();
                String descricao = edit_descricao.getText().toString();
                String endereco = edit_endereco.getText().toString();
                String estado = select_estado.getSelectedItem().toString();
                String cidade = select_cidade.getSelectedItem().toString();
                String telefone = edit_telefone.getText().toString();
                String email = edit_email.getText().toString();
                Drawable img = iv_imagemSelecionada.getDrawable();


                if (titulo.isEmpty() || descricao.isEmpty() || endereco.isEmpty() || estado.isEmpty() || cidade.isEmpty() ||
                telefone.isEmpty() || email.isEmpty() ){

                    Snackbar snackbar = Snackbar.make(v, mensagens[0], Snackbar.LENGTH_SHORT);
                    View view = snackbar.getView();
                    FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
                    params.gravity = Gravity.TOP;
                    view.setLayoutParams(params);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();

                }else if( img == null){

                    Snackbar snackbar = Snackbar.make(v, mensagens[1], Snackbar.LENGTH_SHORT);
                    View view = snackbar.getView();
                    FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
                    params.gravity = Gravity.TOP;
                    view.setLayoutParams(params);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();

                }else{
                    SalvarDadosImovel();
                }
            }
        });

        //botão para selecionar imagem
        bt_selecionarImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionarImage();
            }
        });
    }

    //funcao para selecionar imagem
    private void selecionarImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "SelecionarImagem" ), 1);
    }

    //recebe a imagem da galeria e seta na view
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1){
            Uri uri = data.getData();
            iv_imagemSelecionada.setImageURI(uri);
            //iv_imagemSelecionada.getLayoutParams().height = 180;
        }
    }

    private void SalvarDadosImovel(){

        //salvando imagem
        Bitmap bitmap = ( (BitmapDrawable)iv_imagemSelecionada.getDrawable() ).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imagem = byteArrayOutputStream.toByteArray();

        String filename = UUID.randomUUID().toString();
        StorageReference imgRef = storageRef.child(filename);

        UploadTask uploadTask = imgRef.putBytes(imagem);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        String url = uri.toString();
                        String titulo = edit_titulo.getText().toString();
                        String descricao = edit_descricao.getText().toString();
                        String endereco = edit_endereco.getText().toString();
                        String estado = select_estado.getSelectedItem().toString();
                        String cidade = select_cidade.getSelectedItem().toString();
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
                        imoveis.put("data_cadastro", new Timestamp(new Date()));
                        imoveis.put("img", url);

                        DocumentReference documentReference = db.collection("Imoveis").document();
                        String id_imovel = documentReference.getPath().substring(8);//gambiarra para remover o "Imoveis/" kkkk
                        imoveis.put("id_imovel", id_imovel);

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
                });

                //exibir mensagem e redirecionar pra activity principal
                msfToast("Imóvel Cadastrado com Sucesso!");
                Intent intent = new Intent(FormCadastroImovel.this, MainActivity.class);
                startActivity(intent);

            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                msfToast("Selecione Uma Imagem");
            }
        });

    }

    //mensagem
    private void msfToast(String s) {
        Toast.makeText(getApplicationContext(), s , Toast.LENGTH_SHORT).show();
    }

    //funcao para pegar os ids
    private void IniciarComponentes() {

        edit_titulo = findViewById(R.id.edit_titulo);
        edit_descricao = findViewById(R.id.edit_descricao);
        edit_endereco = findViewById(R.id.edit_endereco);
        edit_telefone = findViewById(R.id.edit_telefone);
        edit_email = findViewById(R.id.edit_email);

        //mascara telefone
        SimpleMaskFormatter smf = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(edit_telefone, smf);
        edit_telefone.addTextChangedListener(mtw);

        //selects
        select_estado = (Spinner)findViewById(R.id.select_estado);
        select_cidade = (Spinner)findViewById(R.id.select_cidade);
        iv_imagemSelecionada = (ImageView)findViewById(R.id.iv_imagemSelecionada);
        bt_selecionarImagem = (Button)findViewById(R.id.bt_selecionarImagem);
        bt_cadastrarImovel = findViewById(R.id.bt_cadastrarImovel);

    }

}