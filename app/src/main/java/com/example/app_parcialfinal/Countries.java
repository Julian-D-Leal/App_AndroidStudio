package com.example.app_parcialfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.zip.Inflater;

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



    public  void onClick(View view){
        Intent navegar = new Intent(this,Imc.class);
        navegar.addFlags(navegar.FLAG_ACTIVITY_CLEAR_TASK | navegar.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(navegar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_countries,menu);
        return true;
    }

   /* @Override
    public boolean onOptionsItemSelected( MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.MenuC){
            ejecutar(null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}