package com.example.juangui.easystoreapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Juan Gui on 16/02/2017.
 */

public class ListProducts extends Fragment {
    final long ONE_MEGABYTE = 1024 * 1024;
    private View rootView;
    private GridView grid;
    private ArrayList<Producto> detallesProductosTmp;
    private ArrayList<Producto> detallesProductos;
    private ArrayList<String> nombreProdutoTmp;
    private ArrayList<String> nombreProducto;
    private ArrayList<Bitmap> imageId;
    private FBConnection fbDatabase;
    private FBConnection fbStorage;
    private Object productosObject[];
    private HashMap productos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.listproducts_fragment, container, false);
        super.onCreate(savedInstanceState);
        final ProgressDialog progress = ProgressDialog.show(rootView.getContext(), "Listando Productos",
                "Espera un momento...", true);
        fbDatabase = new FBConnection();
        fbStorage = new FBConnection();
        detallesProductosTmp = new ArrayList<Producto>();
        detallesProductos = new ArrayList<Producto>();
        nombreProdutoTmp = new ArrayList<String>();
        nombreProducto = new ArrayList<>();
        imageId = new ArrayList<>();

        try {
            listarProductos(progress);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return rootView;
    }


    public void lanzarAddProduct(View v) {
        Intent iniciarAddProduct = new Intent(v.getContext(), AddProduct.class);
        startActivity(iniciarAddProduct);
    }

    public void listarProductos(final ProgressDialog progress) throws InterruptedException {

        fbDatabase.getDatabase("EasyStore");
        fbStorage.getStorage();
        fbDatabase.myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nombretmp;
                productos = (HashMap) dataSnapshot.child("Productos").getValue();
                productosObject = productos.keySet().toArray();
                for (int i = 0; i < productos.size(); i++) {
                    detallesProductosTmp.add(new Producto(productosObject[i].toString(), dataSnapshot.child("Productos").child(productosObject[i].toString()).child("Categoria").getValue().toString(), dataSnapshot.child("Productos").child(productosObject[i].toString()).child("Nombre").getValue().toString(), dataSnapshot.child("Productos").child(productosObject[i].toString()).child("Cantidad").getValue().toString(), dataSnapshot.child("Productos").child(productosObject[i].toString()).child("PrecioC").getValue().toString(), dataSnapshot.child("Productos").child(productosObject[i].toString()).child("PrecioV").getValue().toString(), dataSnapshot.child("Productos").child(productosObject[i].toString()).child("FechaV").getValue().toString()));
                    nombreProdutoTmp.add(detallesProductosTmp.get(i).getNombre());
                }

                for (int i = 0; i < nombreProdutoTmp.size(); i++) {
                    final int finalI = i;
                    StorageReference islandRef = fbStorage.myRefStorage.child("Images/" + nombreProdutoTmp.get(i) + "/Foto.jpg");
                    islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            nombreProducto.add(nombreProdutoTmp.get(finalI));
                            detallesProductos.add(detallesProductosTmp.get(finalI));
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            imageId.add(bitmap);
                            if (nombreProdutoTmp.size() == imageId.size()) {
                                pintar();
                                progress.dismiss();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void pintar() {
        Bitmap AddIcon = BitmapFactory.decodeResource(rootView.getResources(), R.drawable.add);
        imageId.add(AddIcon);
        nombreProducto.add("");
        CustomGrid adapter = new CustomGrid(rootView.getContext(), nombreProducto, imageId);
        grid = (GridView) rootView.findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == imageId.size() - 1) {
                    lanzarAddProduct(rootView);
                } else {
                    FragmentManager fm = getFragmentManager();
                    DetailProductFragment dialogFragment = new DetailProductFragment();
                    dialogFragment.setProducto(detallesProductos.get(position));
                    dialogFragment.setImagenProducto(imageId.get(position));
                    dialogFragment.show(fm, "Sample Fragment");
                }
            }
        });
    }


}
