package com.example.shophouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    //iniciando variaveis
    DrawerLayout drawerLayout;
    RecyclerView recyclerView;

    //dados para teste
    String s1[], s2[];
    int images[] = {R.drawable.logo, R.drawable.logo, R.drawable.logo,
            R.drawable.logo, R.drawable.logo, R.drawable.logo,
            R.drawable.logo, R.drawable.logo, R.drawable.logo, R.drawable.logo};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        //ImovelAdapter
        ImovelAdapter imovelAdapter = new ImovelAdapter(this, s1, s2, images);
        recyclerView.setAdapter(imovelAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


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
}