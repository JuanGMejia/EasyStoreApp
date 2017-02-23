package com.example.juangui.easystoreapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Juan Gui on 16/02/2017.
 */

public class ListProducts extends Fragment{
    View rootView;
    GridView grid;
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<Integer> imageId = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.listproducts_fragment,container,false);
        super.onCreate(savedInstanceState);
        imageId.add(R.drawable.lechealqueria);
        imageId.add(R.drawable.add);
        name.add("Leche Alqueria");
        name.add("");
        CustomGrid adapter = new CustomGrid(rootView.getContext(), name, imageId);
        grid=(GridView)rootView.findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if(position==imageId.size()-1){
                    lanzarAddProduct(rootView);
                }
                Toast.makeText(rootView.getContext(), "You Clicked at " +name.get(position), Toast.LENGTH_SHORT).show();

            }
        });

        return rootView;
    }


    public void lanzarAddProduct(View v){
        Intent iniciarAddProduct= new Intent(v.getContext(),AddProduct.class);
        startActivity(iniciarAddProduct);
    }

}
