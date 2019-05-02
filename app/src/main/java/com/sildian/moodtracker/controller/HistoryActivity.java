package com.sildian.moodtracker.controller;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sildian.moodtracker.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ArrayList<String> textList=new ArrayList<String>();
        textList.add("J-1");
        textList.add("J-2");
        textList.add("J-3");

        setListAdapter(new HistoryAdapter(this, R.layout.list_view_history, textList));
    }

    private class HistoryAdapter<T> extends ArrayAdapter<T> {

        public HistoryAdapter(Context context, int resource, List<T> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            SaveHistoryItem saveHistoryItem;
            if(convertView==null){
                LayoutInflater inflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView=inflater.inflate(R.layout.list_view_history, parent, false);
                saveHistoryItem=new SaveHistoryItem();
                saveHistoryItem.text=convertView.findViewById(R.id.list_view_history_text);
                saveHistoryItem.commentButton=convertView.findViewById(R.id.list_view_history_button_comment);
                convertView.setTag(saveHistoryItem);
            }else
                saveHistoryItem=(SaveHistoryItem)convertView.getTag();

            saveHistoryItem.text.setText(getItem(position).toString());
            saveHistoryItem.commentButton.setImageResource(R.drawable.ic_comment_black_48px);

            return convertView;
        }

        private class SaveHistoryItem{
            TextView text;
            ImageView commentButton;
        }
    }

}
