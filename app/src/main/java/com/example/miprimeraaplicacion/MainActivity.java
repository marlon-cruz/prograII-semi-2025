package com.example.miprimeraaplicacion;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    Button btn;
    TextView tempVal;
    DB db;
    //Acción nuevo para crear un nuevo registro
    String accion = "nuevo", idProducto = "";
    String mostrarMsg(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        return msg;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DB(this);
        btn = findViewById(R.id.btnGuardarProducto);
        btn.setOnClickListener(view->guardarProducto());

        fab = findViewById(R.id.fabListaProductos);
        fab.setOnClickListener(view->abrirVentana());
        mostrarDatos();

    }
    //Para modificar
    private void mostrarDatos(){
        try {
            //Recuperamos los parametros que vienen para modificar
            Bundle parametros = getIntent().getExtras();
            accion = parametros.getString("accion");
            if(accion.equals("modificar")){
                //Recuperamos los datos del amigo
                JSONObject datos = new JSONObject(parametros.getString("productos"));
                idProducto = datos.getString("idProducto");

                tempVal = findViewById(R.id.txtCodigo);
                tempVal.setText(datos.getString("codigo"));

                tempVal = findViewById(R.id.txtDescripcion);
                tempVal.setText(datos.getString("descripcion"));

                tempVal = findViewById(R.id.txtMarca);
                tempVal.setText(datos.getString("marca"));

                tempVal = findViewById(R.id.txtPresentacion);
                tempVal.setText(datos.getString("presentacion"));


                tempVal = findViewById(R.id.txtPrecio);
                tempVal.setText(datos.getString("precio"));

            }

        }
        catch (Exception e){
            mostrarMsg("Error: " + e.getMessage());
        }
    }
    private void abrirVentana(){
        Intent intent = new Intent(this, lista_productos.class);
        startActivity(intent);
    }
    private void guardarProducto() {
        tempVal = findViewById(R.id.txtCodigo);
        String codigo = tempVal.getText().toString();

        tempVal = findViewById(R.id.txtDescripcion);
        String descripcion = tempVal.getText().toString();

        tempVal = findViewById(R.id.txtMarca);
        String marca = tempVal.getText().toString();

        tempVal = findViewById(R.id.txtPresentacion);
        String presentacion = tempVal.getText().toString();

        tempVal = findViewById(R.id.txtPrecio);
        String precio = tempVal.getText().toString();

        if(codigo.isEmpty() || descripcion.isEmpty() || marca.isEmpty() || presentacion.isEmpty() || precio.isEmpty()){
            mostrarMsg("Debe llenar todos los campos");
            return;
        }
        //Arreglo de datos
        String[] datos = {"", codigo, descripcion, marca, presentacion, precio, ""};
        //Llamando al metodo administrar amigos de la clase DB
        if (accion.equals("modificar")){
            datos[0] = idProducto;
            db.administrar_productos("modificar", datos);
            Toast.makeText(getApplicationContext(), "Registro modificado con exito", Toast.LENGTH_LONG).show();
            abrirVentana();//Abrir ventanas
        }/*else if(accion.equals("eliminar")) {
            //Confirmación para eliminar
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Eliminar");
            builder.setMessage("¿Desea eliminar el registro?");
            builder.setPositiveButton("Si", (dialogInterface, i) -> eliminar(datos));
            builder.setNegativeButton("No", (dialogInterface, i) -> {
            });
            builder.show();}*/
        else{
            db.administrar_productos("agregar", datos);
            Toast.makeText(getApplicationContext(), "Registro guardado con exito", Toast.LENGTH_LONG).show();
            abrirVentana();//Abrir ventanas
        }
    }
/*    private void eliminar(String[] datos) {

        datos[0] = idProducto;
        db.administrar_productos("eliminar", datos);
        Toast.makeText(getApplicationContext(), "Registro eliminado con exito", Toast.LENGTH_LONG).show();
        abrirVentana();//Abrir ventanas
    }*/
}