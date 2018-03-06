package com.example.yaoweiqi.myapplicationhelloworld;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class LocationActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;
    LocationManager mLocationManager;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        mTextView = (TextView)findViewById(R.id.show);
        mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        if ( ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_ACCESS_FINE_LOCATION);
        }

        Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        updateView(location);

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateView(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
                updateView(mLocationManager.getLastKnownLocation(provider));
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        });
    }

    public void updateView(Location newLocation)
    {
        if(newLocation != null)
        {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("经度:");
            stringBuilder.append(newLocation.getLongitude());
            stringBuilder.append("\n\n纬度:");
            stringBuilder.append(newLocation.getLatitude());
            stringBuilder.append("\n\n高度:");
            stringBuilder.append(newLocation.getAltitude());
            stringBuilder.append("\n\n方向:");
            stringBuilder.append(newLocation.getBearing());
            stringBuilder.append("\n\n速度:");
            stringBuilder.append(newLocation.getSpeed());
            mTextView.setText(stringBuilder.toString());
        }
    }

}
