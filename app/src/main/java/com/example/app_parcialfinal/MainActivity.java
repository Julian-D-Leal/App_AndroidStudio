package com.example.app_parcialfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.app_parcialfinal.Login;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    Fragment fragmentLogin,fragmentSignUp;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentLogin = new Login();
        fragmentSignUp = new SignUP_Fragment();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Se asigna el fragmento de inicio
        getSupportFragmentManager().beginTransaction().add(R.id.contenedorFragments,fragmentLogin).commit();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        transaction = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()){
            case R.id.MenuSignUp:
                transaction.replace(R.id.contenedorFragments,fragmentSignUp);
                transaction.addToBackStack(null);
                break;
        }
        transaction.commit();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Inflater inflater = new Inflater();
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}