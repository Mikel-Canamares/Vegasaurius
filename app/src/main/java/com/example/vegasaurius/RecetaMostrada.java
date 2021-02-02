package com.example.vegasaurius;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vegasaurius.Model.Ingrediente;
import com.example.vegasaurius.Model.Receta;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RecetaMostrada extends AppCompatActivity {

    ImageView image;
    ListView rvLista;
    TextView tituloRecuperado, tipoRecuperado;
    TextView elaboracionRecuperada;
    List<Ingrediente> items;
    ArrayAdapter ADP;
    DBHelper dbHelper;
    private DrawerLayout myDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.receta_mostrada);
        //Menú lateral
        mNavigationView =  findViewById(R.id.navigationView);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        intent = new Intent(RecetaMostrada.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_recetario:
                        intent = new Intent(RecetaMostrada.this, Categorias.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_nuevaReceta:
                        intent = new Intent(RecetaMostrada.this, plantillaRecetas.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_menu:
                        intent = new Intent(RecetaMostrada.this, GestionMenus.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_listaCompra:
                        intent = new Intent(RecetaMostrada.this, ListasCompra.class);
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
        tipoRecuperado = findViewById(R.id.tvTipo);
        image = findViewById(R.id.ivImagen);
        rvLista = findViewById(R.id.lista);
        tituloRecuperado = findViewById(R.id.tvTitulo);
        elaboracionRecuperada = findViewById(R.id.etElaboracion);

        //Muestra la plantilla con los datos de la receta recuperados de la BBDD
        String tituloRecibido = getIntent().getStringExtra("Titulo");
        Receta datosReceta = dbHelper.obtenerDatos(tituloRecibido);
        String titulo = datosReceta.getTitulo();
        String tipo = datosReceta.getTipo();
        byte [] foto = datosReceta.getFoto();
        String ingredientes = datosReceta.getIngredientes();
        String elaboracion = datosReceta.getElaboracion();
        tituloRecuperado.setText(titulo);
        tipoRecuperado.setText(tipo);
        if (foto != null){
            Bitmap bitmap = Utils.getImage(foto);
            image.setImageBitmap(bitmap);
        }
        else{
            Drawable dinosaurioFoto = getResources().getDrawable(R.drawable.botonrecetas);
            image.setImageDrawable(dinosaurioFoto);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Ingrediente>>(){}.getType();
        items = gson.fromJson(ingredientes, type);
        ADP = new ArrayAdapter(getApplicationContext(),R.layout.estilo_elementos_lista, items);
        rvLista.setAdapter(ADP);
        elaboracionRecuperada.setText(elaboracion); 
        }

        public void ir_a_categorias(View v){
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
