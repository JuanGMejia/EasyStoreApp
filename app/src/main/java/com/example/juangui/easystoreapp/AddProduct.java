package com.example.juangui.easystoreapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CheckableImageButton;
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
import android.widget.ProgressBar;
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
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static java.security.AccessController.getContext;

/**
 * Created by Juan Gui on 21/02/2017.
 */

public class AddProduct extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener{
    //Variables fecha de vencimiento
    DatePickerFragment DPF;
    private TextView fechaVencimiento;
    private Activity context;

    //Variables para las imagenes
    private Bitmap bmp;
    private byte[] datas;
    private Bitmap temporal;
    //Variables de base de datos
    FBConnection fb = new FBConnection();
    private HashMap categorias;

    //Variables del layout
    private CheckableImageButton agregarProducto;
    private EditText nombreProducto;
    private CheckableImageButton scanBtn;
    private MaterialBetterSpinner categoriaProducto;
    private EditText precioVentaProducto;
    private EditText precioCompraProducto;
    private TextView codigoBarrasProducto;
    private EditText cantidadProducto;
    private Button agregar;


    //Variable para el producto
    private Producto producto;
    private ArrayAdapter<String> adaptador;
    private ArrayList<String> categoriasArray;
    private Object categoriasObject[];

    //Variable para el codigo de barras del producto
    private ScanProductBarCode scanProductBarCode;
    private String codigoBarras;

    //Switch de resultado scan o foto
    boolean scan = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);
        context=this;
        final ProgressDialog progress = ProgressDialog.show(AddProduct.this, "Cargando",
                "Espera un momento...", true);

        //Inicializa la variable de base de datos
        fb.getDatabase("EasyStore");

        //Variables de fecha de vencimiento
        context = this;
        DPF=new DatePickerFragment();

        //inicialización variables del layout
        agregarProducto =(CheckableImageButton) findViewById(R.id.imagenProducto);
        agregarProducto.setOnClickListener(this);
        nombreProducto =(EditText) findViewById(R.id.editTextNombreProd);
        categoriaProducto = (MaterialBetterSpinner) findViewById(R.id.spinnerCatProd);
        producto = new Producto();
        precioVentaProducto = (EditText) findViewById(R.id.editTextPrecioVentProd);
        precioCompraProducto = (EditText) findViewById(R.id.editTextPrecioCompProd);
        cantidadProducto = (EditText) findViewById(R.id.numberCantidadProd);
        codigoBarrasProducto = (TextView) findViewById(R.id.textViewCodigoBarras);
        agregar = (Button) findViewById(R.id.buttonAddProduct);
        agregar.setOnClickListener(this);

        //Se inician las variables para escanear el codigo de barras del producto
        scanProductBarCode=new ScanProductBarCode(this);
        scanBtn = (CheckableImageButton) findViewById(R.id.scan_button);
        scanBtn.setOnClickListener(this);

        fechaVencimiento = (TextView) findViewById(R.id.textViewFechaVenc);
        final CheckableImageButton btnOpenPopup = (CheckableImageButton) findViewById(R.id.btnCalendar);

        btnOpenPopup.setOnClickListener(this);

        //Se obtienen las categorias de la Firebase

        fb.myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categoriasArray=new ArrayList<String>();
                categorias = (HashMap) dataSnapshot.child("Categorias").getValue();
                categoriasObject = categorias.keySet().toArray();
                for(int i=0;i<categorias.size();i++){
                    categoriasArray.add(categoriasObject[i].toString());
                }
                adaptador =new ArrayAdapter<String>(AddProduct.this,android.R.layout.simple_spinner_item,categoriasArray);
                categoriaProducto.setAdapter(adaptador);
                progress.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        categoriaProducto.setOnItemClickListener(this);

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
                agregarProducto.setImageBitmap(temporal);
            }
        }



    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.imagenProducto:
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

            case R.id.buttonAddProduct:
                asignarValoresProducto();
                if(producto.verificarCampos()){
                    guardarImagen();
                }
                else{
                    Toast.makeText(AddProduct.this,"Faltan campos por llenar",Toast.LENGTH_LONG).show();
                }
                break;

            default:
                break;
        }
    }

    public void asignarValoresProducto(){
        producto.setNombre(nombreProducto.getText().toString().trim());
        producto.setCantidad(cantidadProducto.getText().toString().trim());
        producto.setPrecioCompra(precioCompraProducto.getText().toString().trim());
        producto.setPrecioVenta(precioVentaProducto.getText().toString().trim());
        producto.setCodigoBarras(codigoBarrasProducto.getText().toString().trim());
        producto.setFechaVencimiento(fechaVencimiento.getText().toString().trim());
    }

    public void tomarFoto(){
        final int cons=0;
        Intent tomarImagen= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(tomarImagen,cons);
    }

    public void guardarImagen(){

        UploadTask uploadTask = fb.getStorage().getReference().child("Images/").child(nombreProducto.getText().toString()+"/").child("Foto.jpg").putBytes(datas);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(AddProduct.this,"Fallo al intentar agregar el producto",Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fb.getDatabase("EasyStore");
                fb.myRef.child("Productos").child(producto.getCodigoBarras()).child("Cantidad").setValue(producto.getCantidad());
                fb.myRef.child("Productos").child(producto.getCodigoBarras()).child("Categoria").setValue(producto.getCategoria());
                fb.myRef.child("Productos").child(producto.getCodigoBarras()).child("Nombre").setValue(producto.getNombre());
                fb.myRef.child("Productos").child(producto.getCodigoBarras()).child("PrecioC").setValue(producto.getPrecioCompra());
                fb.myRef.child("Productos").child(producto.getCodigoBarras()).child("PrecioV").setValue(producto.getPrecioVenta());
                fb.myRef.child("Productos").child(producto.getCodigoBarras()).child("FechaV").setValue(producto.getFechaVencimiento());
                finish();
            }
        });

    }


    public void showDatePickerDialog(View v) {
        DPF.setFechaVencimiento(fechaVencimiento);
        DialogFragment newFragment = DPF;
        newFragment.show(context.getFragmentManager(), "datePicker");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        producto.setCategoria(categoriaProducto.getAdapter().getItem(position).toString());
    }
}
