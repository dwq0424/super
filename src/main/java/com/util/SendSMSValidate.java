package com.util;

import com.alibaba.fastjson.JSONObject;




public class SendSMSValidate {
    public static boolean sendSms( String mobile,String param) {
        String sid="34e8456e0a51a002f3781c1c7f5433c3";
        String token="b282cb0aadc0ee6b0f5f85d7371d942b";
        String appid="f0ec9cbc5cab4291a180dd11039cbfa8";
        String templateid="504303"; // 模板id


        boolean result = false;

        try {
            String url = "https://open.ucpaas.com/ol/sms/sendsms";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sid", sid);
            jsonObject.put("token", token);
            jsonObject.put("appid", appid);
            jsonObject.put("templateid", templateid);
            jsonObject.put("param", param);
            jsonObject.put("mobile", mobile);

            String body = jsonObject.toJSONString();

            System.out.println("body = " + body);

            result = HttpClientUtil.postJson(url, body, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
