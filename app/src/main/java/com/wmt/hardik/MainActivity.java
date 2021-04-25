package com.wmt.hardik;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wmt.hardik.model.Register.Resgister;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText username, email, password;
    Button signup;
    String usernamestr, emailstr, passstr;
    SessionManager sessionManager;
    SharedPreferences login_sharedPreferences;
    private static final String PREF_NAME = "Pref";
    private boolean res1;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(getApplicationContext());
        login_sharedPreferences = getSharedPreferences(PREF_NAME, 0);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.sugn_up);

        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
               // getRegister();
               /* Intent intent = new Intent(MainActivity.this, UserListActivity.class);
                startActivity(intent);
                finish();*/
            }
        });
    }

    public void validate(){


        usernamestr = username.getText().toString();
        passstr = password.getText().toString();
        emailstr = email.getText().toString();

        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(passstr);
        boolean isStringContainsSpecialCharacter = matcher.find();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        if (usernamestr.length()==0 && emailstr.length()==0 && passstr.length()==0){
            Toast.makeText(this, "All Field Require", Toast.LENGTH_SHORT).show();
        }else if (usernamestr.length()==0){
            Toast.makeText(this, "Username Require", Toast.LENGTH_SHORT).show();
        }else if (passstr.length()==0){
            Toast.makeText(this, "Password Require", Toast.LENGTH_SHORT).show();
        }else if (emailstr.length()==0){
            Toast.makeText(this, "Email Require", Toast.LENGTH_SHORT).show();
        }else if (usernamestr.length()<6){
            Toast.makeText(this, "Username must be graterthan 6 letter", Toast.LENGTH_SHORT).show();
        }else if (passstr.length()<6){
            Toast.makeText(this, "Password must be graterthan 6 letter", Toast.LENGTH_SHORT).show();
        }else if (!isStringContainsSpecialCharacter){
            Toast.makeText(this, "Password must be contain special character", Toast.LENGTH_SHORT).show();
        }else if (!emailstr.matches(emailPattern)){
            Toast.makeText(this, "Enter valid formate email", Toast.LENGTH_SHORT).show();
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (isNetworkConnected()) {
                    showpDialog();
                    getRegister();
                }else {
                    Toast.makeText(this, "Check Internet COnnection", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void getRegister(){
        Call<Resgister> resgisterCall = Api.getPostService().getRegister(usernamestr, emailstr, passstr);
        resgisterCall.enqueue(new Callback<Resgister>() {
            @Override
            public void onResponse(Call<Resgister> call, Response<Resgister> response) {
                Log.d("haahahahhahaha", String.valueOf(response.message()));

                res1 = sessionManager.createRegSession();
                hidepDialog();
                if (sessionManager.isLoggedIn()) {
                    if (!response.message().equals("Unprocessable Entity")) {
                        Intent intent = new Intent(MainActivity.this, UserListActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Resgister> call, Throwable t) {

            }
        });
    }
}