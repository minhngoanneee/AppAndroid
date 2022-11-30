package com.example.smart_apps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ActivityProfile extends AppCompatActivity {
    private Button btnback;
    private TextView edtEmail, edtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        edtEmail = findViewById(R.id.txtEmail);
        edtPass = findViewById(R.id.txtPass);
        btnback = findViewById(R.id.btnback);

        btnback.setOnClickListener(view -> finish());

        // nhan gia tri o ben kia gui qua
        Intent profileIntent = getIntent();
        Bundle bundle = profileIntent.getBundleExtra("data");
        String email = bundle.getString("email");
        String pass = bundle.getString("password");
        edtEmail.setText(email);
        edtPass.setText(pass);
    }
}