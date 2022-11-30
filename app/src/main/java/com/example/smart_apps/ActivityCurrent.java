package com.example.smart_apps;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.CombinedChart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ActivityCurrent extends AppCompatActivity {
    private FloatingActionButton fhome;
    private TextView txtValue;
    private CombinedChart mChart;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current);

        fhome = findViewById(R.id.fthomeCurrent);
        txtValue = findViewById(R.id.txtValueCurrent);
        mChart = findViewById(R.id.chartCurrent);
        spinner = findViewById(R.id.spinner_current);

        fhome.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ActivityElectricalHome.class)));

        // tao mang
        String[] values = Common.initValueSpinner();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String gio = values[i];

                final List<String> dataLabels = new ArrayList();
                final List<String> dataValues = new ArrayList<>();
                final Map<String, String> map = new HashMap<>();

                // lap qua tung phut
                for (int phut = 0; phut < 60; phut++) {
                    String key = gio + ":" + (phut < 10 ? "0" : "") + phut;
                    dataLabels.add((phut < 10 ? "0" : "") + phut);


                    try {
                        int finalPhut = phut;
                        FirebaseDatabase.getInstance().getReference(Common.keyCurrent + "/" + key)
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        try {
                                            String valueOfMinute = snapshot.getValue(Long.class) + "";
                                            if (!valueOfMinute.equals("null")) {
                                                dataValues.add(valueOfMinute);
                                                map.put(key, valueOfMinute);
                                            } else {
                                                dataValues.add("0");
                                                map.put(key, valueOfMinute);
                                            }


                                        } catch (Exception e) {
                                            e.printStackTrace();

                                        }

                                        if (finalPhut == 59) {
                                            for (int j = 0; j < dataLabels.size(); j++) {
                                                Log.i(TAG, "onDataChange: " + dataLabels.get(j));
                                                Log.i(TAG, "onDataChange: " + map.get(gio + ":" + dataLabels.get(j)));

                                            }
                                            Log.i(TAG, "onDataChange: " + map);

                                        }



                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {}
                                });
                    } catch (Exception e) {
                        dataValues.add("0");

                    }


                }



                final String text = "Current";
                float[] dataValuesFloat = new float[dataValues.size()];

                for (int j = 0; j < dataValuesFloat.length; j++) {

                    try {
                        dataValuesFloat[j] = Float.parseFloat(dataValues.get(j));
                    } catch (Exception e) {
                        Log.e(TAG, "Error: conver float");
                    }

                    Log.e(TAG, "float: " + dataValuesFloat[j]);
                }

                Common.updateChar(ActivityCurrent.this, dataLabels, dataValuesFloat, mChart, text);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}