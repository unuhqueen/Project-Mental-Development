package com.example.prefinal;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class OnTable extends StringRequest {

    // 서버 URL 설정
    final static private String URL = "http://amine95.dothome.co.kr/Ontable2.php";
    private Map<String, String> map;

    public OnTable(String userMail, String resBod, String resPos, String resRel, String resMen, String resAll, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userMail", userMail);
        map.put("resBod", resBod);
        map.put("resPos", resPos);
        map.put("resRel", resRel);
        map.put("resMen", resMen);
        map.put("resAll", resAll);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}