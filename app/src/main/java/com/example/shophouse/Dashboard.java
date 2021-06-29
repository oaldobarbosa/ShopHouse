package com.example.shophouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import static com.example.shophouse.MainActivity.openDrawer;

public class Dashboard extends AppCompatActivity {
    //iniciando variaveis
    DrawerLayout drawerLayout;
    RecyclerView recyclerView;

    //dados para teste
    //dados para teste
    String s1[], s2[];
    int images[] = {R.drawable.logo, R.drawable.logo, R.drawable.logo,
            R.drawable.logo, R.drawable.logo, R.drawable.logo,
            R.drawable.logo, R.drawable.logo, R.drawable.logo, R.drawable.logo};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //esconder toolbar
        getSupportActionBar().hide();

        //assing variavel
        drawerLayout = findViewById(R.id.drawer_layout);

        //implementando recycle
        //recyclerview
        recyclerView = findViewById(R.id.recyclerView);
        //
        //
        //
        //implementando recycle
        s1 = getResources().getStringArray(R.array.programming_languages);
        s2 = getResources().getStringArray(R.array.description);
        //
        //
        //
        //ImovelpropAdapter
        ImovelPropAdapter imovelPropAdapter = new ImovelPropAdapter(this, s1, s2, images);
        recyclerView.setAdapter(imovelPropAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


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
    }

    public void ClickPerfil(View view){
        MainActivity.redirectActivity(this, PerfilUsuario.class);
    }

    public void ClickDashboard(View view){
        recreate();
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
}