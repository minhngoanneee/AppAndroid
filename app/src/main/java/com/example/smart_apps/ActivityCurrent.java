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
        String[] listGios = Common.initValueSpinner();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listGios);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String gio = listGios[i];

                FirebaseDatabase
                        .getInstance()
                        .getReference(Common.keyCurrent + "/" + gio)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                // This method is called once with the initial value and again
                                // whenever data at this location is updated.
                                List<String> listValue = (ArrayList<String>) snapshot.getValue();
                                List<String> dataLabels = new ArrayList<>();

                                if (listValue != null) {
                                    float[] dataValuesFloat = new float[listValue.size()];

                                    // convert arraylist string to array float
                                    for (int j = 0; j < listValue.size(); j++) {
                                        if (listValue.get(j) == null) {
                                            dataValuesFloat[j] = 0;
                                        } else {
                                            dataValuesFloat[j] = Float.parseFloat(listValue.get(j));
                                        }
                                        dataLabels.add(j + "");
                                    }

                                    String text = "cai quan que";
                                    Common.updateChar(ActivityCurrent.this, dataLabels, dataValuesFloat, mChart, text);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Failed to read value
                                Log.w(TAG, "Failed to read value.", error.toException());
                            }
                        });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}