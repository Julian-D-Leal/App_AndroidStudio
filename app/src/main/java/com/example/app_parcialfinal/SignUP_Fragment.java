package com.example.app_parcialfinal;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SignUP_Fragment extends Fragment {

    EditText Username, Pass, Email;
    Button SignUp;

    public SignUP_Fragment() {
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
        View root = inflater.inflate(R.layout.fragment_sign_u_p_, container, false);
        Username = root.findViewById(R.id.editTextUsername);
        Pass = root.findViewById(R.id.editTextPassword);
        Email = root.findViewById(R.id.editTextEmail);
        SignUp = root.findViewById(R.id.BtnSignUp);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Username.getText().toString().equals("") && !Pass.getText().toString().equals("") && !Email.getText().toString().equals("")) {
                    postDataUsingVolley(Username.getText().toString(),Pass.getText().toString(),Email.getText().toString());
                    Username.setText("");
                    Pass.setText("");
                    Email.setText("");
                }else{
                    Toast.makeText(getActivity(), "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return root;
    }

    private void postDataUsingVolley(String username, String password, String email) {
        // url to post our data
        String url = "https://639f827d7aaf11ceb89c09fd.mockapi.io/api/v1/users";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // on below line we are displaying a success toast message.
                Toast.makeText(getActivity(), "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                Intent navegar = new Intent(getActivity(), Countries.class);
                navegar.addFlags(navegar.FLAG_ACTIVITY_CLEAR_TASK | navegar.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(navegar);
                try {
                    // on below line we are parsing the response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(response);

                    // below are the strings which we
                    // extract from our json object.
                    //String username = respObj.getString("name");
                    //String job = respObj.getString("job");

                    Log.d("respuesta de usuarios",""+response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(getActivity(), "Algo falló", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("username", username);
                params.put("password", password);
                params.put("email", email);

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }
}