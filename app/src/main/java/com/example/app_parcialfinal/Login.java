package com.example.app_parcialfinal;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.zip.Inflater;


public class Login extends Fragment {


    EditText username, password;
    Button submit;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    public Login() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        username = root.findViewById(R.id.IdTxtUsername);
        password = root.findViewById(R.id.IdTxtPassword);
        submit = root.findViewById(R.id.IdBtnSubmit);
        SignInButton signInButton = root.findViewById(R.id.sign_in_button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(internetIsConnected()){
                    if(!username.getText().toString().equals("") && !password.getText().toString().equals("")){
                        LoginVerification(username.getText().toString(), password.getText().toString());
                    }else{
                        Toast.makeText(getContext(), "Ingrese usuario y contraseña", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "Conexión a internet fallida", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(internetIsConnected()){
                    signIn();
                }else{
                    Toast.makeText(getActivity(), "Conexión a internet fallida", Toast.LENGTH_SHORT).show();
                }

            }
        });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(getContext(), gso);

        return root;
    }

    public void LoginVerification(String username, String password){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="https://639f827d7aaf11ceb89c09fd.mockapi.io/api/v1/users?search="+username;

        // Request a string response from the provided URL.
        JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Display the first 500 characters of the response string.
                        //data.setText(""+response.toString());
                        if(response.length()!=0) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject user = response.getJSONObject(i);
                                    String name = user.getString("username");
                                    String pass = user.getString("password");
                                    //Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
                                    if(i+1 == response.length() && !pass.equals(password)){
                                        Toast.makeText(getContext(), "Datos de usuario incorrectos", Toast.LENGTH_SHORT).show();
                                    }
                                    if (name.equals(username) && pass.equals(password)) {
                                        //guardando datos a través del fragmentmanager para extraerlos en otro fragment
                                        Bundle result = new Bundle();
                                        result.putString("usern",name);
                                        getParentFragmentManager().setFragmentResult("bundleKey", result);
                                        //navegando a otra actividad
                                        Intent signIn = new Intent(getActivity(),Countries.class);
                                        signIn.addFlags(signIn.FLAG_ACTIVITY_CLEAR_TASK | signIn.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(signIn);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else{
                            Toast.makeText(getContext().getApplicationContext(), "Datos incorrectos",Toast.LENGTH_SHORT).show();
                        }
                        Log.d("respuesta de usuarios",""+response);

                        //textCountries.setText(""+response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textView.setText("That didn't work!");
                //data.setText("error");
                Log.d("respuesta con error","");
                Toast.makeText(getContext(),"Datos incorrectos",Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            } catch (ApiException e) {
                Toast.makeText(getContext(), "Algo falló", Toast.LENGTH_SHORT).show();
            }

        }
    }
    public boolean internetIsConnected() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }
    public void navigateToSecondActivity(){
        Intent intent = new Intent(getActivity(),Countries.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}