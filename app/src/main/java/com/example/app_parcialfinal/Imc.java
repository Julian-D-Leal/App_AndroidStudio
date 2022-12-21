package com.example.app_parcialfinal;

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

import java.util.zip.Inflater;

public class Imc extends AppCompatActivity {

    private EditText peso, altura;
    private Button calcular;
    private TextView imc, descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);

        peso = findViewById(R.id.txtpeso);
        altura = findViewById(R.id.txtaltura);

        calcular = findViewById(R.id.btncalcular);

        imc = findViewById(R.id.txtimc);
        descripcion = findViewById(R.id.txtdescripcion);

    }

    public void calcular(View view) {
        double npeso = Double.parseDouble(peso.getText().toString());
        double naltura = Double.parseDouble(altura.getText().toString());

        double resultadoimc = npeso/(naltura * naltura);

        imc.setText("IMC: " + String.format("%.2f",resultadoimc));

        if(resultadoimc<18.5){
            descripcion.setText("bajo peso");
        }else if(resultadoimc>=18.5 && resultadoimc<=24.9){
            descripcion.setText("peso normal");
        }else if(resultadoimc>=25 && resultadoimc<=29.9){
            descripcion.setText("sobrepeso");
        }
    }
}