package com.example.vegasaurius;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {

    DBHelper dbHelper;
    ListView listaComidas, listaCenas , listaOtros;
    CharSequence [] tiposRecetas = {"Arroces", "Ensaladas", "Guisos","Sopas y cremas","Tapas y aperitivos","Verduras", "Otros"};
    TextView titulo, numeroPersonas;
    String tituloMenu;
    int numPersonas;
    String tituloReceta;
    int identificador;
    private DrawerLayout myDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        //Creación de un menú lateral
        mNavigationView =  findViewById(R.id.navigationView);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        intent = new Intent(Menu.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_recetario:
                        intent = new Intent(Menu.this, Categorias.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_nuevaReceta:
                        intent = new Intent(Menu.this, plantillaRecetas.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_menu:
                        intent = new Intent(Menu.this, GestionMenus.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_listaCompra:
                        intent = new Intent(Menu.this, ListasCompra.class);
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

        //Obtención de los datos mandados desde otras Activities
        titulo = findViewById(R.id.titulo);
        tituloMenu = getIntent().getStringExtra("TituloMenu");
        titulo.setText(tituloMenu);

        numeroPersonas = findViewById(R.id.tvNumeroPersonas);
        numPersonas = getIntent().getIntExtra("CantidadMenu" , 2);
        numeroPersonas.setText("Personas: "+ numPersonas);

        dbHelper = new DBHelper(this);
        listaComidas = findViewById(R.id.listaComidas);
        listaCenas = findViewById(R.id.listaCenas);
        listaOtros = findViewById(R.id.listaOtros);

        //Carga la lista de comidas con los datos almacenados en la tabla RecetaMenu
        Cursor comida = dbHelper.consultarMenu("Comida", tituloMenu);
        final ArrayList<String> datosComidas = new ArrayList<String>();
        final ArrayList<Integer> datosIdComidas = new ArrayList<Integer>();
        if (comida.moveToFirst()){
            do {
                datosIdComidas.add(comida.getInt(0));
                datosComidas.add(comida.getString(1));
            } while (comida.moveToNext());
            ArrayAdapter<String> adaptadorComidas = new ArrayAdapter<String>(this, R.layout.estilo_elementos_lista, datosComidas);
            listaComidas.setNestedScrollingEnabled(true);
            listaComidas.setAdapter(adaptadorComidas);

            //Si se pulsa un elemento muestra un dialogo con las opciones disponibles
            listaComidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    identificador=  datosIdComidas.get(position);
                    tituloReceta = datosComidas.get(position);
                    muestraDialogo();
                }
            });
        }
        //Carga la lista de cenas con los datos almacenados en la tabla RecetaMenu
        Cursor cena = dbHelper.consultarMenu("Cena", tituloMenu);
        final ArrayList<String> datosCenas = new ArrayList<String>();
        final ArrayList<Integer> datosIdCenas = new ArrayList<Integer>();
        if (cena.moveToFirst()){
            do {
                 datosIdCenas.add(cena.getInt(0));
                datosCenas.add(cena.getString(1));
            } while (cena.moveToNext());
            ArrayAdapter<String> adaptadorCenas = new ArrayAdapter<String>(this, R.layout.estilo_elementos_lista, datosCenas);
            listaCenas.setNestedScrollingEnabled(true);
            listaCenas.setAdapter(adaptadorCenas);
            //Si se pulsa un elemento muestra un dialogo con las opciones disponibles
            listaCenas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    identificador=  datosIdCenas.get(position);
                    tituloReceta = datosCenas.get(position);
                    muestraDialogo();
                }
            });
        }

        //Carga la lista de Otros con los datos almacenados en la tabla RecetaMenu
        Cursor otros = dbHelper.consultarMenu("Otros", tituloMenu);
        final ArrayList<String> datosOtros = new ArrayList<String>();
        final ArrayList<Integer> datosIdOtros = new ArrayList<Integer>();
        if (otros.moveToFirst()){
            do {
                datosIdOtros.add(otros.getInt(0));
                datosOtros.add(otros.getString(1));
            } while (otros.moveToNext());
            ArrayAdapter<String> adaptadorOtros = new ArrayAdapter<String>(this, R.layout.estilo_elementos_lista, datosOtros);
            listaOtros.setNestedScrollingEnabled(true);
            listaOtros.setAdapter(adaptadorOtros);
            listaOtros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    identificador=  datosIdOtros.get(position);
                    tituloReceta = datosOtros.get(position);
                    muestraDialogo();
                }
            });
        }

        //FIN OnCreate
    }

    public void ir_a_lista_menus (View v){
        Intent i = new Intent(this, ListaMenus.class);
        startActivity(i);
    }

    //Métodos para mostrar dialogos en función de si son comidas cenas u otros

    public void muestraTiposComidas(View v){
        final AlertDialog.Builder dialogo = new AlertDialog.Builder(Menu.this);
        dialogo.setTitle("Escoge");
        dialogo.setItems(tiposRecetas, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                cargarLista(tiposRecetas[item], "Comida", tituloMenu, numPersonas);
            }
        });
        dialogo.show();
    }

    public void muestraTiposCenas(View v){
        final AlertDialog.Builder dialogo = new AlertDialog.Builder(Menu.this);
        dialogo.setTitle("Escoge");
        dialogo.setItems(tiposRecetas, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                cargarLista(tiposRecetas[item], "Cena", tituloMenu, numPersonas);
            }
        });
        dialogo.show();
    }

    public void muestraTiposOtros(View v){
        final AlertDialog.Builder dialogo = new AlertDialog.Builder(Menu.this);
        dialogo.setTitle("Escoge");
        dialogo.setItems(tiposRecetas, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                cargarLista(tiposRecetas[item], "Otros", tituloMenu, numPersonas);
            }
        });
        dialogo.show();
    }

    //Manda los datos del menú
    private void cargarLista (CharSequence tipo, String hora, String nombreMenu, int numeroPersonas){
        Intent i = new Intent(this, CategoriaMenu.class);
        i.putExtra("Categoria", tipo);
        i.putExtra("Hora", hora);
        i.putExtra("NombreMenu", nombreMenu);
        i.putExtra("NumeroPersonas", numeroPersonas);
        startActivity(i);
    }

    //Muestra dialogo con las opciones disponibles.
    private void muestraDialogo(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alerta = builder.create();
        builder.setTitle("Elige una opción");
        builder.setItems(new CharSequence[]
                        {"Mostrar receta", "Borrar plato del menú", "Volver"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which) {
                            case 0:
                                ir_a_Receta_mostrada();
                                break;
                            case 1:
                                AlertDialog.Builder dialogo = new AlertDialog.Builder(Menu.this);
                                final AlertDialog alerta2 = builder.create();
                                dialogo.setTitle("Atención");
                                dialogo.setMessage("¿Seguro que quieres borrar este plato?");
                                dialogo.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dbHelper.borrarRecetaMenu(identificador);
                                        finish();
                                        startActivity(getIntent());
                                    }
                                });
                                dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        alerta2.cancel();
                                    }
                                });
                                dialogo.show();
                                break;
                            case 2:
                                alerta.cancel();
                                break;
                        }
                    }
                });
        builder.create().show();
    }

    public void ir_a_Receta_mostrada() {
        Intent i = new Intent(this, RecetaMostrada.class);
        i.putExtra("Titulo", tituloReceta);
        startActivity(i);
    }

    //Metodo del menú lateral
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
