package com.example.shophouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

import static com.example.shophouse.MainActivity.openDrawer;

public class Dashboard extends AppCompatActivity {
    //iniciando variaveis
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //esconder toolbar
        getSupportActionBar().hide();

        //assing variavel
        drawerLayout = findViewById(R.id.drawer_layout);
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
        MainActivity.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }
}