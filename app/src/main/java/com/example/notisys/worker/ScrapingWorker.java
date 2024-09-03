package com.example.notisys.worker;

import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.notisys.R;
import com.example.notisys.dto.Facility;
import com.example.notisys.dto.MjResultDto;
import com.example.notisys.service.Scraping;
import com.example.notisys.utils.NotificationUtil;
import com.google.gson.Gson;

import java.util.List;

public class ScrapingWorker extends Worker {
    // 생성자
    public ScrapingWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // 여기에 백그라운드에서 실행될 작업을 작성
        String url = "https://www.mgtpcr.or.kr/web/campingListModel.do?date=2024-10-26";
        String result = Scraping.Scrap(url);

        Log.d("pjs", result);

        String resultMessage = "불가 XXXXX";
        // Gson 객체 생성
        Gson gson = new Gson();

        // JSON 문자열을 MyClass 객체로 변환
        MjResultDto myObject = gson.fromJson(result, MjResultDto.class);

        List<Facility> list = myObject.getFacilityList();
        for(int i=0; i < list.size(); i++) {
            Facility data = (Facility)list.get(i);

            Log.d("pjs", data.getNAME() + " " + data.getRESERVATION_COUNT().toString());
            if (data.getNAME().equals("북두칠성") || data.getNAME().equals("남두육성")) {
                if (data.getRESERVATION_COUNT() == 0) {
                    resultMessage = "예약가능!!";
                }

            }
        }

        Log.d("pjs", resultMessage);


        // 작업이 성공적으로 완료되었음을 나타내는 Result 반환
        return Result.success();
    }

    private void sendNotification(String title, String message) {
        // 알림을 만들기 위한 NotificationCompat.Builder 생성
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), NotificationUtil.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_menu_camera) // 아이콘 설정
                .setContentTitle(title)                    // 제목 설정
                .setContentText(message)                   // 내용 설정
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) // 우선 순위 설정
                .setAutoCancel(true); // 알림 클릭 시 자동으로 삭제되도록 설정

        // NotificationManager를 통해 알림을 표시
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}
