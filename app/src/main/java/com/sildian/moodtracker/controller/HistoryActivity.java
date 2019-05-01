package com.sildian.moodtracker.controller;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.sildian.moodtracker.R;

import java.util.ArrayList;

public class HistoryActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_history);

        ArrayList<String> textList=new ArrayList<String>();
        textList.add("J-1");
        textList.add("J-2");
        textList.add("J-3");

        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, textList));
    }
}
