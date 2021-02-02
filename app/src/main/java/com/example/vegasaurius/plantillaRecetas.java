package com.example.vegasaurius;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.vegasaurius.Model.CategoriaAdapter;
import com.example.vegasaurius.Model.CategoriaItem;
import com.example.vegasaurius.Model.Ingrediente;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class plantillaRecetas extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener  {

    ListView listaAlimentos;
    Spinner   spinerIngrediente, spinnerCategorias, spinnerTipos;
    EditText etCantidad;
    EditText etTitulo;
    String alimento;
    String categoria;
    List<Ingrediente> items;
    ArrayAdapter ADP;
    //Variable para el explorador de archivos
    static final int seleccion = 333;
    Uri uriFoto;
    ImageView ivFotos;
    Drawable dinosaurioFoto;
    EditText etDescripcion;
    DBHelper dbHelper;
    TextView unidades;
    private DrawerLayout myDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;

    private ArrayList <CategoriaItem> mCategoriaList, mTipoList;
    private CategoriaAdapter mAdapterCategoria, mAdapterTipo;


    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.plantilla_receta);
        //Menu lateral
        mNavigationView =  findViewById(R.id.navigationView);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        intent = new Intent(plantillaRecetas.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_recetario:
                        intent = new Intent(plantillaRecetas.this, Categorias.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_nuevaReceta:
                        intent = new Intent(plantillaRecetas.this, plantillaRecetas.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_menu:
                        intent = new Intent(plantillaRecetas.this, GestionMenus.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_listaCompra:
                        intent = new Intent(plantillaRecetas.this, ListasCompra.class);
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

        //Carga el spinner con las las categorias
        initListCategorias();
        spinnerCategorias = findViewById(R.id.spCategoria);
        mAdapterCategoria = new CategoriaAdapter(this, mCategoriaList);
        spinnerCategorias.setAdapter(mAdapterCategoria);
        spinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               CategoriaItem itemPulsado = (CategoriaItem) parent.getItemAtPosition(position);
               categoria = itemPulsado.getmCategoriaNombre();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //carga el Spinner con los tipos de ingredientes
        initListTipos();
        spinnerTipos = findViewById(R.id.spinnerTipo);
        mAdapterTipo = new CategoriaAdapter(this, mTipoList);
        spinnerTipos.setAdapter(mAdapterTipo);
        spinnerTipos.setOnItemSelectedListener(this);

        spinerIngrediente = findViewById(R.id.spinner_Ingrediente);
        spinerIngrediente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void  onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                alimento = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        unidades = findViewById(R.id.tvUnidades);

        dbHelper = new DBHelper(this);
        etTitulo = findViewById(R.id.tituloReceta);
        etCantidad = findViewById(R.id.etCantidad);

        //Identificador del imageview
        ivFotos = findViewById(R.id.ivFoto);
        dinosaurioFoto = getResources().getDrawable(R.drawable.eligefotorecortadasintitulo);
        ivFotos.setImageDrawable(dinosaurioFoto);
        etDescripcion = findViewById(R.id.etDescripcion);

        //Array para almacenar los ingredientes tras pulsar un botón
        items = new ArrayList<Ingrediente>();
        ADP = new ArrayAdapter(getApplicationContext(),R.layout.estilo_elementos_lista, items);
        listaAlimentos = findViewById(R.id.listaIngredientes);
        listaAlimentos.setAdapter(ADP);

        //Muestra las opciones disponibles al pulsar sobre un ingrediente
        listaAlimentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(plantillaRecetas.this);
                final AlertDialog alerta = builder.create();
                builder.setTitle("¿Quieres borrar este ingrediente?");
                builder.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        items.remove(position);
                        ADP.notifyDataSetChanged();
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                          alerta.cancel();
                    }
                });
                  builder.create().show();
            }
        });
    //FIN ON CREATE
    }


    //Metodo para cargar un segundo Spinner en función de la seleccion de otro Spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int [] ingredientes = {R.array.array_alimento, R.array.array_arroz_pasta, R.array.array_Aceite,
                R.array.array_Bebidas ,R.array.array_Cereales, R.array.array_Conservas, R.array.array_Cacao,
                R.array.array_Huevos, R.array.array_Fruta, R.array.array_Panaderia,
                R.array.array_Sustitutos, R.array.array_Verduras};
        ArrayAdapter  adapter = ArrayAdapter.createFromResource(this, ingredientes [position], R.layout.estilo_spinner_alimento);
        adapter.setDropDownViewResource(R.layout.estilo_spinner_alimento);
        spinerIngrediente.setAdapter(adapter);
        if(position == 1 || position == 2 || position == 4 || position == 5 || position == 6 || position == 9 || position == 10 ){
            unidades.setText("gr");
        }
        if(position == 3){
            unidades.setText("ml");
        }
        if(position == 7 || position == 8 || position == 11){
            unidades.setText("uds");
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    //Funcionalidad de los botones de la vista
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btCargarImagen:
                seleccionarFoto();
                break;
            case R.id.btGuardar:
                    if(etTitulo.getText().toString().isEmpty()){
                        Toast.makeText(this, "Introduce un título" , Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(categoria.equals("Selecciona una categoría")){
                           Toast.makeText(this, "Elige una categoría" , Toast.LENGTH_SHORT).show();
                        }
                        else{
                            String titulo = etTitulo.getText().toString();
                            byte [] foto;
                            if(ivFotos.getDrawable() == null){
                                foto = null;
                            }else{
                                //Se convierte el src del imageView a Bitmap
                                Bitmap  bitmap = ((BitmapDrawable)ivFotos.getDrawable()).getBitmap();
                                foto = Utils.getBytes(bitmap);
                            }
                            //Se convierte el arraylist de ingredientes a texto
                            Gson gson = new Gson();
                            String ingredientesTexto = gson.toJson(items);
                            String elaboracion = etDescripcion.getText().toString();
                            dbHelper.guardarReceta(titulo, categoria, foto, ingredientesTexto , elaboracion );
                            //Resetea el activity
                            Toast.makeText(plantillaRecetas.this, "Guardado con exito" , Toast.LENGTH_SHORT).show();
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    }
                break;
            default:
                break;
        }
    }

    ///Metodo para añadir un ingrediente a la lista de la receta
    public void nuevoIngrediente(View v){
        if (alimento.equals("Elige un ingrediente")){
            Toast.makeText(this, "Porfavor elige un ingrediente" , Toast.LENGTH_SHORT).show();
        }
        else{
            if(etCantidad.getText().toString().isEmpty()){
                Toast.makeText(this, "Introduce una cantidad" , Toast.LENGTH_SHORT).show();
            } else{
                int cantidad = Integer.parseInt(etCantidad.getText().toString());
                String tipoUnidad = unidades.getText().toString();
                Ingrediente ingrediente = new Ingrediente(alimento, cantidad, tipoUnidad);
                items.add(ingrediente);
                ADP.notifyDataSetChanged();
            }

        }
    }

    //Metodo para abrir explorador de archivos y cargar una imagen
    private void seleccionarFoto(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //Muestra los archivos de imagen
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try{
            startActivityForResult(Intent.createChooser(intent, "Seleccione una imagen"), seleccion);
        }catch(android.content.ActivityNotFoundException ex){
            Toast.makeText(this, "Necesita un explorador de archivos", Toast.LENGTH_LONG).show();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case seleccion:
                if(resultCode == RESULT_OK){
                    Uri uri = data.getData();
                    uriFoto = uri;
                    ivFotos.setImageURI(uriFoto);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initListCategorias(){
        mCategoriaList = new ArrayList<>();
        mCategoriaList.add(new CategoriaItem("Selecciona una categoría", R.drawable.vegetarian ));
        mCategoriaList.add(new CategoriaItem("Arroces", R.drawable.ic_rice ));
        mCategoriaList.add(new CategoriaItem("Ensaladas", R.drawable.ic_ensalada ));
        mCategoriaList.add(new CategoriaItem("Bebidas", R.drawable.ic_juice ));
        mCategoriaList.add(new CategoriaItem("Guisos", R.drawable.ic_stew ));
        mCategoriaList.add(new CategoriaItem("Otros", R.drawable.ic_pizza ));
        mCategoriaList.add(new CategoriaItem("Postres y desayunos", R.drawable.ic_dessert ));
        mCategoriaList.add(new CategoriaItem("Salsas y guarniciones", R.drawable.ic_mustard ));
        mCategoriaList.add(new CategoriaItem("Sopas y cremas", R.drawable.ic_soup ));
        mCategoriaList.add(new CategoriaItem("Tapas y aperitivos", R.drawable.ic_tapa ));
        mCategoriaList.add(new CategoriaItem("Verduras", R.drawable.ic_salad ));
    }


    private void initListTipos(){
        mTipoList = new ArrayList<>();
        mTipoList.add(new CategoriaItem("Elige el tipo de ingrediente", R.drawable.vegetarian ));
        mTipoList.add(new CategoriaItem("Arroz, pasta y legumbres", R.drawable.vegetarian ));
        mTipoList.add(new CategoriaItem("Aceite, vinagres y sal", R.drawable.vegetarian ));
        mTipoList.add(new CategoriaItem("Bebidas y refrescos", R.drawable.vegetarian ));
        mTipoList.add(new CategoriaItem("Cereales y galletas", R.drawable.vegetarian ));
        mTipoList.add(new CategoriaItem("Conservas", R.drawable.vegetarian ));
        mTipoList.add(new CategoriaItem("Cacao, café e infusiones", R.drawable.vegetarian ));
        mTipoList.add(new CategoriaItem("Huevos y Lacteos", R.drawable.vegetarian ));
        mTipoList.add(new CategoriaItem("Fruta", R.drawable.vegetarian ));
        mTipoList.add(new CategoriaItem("Panadería y pastelería", R.drawable.vegetarian ));
        mTipoList.add(new CategoriaItem("Proteina Vegetal", R.drawable.vegetarian ));
        mTipoList.add(new CategoriaItem("Verduras y hortalizas", R.drawable.vegetarian ));
    }


}


