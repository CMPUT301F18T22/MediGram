package medigram.medigram;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
/*
* @author Zeyu Liu*/


import java.util.ArrayList;


public class RecordListActivity  extends AppCompatActivity {
    private ListView recordsView;
    private ArrayAdapter<Record> adapter;
    private ArrayList<Record> recordList;
    int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);
        recordsView = (ListView) findViewById(R.id.RecordListView);
        recordsView.setTextFilterEnabled(true);



    }}


