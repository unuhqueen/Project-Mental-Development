package com.example.prefinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    String prefName = "DATA";
    private EditText et_mail, et_pass;
    private Button btn_login, btn_register, btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_mail = findViewById(R.id.et_mail);
        et_pass = findViewById(R.id.et_pass);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        btn_back = findViewById(R.id.btn_backmain);

        btn_register.setOnClickListener(new View.OnClickListener() { // 회원가입 버튼 누르면 회원가입 화면으로 넘겨줌
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() { // 뒤로가기 버튼 누르면 시작 화면으로 넘겨줌
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() { // 로그인 버튼을 누를 경우
            @Override
            public void onClick(View view) { // 입력한 메일과 비밀번호 받기
                String userMail = et_mail.getText().toString();
                String userPass = et_pass.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                String userMail = jsonObject.getString("userMail");
                                String userName = jsonObject.getString("userName");
                                String userNick = jsonObject.getString("userNick");
                                String userExp = jsonObject.getString("userExp");

                                SharedPreferences data = getSharedPreferences(prefName, 0); // sharedpreferance 설정 및 데이터 저장
                                SharedPreferences.Editor editor =data.edit();
                                editor.putString("userMail", userMail);
                                editor.putString("userName", userName);
                                editor.putString("userNick", userNick);
                                editor.putString("userExp", userExp);
                                editor.commit();

                                Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(LoginActivity.this, MenuActivity.class); // 메뉴 화면으로 넘겨줌
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(userMail, userPass, responseListener); // 로그인 실행
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}