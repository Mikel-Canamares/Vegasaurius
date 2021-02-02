package com.example.vegasaurius;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class ListasCompra extends AppCompatActivity {
    ListView listaMenus;
    DBHelper dbHelper;
    String titulo;
    int numeroPersonas;
    private DrawerLayout myDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_menus);
        //Menu lateral
        mNavigationView =  findViewById(R.id.navigationView);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        intent = new Intent(ListasCompra.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_recetario:
                        intent = new Intent(ListasCompra.this, Categorias.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_nuevaReceta:
                        intent = new Intent(ListasCompra.this, plantillaRecetas.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_menu:
                        intent = new Intent(ListasCompra.this, GestionMenus.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_listaCompra:
                        intent = new Intent(ListasCompra.this, ListasCompra.class);
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

        dbHelper = new DBHelper(this);
        listaMenus = findViewById(R.id.lvListaMenus);

        //Carga la lista con los nombres de los menús almacenados
        Cursor c = dbHelper.consultarTitulosMenu();
        final ArrayList<String> titulosMenus = new ArrayList<String>();
        final ArrayList<Integer> personasMenus = new ArrayList<Integer>();
        if (c.moveToFirst()) {
            do {
                titulosMenus.add(c.getString(4));
                personasMenus.add(c.getInt(5));
            } while (c.moveToNext());
            ArrayAdapter<String> adaptadorTitulos = new ArrayAdapter<String>
                    (this, R.layout.estilo_elementos_lista_grande, titulosMenus);
            listaMenus.setAdapter(adaptadorTitulos);
            listaMenus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    titulo = titulosMenus.get(position);
                    numeroPersonas = personasMenus.get(position);
                    cargarLista(titulo, numeroPersonas);
                }
            });

        }

    }
    private void cargarLista (String tituloMenu, int numPersonas){
        Intent i = new Intent(this, ListaAlimentos.class);
        i.putExtra("TituloMenu", tituloMenu);
        i.putExtra("CantidadMenu", numPersonas);
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
