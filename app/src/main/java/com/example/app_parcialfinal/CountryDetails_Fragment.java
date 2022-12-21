package com.example.app_parcialfinal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CountryDetails_Fragment extends Fragment {


    TextView nombre,capital,region,population;
    String url;
    ImageView flag;

    public CountryDetails_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //se reciben los datos que manda el fragmento con la lista de paises con el fragmentManager
        getParentFragmentManager().setFragmentResultListener("bundleKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                String country = bundle.getString("descrip");
                url = String.format("https://restcountries.com/v2/name/%s",country);
                getCountryDetails(url);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_country_details_, container, false);
        nombre = root.findViewById(R.id.CountryDetails);
        capital = root.findViewById(R.id.CountryCapital);
        region = root.findViewById(R.id.CountryRegion);
        population = root.findViewById(R.id.CountryPopulation);
        flag = root.findViewById(R.id.imageViewFlag);
        return root;
    }

    public void getCountryDetails(String url){
        RequestQueue queue = Volley.newRequestQueue(getContext());


        JsonArrayRequest stringRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0;i<=response.length();i++){
                    try {
                        JSONObject pais = response.getJSONObject(i);
                        nombre.setText(pais.getString("name"));
                        capital.setText(pais.getString("capital"));
                        region.setText(pais.getString("region"));
                        JSONObject bandera = pais.getJSONObject("flags");
                        population.setText(pais.getString("population"));
                        String urlFlag = bandera.getString("png");
                        Glide.with(getContext()).load(urlFlag).into(flag);//sirve para cargar imagenes desde una url
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("respuesta de request",""+response.length());
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textView.setText("That didn't work!");
                //data.setText("error");
                Log.d("respuesta con error","");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}