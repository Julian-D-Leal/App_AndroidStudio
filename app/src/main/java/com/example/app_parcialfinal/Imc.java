package com.example.app_parcialfinal;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;

public class Imc extends AppCompatActivity {

    private EditText peso, altura;
    private Button calcular;
    private TextView imc, descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        peso = findViewById(R.id.txtpeso);
        altura = findViewById(R.id.txtaltura);

        calcular = findViewById(R.id.btncalcular);

        imc = findViewById(R.id.txtimc);
        descripcion = findViewById(R.id.txtdescripcion);

        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(peso.getText().toString().equals("") || altura.getText().toString().equals("")){
                    Toast.makeText(Imc.this, "Debe ingresar peso y altura", Toast.LENGTH_SHORT).show();
                }else{
                    double npeso = Double.parseDouble(peso.getText().toString());
                    double naltura = Double.parseDouble(altura.getText().toString());

                    double resultadoimc = npeso/(naltura * naltura);
                    imc.setText("IMC: " + String.format("%.3f",resultadoimc));

                    if(resultadoimc<18.599){
                        descripcion.setText("Descripción: Bajo peso");
                    }else if(resultadoimc>=18.5 && resultadoimc<=24.999){
                        descripcion.setText("Descripción: Peso normal");
                    }else if(resultadoimc>=25 && resultadoimc<=29.999){
                        descripcion.setText("Descripción: Sobrepeso");
                    }else if(resultadoimc>=30 && resultadoimc<=34.999){
                        descripcion.setText("Descripción: Obesidad moderada");
                    }else if(resultadoimc>=35 && resultadoimc<=39.999){
                        descripcion.setText("Descripción: Obesidad severa");
                    }else{
                        descripcion.setText("Descripción: Obesidad muy severa");
                    }
                }
            }
        });
    }

}