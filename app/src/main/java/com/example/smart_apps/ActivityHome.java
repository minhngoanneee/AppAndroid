package com.example.smart_apps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityHome extends AppCompatActivity {
    private ImageButton btndien,btnnuoc,btnmua;
    private TextView txtdien,txtnuoc,txtmua ;
    private FloatingActionButton flthongtin,fthome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btndien = findViewById(R.id.btndien);
        btnmua = findViewById(R.id.btnmua);
        btnnuoc = findViewById(R.id.btnnuoc);
        flthongtin = findViewById(R.id.flthongtin);
        fthome = findViewById(R.id.fthome);
        txtdien = findViewById(R.id.txtdien);
        txtmua = findViewById(R.id.txtmua);
        txtnuoc = findViewById(R.id.txtnuoc);

        txtdien.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ActivityElectricalHome.class)));
        txtnuoc.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ActivityWalter.class)));
        txtmua.setOnClickListener(view ->  startActivity(new Intent(getApplicationContext(), ActivityAir.class)));

        flthongtin.setOnClickListener(view -> {
            // nhan gia tri o ben kia gui qua
            Intent homeIntent = getIntent();
            Bundle bundle = homeIntent.getBundleExtra("data");
            Intent intent = new Intent(getApplicationContext(), ActivityProfile.class);
            intent.putExtra("data", bundle);
            startActivity(intent);
        });

        btndien.setOnClickListener(view -> startActivity(new Intent(ActivityHome.this, ActivityVoltage.class)));
        fthome.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ActivityLogin.class)));
    }
}