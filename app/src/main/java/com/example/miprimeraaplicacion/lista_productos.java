package com.example.miprimeraaplicacion;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class lista_productos extends Activity {
    Bundle parametros = new Bundle();
    ListView ltsproductos;
    Cursor cproductos;
    Cursor cproductosAuxiliar;
    DB db;
    final ArrayList<productos> alproductos = new ArrayList<productos>();
    final ArrayList<productos> alProductosCopia = new ArrayList<productos>();
    JSONArray jsonArray;
    JSONArray jsonArrayAuxiliar;
    JSONObject jsonObject;
    JSONObject jsonObjectAuxiliar;
    productos misProductos;
    FloatingActionButton fab;
    int posicion = 0;
    obtenerDatosServidor datosServidor;
    detectarInternet di;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        parametros.putString("accion", "nuevo");
        db = new DB(this);

        fab = findViewById(R.id.fabAgregarProductos);
        fab.setOnClickListener(view -> abriVentana());
        DatosLocalRemoto();
        listarDatos();
        buscarproductos();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mimenu, menu);
        try {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            posicion = info.position;
            di = new detectarInternet(this);
            if (di.hayConexionInternet()) {
                menu.setHeaderTitle(jsonArray.getJSONObject(posicion).getJSONObject("value").getString("codigo"));
            } else {
                menu.setHeaderTitle(jsonArray.getJSONObject(posicion).getString("codigo"));
            }

        } catch (Exception e) {
            mostrarMsg("Error:  1asada" + e.getMessage());
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        try {


            if (item.getItemId() == R.id.mnxNuevo) {
                abriVentana();
            } else if (item.getItemId() == R.id.mnxModificar) {
                di = new detectarInternet(this);
                if (di.hayConexionInternet()) {
                    parametros.putString("accion", "modificar");
                    parametros.putString("productos", jsonArray.getJSONObject(posicion).getJSONObject("value").toString());
                    mostrarMsg("abrir en remota");
                    abriVentana();
                } else {
                    parametros.putString("accion", "modificar");
                    parametros.putString("productos", jsonArray.getJSONObject(posicion).toString());
                    mostrarMsg("abrir en local");
                    abriVentana();
                }


            } else if (item.getItemId() == R.id.mnxEliminar) {
                eliminarProducto();
            }
            return true;
        } catch (Exception e) {
            mostrarMsg("Error:  2" + e.getMessage());
            return super.onContextItemSelected(item);
        }
    }

    private void eliminarProducto() {
        try {
            String codigo = "";
            di = new detectarInternet(this);
            if (di.hayConexionInternet()) {
                codigo = jsonArray.getJSONObject(posicion).getJSONObject("value").getString("codigo");

            } else {
                codigo = jsonArray.getJSONObject(posicion).getString("codigo");

            }

            AlertDialog.Builder confirmacion = new AlertDialog.Builder(this);
            confirmacion.setTitle("Esta seguro de eliminar a: ");
            confirmacion.setMessage(codigo);
            confirmacion.setPositiveButton("Si", (dialog, which) -> {
                try {

                    if (di.hayConexionInternet()) {//online
                        JSONObject datosproductos = new JSONObject();

                        String _id = jsonArray.getJSONObject(posicion).getJSONObject("value").getString("_id");
                        String _rev = jsonArray.getJSONObject(posicion).getJSONObject("value").getString("_rev");
                        String url = utilidades.url_mto + "/" + _id + "?rev=" + _rev;
                        enviarDatosServidor objEnviarDatosServidor = new enviarDatosServidor(this);
                        String respuesta = objEnviarDatosServidor.execute(datosproductos.toString(), "DELETE", url).get();
                        JSONObject respuestaJSON = new JSONObject(respuesta);
                        if (respuestaJSON.getBoolean("ok")) {
                            obtenerDatosproductos();
                            mostrarMsg("Registro eliminado con exito remota");
                        } else {
                            mostrarMsg("Error:  3" + respuesta);
                        }
                    }else {
                        db.administrarActualizados("modificar", "verdadero");
                    }

                    String respuesta = db.administrar_productos("eliminar", new String[]{jsonArray.getJSONObject(posicion).getString("idProducto")});

                    if (respuesta.equals("ok")) {
                        obtenerDatosproductos();
                        mostrarMsg("Registro eliminado con exito local");
                    } else {
                        mostrarMsg("Error:  4" + respuesta);
                    }
                } catch (Exception e) {
                    mostrarMsg("Error:  5" + e.getMessage());
                }
            });
            confirmacion.setNegativeButton("No", (dialog, which) -> {
                dialog.dismiss();
            });
            confirmacion.create().show();
        } catch (Exception e) {
            mostrarMsg("Error:  6" + e.getMessage());
        }
    }

    private void abriVentana() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(parametros);
        startActivity(intent);
    }

    private void listarDatos() {
        try {
            di = new detectarInternet(this);
            if (di.hayConexionInternet()) {//online

                datosServidor = new obtenerDatosServidor();
                String respuesta = datosServidor.execute().get();

                jsonObject = new JSONObject(respuesta);
                jsonArray = jsonObject.getJSONArray("rows");

                cproductosAuxiliar = db.lista_productosActializados();

                if (cproductosAuxiliar.moveToFirst()) {
                    jsonArrayAuxiliar = new JSONArray();

                    String falso = "falso";

                    if ( Objects.equals(cproductosAuxiliar.getString(1), falso)) {
                        mostrarDatosproductos();
                    }

                }



            } else {//offline

                obtenerDatosproductos();
            }
        } catch (Exception e) {
            mostrarMsg("Error:  7" + e.getMessage());
        }
    }

    private void obtenerDatosproductos() {
        try {
            cproductos = db.lista_productos();
            if (cproductos.moveToFirst()) {
                jsonArray = new JSONArray();
                do {
                    jsonObject = new JSONObject();
                    jsonObject.put("idProducto", cproductos.getString(0));
                    jsonObject.put("codigo", cproductos.getString(1));
                    jsonObject.put("descripcion", cproductos.getString(2));
                    jsonObject.put("marca", cproductos.getString(3));
                    jsonObject.put("presentacion", cproductos.getString(4));
                    jsonObject.put("precio", cproductos.getString(5));
                    jsonObject.put("foto", cproductos.getString(6));

                    jsonArray.put(jsonObject);
                } while (cproductos.moveToNext());



                mostrarDatosproductos();
            } else {
                mostrarMsg("No hay productos registrados.");
                abriVentana();
            }
        } catch (Exception e) {
            mostrarMsg("Error:  8" + e.getMessage());
        }
    }

    private void mostrarDatosproductos() {
        try {


            if (jsonArray.length() >= 1) {
                ltsproductos = findViewById(R.id.ltsProductos);
                alproductos.clear();
                alProductosCopia.clear();
                di = new detectarInternet(this);
                cproductosAuxiliar = db.lista_productosActializados();

                boolean respuesta = di.hayConexionInternet();

                boolean results = true;
                cproductosAuxiliar = db.lista_productosActializados();
                if (cproductosAuxiliar.moveToFirst()) {


                    String verdadero = "verdadero";
                    if (Objects.equals(cproductosAuxiliar.getString(1), verdadero)) {
                        results = false;
                    }
                }


                for (int i = 0; i < jsonArray.length(); i++) {

                    if (respuesta && results) {
                        jsonObject = jsonArray.getJSONObject(i).getJSONObject("value");
                    }else {

                        jsonObject = jsonArray.getJSONObject(i);
                    }


                    misProductos = new productos(
                            jsonObject.getString("idProducto"),
                            jsonObject.getString("codigo"),
                            jsonObject.getString("descripcion"),
                            jsonObject.getString("marca"),
                            jsonObject.getString("presentacion"),
                            jsonObject.getString("precio"),
                            jsonObject.getString("foto")
                    );
                    alproductos.add(misProductos);

                }
                alProductosCopia.addAll(alproductos);
                ltsproductos.setAdapter(new AdaptadorProductos(this, alproductos));
                registerForContextMenu(ltsproductos);
            } else {
                mostrarMsg("No hay productos registrados.");
                abriVentana();
            }
        } catch (Exception e) {
            mostrarMsg("Error:  9" + e.getMessage());
        }
    }

    private void buscarproductos(){
        TextView tempVal = findViewById(R.id.txtBuscarProductos);
        tempVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                alproductos.clear();
                String buscar = tempVal.getText().toString().trim().toLowerCase();
                if( buscar.length()<=0){
                    alproductos.addAll(alProductosCopia);
                }else{
                    for (productos item: alProductosCopia){
                        if(item.getcodigo().toLowerCase().contains(buscar) ||
                                item.getmarca().toLowerCase().contains(buscar) ||
                                item.getdescripcion().toLowerCase().contains(buscar)){
                            alproductos.add(item);
                        }
                    }
                    ltsproductos.setAdapter(new AdaptadorProductos(getApplicationContext(), alproductos));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void mostrarMsg(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
    private void DatosLocalRemoto(){
        try {

            di = new detectarInternet(this);
            if (di.hayConexionInternet()) {
                cproductosAuxiliar = db.lista_productosActializados();
                if (cproductosAuxiliar.moveToFirst()) {
                    jsonArrayAuxiliar = new JSONArray();

                    String verdadero = "verdadero";
                    if (Objects.equals(cproductosAuxiliar.getString(1), verdadero)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Datos no guardados");
                        builder.setMessage("¿Reestablecer datos?");
                        builder.setPositiveButton("Usar datos del dispositivo", (dialogInterface, i) -> datosDispositivo());
                        builder.setNegativeButton("Usar datos de gurdados en la nube", (dialogInterface, i) -> datosNube());
                        builder.show();
                    }
                }
            }
        } catch (Exception e) {
           mostrarMsg("Error: 11" + e.getMessage());
        }

    }
    private void datosDispositivo(){
        try{
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject datosproductos = new JSONObject();
                String _id = jsonArray.getJSONObject(i).getJSONObject("value").getString("_id");
                String _rev = jsonArray.getJSONObject(i).getJSONObject("value").getString("_rev");
                String url = utilidades.url_mto + "/" + _id + "?rev=" + _rev;
                enviarDatosServidor objEnviarDatosServidor = new enviarDatosServidor(this);
                String respuesta = objEnviarDatosServidor.execute(datosproductos.toString(), "DELETE", url).get();
            }
            obtenerDatosproductos();

            for (int i = 0; i < jsonArray.length(); i++) {


                    jsonObject = jsonArray.getJSONObject(i);

                JSONObject datosProductos = new JSONObject();

                datosProductos.put("idProducto", jsonObject.getString("idProducto"));
                datosProductos.put("codigo", jsonObject.getString("codigo"));
                datosProductos.put("descripcion", jsonObject.getString("descripcion"));
                datosProductos.put("marca", jsonObject.getString("marca"));
                datosProductos.put("presentacion", jsonObject.getString("presentacion"));
                datosProductos.put("precio", jsonObject.getString("precio"));
                datosProductos.put("foto", jsonObject.getString("foto"));

                alproductos.add(misProductos);
                enviarDatosServidor objEnviarDatos = new enviarDatosServidor(this);
                String respuesta = objEnviarDatos.execute(datosProductos.toString(), "POST", utilidades.url_mto).get();
            }
            db = new DB(this);
            String res =   db.administrarActualizados("modificar", "falso");
            mostrarMsg(res + " Datos actualizados con exito");
            listarDatos();
        } catch (Exception e) {
            mostrarMsg("Error: 10" + e.getMessage());
        }

    }

    public void datosNube(){
        try{
            db = new DB(this);
            String[] datos1 = {};
            String respuesta1 = db.administrar_productos("eliminarTodo", datos1);

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i).getJSONObject("value");

             String idProducto = jsonObject.getString("idProducto");
             String codigo = jsonObject.getString("codigo");
             String descripcion = jsonObject.getString("descripcion");
             String marca = jsonObject.getString("marca");
             String presentacion = jsonObject.getString("presentacion");
             String precio = jsonObject.getString("precio");
             String foto = jsonObject.getString("foto");

                String[] datos = {idProducto, codigo, descripcion, marca, presentacion, precio, foto};
                String respuesta = db.administrar_productos("nuevo", datos);
            }
            db = new DB(this);
            String res =   db.administrarActualizados("modificar", "falso");
            mostrarMsg(res + " Datos actualizados con exito");
            listarDatos();
        } catch (Exception e) {
            mostrarMsg("Error: 10" + e.getMessage());
        }

    }


}