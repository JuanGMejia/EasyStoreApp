package com.example.juangui.easystoreapp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Juan Gui on 16/03/2017.
 */

public class DetailProductFragment extends DialogFragment{
    Producto producto;
    Bitmap imagenProducto;
    ImageView imagen;






    public DetailProductFragment(){}

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setImagenProducto(Bitmap imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detailsproduct_fragment, container, false);
        imagen = (ImageView) rootView.findViewById(R.id.imagenDetail);
        imagen.setImageBitmap(imagenProducto);
        getDialog().setTitle(producto.getNombre());
        return rootView;
    }
}
