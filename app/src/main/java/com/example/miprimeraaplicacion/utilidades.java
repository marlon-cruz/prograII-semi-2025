package com.example.miprimeraaplicacion;

import java.util.Base64;

public class utilidades {
    static String url_consulta = "http://192.168.85.93/tiendacouch/_design/marlon/_view/marlon";
    static String url_mto = "http://192.168.98.84:5984/tiendacouch";
    static String user = "marstev";//Agregar usuario
    static String passwd = "marstv2312";//Agregar contraseña
    static String credencialesCodificadas = Base64.getEncoder().encodeToString((user + ":" + passwd).getBytes());
    public String generarUnicoId(){
        return java.util.UUID.randomUUID().toString();
    }
}