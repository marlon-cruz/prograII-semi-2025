package com.example.miprimeraaplicacion;
public class productos {
    String idProducto;
    String codigo;
    String descripcion;
    String marca;
    String presentacion;
    String precio;
    String foto;
    String foto1;
    String foto2;
    String costo;
    String stock;



    public productos(String idProducto, String codigo, String descripcion, String marca, String presentacion, String precio, String foto, String foto1, String foto2, String costo,String stock) {
        this.idProducto = idProducto;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.marca = marca;
        this.presentacion = presentacion;
        this.precio = precio;
        this.foto = foto;
        this.costo = costo;
        this.stock = stock;
        this.foto1 = foto1;
        this.foto2 = foto2;

    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getidProducto() {
        return idProducto;
    }

    public void setidProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getcodigo() {
        return codigo;
    }

    public void setcodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getdescripcion() {
        return descripcion;
    }

    public void setdescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getmarca() {
        return marca;
    }

    public void setmarca(String marca) {
        this.marca = marca;
    }

    public String getpresentacion() {
        return presentacion;
    }

    public void setpresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getprecio() {
        return precio;
    }

    public void setprecio(String precio) {
        this.precio = precio;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFoto1() {
        return foto1;
    }

    public void setFoto1(String foto1) {
        this.foto1 = foto1;
    }
    public String getFoto2() {
        return foto2;
    }

    public void setFoto2(String foto2) {
        this.foto2 = foto2;
    }
    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }
}