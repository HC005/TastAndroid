package com.wmt.hardik;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wmt.hardik.model.UserList.User;
import com.wmt.hardik.model.UserList.UserList;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserListActivity extends AppCompatActivity {

    UserAdapter userAdapter;
    ArrayList<User>  userLists = new ArrayList<>();
    RecyclerView user_rec;
    Button logout, next, previous;
    int pageCount=1;
    private ProgressDialog pDialog;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        sessionManager = new SessionManager(getApplicationContext());
        logout = findViewById(R.id.logout);
        user_rec = findViewById(R.id.user_rec);
        next = findViewById(R.id.next);
        previous = findViewById(R.id.previous);
        pDialog = new ProgressDialog(UserListActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutUser();
                Intent intent = new Intent(UserListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isNetworkConnected()) {
                showpDialog();
                getData("http://68.183.48.101:3333/users/list?page=" + pageCount);
            }
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageCount = pageCount+1;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (isNetworkConnected()) {
                        showpDialog();
                        getData("http://68.183.48.101:3333/users/list?page=" + pageCount);
                    }
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageCount > 1){
                    pageCount = pageCount-1;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (isNetworkConnected()) {
                            showpDialog();
                            getData("http://68.183.48.101:3333/users/list?page=" + pageCount);
                        }
                    }
                }
            }
        });
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

    public void getData(String s){
        userLists.clear();
      //  userAdapter.notifyDataSetChanged();
        Call<UserList> userListCall = Api.getGetServise().getUsers(s);
        userListCall.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, retrofit2.Response<UserList> response) {
                hidepDialog();
                if (response.body().getMeta().getStatus().equals("ok")){
                    userLists = response.body().getData().getUsers();
                    userAdapter = new UserAdapter(UserListActivity.this, userLists);
                    user_rec.setLayoutManager(new LinearLayoutManager(UserListActivity.this, RecyclerView.VERTICAL, false));
                    user_rec.setAdapter(userAdapter);
                }else {
                    Toast.makeText(UserListActivity.this, response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {

            }
        });


    }

}