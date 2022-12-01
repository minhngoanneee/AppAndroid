package com.example.smart_apps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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

public class ActivityWalter extends AppCompatActivity {
    private FloatingActionButton fthome;
    private CombinedChart mChart;
    private TextView txtHigh, txtWalter;
    private ImageView iconHigh;
    private ImageView iconHighLeft;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walter);

        fthome = findViewById(R.id.fthome);
        txtHigh = findViewById(R.id.high);
        iconHigh = findViewById(R.id.iconHigh);
        iconHighLeft = findViewById(R.id.iconHighLeft);
        txtWalter = findViewById(R.id.txtWalter);
        mChart = findViewById(R.id.bieuDoNuoc);

        fthome.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ActivityHome.class)));

        // cam bien
        iconHighLeft.setColorFilter(Color.WHITE);
        iconHigh.setColorFilter(Color.WHITE);
//
//        // cu 1 giay thi update
//        int miliSecond = 1000;
//        new Timer().scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                runOnUiThread(() -> {
//                    // lay data tu FireBase
//                    FirebaseDatabase.getInstance().getReference(Common.keyChatLuongNuoc)
//                            .addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    float result = snapshot.getValue(Float.class);
//
//                                    txtWalter.setText(result + " %");
//                                    dataValues[0] = Common.updateDataValues(dataValues[0], result);
//                                    dataLabels[0] = Common.updateLabelValues(dataLabels[0], result + "");
//
//                                    final String text = "SOLUTE CONCENTRATION";
//                                    Common.updateChar(ActivityWalter.this, dataLabels[0], dataValues[0], mChart, text);
//
//                                    if (result >= Common.maxValueWalter) {
//                                        Common.thayDoiMau(txtHigh, iconHighLeft, iconHigh, Color.RED);
//                                    } else {
//                                        Common.thayDoiMau(txtHigh, iconHighLeft, iconHigh, Color.WHITE);
//                                    }
//
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