package com.example.smart_apps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ActivityVoltage extends AppCompatActivity {
    private FloatingActionButton fthome;
    private CombinedChart mChart;
    private TextView txtValue;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voltage);

        fthome = findViewById(R.id.fthome);
        txtValue = findViewById(R.id.txtValueVoltage);
        mChart = findViewById(R.id.chartVoltage);

        fthome.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ActivityElectricalHome.class)));
//
//        // cam bien
//        final List<String>[] dataLabels = new List[]{Common.initLabelValues()};
//        float[][] dataValues = {Common.initDataValues()};
//
//        // cu 1 giay thi update
//        int miliSecond = 1000;
//        new Timer().scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                runOnUiThread(() -> {
//                    // lay data tu FireBase
//                    FirebaseDatabase.getInstance().getReference(Common.keyVoltage)
//                            .addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    float result = snapshot.getValue(Float.class);
//
//                                    txtValue.setText(result + " Voltage");
//                                    dataValues[0] = Common.updateDataValues(dataValues[0], result);
//                                    dataLabels[0] = Common.updateLabelValues(dataLabels[0], result + "");
//
//                                    final String text = "Voltage";
//                                    Common.updateChar(ActivityVoltage.this, dataLabels[0], dataValues[0], mChart, text);
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                }
//                            });
//
//                });
//            }
//        },0,miliSecond);
    }
}
