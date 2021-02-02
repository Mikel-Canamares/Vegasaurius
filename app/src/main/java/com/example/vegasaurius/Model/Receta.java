package com.example.vegasaurius.Model;

public class Receta {
    private String titulo;
    private String tipo;
    private byte [] foto;
    private String ingredientes;
    private String elaboracion;

    public Receta(String titulo, String tipo, byte[] foto, String ingredientes, String elaboracion) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.foto = foto;
        this.ingredientes = ingredientes;
        this.elaboracion = elaboracion;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public byte[] getFoto() {
        return foto;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public String getElaboracion() {
        return elaboracion;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public void setElaboracion(String elaboracion) {
        this.elaboracion = elaboracion;
    }
}
