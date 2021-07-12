package com.example.shophouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.core.OrderBy;
import com.google.firestore.v1.StructuredQuery;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //iniciando variaveis
    DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    String usuarioAtualId;

    //contexto
    Context context;

    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //esconder toolbar
        getSupportActionBar().hide();
        //assing variavel
        drawerLayout = findViewById(R.id.drawer_layout);

        //instancia do firestore
        firebaseFirestore = FirebaseFirestore.getInstance();
        // pegando recyclerview
        recyclerView = findViewById(R.id.recyclerView);

        //query
        usuarioAtualId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Query query = firebaseFirestore.collection("Imoveis")/*.whereEqualTo("id_user", usuarioAtualId)*/.orderBy("data_cadastro", Query.Direction.DESCENDING);
        //recycleroptions
        FirestoreRecyclerOptions<Imovel> options = new FirestoreRecyclerOptions.Builder<Imovel>()
                .setQuery(query, Imovel.class)
                .build();
        //firestore recycler adapter
        adapter = new FirestoreRecyclerAdapter<Imovel, ImovelViewHolder>(options) {

            @NonNull
            @NotNull
            @Override
            public ImovelViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {


                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.imovel_item, parent, false);
                return new ImovelViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull ImovelViewHolder holder, int position, @NonNull @NotNull Imovel model) {
                //
                //

                Picasso.get().load(model.getImg()).resize(60, 60).centerCrop().into(holder.imagemImovel);

                holder.titulo.setText(model.getTitulo());
                holder.cidade.setText(model.getCidade());
                holder.estado.setText(model.getEstado());

                //clicar no Imovel
                holder.cardLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), ViewImovel.class);

                        intent.putExtra("img", model.getImg());
                        intent.putExtra("titulo", model.getTitulo());
                        intent.putExtra("descricao", model.getDescricao());
                        intent.putExtra("endereco", model.getEndereco());
                        intent.putExtra("cidade", model.getCidade());
                        intent.putExtra("estado", model.getEstado());
                        intent.putExtra("telefone", model.getTelefone());
                        intent.putExtra("email", model.getEmail());


                        v.getContext().startActivity(intent);
                    }
                });

            }

        };

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    public void ClickMenu(View view){
        //abrir drawer
        openDrawer(drawerLayout);

    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        // open drawe layout
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        //fechar drawer
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        //close draw layout
        //checando
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            //quando est aaberta
            //fechar
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickNewImovel(View view){
        redirectActivity(this, FormCadastroImovel.class);
    }

    public void ClickHome(View view){
        //recreat atividade
        recreate();
    }

    public void ClickDashboard(View view){
        //redirect para dashboard atividae
        redirectActivity(this, Dashboard.class);
    }

    public void ClickPerfil(View view){
    //about us
        redirectActivity(this, PerfilUsuario.class);
    }

    public void ClickLogout(View view){
        //close app
        Logout(this);
        //redirectActivity(this,);
    }

    public void Logout(Activity activity) {
        //inicializado aleta modal
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //set title
        builder.setTitle("Logout");
        builder.setMessage("Voce deseja realmente sair?");
        //bt sim
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //finish activy
                //logout
                FirebaseAuth.getInstance().signOut();

                ChamarTelaLogin();

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

    public void ChamarTelaLogin() {

        //redirecionar para tela de login
        Intent intent = new Intent(MainActivity.this, FormLogin.class);
        startActivity(intent);
        finish();
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        //inicializando intent
        Intent intent = new Intent(activity, aClass);
        //set flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //start actividade
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //close drawer
        closeDrawer(drawerLayout);
    }




    //teste firebase ui
    //Imovel View Holder

    private class ImovelViewHolder extends RecyclerView.ViewHolder {

        private ImageView imagemImovel;

        private TextView titulo;
        private TextView cidade;
        private TextView estado;

        //id item card
        ConstraintLayout cardLayout;

        public ImovelViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imagemImovel = itemView.findViewById(R.id.imagemImovel);
            titulo = itemView.findViewById(R.id.textViewTituloImovel);
            cidade = itemView.findViewById(R.id.textViewCidade);
            estado = itemView.findViewById(R.id.textViewEstado);

            cardLayout = itemView.findViewById(R.id.cardLayout);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}