package com.example.juangui.easystoreapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import static java.security.AccessController.getContext;

/**
 * Created by Juan Gui on 21/02/2017.
 */

public class AddProduct extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{
    //Variables fecha de vencimiento
    DatePickerFragment DPF;
    private TextView fechaVencimiento;
    private Activity context;

    //Variables para las imagenes
    private Bitmap bmp;
    private byte[] datas;
    private Bitmap temporal;
    //Variables de base de datos
    FBConnection fb=new FBConnection();
    private HashMap categorias;

    //Variables del layout
    private Button agregarProducto;
    private ImageButton editarFoto;
    private ImageView fotoProducto;
    private EditText nombreProducto;
    private Button scanBtn;
    private Spinner categoriaProducto;
    private EditText precioCompraProducto;
    private EditText precioVentaProducto;
    private TextView codigoBarrasProducto;
    private EditText cantidadProducto;
    private Button agregarCantidad;
    private Button disminuirCantidad;
    private Integer cantidad;

    //Variable para el produto
    private Producto producto;
    ArrayAdapter<String> adaptador;
    ArrayList<String> categoriasArray;
    Object categoriasObject[];

    //Variable para el codigo de barras del producto
    private ScanProductBarCode scanProductBarCode;
    private String codigoBarras;

    //Switch de resultado scan o foto
    boolean scan=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        //Variables de fecha de vencimiento
        context = this;
        DPF=new DatePickerFragment();

        //inicialización variables del layout
        fotoProducto =(ImageView) findViewById(R.id.imageViewFotoProd);
        agregarProducto = (Button) findViewById(R.id.buttonAddProduct);
        agregarProducto.setOnClickListener(this);
        editarFoto =(ImageButton) findViewById(R.id.imageButtonEdit);
        editarFoto.setOnClickListener(this);
        nombreProducto =(EditText) findViewById(R.id.editTextNombreProd);
        categoriaProducto = (Spinner) findViewById(R.id.spinnerCatProd);
        cantidadProducto = (EditText) findViewById(R.id.numberCantidadProd);
        disminuirCantidad =(Button) findViewById(R.id.btnDisminuir);
        disminuirCantidad.setOnClickListener(this);
        agregarCantidad =(Button) findViewById(R.id.btnAgregar);
        agregarCantidad.setOnClickListener(this);
        precioCompraProducto = (EditText) findViewById(R.id.editTextPrecioCompProd);
        precioVentaProducto = (EditText) findViewById(R.id.editTextPrecioVentProd);
        codigoBarrasProducto = (TextView) findViewById(R.id.textViewCodigoBarras);
        fechaVencimiento = (TextView) findViewById(R.id.textViewfechaVenc);
        final Button btnOpenPopup = (Button) findViewById(R.id.btnCalendar);
        btnOpenPopup.setOnClickListener(this);

        //Se inician las variables para escanear el codigo de barras del producto
        scanProductBarCode=new ScanProductBarCode(this);
        scanBtn = (Button) findViewById(R.id.scan_button);
        scanBtn.setOnClickListener(this);

        //Se obtienen las categorias de la Firebase
        fb.getDatabase("EasyStore");
        fb.myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categoriasArray=new ArrayList<String>();
                categorias = (HashMap) dataSnapshot.child("Categorias").getValue();
                categoriasObject = categorias.keySet().toArray();
                categoriasArray.add("Seleccionar Categoria");
                for(int i=0;i<categorias.size();i++){
                    categoriasArray.add(categoriasObject[i].toString());
                }
                adaptador =new ArrayAdapter<String>(AddProduct.this,android.R.layout.simple_spinner_item,categoriasArray);
                categoriaProducto.setAdapter(adaptador);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        categoriaProducto.setOnItemSelectedListener(this);

    }

    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data) {

        if(scan){
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanningResult != null) {
                //Quiere decir que se obtuvo resultado pro lo tanto:
                //Desplegamos en pantalla el contenido del código de barra scaneado
                scanProductBarCode.setCodigoBarras(scanningResult.getContents());
                codigoBarras=scanProductBarCode.getCodigoBarras();
                codigoBarrasProducto.setTextColor(Color.parseColor("#008080"));
                codigoBarrasProducto.setText(codigoBarras);
            }else{
                //Quiere decir que NO se obtuvo resultado
                scanProductBarCode.setCodigoBarras("");
            }
        }else{
            super.onActivityResult(requestCode,resultCode,data);
            if(resultCode== Activity.RESULT_OK)
            {
                Bundle ext = data.getExtras();
                bmp=(Bitmap) ext.get("data");
                temporal=bmp;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG,100,baos);
                datas = baos.toByteArray();
                fotoProducto.setImageBitmap(temporal);
            }
        }



    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonAddProduct:
                asignarValoresProducto();
                if(producto.verificarCampos()){
                    //guardarImagen();

                    Toast.makeText(AddProduct.this,"Se guarda imagen",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(AddProduct.this,"Faltan campos por llenar",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.imageButtonEdit:
                scan=false;
                tomarFoto();
                break;
            case R.id.scan_button:
                scan=true;
                scanProductBarCode.iniciarScanner();
                break;
            case R.id.btnCalendar:
                showDatePickerDialog(v);
                break;
            case R.id.btnDisminuir:
                cantidad=Integer.parseInt(cantidadProducto.getText().toString());
                if(cantidad>0){
                    cantidad-=1;
                    cantidadProducto.setText(cantidad.toString());
                }
                break;
            case R.id.btnAgregar:
                cantidad=Integer.parseInt(cantidadProducto.getText().toString());
                cantidad+=1;
                cantidadProducto.setText(cantidad.toString());
                break;
            default:
                break;
        }
    }

    public void asignarValoresProducto(){
        producto.setNombre(nombreProducto.getText().toString().trim());
        producto.setCantidad((String.valueOf(cantidadProducto.getText().toString())).trim());
        producto.setPrecioCompra(precioCompraProducto.getText().toString().trim());
        producto.setPrecioVenta(precioVentaProducto.getText().toString().trim());
    }

    public void tomarFoto(){
        final int cons=0;
        Intent tomarImagen= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(tomarImagen,cons);
    }
/*
    public void guardarImagen(){

        UploadTask uploadTask = connection.getStorage().getReference().child("Images/").child(nombreProducto.getText().toString()+"/").child("Perfil.jpg").putBytes(datas);

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

    }*/



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (view.getId()) {
            case R.id.spinnerCatProd:
                if(categoriaProducto.getId()>0){
                    producto.setCategoria(String.valueOf(categoriaProducto.getSelectedItem()).trim());
                }
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void showDatePickerDialog(View v) {
        DPF.setFechaVencimiento(fechaVencimiento);
        DialogFragment newFragment = DPF;
        newFragment.show(context.getFragmentManager(), "datePicker");
    }

}
