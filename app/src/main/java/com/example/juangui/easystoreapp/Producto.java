package com.example.juangui.easystoreapp;

import java.util.Date;

/**
 * Created by Juan Gui on 23/02/2017.
 */

public class Producto {
    private String codigoBarras;
    private String categoria;
    private String nombre;
    private String cantidad;
    private String precioCompra;
    private String precioVenta;
    private String fechaVencimiento;

    public Producto(){

    }

    public Producto(String codigoBarras, String categoria, String nombre, String cantidad, String precioCompra, String precioVenta, String fechaVencimiento) {
        this.codigoBarras = codigoBarras;
        this.categoria = categoria;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(String precioCompra) {
        this.precioCompra = precioCompra;
    }

    public String getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(String precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public boolean verificarCampos(){
        if(!this.codigoBarras.equals("#########") && !this.nombre.equals("") && !this.categoria.equals("") && !this.precioCompra.equals("")
                && !this.precioVenta.equals("") && !this.fechaVencimiento.equals("")){
            return true;
        }else {
            return false;
        }

    }


}
