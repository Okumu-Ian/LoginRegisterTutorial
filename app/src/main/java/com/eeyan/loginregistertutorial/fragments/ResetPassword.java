package com.eeyan.loginregistertutorial.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ResetPassword extends Fragment {
    private View mView;
    private AppCompatButton button;
    private TextInputEditText email;
    public ResetPassword() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_register,container,false);
        email = mView.findViewById(R.id.edt_register_email);
        button = mView.findViewById(R.id.btn_register);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }

    private void sendEmail(){
        final String user_email = email.getText().toString();
        String url = "";
        StringRequest st = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase(response)) {
                    Intent mIntent = new Intent(getActivity(), Welcome.class);
                } else {
                    Toast.makeText(getActivity(), "" + response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("email",user_email);
                params.put("token","reset");
                return params;
            }
        };
        Volley.newRequestQueue(getActivity()).add(st);
    }
}
