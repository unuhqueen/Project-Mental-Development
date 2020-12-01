package com.example.prefinal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResResult extends AppCompatActivity {

    private RadarChart mChart;
    private SparseIntArray factors = new SparseIntArray(5);
    private SparseIntArray scores = new SparseIntArray(5);
    private ArrayList<RadarEntry> entries = new ArrayList<>();
    private ArrayList<IRadarDataSet> dataSets = new ArrayList<>();

    String prefName = "DATA";

    int score;
    int rcause1;
    int rcause2;
    int rcause3;
    int rcause4;

    private Button save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.res_result);

        Intent intent = getIntent();
        String sc = intent.getExtras().getString("key1");
        System.out.println(sc);
        Button cb = (Button) findViewById(R.id.exit);

        TextView resT = findViewById(R.id.resT);
        TextView resC = findViewById(R.id.resC);

        factors.append(1, R.string.rc1);
        factors.append(2, R.string.rc2);
        factors.append(3, R.string.rc3);
        factors.append(4, R.string.rc4);

        mChart = findViewById(R.id.chart);

        for(int i=1; i<21; i++) {
            sc = intent.getExtras().getString("key" + i);
            System.out.println(sc);
            int sco = Integer.parseInt(sc);
            switch(i) {
                case 1:
                case 2:
                case 3:
                case 5:
                case 6:
                case 7:
                case 13:
                case 20:
                    rcause1 += sco;
                    break;
                case 4 :
                case 8 :
                case 12 :
                case 16 :
                    rcause2 += 4 - sco;
                    break;
                case 9:
                case 14 :
                case 15:
                case 19:
                    rcause3 += sco;
                case 10:
                case 11:
                case 17:
                case 18:
                    rcause4 += sco;
            }
        }

        entries.add(new RadarEntry(rcause1));
        entries.add(new RadarEntry(rcause2));
        entries.add(new RadarEntry(rcause3));
        entries.add(new RadarEntry(rcause4));

        score = rcause1 + rcause2 + rcause3 + rcause4;

        if(score >= 25) {
            resT.setText(R.string.re4T);
            resC.setText(R.string.re4C);
        }
        else if(score > 20){
            resT.setText(R.string.re3T);
            resC.setText(R.string.re3C);
        } else if(score > 15) {
            resT.setText(R.string.re2T);
            resC.setText(R.string.re2C);
        } else {
            resT.setText(R.string.re1T);
            resC.setText(R.string.re1C);
        }

        RadarDataSet radarDataSet = new RadarDataSet(entries, "점수");
        radarDataSet.setColor(R.color.colorPrimary);
        radarDataSet.setDrawFilled(true);

        RadarData data = new RadarData();
        data.addDataSet(radarDataSet);
        String[] labels = {"신체저하", "긍정정서", "대인관계", "우울정서"};
        XAxis xAxis = mChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        mChart.setData(data);

        TextView sc0  = (TextView)findViewById(R.id.result);
        sc0.setText("총점수 " + score);
        TextView sc1 = (TextView)findViewById(R.id.result1);
        sc1.setText("신체저하 :" + rcause1);
        TextView sc2 = (TextView)findViewById(R.id.result2);
        sc2.setText("긍정정서 :"+rcause2);
        TextView sc3 = (TextView)findViewById(R.id.result3);
        sc3.setText("대인관계" +rcause3);
        TextView sc4 = (TextView)findViewById(R.id.result4);
        sc4.setText("우울정서" +rcause4);

        cb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });

        save = findViewById(R.id.btn_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String resBod = Integer.toString(rcause1);
                String resPos = Integer.toString(rcause2);
                String resRel = Integer.toString(rcause3);
                String resMen = Integer.toString(rcause4);
                String resAll = Integer.toString(score);

                SharedPreferences data = getSharedPreferences(prefName, 0); // sharedprefernace 설정
                String userMail = data.getString("userMail", "NONE"); // userNick 불러옴

                Toast.makeText(getApplicationContext(), userMail, Toast.LENGTH_SHORT).show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(), "점수를 등록했습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ResResult.this, MenuActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "점수 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                OnTable onTable = new OnTable(userMail, resBod, resPos, resRel, resMen, resAll, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ResResult.this);
                queue.add(onTable);
            }
        });
    }
}
