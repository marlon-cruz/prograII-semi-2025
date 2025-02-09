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
            tbh.addTab(tbh.newTabSpec("Masa").setContent(R.id.tabMasa).setIndicator("MASA", null));
            tbh.addTab(tbh.newTabSpec("Volumen").setContent(R.id.tabVolumen).setIndicator("VOLUMEN", null));
            tbh.addTab(tbh.newTabSpec("Longitud").setContent(R.id.tabLongitud).setIndicator("LONGITUD", null));
            tbh.addTab(tbh.newTabSpec("Almacenamiento").setContent(R.id.tabAlmacenamiento).setIndicator("ALMACENAMIENTO", null));
            tbh.addTab(tbh.newTabSpec("Tiempo").setContent(R.id.tabTiempo).setIndicator("TIEMPO", null));
            tbh.addTab(tbh.newTabSpec("TransferenciaDatos").setContent(R.id.tabTransferenciaDatos).setIndicator("TRANFERENCIA DE DATOS", null));


            btn = findViewById(R.id.btnCalcular);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int opcion = tbh.getCurrentTab();
                    int de = 0;
                    int a = 0;
                    switch (opcion){
                        case 0:
                            spn = findViewById(R.id.spnDeMonedas);
                             de = spn.getSelectedItemPosition();

                            spn = findViewById(R.id.spnAMonedas);
                             a = spn.getSelectedItemPosition();
                            break;
                        case 1:
                            spn = findViewById(R.id.spnDeMasa);
                            de = spn.getSelectedItemPosition();

                            spn = findViewById(R.id.spnAMasa);
                            a = spn.getSelectedItemPosition();
                            break;
                        case 2:
                            spn = findViewById(R.id.spnDeVolumen);
                            de = spn.getSelectedItemPosition();

                            spn = findViewById(R.id.spnAVolumen);
                            a = spn.getSelectedItemPosition();
                            break;
                        case 3:
                            spn = findViewById(R.id.spnDeLongitud);
                            de = spn.getSelectedItemPosition();

                            spn = findViewById(R.id.spnALongitud);
                            a = spn.getSelectedItemPosition();
                            break;
                        case 4:
                            spn = findViewById(R.id.spnDeAlmacenamiento);
                            de = spn.getSelectedItemPosition();

                            spn = findViewById(R.id.spnAAlmacenamiento);
                            a = spn.getSelectedItemPosition();
                            break;
                        case 5:
                            spn = findViewById(R.id.spnDeTiempo);
                            de = spn.getSelectedItemPosition();

                            spn = findViewById(R.id.spnATiempo);
                            a = spn.getSelectedItemPosition();
                            break;
                        case 6:
                            spn = findViewById(R.id.spnDeTransferenciasDatos);
                            de = spn.getSelectedItemPosition();

                            spn = findViewById(R.id.spnATransferenciasDatos);
                            a = spn.getSelectedItemPosition();
                            break;
                    }



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
    double[][] valores = {
            {1, 0.98, 7.73, 25.45, 36.78, 508.87, 8.74}, // monedas
            // {1, 0.98, 7.73, 25.45, 36.78, 508.87, 8.74}, // monedas (comentado)
            {1, 1000, 1000000, 0.001, 35.274, 2.20462, 0.01, 10, 100, 10000}, // masa
            {1, 0.000000001, 0.000001, 0.001, 1000, 1057, 264.172, 6.29, 35.3148, 61023.7}, // volumen
            {1, 1000, 0.01, 0.001, 0.0254, 0.3048, 0.9144, 1609.34, 0.000001, 0.000000001}, // longitud
            {}, // tiempo
            {}, // almacenamiento
    };
    public double convertir(int opcion, int de, int a, double cantidad){
        return valores[opcion][a] / valores[opcion][de] * cantidad;
    }
}