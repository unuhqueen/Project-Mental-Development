package com.example.prefinal;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    // 서버 URL 설정
    final static private String URL = "http://amine95.dothome.co.kr/Register.php";
    private Map<String, String> map;

    public RegisterRequest(String userMail, String userName, String userNick, String userPass, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userMail", userMail);
        map.put("userName", userName);
        map.put("userNick", userNick);
        map.put("userPass", userPass);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}