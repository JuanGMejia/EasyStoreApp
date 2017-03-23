package com.example.juangui.easystoreapp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Juan Gui on 16/03/2017.
 */

public class DetailProductFragment extends DialogFragment{
    Producto producto;
    Bitmap imagenProducto;
    ImageView imagen;
    TextView cantidadDetail;
    TextView fechaVDetail;
    TextView precioCDetail;
    TextView precioVDetail;
    TextView categoriaDetail;
    TextView codigoBarras;






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
        cantidadDetail = (TextView) rootView.findViewById(R.id.cantidadDetail);
        fechaVDetail = (TextView) rootView.findViewById(R.id.fechaVDetail);
        precioCDetail = (TextView) rootView.findViewById(R.id.precioCDetail);
        precioVDetail = (TextView) rootView.findViewById(R.id.precioVDetail);
        categoriaDetail = (TextView) rootView.findViewById(R.id.categoriaDetail);
        codigoBarras = (TextView) rootView.findViewById(R.id.codebarDetail);
        cantidadDetail.setText(producto.getCantidad());
        fechaVDetail.setText(producto.getFechaVencimiento());
        precioCDetail.setText(producto.getPrecioCompra());
        precioVDetail.setText(producto.getPrecioVenta());
        categoriaDetail.setText(producto.getCategoria());
        codigoBarras.setText(producto.getCodigoBarras());
        getDialog().setTitle(producto.getNombre());
        return rootView;
    }
}
