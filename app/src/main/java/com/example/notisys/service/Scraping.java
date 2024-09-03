package com.example.notisys.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.util.Log;

public class Scraping {

    private static String _charset = "UTF-8";

    public static void setCharset(String charset) {
        _charset = charset;
    }

    public static String Scrap(String urlString) {

        String resultText = "";

        try {
            URL url = new URL(urlString);


            Log.d("pjs", " URL : " + url.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(MethodType.GET.toString());

            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            // 요청 방식 구하기
            // System.out.println("getRequestMethod():" + conn.getRequestMethod());
            // 응답 콘텐츠 유형 구하기
            // System.out.println("getContentType():" + conn.getContentType());
            // 응답 코드 구하기
            // System.out.println("getResponseCode():"    + conn.getResponseCode());
            // 응답 메시지 구하기
            // System.out.println("getResponseMessage():" + conn.getResponseMessage());

            // 응답 헤더의 정보를 모두 출력
            // for (Map.Entry<String, List<String>> header : conn.getHeaderFields().entrySet()) {
            //     for (String value : header.getValue()) {
            //         System.out.println(header.getKey() + " : " + value);
            //     }
            // }

            // 응답 내용(BODY) 구하기
            try (InputStream in = conn.getInputStream();
                 ByteArrayOutputStream out = new ByteArrayOutputStream()) {

                byte[] buf = new byte[1024 * 8];
                int length = 0;
                while ((length = in.read(buf)) != -1) {
                    out.write(buf, 0, length);
                }

                resultText = new String(out.toByteArray(), _charset);

            }

            // 접속 해제
            conn.disconnect();


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("pjs","====== error =============");


        }


        return resultText;

    }

    public enum MethodType {
        GET,
        POST
    }

}
