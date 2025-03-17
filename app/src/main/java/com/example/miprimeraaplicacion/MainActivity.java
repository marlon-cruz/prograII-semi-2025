package com.example.miprimeraaplicacion;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    Button btn;
    TextView tempVal;
    DB db;
    //Acción nuevo para crear un nuevo registro
    String accion = "nuevo", idProducto = "";
    ImageView img;
    String urlCompletaFoto = "";
    Intent tomarFotoIntent;

    String mostrarMsg(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        return msg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DB(this);
        btn = findViewById(R.id.btnGuardarProducto);
        btn.setOnClickListener(view -> guardarProducto());
        img = findViewById(R.id.imgFotoProducto);
        fab = findViewById(R.id.fabListaProductos);
        fab.setOnClickListener(view -> abrirVentana());
        mostrarDatos();
        tomarFoto();
    }

    //Para modificar
    private void mostrarDatos() {
        try {
            //Recuperamos los parametros que vienen para modificar
            Bundle parametros = getIntent().getExtras();
            accion = parametros.getString("accion");


            if (accion.equals("modificar")) {
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

                urlCompletaFoto = datos.getString("foto");
                Bitmap bitmap = BitmapFactory.decodeFile(urlCompletaFoto);
                img.setImageBitmap(bitmap);


            }

        } catch (Exception e) {
            mostrarMsg("Error: " + e.getMessage());
        }
    }

    private void tomarFoto() {
        img.setOnClickListener(view -> {
            tomarFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File fotoAmigo = null;
            try {
                fotoAmigo = crearFotoProducto();
                if (fotoAmigo != null) {
                    Uri uriFotoAimgo = FileProvider.getUriForFile(MainActivity.this,
                            "com.example.miprimeraaplicacion.fileprovider", fotoAmigo);
                    tomarFotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriFotoAimgo);
                    startActivityForResult(tomarFotoIntent, 1);
                } else {
                    mostrarMsg("No se pudo crear la imagen.");
                }
            } catch (Exception e) {
                mostrarMsg("Error: " + e.getMessage());
            }
        });
    }

    private File crearFotoProducto() {
        try {
            String fechaHoraMs = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()),
                    fileName = "imagen_" + fechaHoraMs + "_";
            File dirAlmacenamiento = getExternalFilesDir(Environment.DIRECTORY_DCIM);
            if (dirAlmacenamiento.exists() == false) {
                dirAlmacenamiento.mkdir();
            }
            File image = File.createTempFile(fileName, ".jpg", dirAlmacenamiento);
            urlCompletaFoto = image.getAbsolutePath();
            return image;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1 && resultCode == RESULT_OK) {
                //Bitmap imageBitmap = BitmapFactory.decodeFile(urlCompletaFoto)
                img.setImageURI(Uri.parse(urlCompletaFoto));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void abrirVentana() {
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

        if (codigo.isEmpty() || descripcion.isEmpty() || marca.isEmpty() || presentacion.isEmpty() || precio.isEmpty()) {
            mostrarMsg("Debe llenar todos los campos");
            return;
        }
        //Arreglo de datos
        String[] datos = {idProducto, codigo, descripcion, marca, presentacion, precio, urlCompletaFoto};

        //Llamando al metodo administrar amigos de la clase DB
        db.administrar_productos(accion,datos);
        Toast.makeText(getApplicationContext(), "Registro guardado con exito.", Toast.LENGTH_LONG).show();
        abrirVentana();
    }


}