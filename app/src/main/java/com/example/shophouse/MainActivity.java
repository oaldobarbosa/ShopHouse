package com.example.shophouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    //iniciando variaveis
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //esconder toolbar
        getSupportActionBar().hide();

        //assing variavel
        drawerLayout = findViewById(R.id.drawer_layout);
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
        logout(this);
        //redirectActivity(this,);
    }

    public static void logout(Activity activity) {
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
                activity.finishAffinity();
                //exit
                System.exit(0);
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