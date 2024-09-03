package com.example.notisys;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

import com.example.notisys.service.Scraping;
import com.example.notisys.task.AsyncScrapingTask;
import com.example.notisys.utils.NotificationUtil;
import com.example.notisys.worker.ScrapingWorker;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.notisys.databinding.ActivityMainBinding;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private Handler handler;
    private Runnable runnable;

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        editText = findViewById(R.id.editTextText);

        editText.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                             //   Log.d("pjs - beforeTextChanged ", charSequence.toString());
                                             //   callNotification();

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                Log.d("pjs - beforeTextChanged ", charSequence.toString());
                                                if (charSequence.toString().indexOf("예약가능") > -1) {
                                                    Log.d("pjs", "조건 부합 - 알림 발송");
                                                    callNotification();
                                                }
                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {

                                            }
                                        }
        );

        // Notification 채널 생성
        NotificationUtil.createNotificationChannel(this);

        // 알람 생성
        createNotification();

        // callUrl();

        // scheduling();
        // scheduledRun();

        // 주기적으로 조회
        handlerRun();
    }

    NotificationCompat.Builder builder;

    public void createNotification() {
        // 알림 채널 ID
        String channelId = NotificationUtil.CHANNEL_ID;

        // NotificationCompat.Builder를 사용하여 알림 생성
        builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.noti_sys) // 알림 아이콘
                .setContentTitle("예약가능하다.") // 알림 제목
                .setContentText("가능하다 예약해라") // 알림 내용
                .setPriority(NotificationCompat.PRIORITY_HIGH) // 알림의 우선 순위 설정
                .setAutoCancel(true); // 알림을 탭하면 자동으로 삭제

        // 알림과 연동될 인텐트 설정
        Intent intent = new Intent(this, YesActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);
    }

    private void callNotification() {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(1, builder.build());
    }


    private ScheduledExecutorService scheduler;

    private void scheduledRun() {
        scheduler = Executors.newScheduledThreadPool(1);

        // 5초마다 실행되는 작업 스케줄링
        scheduler.scheduleAtFixedRate(() -> {
            // 여기에 3초마다 실행할 작업을 작성
            // callUrl();
            Log.d("pjs", "5초 주기로 작업이 실행되었습니다.");
        }, 0, 5, TimeUnit.SECONDS);
    }

    private void handlerRun() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                // AsyncTask를 실행
                callUrl();

                // 3초 후에 다시 실행하도록 설정
                handler.postDelayed(this, 5000);
            }
        };

        // 첫 실행을 위해 즉시 실행
        handler.post(runnable);
    }

    private void scheduling() {
        // OneTimeWorkRequest 객체를 생성하여 MyWorker 작업을 예약
        //OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(ScrapingWorker.class).build();

        // WorkManager를 통해 Work 요청을 큐에 추가하여 작업을 예약
        //WorkManager.getInstance(this).enqueue(workRequest);


        // 주기적으로 실행되는 Work 요청 생성 (15분 주기)
        PeriodicWorkRequest periodicWorkRequest =
                new PeriodicWorkRequest.Builder(ScrapingWorker.class, 1, TimeUnit.MINUTES)
                        .build();

        WorkManager.getInstance(this).enqueue(periodicWorkRequest);
    }

    private void callUrl() {
        //
        String url = "https://www.mgtpcr.or.kr/web/campingListModel.do?date=2024-10-26";

        AsyncScrapingTask task = new AsyncScrapingTask(editText);
        task.execute(url);

//        Log.d("pjs", jsonText);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
}