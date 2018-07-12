package com.example.userp.timestamp;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private Button startBtn;
    private Button stopBtn;

    private ArrayList<ListviewItem> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        startBtn = (Button) findViewById(R.id.startBtn);
        stopBtn = (Button) findViewById(R.id.StopBtn);


        data = new ArrayList<>();

        ListviewItem sample = new ListviewItem(String.valueOf(new Date(System.currentTimeMillis())), "Start");

        final ListViewAdapter adapter = new ListViewAdapter(this, R.layout.listview_item, data);
        listView.setAdapter(adapter);

        data.add(sample);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = String.valueOf(new Date(System.currentTimeMillis()));
                data.add(new ListviewItem(date, "Start"));
                adapter.notifyDataSetChanged();
            }
        });
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = String.valueOf(new Date(System.currentTimeMillis()));
                data.add(new ListviewItem(date, "Stop"));
                adapter.notifyDataSetChanged();
            }
        });
    }
}
