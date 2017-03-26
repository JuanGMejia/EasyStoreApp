package com.example.juangui.easystoreapp;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.google.zxing.integration.android.IntentIntegrator;

/**
 * Created by Juan Gui on 27/02/2017.
 */

public class ScanProductBarCode {
    private String codigoBarras;
    private Activity rootActivity;

    public ScanProductBarCode(Activity rootActivity) {
        this.rootActivity = rootActivity;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public void iniciarScanner() {
        //Se instancia un objeto de la clase IntentIntegrator
        IntentIntegrator scanIntegrator = new IntentIntegrator(this.rootActivity);
        //Se procede con el proceso de scaneo
        scanIntegrator.initiateScan();

    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //Se obtiene el resultado del proceso de scaneo y se parsea
        Log.d("Valor------->", "ENTRA AL ACTIVITY SCAN");

    }

}
