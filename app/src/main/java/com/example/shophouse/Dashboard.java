package com.example.shophouse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import static com.example.shophouse.MainActivity.openDrawer;

public class Dashboard extends AppCompatActivity {
    //iniciando variaveis
    DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    String usuarioAtualId;

    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    private TextView titulo_toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //esconder toolbar
        getSupportActionBar().hide();

        titulo_toolbar = findViewById(R.id.titulo_toolbar);
        titulo_toolbar.setText("Dashboard");

        //assing variavel
        drawerLayout = findViewById(R.id.drawer_layout);
        //implementando recycle
        recyclerView = findViewById(R.id.recyclerView);
        //instancia firestore
        firebaseFirestore = FirebaseFirestore.getInstance();

        usuarioAtualId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Query query = firebaseFirestore.collection("Imoveis").whereEqualTo("id_user", usuarioAtualId).orderBy("data_cadastro", Query.Direction.DESCENDING);
        //recycleroptions
        FirestoreRecyclerOptions<Imovel> options = new FirestoreRecyclerOptions.Builder<Imovel>()
                .setQuery(query, Imovel.class)
                .build();
        //firestore recycler adapter
        adapter = new FirestoreRecyclerAdapter<Imovel, ImovelPropViewHolder>(options) {

            @NonNull
            @NotNull
            @Override
            public ImovelPropViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {


                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.imovel_item, parent, false);
                return new ImovelPropViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull ImovelPropViewHolder holder, int position, @NonNull @NotNull Imovel model) {
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
                        Intent intent = new Intent(v.getContext(), ViewImovelProp.class);

                        intent.putExtra("img", model.getImg());
                        intent.putExtra("titulo", model.getTitulo());
                        intent.putExtra("descricao", model.getDescricao());
                        intent.putExtra("endereco", model.getEndereco());
                        intent.putExtra("cidade", model.getCidade());
                        intent.putExtra("estado", model.getEstado());
                        intent.putExtra("telefone", model.getTelefone());
                        intent.putExtra("email", model.getEmail());
                        intent.putExtra("id_imovel", model.getId_imovel());

                        v.getContext().startActivity(intent);
                    }
                });
            }
        };

        //recyclerViewSets
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    public void ClickNewImovel(View view){
        MainActivity.redirectActivity(this, FormCadastroImovel.class);
    }

    public void ClickMenu(View view){
        //open drawer
        MainActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view){
        MainActivity.closeDrawer(drawerLayout);
    }

    public void ClickHome(View view){
        //redirect para home
        MainActivity.redirectActivity(this, MainActivity.class);
        finish();
    }

    public void ClickPerfil(View view){
        MainActivity.redirectActivity(this, PerfilUsuario.class);
    }

    public void ClickDashboard(View view){
        MainActivity.closeDrawer(drawerLayout);
    }

    public void ClickLogout(View view){
        Logout(this);
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

                //exit
                //System.exit(0);
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
        Intent intent = new Intent(Dashboard.this, FormLogin.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }

    //ViewHolderDashboard
    private class ImovelPropViewHolder extends RecyclerView.ViewHolder {

        private ImageView imagemImovel;
        private TextView titulo;
        private TextView cidade;
        private TextView estado;

        //id item card
        ConstraintLayout cardLayout;

        public ImovelPropViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imagemImovel = itemView.findViewById(R.id.imagemImovel);
            titulo = itemView.findViewById(R.id.textViewTituloImovel);
            cidade = itemView.findViewById(R.id.textViewCidade);
            estado = itemView.findViewById(R.id.textViewEstado);
            cardLayout = itemView.findViewById(R.id.cardLayout);
        }
    }

    //stop adapter
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    //start adapter
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
