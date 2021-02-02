package com.example.vegasaurius;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.vegasaurius.Model.Ingrediente;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListaAlimentos extends AppCompatActivity {

    ListView listaIngredientes;
    String tituloMenu;
    int numPersonas;
    DBHelper dbHelper;
    ArrayList<Ingrediente> items;
    TextView tituloLista;
    private DrawerLayout myDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;





    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_alimentos);
    //Menu lateral
        mNavigationView =  findViewById(R.id.navigationView);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        intent = new Intent(ListaAlimentos.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_recetario:
                        intent = new Intent(ListaAlimentos.this, Categorias.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_nuevaReceta:
                        intent = new Intent(ListaAlimentos.this, plantillaRecetas.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_menu:
                        intent = new Intent(ListaAlimentos.this, GestionMenus.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_listaCompra:
                        intent = new Intent(ListaAlimentos.this, ListasCompra.class);
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


        tituloLista = findViewById(R.id.tvTituloListaIngredientes);
        listaIngredientes = findViewById(R.id.lvListaIngredientesMenu);
        dbHelper = new DBHelper(this);
        tituloMenu = getIntent().getStringExtra("TituloMenu");
        numPersonas = getIntent().getIntExtra("CantidadMenu" , 2);
        tituloLista.setText(tituloMenu);

        //Carga la lista de ingredientes para la elaboración del menú
        Cursor ingredientes = dbHelper.consultarIngredientesMenu(tituloMenu);
        ArrayList<String> datosIngredientes = new ArrayList<String>();
        if (ingredientes.moveToFirst()) {
            do {
                datosIngredientes.add(ingredientes.getString(0));

            } while (ingredientes.moveToNext());

            //Crea un Array de objetos tipo Ingrediente usando la libreria Gson
            ArrayList<Ingrediente> itemsCompletos = new ArrayList<Ingrediente>();
            for (int i = 0; i < datosIngredientes.size(); i++) {
                String s = datosIngredientes.get(i);
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Ingrediente>>(){}.getType();
                //Rellena el array  de objetos
                items = gson.fromJson(s, type);
                //Por cada ingrediente multiplica la cantidad por el numero de personas
                for (int j = 0; j < items.size(); j++) {
                    Ingrediente ing = items.get(j);
                    ing.setCantidad(ing.getCantidad() * numPersonas);
                    itemsCompletos.add(ing);
                }
                //Recorre el array comparando los ingredientes y si se repiten suma las cantidades y elimina
                // el ingrediente duplicado.
                for (int h = 0; h < itemsCompletos.size(); h++) {
                    Ingrediente ingredienteActual = itemsCompletos.get(h);
                    for (int j = h + 1; j < itemsCompletos.size(); j++) {
                        Ingrediente compare = itemsCompletos.get(j);
                        if (ingredienteActual.getNombre().equals(compare.getNombre())) {
                            ingredienteActual.setCantidad(ingredienteActual.getCantidad() + compare.getCantidad());
                            itemsCompletos.remove(compare);
                            j--;
                        }
                    }
                }

                //Carga la lista con los ingredientes sin duplicar
                ArrayAdapter adaptadorIngredientes = new ArrayAdapter
                        (this, R.layout.estilo_elementos_lista, itemsCompletos);
                listaIngredientes.setAdapter(adaptadorIngredientes);

                // si se pulsa sobre un ingrediente lo colorea
                listaIngredientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        listaIngredientes.getChildAt(position).setBackgroundColor(Color.parseColor("#00574B"));
                    }
                });
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
