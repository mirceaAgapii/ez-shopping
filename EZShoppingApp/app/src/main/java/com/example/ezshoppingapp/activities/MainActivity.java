package com.example.ezshoppingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ezshoppingapp.R;
import com.example.ezshoppingapp.RetrofitClient;
import com.google.android.material.button.MaterialButton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        TextView login = (TextView) findViewById(R.id.reg_username);
        TextView password = (TextView) findViewById(R.id.reg_password);
        View register = findViewById(R.id.register);

        MaterialButton loginButton = (MaterialButton) findViewById(R.id.button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!login.getText().toString().isEmpty()
                        && !password.getText().toString().isEmpty()) {
                    Call<ResponseBody> call = RetrofitClient.getInstance()
                            .getApi()
                            .login(login.getText().toString(), password.getText().toString());

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            String access_token = response.headers().get("access_token");
                            String refresh_token = response.headers().get("refresh_token");
                            String userId = response.headers().get("userId");
                            String r = response.body().toString();
                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(getApplicationContext(),MainScreen.class);
                            startActivity(i);
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Wrong login or password", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Login or password incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}
