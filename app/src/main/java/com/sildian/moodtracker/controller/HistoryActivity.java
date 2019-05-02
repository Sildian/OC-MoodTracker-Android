package com.sildian.moodtracker.controller;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sildian.moodtracker.R;
import com.sildian.moodtracker.model.Mood;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends ListActivity {

    /**Callback methods**/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        /*TEMPORARY : list of moods to fill the ListView*/

        ArrayList<Mood> moodArrayList=new ArrayList<Mood>();
        moodArrayList.add(new Mood(7, Mood.HAPPY, ""));
        moodArrayList.add(new Mood(6, Mood.NORMAL, ""));
        moodArrayList.add(new Mood(5, Mood.DISAPPOINTED, ""));
        moodArrayList.add(new Mood(4, Mood.SUPER_HAPPY, "Oh yeah!"));
        moodArrayList.add(new Mood(3, Mood.SAD, "Sniff"));
        moodArrayList.add(new Mood(2, Mood.NORMAL, ""));
        moodArrayList.add(new Mood(1, Mood.NORMAL, ""));

        /*Sets the adapter to the ListView*/

        setListAdapter(new HistoryAdapter(this, R.layout.list_view_history, moodArrayList));
    }

    /**
     * HistoryAdapter
     * Internal class providing a custom adapter
     */

    private class HistoryAdapter<T> extends ArrayAdapter<T> {

        /**
         * Constructor
         * @param context : the context
         * @param resource : the template layout to be used for each item
         * @param objects : the list of items to be used to fill the ListView
         */

        public HistoryAdapter(Context context, int resource, List<T> objects) {
            super(context, resource, objects);
        }

        /**
         * getView
         * Build each item in the ListView
         * @param position : the position of the current item
         * @param convertView : recycles unused items
         * @param parent : the parent layout to be used
         * @return the current view
         */

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            /*Creates a SaveHistoryItem to save the current view*/

            SaveHistoryItem saveHistoryItem;

            /*If the convertView is null...*/

            if(convertView==null){

                /*Inflates the view*/

                LayoutInflater inflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView=inflater.inflate(R.layout.list_view_history, parent, false);

                /*Gets the different widgets from the view*/

                saveHistoryItem=new SaveHistoryItem();
                saveHistoryItem.layout=convertView.findViewById(R.id.list_view_history_layout);
                saveHistoryItem.text=convertView.findViewById(R.id.list_view_history_text);
                saveHistoryItem.commentButton=convertView.findViewById(R.id.list_view_history_button_comment);

                /*Sets tag for saving the view*/

                convertView.setTag(saveHistoryItem);

            }else {

                /*Else recycles the convertView*/

                saveHistoryItem = (SaveHistoryItem) convertView.getTag();
            }

            /*Gets the current mood*/

            Mood itemMood=(Mood)getItem(position);

            /*Gets some resources to be used, related to the current mood*/

            double screenWidth=getResources().getDisplayMetrics().widthPixels;
            int resIdColor=getResources().getIdentifier(Mood.COLORS[itemMood.getMoodLevel()], "color", getPackageName());
            int resIdText=getResources().getIdentifier("text_dm"+itemMood.getDay(), "string", getPackageName());

            /*Sets the parameters of the view using the resources and the current mood*/

            saveHistoryItem.layout.setLayoutParams(new ListView.LayoutParams((int)(screenWidth*(itemMood.getMoodLevel()+1)/5), ListView.LayoutParams.MATCH_PARENT));
            saveHistoryItem.layout.setBackgroundResource(resIdColor);
            saveHistoryItem.text.setText(resIdText);
            saveHistoryItem.commentButton.setImageResource(R.drawable.ic_comment_black_48px);
            if(itemMood.getComment()=="")
                saveHistoryItem.commentButton.setVisibility((View.INVISIBLE));

            return convertView;
        }

        /**
         * SaveHistoryItem
         * Internal class allowing to save a view in the ListView
         */

        private class SaveHistoryItem{
            View layout;
            TextView text;
            ImageView commentButton;
        }
    }

}
