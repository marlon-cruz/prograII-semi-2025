package com.example.miprimeraaplicacion;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btn;
    TextView tempVal;
    Spinner spn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btnCalcular);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempVal = findViewById(R.id.txtNum1);
                double num1 = Double.parseDouble(tempVal.getText().toString());

                tempVal = findViewById(R.id.txtNum2);
                double num2 = Double.parseDouble(tempVal.getText().toString());

                double respuesta = 0.0;

                spn = findViewById(R.id.spnOpciones);
                switch (spn.getSelectedItemPosition()){
                    case 0:
                        respuesta = num1 + num2;
                        break;
                    case 1:
                        respuesta = num1 - num2;
                        break;
                    case 2:
                        respuesta = num1 * num2;
                        break;
                    case 3:
                        respuesta = num1 / num2;
                        break;
                    case 4:
                        respuesta = Math.pow(num1, num2);
                        break;
                    case 5:
                        respuesta = (num1 / num2) * 100;
                        break;
                    case 6:
                        respuesta = calcularRaiz(num1,num2);
                        break;
                    case 7:
                        respuesta = factorial((int)num1);

                        break;
                }
                tempVal = findViewById(R.id.lblRespuesta);
                tempVal.setText("Respuesta: "+ respuesta);
            }
        });

    }

    public static int factorial(int num) {
        int resultado = 1;
        for (int i = 2; i <= num; i++) {
            resultado *= i;
        }
        return resultado;
    }

    public static double calcularRaiz(double numero, double indice) {
        if (numero < 0 && indice % 2 == 0) {
           return 0.0;
        }
        return Math.pow(numero, 1.0 / indice);
    }

}