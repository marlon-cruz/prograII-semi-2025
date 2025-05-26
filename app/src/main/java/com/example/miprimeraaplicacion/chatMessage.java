package com.example.miprimeraaplicacion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
public class chatMessage {
    public boolean posicion; //izquierdo o derecho.
    public String mensaje;
    public chatMessage(boolean posicion, String mensaje){
        super();
        this.posicion = posicion;
        this.mensaje = mensaje;
    }
}
