package com.example.miprimeraaplicacion;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


    public class MainActivity extends AppCompatActivity {

        Button btn;
        TextView temVal;

        DB db;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            btn.findViewById(R.id.btnGuardarAmigo);

            db = new DB(this);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buardarAmigo();
                }
            });
        }

        private void buardarAmigo() {
                temVal = findViewById(R.id.txtNombre);
                String nombre = temVal.getText().toString();
                temVal = findViewById(R.id.txtDireccion);
                String direccion = temVal.getText().toString();
                temVal = findViewById(R.id.txtTelefono);
                String telefono = temVal.getText().toString();
                temVal = findViewById(R.id.txtEmail);
                String email = temVal.getText().toString();
                temVal = findViewById(R.id.txtDui);
                String dui = temVal.getText().toString();

                String[] datos = {"", nombre, direccion, telefono, email, dui, ""};
                db.administrar_amigos("agregar", datos);
            Toast.makeText(getApplicationContext(),"Amigo agredado",Toast.LENGTH_LONG).show();
        }

    }
