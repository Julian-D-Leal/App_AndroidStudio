package com.example.app_parcialfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        fragmentCountries = new Countries_fragment();

        //Se asigna el fragmento de inicio
        getSupportFragmentManager().beginTransaction().add(R.id.ContainerCountries,fragmentCountries).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Inflater inflater = new Inflater();
        getMenuInflater().inflate(R.menu.menu_countries,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent navegar = new Intent(this,Imc.class);
        navegar.addFlags(navegar.FLAG_ACTIVITY_CLEAR_TASK | navegar.FLAG_ACTIVITY_CLEAR_TOP);

        switch (item.getItemId()){
            case R.id.MenuC:
                startActivity(navegar);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}