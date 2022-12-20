package com.example.app_parcialfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class Countries extends AppCompatActivity {

    Fragment fragmentCountries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);

        fragmentCountries = new Countries_fragment();

        //Se asigna el fragmento de inicio
        getSupportFragmentManager().beginTransaction().add(R.id.ContainerCountries,fragmentCountries).commit();
    }
}