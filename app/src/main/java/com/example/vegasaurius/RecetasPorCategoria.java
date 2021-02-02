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

public class RecetasPorCategoria extends AppCompatActivity {

    ArrayAdapter ADP;
    DBHelper dbHelper;
    ListView lvLista;
    TextView prueba;
    String titulo;
    private DrawerLayout myDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;


    protected  void onCreate (Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.recetas_por_categoria);
        mNavigationView =  findViewById(R.id.navigationView);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        intent = new Intent(RecetasPorCategoria.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_recetario:
                        intent = new Intent(RecetasPorCategoria.this, Categorias.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_nuevaReceta:
                        intent = new Intent(RecetasPorCategoria.this, plantillaRecetas.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_menu:
                        intent = new Intent(RecetasPorCategoria.this, GestionMenus.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_listaCompra:
                        intent = new Intent(RecetasPorCategoria.this, ListasCompra.class);
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
        lvLista = findViewById(R.id.listaCategorias);
        String categoria = getIntent().getStringExtra("Categoria");
        prueba = findViewById(R.id.etCategoria);
        prueba.setText(categoria);

        //Carga la lista con las recetas de la categoria indicada
        Cursor c = dbHelper.consultarPorCategoria(categoria);
        final ArrayList<String> datos = new ArrayList<String>();
        if (c.moveToFirst()) {
            do {
                datos.add(c.getString(1));
            } while (c.moveToNext());
            ArrayAdapter<String> adaptador = new ArrayAdapter<String>
                    (this, R.layout.estilo_elementos_lista_grande, datos);
            lvLista.setAdapter(adaptador);

            //Muestra las opciones disponibles al pulsar sobre una receta
            lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                     titulo = datos.get(position);
                     muestraDialogo();
                }
            });
        }
    }

    private void muestraDialogo(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alerta = builder.create();
        builder.setTitle("Elige una opción");
        builder.setItems(new CharSequence[]
                        {"Mostrar receta", "Editar receta", "Borrar receta", "Volver"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which) {
                            case 0:
                                ir_a_Receta_mostrada();
                                break;
                            case 1:
                                ir_a_modificar_receta();
                                break;
                            case 2:
                                AlertDialog.Builder dialogo = new AlertDialog.Builder(RecetasPorCategoria.this);
                                final AlertDialog alerta2 = builder.create();
                                dialogo.setTitle("Atención");
                                dialogo.setMessage("¿Seguro que quieres borrar esta receta?");
                                dialogo.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dbHelper.borrarReceta(titulo);
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
                            case 3:
                                alerta.cancel();
                                break;
                        }
                    }
                });
        builder.create().show();
    }

    public void ir_a_Receta_mostrada() {
        Intent i = new Intent(this, RecetaMostrada.class);
        i.putExtra("Titulo", titulo);
        startActivity(i);
    }

    public void ir_a_modificar_receta() {
        Intent i = new Intent(this, ModificarReceta.class);
        i.putExtra("Titulo", titulo);
        startActivity(i);
    }

    public void ir_a_categorias(View v) {
        Intent i = new Intent(this, Categorias.class);
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
