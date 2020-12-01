package com.example.prefinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {

    private EditText et_mail, et_name, et_nick, et_pass, et_passi;
    private Button btn_register, btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 아이디 값 찾아주기
        et_mail = findViewById(R.id.et_mail);
        et_name = findViewById(R.id.et_name);
        et_nick = findViewById(R.id.et_nick);
        et_pass = findViewById(R.id.et_pass);
        et_passi = findViewById(R.id.et_passi);

        btn_back = findViewById(R.id.btn_backlogin);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });

        // 회원가입 버튼 클릭시 수행
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_mail.getText().toString().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Email을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    et_mail.requestFocus();
                    return;
                }
                else if (et_name.getText().toString().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    et_name.requestFocus();
                    return;
                }
                else if (et_nick.getText().toString().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    et_nick.requestFocus();
                    return;
                }
                else if (et_pass.getText().toString().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    et_pass.requestFocus();
                    return;
                }
                else if (et_passi.getText().toString().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "비밀번호 확인을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    et_passi.requestFocus();
                    return;
                }
                else if(!et_passi.getText().toString().equals(et_pass.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    String userMail = et_mail.getText().toString();
                    String userName = et_name.getText().toString();
                    String userNick = et_nick.getText().toString();
                    String userPass = et_pass.getText().toString();

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if(success){
                                    Toast.makeText(getApplicationContext(), "회원 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "회원 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    RegisterRequest registerRequest = new RegisterRequest(userMail, userName, userNick, userPass, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
                }
            }
        });
    }
}