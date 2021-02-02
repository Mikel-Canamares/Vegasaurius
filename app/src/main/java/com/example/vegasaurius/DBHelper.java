package com.example.vegasaurius;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.vegasaurius.Model.Receta;
import com.example.vegasaurius.Model.RecetaMenu;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBHelper extends SQLiteAssetHelper {

    static String DATABASE_NAME = "Recetario.db";
    static int DATABASE_VERSION = 1;
    static String TABLE_NAME = "Recetas";
    static String COL_Titulo = "Titulo";
    static String COL_Categoria = "Categoria";
    static String COL_Foto = "Foto";
    static String COL_Ingredientes = "Ingredientes";
    static String COL_Elaboracion = "Elaboracion";

    static String TABLE_MENU_NAME = "RecetasMenu";
    static String COL_IDMenu = "ID";
    static String COL_TituloMenu = "Titulo";
    static String COL_IngredientesMenu = "Ingredientes";
    static String COL_TipoMenu = "Tipo";
    static String COL_NombreMenu = "NombreMenu";
    static String COL_PersonasMenu = "PersonasMenu";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void guardarReceta(String titulo, String categoria, byte [] foto, String ingredientes, String elaboracion)
            throws SQLiteException{
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_Titulo, titulo);
        cv.put(COL_Categoria, categoria);
        cv.put(COL_Foto, foto);
        cv.put(COL_Ingredientes, ingredientes);
        cv.put(COL_Elaboracion, elaboracion);
        database.insert(TABLE_NAME, null, cv);
        database.close();
    }

    public void guardarRecetaMenu(String titulo, String ingredientes, String hora, String tituloMenu, int numeroPersonas)
            throws SQLiteException{
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_TituloMenu, titulo);
        cv.put(COL_IngredientesMenu, ingredientes);
        cv.put(COL_TipoMenu, hora);
        cv.put(COL_NombreMenu, tituloMenu);
        cv.put(COL_PersonasMenu, numeroPersonas);
        database.insert(TABLE_MENU_NAME, null, cv);
        database.close();
    }


    public void modificarReceta(String titulo, String categoria, byte [] foto, String ingredientes, String elaboracion)
            throws SQLiteException{
        SQLiteDatabase database = this.getWritableDatabase();
        String [] condicion = new String[] {titulo};
        ContentValues cv = new ContentValues();
        cv.put(COL_Titulo, titulo);
        cv.put(COL_Categoria, categoria);
        cv.put(COL_Foto, foto);
        cv.put(COL_Ingredientes, ingredientes);
        cv.put(COL_Elaboracion, elaboracion);
        database.update(TABLE_NAME, cv, COL_Titulo+"=?", condicion);
        database.close();
    }

    public Receta obtenerDatos(String name){
        SQLiteDatabase database = this.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String [] select = {COL_Titulo, COL_Categoria, COL_Foto, COL_Ingredientes, COL_Elaboracion};
        qb.setTables(TABLE_NAME);
        Cursor c = qb.query(database, select, "Titulo = ?", new String[] {name}, null, null, null);
        Receta resultado =null;
        if(c.moveToFirst()){
            do{
                resultado = new Receta(c.getString(0), c.getString(1), c.getBlob(2),
                        c.getString(3), c.getString(4));
            }while (c.moveToNext());
        }
        return resultado;
    }

    public RecetaMenu obtenerDatosRecetaMenu(String name){
        SQLiteDatabase database = this.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String [] select = {COL_Titulo, COL_Ingredientes};
        qb.setTables(TABLE_NAME);
        Cursor c = qb.query(database, select, "Titulo = ?", new String[] {name}, null, null, null);
        RecetaMenu resultado =null;
        if(c.moveToFirst()){
            do{
                resultado = new RecetaMenu(c.getString(0), c.getString(1));
            }while (c.moveToNext());
        }
        return resultado;
    }




    public Cursor consultarPorCategoria(String categoria){
        String [] param = new String [1];
        param[0] = (categoria);
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Categoria LIKE ?" , param);
    }

    public Cursor consultarMenu(String tipo, String tituloMenu) {
        String[] param = new String[2];
        param[0] = (tipo);
        param[1] = (tituloMenu);
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_MENU_NAME + " WHERE Tipo LIKE ? AND NombreMenu LIKE ? ", param);
    }

    public Cursor consultarIngredientesMenu(String tituloMenu) {
        String[] param = new String[1];
        param[0] = (tituloMenu);
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT Ingredientes FROM " + TABLE_MENU_NAME + " WHERE NombreMenu LIKE ? ", param);
    }


    public Cursor consultarTitulosMenu(){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_MENU_NAME + " GROUP BY NombreMenu", null);
    }


    public void borrarReceta(String titulo) throws SQLiteException{
        SQLiteDatabase database = this.getWritableDatabase();
        String [] condicion = new String[] {titulo};
        database.delete(TABLE_NAME, COL_Titulo+"=?", condicion);
        database.close();
    }

    public void borrarMenu(String titulo) throws SQLiteException{
        SQLiteDatabase database = this.getWritableDatabase();
        String [] condicion = new String[] {titulo};
        database.delete(TABLE_MENU_NAME, COL_NombreMenu+"=?", condicion);
        database.close();
    }


    public void borrarRecetaMenu(int identificador) throws SQLiteException{
        SQLiteDatabase database = this.getWritableDatabase();
        String id = String.valueOf(identificador);
        String [] condicion = new String[] {id};
        database.delete(TABLE_MENU_NAME, COL_IDMenu+"=?", condicion);
        database.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
