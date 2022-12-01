package com.example.smart_apps;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Common {

    public static final String keyNhietDo = "temp";
    public static final String keyDoAmKhongKhi = "hum";
    public static final String keyChatLuongNuoc = "Water";
    public static final String keyGas = "ssCO2";
    public static final String keyVoltage = "Votage";
    public static final String keyPower = "Power";
    public static final String keyCurrent = "Current";
    public static final String keyDust = "dustDensity";
    public static final int backgroundChart = Color.WHITE; //parseColor("#B6DBD2");
    public static final int colorLine = Color.parseColor("#2B6C55");
    public static final float maxValueTemp = 30;
    public static final float maxValueCO2 = 250;
    public static final float maxValueWalter = 100;

    // method init values spinner
    public static String[] initValueSpinner() {
        return new String[] {
                "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "10", "11",
                "12", "13", "14", "15", "16",
                "17", "18", "19", "20", "21",
                "22", "23"
        };
    }

    // method init adapter spinner
    public static ArrayAdapter<String> initAdaper(Context context, String[] listGios) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, listGios);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        return adapter;
    }

    // method update chart
    public static void updateChar(Context context, List<String> xLabel, float[] dataValues, CombinedChart mChart, String text, String kyHieu) {
        mChart.getDescription().setEnabled(false);
        mChart.setBackgroundColor(backgroundChart);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
//                Toast.makeText(context, "Value: "
//                        + e.getY()
//                        + ", Phut: "
//                        + h.getX()
//                        + ", DataSet index: "
//                        + h.getDataSetIndex(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Phut: " + (int) h.getX() + " - Value: " + e.getY() + " " + kyHieu, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected() {}
        });

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f);
        rightAxis.setAxisMaximum(15f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int x = 0;
                try {
                    x = (int) value % xLabel.size();
                } catch (ArithmeticException ex) {
                    ex.printStackTrace();
                    return "";
                }
                return xLabel.get(x);
            }
        });

        LineData lineDatas = new LineData();
        lineDatas.addDataSet((ILineDataSet) dataChart(dataValues, text));

        CombinedData data = new CombinedData();
        data.setData(lineDatas);

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        mChart.setData(data);
        mChart.invalidate();
    }

    private static DataSet dataChart(float[] data, String text) {
        LineData d = new LineData();
        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < data.length; index++) {
            entries.add(new Entry(index, (float) data[index]));
        }

        LineDataSet set = new LineDataSet(entries, text);
        set.setColor(colorLine);
        set.setLineWidth(3f);
        set.setCircleColor(colorLine);
        set.setCircleRadius(5f);
        set.setFillColor(colorLine);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(colorLine);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return set;
    }

    // method thay doi mau
    public static void thayDoiMau(TextView txtHighTemp, ImageView iconHighTempLeft, ImageView iconHighTempRight, int colerCode) {
        txtHighTemp.setTextColor(colerCode);
        iconHighTempLeft.setColorFilter(colerCode);
        iconHighTempRight.setColorFilter(colerCode);
    }

    // method
    public static void hienThiChartTheoGio(Context context, String[] listGios, int i, String path, CombinedChart mChart, String text, String kyHieu) {
        String gio = listGios[i];
        int gioKieuInt = Integer.parseInt(gio);

        if (gioKieuInt > new Date().getHours()) {
            Toast.makeText(context, "Phải chọn giờ trước giờ hiện tại !!!", Toast.LENGTH_SHORT).show();
        } else {
            FirebaseDatabase
                    .getInstance()
                    .getReference(path + "/" + gio)
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

                            Common.updateChar(context, dataLabels, dataValuesFloat, mChart, text, kyHieu);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Failed to read value
                            Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });
        }
    }

    public static void updateNow(String path, TextView txtValue) {
        FirebaseDatabase
                .getInstance()
                .getReference(path + "/now")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        txtValue.setText(snapshot.getValue() + " A");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public static void removeDataAfterNow(String path) {
// thoi gian hien tai
        Date date = new Date();

        // xoa data tu thoi diem hien tai
        FirebaseDatabase
                .getInstance()
                .getReference(path + "/" + date.getHours())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int index = 0;
                        for (DataSnapshot appleSnapshot : snapshot.getChildren()) {
                            if (index > date.getMinutes()) {
                                appleSnapshot.getRef().removeValue();
                                Log.e(TAG, "onDataChange: " + appleSnapshot.getValue());
                            }
                            index++;
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, "onCancelled", error.toException());
                    }
                });

        // xoa data sau gio
        for (int i = date.getHours() + 1; i < 24; i++) {
            FirebaseDatabase
                    .getInstance()
                    .getReference(path + "/" + i)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot appleSnapshot : snapshot.getChildren()) {
                                appleSnapshot.getRef().removeValue();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e(TAG, "onCancelled", error.toException());
                        }
                    });
        }
    }
}
