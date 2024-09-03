package com.example.notisys.task;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.notisys.YesActivity;
import com.example.notisys.dto.Facility;
import com.example.notisys.dto.MjResultDto;
import com.example.notisys.service.Scraping;
import com.example.notisys.utils.NotificationUtil;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AsyncScrapingTask extends AsyncTask<String, Integer, String> {

    private EditText editText;

    // 생성자를 통해 UI 요소를 전달받을 수 있습니다.
    public AsyncScrapingTask(EditText editText) {
        this.editText = editText;
    }

    // 백그라운드 작업 전에 실행되는 메서드
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //textView.setText("작업 시작 중...");
    }

    // 백그라운드에서 실행되는 메서드
    @Override
    protected String doInBackground(String... params) {
        String result = "";

        try {
            result = Scraping.Scrap(params[0]);
            //result = "작업 완료!";
        } catch (Exception e) {
            result = "작업 중 오류 발생!";
        }

        return result;
    }

    // 백그라운드 작업 중 진행 상황을 업데이트하는 메서드
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        //textView.setText("진행 중... " + values[0] + "% 완료");
    }




    // 백그라운드 작업 완료 후 실행되는 메서드
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        String resultMessage = "불가 XXX";

        // 데이터 확인
        // Gson 객체 생성
        Gson gson = new Gson();

        // JSON 문자열을 MyClass 객체로 변환
        MjResultDto myObject = gson.fromJson(result, MjResultDto.class);

        List<Facility> list = myObject.getFacilityList();
        List<String> checkRooms = Arrays.asList("북두칠성", "남두육성");

        for(int i=0; i < list.size(); i++) {
            Facility data = (Facility) list.get(i);

            Log.d("pjs", data.getNAME() + " " + data.getRESERVATION_COUNT().toString());
            if (checkRooms.contains(data.getNAME())) {
                if (data.getRESERVATION_COUNT() == 0) {
                    resultMessage = "예약가능 OOO";
                    break;
                }
            }
        }
        Log.d("pjs", resultMessage);
        editText.setText(editText.getText().toString() + "\r\n" + getCurrentTime() + " : " + resultMessage);

    }


    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
        return sdf.format(new Date());
    }

}
