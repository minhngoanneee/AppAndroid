package com.example.smart_apps;

import androidx.annotation.NonNull;
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

public class ActivityGas extends AppCompatActivity {
    private FloatingActionButton fthome;
    private CombinedChart mChart;
    private TextView txtHigh, txtValue;
    private ImageView imgLeft, imgRight;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas);

        // tham chieu ben giao dien
        txtHigh = findViewById(R.id.highGas);
        txtValue = findViewById(R.id.txtValeGas);
        imgLeft = findViewById(R.id.imgLeftGas);
        imgRight = findViewById(R.id.imgRightGas);
        mChart = findViewById(R.id.bieuDoGas);
        fthome = findViewById(R.id.fthome);

        fthome.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ActivityAir.class)));

//        // cam bien
//        final List<String>[] dataLabels = new List[]{Common.initLabelValues()};
//        float[][] dataValues = {Common.initDataValues()};
//
//        imgLeft.setColorFilter(Color.WHITE);
//        imgRight.setColorFilter(Color.WHITE);
//
//        // cu 1 giay thi update
//        int miliSecond = 1000;
//        new Timer().scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                runOnUiThread(() -> {
//                    // lay data tu FireBase
//                    FirebaseDatabase.getInstance().getReference(Common.keyGas)
//                            .addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    float result = snapshot.getValue(Float.class);
//
//                                    txtValue.setText(result + " ppm");
//                                    dataValues[0] = Common.updateDataValues(dataValues[0], result);
//                                    dataLabels[0] = Common.updateLabelValues(dataLabels[0], result + "");
//
//                                    final String text = "CO2";
//                                    Common.updateChar(ActivityGas.this, dataLabels[0], dataValues[0], mChart, text);
//
//                                    if (result >= Common.maxValueCO2) {
//                                        Common.thayDoiMau(txtHigh, imgLeft, imgRight, Color.RED);
//                                    } else {
//                                        Common.thayDoiMau(txtHigh, imgLeft, imgRight, Color.WHITE);
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                }
//                            });
//                });
//            }
//        },0, miliSecond);
    }
}
