package com.example.ezshoppingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ezshoppingapp.R;
import com.example.ezshoppingapp.RetrofitClient;
import com.example.ezshoppingapp.model.UserRegistrationDTO;
import com.google.android.material.button.MaterialButton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        MaterialButton backBtn = (MaterialButton)findViewById(R.id.back);
        MaterialButton submitBtn = (MaterialButton)findViewById(R.id.submit);

        TextView login = (TextView) findViewById(R.id.reg_username);
        TextView password = (TextView) findViewById(R.id.reg_password);
        TextView rePassword = (TextView) findViewById(R.id.re_password);
        TextView email = (TextView) findViewById(R.id.reg_email);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!login.getText().toString().isEmpty()
                   && !password.getText().toString().isEmpty()
                   && !rePassword.getText().toString().isEmpty()
                   && !email.getText().toString().isEmpty()) {
                    if (password.getText().toString().equals(rePassword.getText().toString())) {
                        UserRegistrationDTO dto = new UserRegistrationDTO(login.getText().toString(),
                                password.getText().toString(),
                                email.getText().toString());
                        Call<ResponseBody> call = RetrofitClient.getInstance()
                                .getApi()
                                .register(dto);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Toast.makeText(RegisterActivity.this, "New user registered", Toast.LENGTH_SHORT).show();
                               // Intent i = new Intent(getApplicationContext(), MainActivity.class);
                               // startActivity(i);
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(RegisterActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Fill all required fields", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}