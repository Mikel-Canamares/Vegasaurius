package com.example.vegasaurius.Model;

public class CategoriaItem {
    private String mCategoriaNombre;
    private int mCategoriaImage;

    public CategoriaItem(String mCategoriaNombre, int mCategoriaImage) {
        this.mCategoriaNombre = mCategoriaNombre;
        this.mCategoriaImage = mCategoriaImage;
    }

    public String getmCategoriaNombre() {
        return mCategoriaNombre;
    }

    public int getmCategoriaImage() {
        return mCategoriaImage;
    }

    public void setmCategoriaNombre(String mCategoriaNombre) {
        this.mCategoriaNombre = mCategoriaNombre;
    }

    public void setmCategoriaImage(int mCategoriaImage) {
        this.mCategoriaImage = mCategoriaImage;
    }
}
