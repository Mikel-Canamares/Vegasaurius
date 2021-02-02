package com.example.vegasaurius.Model;

public class RecetaMenu {
    private String tituloMenu;
    private String ingredientesMenu;

    public RecetaMenu(String titulo, String ingredientes) {
        this.tituloMenu = titulo;
        this.ingredientesMenu = ingredientes;
    }

    public String getTituloMenu() {
        return tituloMenu;
    }

    public String getIngredientesMenu() {
        return ingredientesMenu;
    }

    public void setTituloMenu(String tituloMenu) {
        this.tituloMenu = tituloMenu;
    }

    public void setIngredientesMenu(String ingredientesMenu) {
        this.ingredientesMenu = ingredientesMenu;
    }

}