package com.example.vegasaurius;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vegasaurius.Model.RecetaMenu;

import java.util.ArrayList;

public class CategoriaMenu extends AppCompatActivity {

    DBHelper dbHelper;
    ListView lvLista;
    TextView nombre;
    String titulo;

    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.recetas_por_categoria);
        dbHelper = new DBHelper(this);
        lvLista = findViewById(R.id.listaCategorias);

        //Obtiene los datos recibidos desde la creación de menús
        String categoria = getIntent().getStringExtra("Categoria");
        final String hora = getIntent().getStringExtra("Hora");
        final String tituloMenu = getIntent().getStringExtra("NombreMenu");
        final int numeroPersonas = getIntent().getIntExtra("NumeroPersonas" , 2);
        nombre = findViewById(R.id.etCategoria);
        nombre.setText(categoria);

        //Carga la lista con los datos obtenidos de la BBDD
        Cursor c = dbHelper.consultarPorCategoria(categoria);
        final ArrayList<String> datos = new ArrayList<String>();
        if (c.moveToFirst()) {
            do {
                datos.add(c.getString(1));
            } while (c.moveToNext());
            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.estilo_elementos_lista_grande, datos);
            lvLista.setAdapter(adaptador);

            //Al pulsar sobre una receta obtiene los datos de la tabla Recetas y los almacena en RecetasMenu
            //junto con el nombre y el número de personas. Redirige a la creación del menú.
            lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    titulo = datos.get(position);
                    RecetaMenu datosRecetaMenu = dbHelper.obtenerDatosRecetaMenu(titulo);
                    String titulosRecetaMenu = datosRecetaMenu.getTituloMenu();
                    String ingredientesRecetaMenu = datosRecetaMenu.getIngredientesMenu();
                    dbHelper.guardarRecetaMenu(titulosRecetaMenu, ingredientesRecetaMenu, hora, tituloMenu, numeroPersonas);
                    volver_a_Menu(tituloMenu, numeroPersonas);
                }
            });
        }
        //FIN DEL CREATE
    }
    //Vuleve al la plantilla de creación de menús mandando el titulo y el num de personas
    private void volver_a_Menu (String tituloMenu, int numeroPersonas){
        Intent i = new Intent(this, Menu.class);
        i.putExtra("TituloMenu", tituloMenu);
        i.putExtra("CantidadMenu", numeroPersonas);
        startActivity(i);
    }


}


