package com.example.miprimeraaplicacion;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TabHost tbh;
    Button btn;
    TextView tempVal;
    Spinner spn;
    conversores objConversores = new conversores();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tbh = findViewById(R.id.tbhConversor);
        tbh.setup();

        tbh.addTab(tbh.newTabSpec("Monedas").setContent(R.id.tabMonedas).setIndicator("MONEDAS", null));
        tbh.addTab(tbh.newTabSpec("Longitud").setContent(R.id.tabLongitud).setIndicator("LONGITUD", null));
        tbh.addTab(tbh.newTabSpec("Tiempo").setContent(R.id.tabTiempo).setIndicator("TIEMPO", null));
        tbh.addTab(tbh.newTabSpec("Almacenamiento").setContent(R.id.tabAlmacenamiento).setIndicator("ALMACENAMIENTO", null));

        btn = findViewById(R.id.btnCalcular);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int opcion = tbh.getCurrentTab();

                spn = findViewById(R.id.spnDeMonedas);
                int de = spn.getSelectedItemPosition();

                spn = findViewById(R.id.spnAMonedas);
                int a = spn.getSelectedItemPosition();

                tempVal = findViewById(R.id.txtCantidad);
                double cantidad = Double.parseDouble(tempVal.getText().toString());

                tempVal = findViewById(R.id.lblRespuesta);
                double respuesta = objConversores.convertir(opcion, de, a, cantidad);
                tempVal.setText("Respuesta: "+ respuesta);
            }
        });
    }
}
class conversores{
    double[][] valores= {
            {1,0.98, 7.73, 25.45, 36.78, 508.87, 8.74},//monedas
            {},//Longitud
            {},//tiempo
            {},//Almacenamiento
    };
    public double convertir(int opcion, int de, int a, double cantidad){
        return valores[opcion][a] / valores[opcion][de] * cantidad;
    }
}