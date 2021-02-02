package com.example.vegasaurius;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class GestionMenus extends AppCompatActivity {

    private String tituloMenu;
    private int cantidadMenu;
    private DrawerLayout myDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gestion_menus);
        //Menú lateral
        mNavigationView =  findViewById(R.id.navigationView);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        intent = new Intent(GestionMenus.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_recetario:
                        intent = new Intent(GestionMenus.this, Categorias.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_nuevaReceta:
                        intent = new Intent(GestionMenus.this, plantillaRecetas.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_menu:
                        intent = new Intent(GestionMenus.this, GestionMenus.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_listaCompra:
                        intent = new Intent(GestionMenus.this, ListasCompra.class);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //Crea un nuevo menú socilitando nombre y numero de personas
    public void nuevoMenu(View v){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(GestionMenus.this);
        dialogo.setTitle("Introduce el nombre del menú");
        final EditText nombreMenuInput = new EditText(GestionMenus.this);
        nombreMenuInput.setInputType(InputType.TYPE_CLASS_TEXT);
        dialogo.setView(nombreMenuInput);
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tituloMenu = nombreMenuInput.getText().toString();
                AlertDialog.Builder dialogo = new AlertDialog.Builder(GestionMenus.this);
                dialogo.setTitle("Introduce la cantidad de personas");
                final EditText cantidadMenuInput = new EditText(GestionMenus.this);
                cantidadMenuInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                dialogo.setView(cantidadMenuInput);
                dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String textoCantidad = cantidadMenuInput.getText().toString();
                        cantidadMenu = Integer.parseInt(textoCantidad);
                        cargarMenu(tituloMenu, cantidadMenu);
                    }
                });
                dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialogo.show();
            }
        });
        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialogo.show();
    }

    private void cargarMenu (String tituloMenu, int personasMenu){
        Intent i = new Intent(this, Menu.class);
        i.putExtra("TituloMenu", tituloMenu);
        i.putExtra("CantidadMenu", personasMenu);
        startActivity(i);
    }

    public void verMenus (View v){
        Intent i = new Intent(this, ListaMenus.class);
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
