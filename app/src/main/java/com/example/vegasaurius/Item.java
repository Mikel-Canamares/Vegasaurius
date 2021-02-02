package com.example.vegasaurius;

import android.widget.Button;

public class Item {
    private String titulo;
    private Button boton;

    public Item(String titulo, Button boton) {
        this.titulo = titulo;
        this.boton = boton;
    }

    public String getTitulo() {
        return titulo;
    }

    public Button getBoton() {
        return boton;
    }

    public void setTituto(String titulo) {
        this.titulo = titulo;
    }

    public void setBoton(Button boton) {
        this.boton = boton;
    }
}
