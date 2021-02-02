package com.example.vegasaurius;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class Recetario extends AppCompatActivity {

    private DrawerLayout myDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recetario);
        mNavigationView =  findViewById(R.id.navigationView);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        intent = new Intent(Recetario.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_recetario:
                        intent = new Intent(Recetario.this, Categorias.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_nuevaReceta:
                        intent = new Intent(Recetario.this, plantillaRecetas.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_menu:
                        intent = new Intent(Recetario.this, GestionMenus.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_listaCompra:
                        intent = new Intent(Recetario.this, ListasCompra.class);
                        startActivity(intent);



                }
                //close the drawer
                myDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        myDrawerLayout = findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, myDrawerLayout, R.string.open, R.string.close );
        myDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void ir_a_plantilla(View v){
        Intent i = new Intent(this , plantillaRecetas.class);
        startActivity(i);
    }

    public void ir_a_recetas(View v){
        Intent i = new Intent(this , Categorias.class);
        startActivity(i);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

