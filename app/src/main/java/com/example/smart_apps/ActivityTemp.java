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

public class ActivityTemp extends AppCompatActivity {
    private FloatingActionButton fthome;
    private TextView txtHighTemp, txtValue;
    private ImageView iconHighTempLeft;
    private ImageView iconHighTempRight;
    private CombinedChart mChart;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        // tham chieu ben giao dien
             txtValue = findViewById(R.id.txtValue);

        mChart = (CombinedChart) findViewById(R.id.ChartTemp);
        fthome = findViewById(R.id.fthome);

        // click tro ve
        fthome.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ActivityAir.class)));

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
//                    FirebaseDatabase.getInstance().getReference(Common.keyNhietDo)
//                            .addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    float result = snapshot.getValue(Float.class);
//
//                                    txtValue.setText(result + " Celsius");
//                                    dataValues[0] = Common.updateDataValues(dataValues[0], result);
//                                    dataLabels[0] = Common.updateLabelValues(dataLabels[0], result + "");
//
//                                    final String text = "Temp";
//                                    Common.updateChar(ActivityTemp.this, dataLabels[0], dataValues[0], mChart, text);
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