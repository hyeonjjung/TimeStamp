package com.example.userp.timestamp;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private Button startBtn;
    private Button stopBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        startBtn = (Button) findViewById(R.id.startBtn);
        stopBtn = (Button) findViewById(R.id.StopBtn);


        ArrayList<ListviewItem> data = new ArrayList<>();

        ListviewItem sample = new ListviewItem(String.valueOf(new Date(System.currentTimeMillis())), "Start");

        ListViewAdapter adapter = new ListViewAdapter(this, R.layout.listview_item, data);
        listView.setAdapter(adapter);

        data.add(sample);
    }
}
