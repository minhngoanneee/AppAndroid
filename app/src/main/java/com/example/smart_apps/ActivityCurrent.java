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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Stream;

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
                int gioKieuInt = Integer.parseInt(gio);

                // neu gio lon hon 12 thì -12 cho zui
                gioKieuInt = gioKieuInt > 12 ? gioKieuInt - 12 : gioKieuInt;

                if (gioKieuInt > Calendar.getInstance().get(Calendar.HOUR)) {
                    Toast.makeText(ActivityCurrent.this, "Phải chọn giờ trước giờ hiện tại !!!", Toast.LENGTH_SHORT).show();
                }

                FirebaseDatabase
                        .getInstance()
                        .getReference(Common.keyCurrent + "/" + gio)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                // This method is called once with the initial value and again
                                // whenever data at this location is updated.

                                Object ob = snapshot.getValue();
                                boolean isHashMap = ob instanceof HashMap;

                                List<String> dataLabels = new ArrayList<>();
                                float[] dataValuesFloat = null;

                                if (isHashMap) {
                                    // truong hop nhan duoc hashmap
                                    Map<String, String> map = (Map<String, String>) ob;

                                    dataLabels = new ArrayList<>(map.keySet());
                                    dataValuesFloat = new float[dataLabels.size()];

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        dataLabels.sort((key1, key2) -> {
                                            return key1.compareTo(key2);
                                        });
                                    }

                                    for (int i = 0; i < dataLabels.size(); i++) {
                                        String value = map.get(dataLabels.get(i));
                                        dataValuesFloat[i] = Float.parseFloat(value);
                                    }

                                    for (Float s: dataValuesFloat) {
                                        Log.e(TAG, "Value: " + s );
                                    }

                                } else {
                                    //truong hop nhan duoc arraylist
                                    List<String> listValue = (ArrayList<String>) ob;
                                    if (listValue != null) {
                                        dataValuesFloat = new float[listValue.size()];

                                        // convert arraylist string to array float
                                        for (int j = 0; j < listValue.size(); j++) {
                                            if (listValue.get(j) == null) {
                                                dataValuesFloat[j] = 0;
                                            } else {
                                                dataValuesFloat[j] = Float.parseFloat(listValue.get(j));
                                            }
                                            dataLabels.add(j + "");
                                        }
                                    } else {
                                        // truong hop ni không co data theo gio
                                        dataValuesFloat = new float[0];
                                    }
                                }
                                String text = "Current";
                                Common.updateChar(ActivityCurrent.this, dataLabels, dataValuesFloat, mChart, text, "A");
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

        // cu 1 giay thi update
        int miliSecond = 1000;
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    // lay data tu FireBase
                    FirebaseDatabase
                            .getInstance()
                            .getReference(Common.keyCurrent + "/now")
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    txtValue.setText(snapshot.getValue() + " A");
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                });
            }
        },0,miliSecond);
    }
}