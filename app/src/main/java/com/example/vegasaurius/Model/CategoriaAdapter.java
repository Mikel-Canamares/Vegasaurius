package com.example.vegasaurius.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.vegasaurius.R;

import java.util.ArrayList;

public class CategoriaAdapter extends ArrayAdapter <CategoriaItem> {

    public CategoriaAdapter (Context context, ArrayList <CategoriaItem> categoriaList){
        super(context, 0, categoriaList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position,  View convertView,  ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.categorias_spinner, parent, false);
        }

        ImageView imageViewCategoria = convertView.findViewById(R.id.image_view_categoria);
        TextView textViewcategoria = convertView.findViewById(R.id.text_view_categoria);

        CategoriaItem currentItem = getItem(position);

        if(currentItem != null) {
            imageViewCategoria.setImageResource(currentItem.getmCategoriaImage());
            textViewcategoria.setText((currentItem.getmCategoriaNombre()));
        }
        return convertView;
    }
}
