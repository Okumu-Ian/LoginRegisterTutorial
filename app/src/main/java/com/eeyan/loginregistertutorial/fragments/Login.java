package com.eeyan.loginregistertutorial.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eeyan.loginregistertutorial.MainActivity;
import com.eeyan.loginregistertutorial.R;
import com.eeyan.loginregistertutorial.Welcome;

import java.util.HashMap;
import java.util.Map;

public class Login extends Fragment {

    private View mView;
    private TextInputEditText email,password;
    private TextView textViewSigning;
    private AppCompatButton button;

    public Login() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_register,container,false);
        email = mView.findViewById(R.id.edt_register_email);
        password = mView.findViewById(R.id.edt_register_password);
        textViewSigning = mView.findViewById(R.id.txt_signin);
        button = mView.findViewById(R.id.btn_register);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //give buttons click listeners
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call checkCredentials Method
                checkCredentials();
            }
        });

        textViewSigning.setClickable(true);
        textViewSigning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call the log in fragment
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_container,new Register());
                transaction.commit();
            }
        });

    }

    private void checkCredentials(){
        //get values from strings
        String user_email = email.getText().toString();
        String user_password = password.getText().toString();

        //check for blanks
        if(user_email.length()==0){
            email.setError("Email can't be blank");
        }else if (!Patterns.EMAIL_ADDRESS.matcher(user_email).matches()){
            email.setError("Enter a valid email");
        }else if(user_password.length()<6){
            email.setError("Enter a longer password");}
        else{
            loginUSER(user_email,user_password);
        }
    }

    private void loginUSER(final String sample_email, final String sample_password){

        String url = "";
        //Send data to server
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("success")){
                    Intent mIntent = new Intent(getActivity(), Welcome.class);
                    startActivity(mIntent);
                }else{
                    Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), ""+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("email",sample_email);
                params.put("password",sample_password);
                params.put("token","login");
                return params;
            }
        };

        Volley.newRequestQueue(getActivity()).add(stringRequest);

    }
}
