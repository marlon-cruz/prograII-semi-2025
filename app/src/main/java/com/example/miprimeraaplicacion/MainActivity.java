package com.example.miprimeraaplicacion;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btn;
    TextView tempVal;
     RadioGroup rdg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btnCalcular);
        btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                try {

                    tempVal = findViewById(R.id.txtNum1);
                    double num1 = 0;

                    if (tempVal.getText().toString().isEmpty()){
                        num1 = 0;
                    }else {
                        num1 = Double.parseDouble(tempVal.getText().toString());
                    }

                    tempVal = findViewById(R.id.txtNum2);
                    double num2 = 0;
                    double respuesta = 0;

                    if (tempVal.getText().toString().isEmpty()){
                        num2 = 0;
                    }else {
                        num2 = Double.parseDouble(tempVal.getText().toString());
                    }



                    tempVal = findViewById(R.id.lblRespuesta);
                    rdg = findViewById(R.id.radioBtn);

                    if (rdg.getCheckedRadioButtonId() == R.id.radio_suma) {
                        respuesta = num1 + num2;
                    } else if (rdg.getCheckedRadioButtonId() == R.id.radio_multiplicacion) {
                        respuesta = num1 * num2;
                    } else if (rdg.getCheckedRadioButtonId() == R.id.radio_resta) {
                        respuesta = num1 - num2;
                    } else if (rdg.getCheckedRadioButtonId() == R.id.radio_divicion) {
                        respuesta = num1 / num2;
                    }
                    //aqui comienza la segunda parte
                    else if (rdg.getCheckedRadioButtonId() == R.id.radio_potencia) {
                        respuesta = Math.pow(num1, num2);
                    } else if (rdg.getCheckedRadioButtonId() == R.id.radio_porcentaje) {
                        respuesta = (num1 / num2) * 100;
                    } else if (rdg.getCheckedRadioButtonId() == R.id.radio_raiz) {
                        respuesta = calcularRaiz(num1,num2);
                    } else if (rdg.getCheckedRadioButtonId() == R.id.radio_factorial) {
                        respuesta = factorial((int)num1);
                    }

                    tempVal.setText("Respuesta: " + respuesta);


                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
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