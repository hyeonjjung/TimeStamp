package com.example.userp.timestamp;

import android.app.ListActivity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 101;

    private double currentSpeed = 0;
    private static int GPS_UPDATE_MIN_DISTANCE = 0;
    private static int GPS_UPDATE_MIN_TIME = 0;

    private LocationManager locationManager = null;
    private LocationListener locationListener = null;

    private ListView listView;
    private String location;

    private Button startBtn;
    private Button stopBtn;
    private Button pauseBtn;

    private Spinner spinner;

    private SimpleDateFormat simpleDateFormat;

    private ArrayList<ListviewItem> data;

    private File appDirectory;
    private FileWriter fileWriter;

    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        startBtn = (Button) findViewById(R.id.startBtn);
        stopBtn = (Button) findViewById(R.id.StopBtn);
        pauseBtn = (Button) findViewById(R.id.pauseBtn);

        spinner = (Spinner) findViewById(R.id.spinner);

        turnOff();
        fileInit();

        ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.location, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        data = new ArrayList<>();

        final ListViewAdapter adapter = new ListViewAdapter(this, R.layout.listview_item, data);
        listView.setAdapter(adapter);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new SpeedActionListener();


        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOn();
                time = getCurrentTimeStamp();
                data.add(new ListviewItem(time, currentSpeed+"km/h","Start"));
                adapter.notifyDataSetChanged();

                try {
                    fileWriter = new FileWriter(new File(appDirectory, location+System.currentTimeMillis()+".csv"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                writeFile(time, "Start");
            }
        });
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOff();
                time = getCurrentTimeStamp();
                data.add(new ListviewItem(time, currentSpeed+"km/h","Stop"));
                adapter.notifyDataSetChanged();
                writeFile(time, "Stop");

                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time = getCurrentTimeStamp();
                data.add(new ListviewItem(time, currentSpeed+"km/h","Pause"));
                adapter.notifyDataSetChanged();
                writeFile(time, "Pause");
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                location = String.valueOf(adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    private class SpeedActionListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            if(location != null) {
                currentSpeed = location.getSpeed() * 3.6;
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }
    private static String getCurrentTimeStamp() {
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
    private void turnOn() {
        startBtn.setEnabled(false);
        stopBtn.setEnabled(true);
        pauseBtn.setEnabled(true);
        spinner.setEnabled(false);
    }
    private void turnOff() {
        startBtn.setEnabled(true);
        stopBtn.setEnabled(false);
        pauseBtn.setEnabled(false);
        spinner.setEnabled(true);
    }
    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    private void fileInit () {
        if(isExternalStorageWritable()) {
            appDirectory = new File(Environment.getExternalStorageDirectory()+"/TimeStamp");

            if (!appDirectory.exists()) {
                appDirectory.mkdir();
            }

        }
    }
    private void writeFile(String time, String state) {
        try {
            fileWriter.write(String.format("%s, %f, %s\n", time, currentSpeed, state));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
