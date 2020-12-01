package com.example.prefinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class MenuActivity extends AppCompatActivity {

    String prefName = "DATA";
    private ImageView iv_mental;
    private TextView tv_stat, tv_ment;
    private Button btn_sur, btn_todo, btn_book, btn_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        SharedPreferences data = getSharedPreferences(prefName, 0); // sharedprefernace 설정
        String userMail = data.getString("userMail", "NONE");
        String userName = data.getString("userName", "NONE");
        String userNick = data.getString("userNick", "NONE");
        String userExp = data.getString("userExp", "-1");

        Random rand = new Random();

        iv_mental = findViewById(R.id.iv_mentalmon);
        tv_ment = findViewById(R.id.tv_ment);
        tv_stat = findViewById(R.id.tv_stat);

        int[] rand_text_lv01 = {R.string.ment_lv1_01, R.string.ment_lv1_02, R.string.ment_lv1_03, R.string.ment_lv1_04, R.string.ment_lv1_05, R.string.ment_lv1_06};
        int[] rand_text_lv02 = {R.string.ment_lv2_01};
        int[] rand_text_lv03 = {R.string.ment_lv3_01};
        int[] rand_text_lv04 = {R.string.ment_lv4_01};
        int[] rand_text_lv05 = {R.string.ment_lv5_01};

        int int_exp = Integer.parseInt(userExp);
        int level = -1;

        if (int_exp <= 100) {
            level = 1;

            int num = rand.nextInt(rand_text_lv01.length);
            tv_ment.setText(rand_text_lv01[num]);

            iv_mental.setImageResource(R.drawable.pic_lv1);
            iv_mental.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int num = rand.nextInt(rand_text_lv01.length);
                    tv_ment.setText(rand_text_lv01[num]);
                }
            });
        }
        else if (int_exp <= 200) {
            level = 2;

            int num = rand.nextInt(rand_text_lv02.length);
            tv_ment.setText(rand_text_lv02[num]);

            iv_mental.setImageResource(R.drawable.pic_lv2);
            iv_mental.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int num = rand.nextInt(rand_text_lv02.length);
                    tv_ment.setText(rand_text_lv02[num]);
                }
            });
        }
        else if (int_exp <= 300) {
            level = 3;

            int num = rand.nextInt(rand_text_lv03.length);
            tv_ment.setText(rand_text_lv03[num]);

            iv_mental.setImageResource(R.drawable.pic_lv3);
            iv_mental.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int num = rand.nextInt(rand_text_lv03.length);
                    tv_ment.setText(rand_text_lv03[num]);
                }
            });
        }
        else if (int_exp <= 400) {
            level = 4;

            int num = rand.nextInt(rand_text_lv04.length);
            tv_ment.setText(rand_text_lv04[num]);

            iv_mental.setImageResource(R.drawable.pic_lv4);
            iv_mental.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int num = rand.nextInt(rand_text_lv04.length);
                    tv_ment.setText(rand_text_lv04[num]);
                }
            });
        }
        else {
            level = 5;

            int num = rand.nextInt(rand_text_lv05.length);
            tv_ment.setText(rand_text_lv05[num]);

            iv_mental.setImageResource(R.drawable.pic_lv5);
            iv_mental.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int num = rand.nextInt(rand_text_lv05.length);
                    tv_ment.setText(rand_text_lv05[num]);
                }
            });
        }

        String stat = "닉네임 : " + userNick + " / 레벨(경험치) : " + level + "(" + userExp + ")";
        tv_stat.setText(stat);

        btn_sur = findViewById(R.id.btn_sur);
        btn_todo = findViewById(R.id.btn_todo);
        btn_book = findViewById(R.id.btn_book);
        btn_map = findViewById(R.id.btn_map);

        btn_sur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ResStart.class);
                startActivityForResult(intent, 1);
            }
        });

        btn_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, TodoActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, BookActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, MapActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }
}