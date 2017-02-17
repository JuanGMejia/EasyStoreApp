package com.example.juangui.easystoreapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by Juan Gui on 16/02/2017.
 */

public class ListProducts extends Fragment{
    View rootView;
    GridView grid;
    String[] name = {
            "Leche Colanta",
            "Leche Alqueria"
    } ;
    int[] imageId = {
            R.drawable.lechecolanta,
            R.drawable.lechealqueria
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.listproducts_fragment,container,false);
        super.onCreate(savedInstanceState);

        CustomGrid adapter = new CustomGrid(rootView.getContext(), name, imageId);
        grid=(GridView)rootView.findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(rootView.getContext(), "You Clicked at " +name[+ position], Toast.LENGTH_SHORT).show();

            }
        });

        return rootView;
    }




}
