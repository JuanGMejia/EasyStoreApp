package com.example.juangui.easystoreapp.tests;

import android.util.Log;

import com.example.juangui.easystoreapp.Producto;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.juangui.easystoreapp.R.id.snap;
import static org.junit.Assert.*;

/**
 * Created by Juan Gui on 22/02/2017.
 */
public class AddProductTest {
    FirebaseDatabase fb;

    private Producto productobueno=new Producto("101","Lacteos","leche","2","3100","3500","21/12/2012");
    private Producto productomalo=new Producto("101","","leche","2","3100","","21/12/2012");

    public AddProductTest() throws ParseException {

    }

    @Test
    public void verificarCampos() throws Exception {
        boolean pasa=true;
        String mensaje="Prueba exitosa";
        if(productomalo.verificarCampos()){
            pasa=false;
            mensaje="producto malo pasa test";
        }
        if(!productobueno.verificarCampos()) {
            pasa = false;
            mensaje=" Producto bueno no pasa test";
        }
        assertTrue(mensaje,pasa);
    }

    @Test
    public void guardarImagen() throws Exception {

    }

}