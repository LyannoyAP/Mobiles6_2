package com.example.mobiles6_2;

import static android.content.Intent.getIntent;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;

public class HardwareOverlayService extends Service {
    public HardwareOverlayService() {
    }
    private Button bBack;
    private TextView tData;
    private boolean isCreatedBeforeStarted;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!isCreatedBeforeStarted) {
            return super.onStartCommand(intent, flags, startId);
        }
        isCreatedBeforeStarted = false;
        String data = intent.getStringExtra("data");
        View view = LayoutInflater.from(this).inflate(R.layout.info_layout, null);
        WindowManager mWindowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
        final WindowManager.LayoutParams params = new
                WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        mWindowManager.addView(view, params);
        params.gravity = Gravity.TOP | Gravity.CENTER;
        mWindowManager.updateViewLayout(view, params);
        tData = view.findViewById(R.id.t_data);
        tData.setText(data);
        bBack = view.findViewById(R.id.b_back);
        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent0 = new Intent(getApplicationContext(), MainActivity.class);
                intent0.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent0);
                mWindowManager.removeView(view);
                stopSelf();
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isCreatedBeforeStarted = true;
        Toast.makeText(this,"Created", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"Destroyed", Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this,"Binded", Toast.LENGTH_LONG).show();
        throw new UnsupportedOperationException("Not yet implemented");
    }
}