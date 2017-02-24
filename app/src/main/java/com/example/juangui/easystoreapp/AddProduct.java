package com.example.juangui.easystoreapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import static java.security.AccessController.getContext;

/**
 * Created by Juan Gui on 21/02/2017.
 */

public class AddProduct extends AppCompatActivity implements View.OnClickListener {
    Bitmap bmp;
    byte[] datas;
    ImageView fotoProduct;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    Bitmap temporal;
    boolean camposLlenos=false;
    TextView nameProduct;
    ImageButton editarFoto;
    Button agregarProducto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);
        fotoProduct =(ImageView) findViewById(R.id.imageViewFotoProd);
        nameProduct =(TextView) findViewById(R.id.textViewNameProduct);
        agregarProducto = (Button) findViewById(R.id.buttonAddProduct);
        agregarProducto.setOnClickListener(this);
        editarFoto =(ImageButton) findViewById(R.id.imageButtonEdit);
        editarFoto.setOnClickListener(this);

    }

    public boolean verificarCampos(){
        if(!nameProduct.getText().equals("")){
            camposLlenos=true;
        }
        return camposLlenos;
    }

    public void guardarImagen(){

        UploadTask uploadTask = storage.getReference().child("Images/").child(nameProduct.getText().toString()+"/").child("Perfil.jpg").putBytes(datas);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddProduct.this,"Produto Agregado",Toast.LENGTH_LONG).show();
            }
        });

    }


    public void tomarFoto(){
        final int cons=0;
        Intent tomarImagen= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(tomarImagen,cons);
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode== Activity.RESULT_OK)
        {
            Bundle ext = data.getExtras();
            bmp=(Bitmap) ext.get("data");
            temporal=bmp;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG,100,baos);
            datas = baos.toByteArray();
            fotoProduct.setImageBitmap(temporal);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonAddProduct:
                if(verificarCampos()){
                    guardarImagen();
                }
                else{
                    Toast.makeText(AddProduct.this,"Faltan campos por llenar",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.imageButtonEdit:
                tomarFoto();
            default:
                break;
        }
    }
}
