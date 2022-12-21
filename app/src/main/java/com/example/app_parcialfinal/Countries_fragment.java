package com.example.app_parcialfinal;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Countries_fragment extends Fragment {


    private Button Signout;
    private TextView username;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    private ArrayAdapter adapter;
    EditText buscador;
    ListView countries;
    Fragment fragmentDetails;
    FragmentTransaction transaction;
    ArrayList<String> names = new ArrayList<>();

    public Countries_fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAllCountries();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_countries_fragment, container, false);
        countries = root.findViewById(R.id.idListCountries);
        buscador = root.findViewById(R.id.idSearch);
        fragmentDetails = new CountryDetails_Fragment();
        adapter = new ArrayAdapter(getActivity(),R.layout.list_item_layout,names);
        countries.setAdapter(adapter);
        Signout = root.findViewById(R.id.BtnSignout);
        username = root.findViewById(R.id.textViewUsername);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(getContext(), gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());

        if(acct != null){
            username.setText(username.getText()+acct.getDisplayName());
        }

        Signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(acct!=null){
                    SignOut();
                }else{
                    SignOutMocky();
                }
            }
        });

        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (Countries_fragment.this).adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        countries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Object pos = adapter.getItem(position);
                //Toast.makeText(getContext(), "Has pulsado: "+ names.get(position)+pos.toString(), Toast.LENGTH_LONG).show();
                Bundle result = new Bundle();
                result.putString("descrip",pos.toString());
                //se mandan los datos que se quieren enviar al otro fragmento al fragmentManager
                getParentFragmentManager().setFragmentResult("bundleKey", result);
                transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.ContainerCountries,fragmentDetails).commit();
                transaction.addToBackStack(null);
                buscador.setText("");
            }
        });

        return root;
    }

    public void SignOut(){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                try {
                    Countries_fragment.this.finalize();
                    Intent so = new Intent(getActivity(),MainActivity.class);
                    so.addFlags(so.FLAG_ACTIVITY_CLEAR_TASK | so.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(so);
                } catch (Throwable e) {
                    e.printStackTrace();
                }


            }
        });
    }

    public void SignOutMocky(){
        Intent so = new Intent(getActivity(),MainActivity.class);
        so.addFlags(so.FLAG_ACTIVITY_CLEAR_TASK | so.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(so);
    }

    public void getAllCountries(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="https://restcountries.com/v2/all";

        // Request a string response from the provided URL.
        JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Display the first 500 characters of the response string.
                        //data.setText(""+response.toString());

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject pais = response.getJSONObject(i);
                                String name = pais.getString("name");
                                names.add(name);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("respuesta de countries",""+response.length());

                        //textCountries.setText(""+response.toString());
                    }
                }, new Response.ErrorListener() {
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