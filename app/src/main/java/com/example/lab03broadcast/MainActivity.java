package com.example.lab03broadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView textView,percent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view);
        percent = findViewById(R.id.battery);
    }

    public void checkBattery(View view){
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level * 100 / (float)scale;
        Toast.makeText(this,String.valueOf(batteryPct)+"%",Toast.LENGTH_SHORT).show();

    }

    public void sendBroadcast(View v) {
        Intent intent = new Intent("com.example.lab03broadcast.EXAMPLE_ACTION");
        intent.putExtra("com.example.lab03broadcast.EXTRA_TEXT", "Broadcast received");
        sendBroadcast(intent);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String receivedText = intent.getStringExtra("com.example.lab03broadcast.EXTRA_TEXT");
            //textView.setText(receivedText);
            Toast.makeText(context,receivedText,Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter("com.example.lab03broadcast.EXAMPLE_ACTION");
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }
}
