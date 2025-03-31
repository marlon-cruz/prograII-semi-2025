package com.example.miprimeraaplicacion;

import java.util.Base64;

public class utilidades {
    static String url_consulta = "http://192.168.0.7:5984/agenda/_design/agenda/_view/agenda";
    static String url_mto = "http://192.168.0.7:5984/agenda";
    static String user = "";//Agregar usuario
    static String passwd = "";//Agregar contraseña
    static String credencialesCodificadas = Base64.getEncoder().encodeToString((user + ":" + passwd).getBytes());
    public String generarUnicoId(){
        return java.util.UUID.randomUUID().toString();
    }
}