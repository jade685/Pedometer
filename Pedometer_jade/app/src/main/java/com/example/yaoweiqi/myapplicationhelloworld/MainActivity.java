package com.example.yaoweiqi.myapplicationhelloworld;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    private Button startMap;
    private Button startLocation;
    private SensorManager mSensorManager;
    private Sensor mSensor;

    private TextView step;

    private int total_step = 0;
    private long initTime = 0;
    private long lastTime = 0;
    private long curTime = 0;
    private long duration = 0;
    private float last_x = 0.0f;
    private float last_y = 0.0f;
    private float last_z = 0.0f;
    private float shake = 0.0f;
    private float totalShake = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                curTime = System.currentTimeMillis();
                if ((curTime - lastTime) > 100) {
                    duration = (curTime - lastTime);
                    if (last_x == 0.0f && last_y == 0.0f && last_z == 0.0f) {
                        initTime = System.currentTimeMillis();
                    } else {
                        shake = (Math.abs(x - last_x) + Math.abs(y - last_y) + Math.abs(z - last_z)) / duration * 100;
                    }
                    totalShake += shake;
                    if(z>1.5)
                    if (shake > 5)
                        total_step += 1;
                    if (totalShake > 10 && totalShake / (curTime - initTime) * 1000 > 10) {
                        initShake();
                    }
                    step.setText("步数:\n" + (int) ( total_step / 1.5) + "步");
                }
                last_x = x;
                last_y = y;
                last_z = z;
                lastTime = curTime;
            }

            private void initShake() {
                lastTime = 0;
                duration = 0;
                curTime = 0;
                initTime = 0;
                last_x = 0.0f;
                last_y = 0.0f;
                last_z = 0.0f;
                shake = 0.0f;
                totalShake = 0.0f;
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, mSensor, SensorManager.SENSOR_DELAY_NORMAL);


        step = (TextView) findViewById(R.id.Steps_textResult);
        startMap = (Button) findViewById(R.id.StartMap);
        startLocation = (Button) findViewById(R.id.StartLocation);
        startMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
            }
        });
        startLocation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LocationActivity.class));
            }
        });
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.add(0,0,0,"关于");
        menu.add(0,1,0,"退出");
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case 1 :
                System.exit(0);
            case 0 :
                Toast.makeText(this,"作者:Jade\n祝您使用愉快",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}


