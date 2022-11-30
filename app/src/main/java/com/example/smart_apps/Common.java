package com.example.smart_apps;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

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

    private static CombinedChart mChart;

    // method init dataValues
    public static float[] initDataValues() {
        return new float[]{0, 0, 0, 0 ,0 ,0};
    }

    // method init values spinner
    public static String[] initValueSpinner() {
        return new String[] {
                "0",
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10",
                "11",
                "12",
                "13",
                "14",
                "15",
                "16",
                "17",
                "18",
                "19",
                "20",
                "21",
                "22",
                "23"
        };
    }

    // method init labelValues
    public static List<String> initLabelValues() {
        List<String> xLabel = new ArrayList<>();
        xLabel.add("Jan");
        xLabel.add("Feb");
        xLabel.add("Mar");
        xLabel.add("Apr");
        xLabel.add("May");
        xLabel.add("Jun");
        return xLabel;
    }

    // method update dataValues
    public static float[] updateDataValues(float[] dataValues, float newValue) {
        // loop de chuyen doi gia tri ve sau
        for (int i = 0; i < dataValues.length - 1; i++) {
            dataValues[i] = dataValues[i + 1];
        }
        // set gia tri moi vao
        dataValues[dataValues.length - 1] = newValue;
        return dataValues;
    }

    // method update labelValues
    public static List<String> updateLabelValues(List<String> labelValues, String newLabel) {
        // loop de chuyen doi gia tri ve sau
        for (int i = 0; i < labelValues.size() - 1; i++) {
            labelValues.set(i, labelValues.get(i + 1));
        }
        // set gia tri moi vao
        labelValues.set(labelValues.size() - 1, newLabel);
        return labelValues;
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
            public void onNothingSelected() {

            }
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
}
