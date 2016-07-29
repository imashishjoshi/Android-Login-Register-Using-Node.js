package com.ghostcreations.androidloginreg;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ghostcreations.androidloginreg.models.ServerRequest;
import com.ghostcreations.androidloginreg.models.ServerResponse;
import com.ghostcreations.androidloginreg.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ghost on 7/29/2016.
 */
public class Register extends android.app.Fragment implements View.OnClickListener {

    private AppCompatButton btn_register;
    private EditText et_email,et_password,et_name,et_username,et_cpassword;
    private TextView tv_login;
    private ProgressBar progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View view){

        btn_register = (AppCompatButton)view.findViewById(R.id.btn_register);
        tv_login = (TextView)view.findViewById(R.id.tv_login);
        et_name = (EditText)view.findViewById(R.id.et_name);
        et_email = (EditText)view.findViewById(R.id.et_email);
        et_username = (EditText)view.findViewById(R.id.et_username);
        et_password = (EditText)view.findViewById(R.id.et_password);
        et_cpassword = (EditText)view.findViewById(R.id.et_cpassword);

        progress = (ProgressBar)view.findViewById(R.id.progress);

        btn_register.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_login:
               // goToLogin();
                break;

            case R.id.btn_register:

                String name = et_name.getText().toString();
                String email = et_email.getText().toString();
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                String cpassword = et_cpassword.getText().toString();

                if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !username.isEmpty() && !cpassword.isEmpty()) {

                    if(email.contains("@") && email.contains(".")){
                        if(cpassword.equals(password)){
                            if(password.length() >= 6) {
                                progress.setVisibility(View.VISIBLE);
                                registerProcess(name, email, password, username);
                            }else{
                                Snackbar.make(getView(), "Passwords too Short !", Snackbar.LENGTH_LONG).show();
                            }
                        }else{
                            Snackbar.make(getView(), "Passwords Doesn't Match !", Snackbar.LENGTH_LONG).show();
                        }
                    }else{
                        et_email.requestFocus();
                        Snackbar.make(getView(), "Invalid Email !", Snackbar.LENGTH_LONG).show();
                    }
                } else {

                    Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                }
                break;

        }

    }

    private void registerProcess(String name, String email,String password, String username){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RegisterInterface requestInterface = retrofit.create(RegisterInterface.class);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setUser(user);

Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG,"failed");
                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();


            }
        });
    }
}
