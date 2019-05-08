package com.sildian.moodtracker.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.sildian.moodtracker.R;
import com.sildian.moodtracker.model.Mood;

import java.util.ArrayList;
import java.util.List;

/**
 * HistoryActivity
 * This activity shows the history moods registered during the last days.
 * If a comment is attached to a mood, it is possible to see it by clicking on the related line.
 * A pie chart is also shown below the history.
 */

public class HistoryActivity extends AppCompatActivity {

    /***Attributes*/

    private ListView mListView;                 //The ListView containing the history moods data
    private PieChart mPieChart;                 //A pie chart showing the history data
    private TextView mNoHistoryText;            //A text to display in case there is no history

    /**Callback methods**/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        /*Opens the SharedPreferences file containing the data, then loads the data into an ArrayList*/

        SharedPreferences sharedPreferences=getSharedPreferences(Mood.FILE_MOOD_DATA, MODE_PRIVATE);
        ArrayList<Mood> historyMoods=Mood.loadHistoryMoods(sharedPreferences, 1);

        /*Sets the different views*/

        mListView=findViewById(R.id.activity_history_list);
        mPieChart=findViewById(R.id.activity_history_chart);
        mNoHistoryText = findViewById(R.id.activity_history_text_no_history);

        /*If there is no history data*/

        if(historyMoods.isEmpty()) {

            /*Hides the list and the chart*/

            mListView.setVisibility(View.GONE);
            mPieChart.setVisibility(View.GONE);

        }else{

            /*Else generates the list and the chart, and hides the text*/

            generateListView(historyMoods);
            generatePieChart(historyMoods);
            mNoHistoryText.setVisibility(View.GONE);
        }
    }

    /**
     * generateListView
     * @param historyMoods : the list of items allowing to fill the list
     */

    private void generateListView(ArrayList<Mood> historyMoods){

        /*Sets the adapter*/

        mListView.setAdapter(new HistoryAdapter(this, R.layout.list_view_history, historyMoods));

        /*OnItemClick*/

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /*Gets the item mood and, if a comment exists, displays it as a toast*/

                Mood itemMood=(Mood)mListView.getItemAtPosition(position);
                if(!itemMood.getComment().equals("")) {
                    Toast.makeText(getBaseContext(), itemMood.getComment(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * generatePieChart
     * @param historyMoods : the list of items allowing to fill the list
     */

    private void generatePieChart(ArrayList<Mood> historyMoods){

        /*Main items to generate the entries*/

        List<PieEntry> entries = new ArrayList<>();                     //The entries allowing to fill the pie chart
        List<Integer> entriesMoodLevels=new ArrayList<>();              //The mood levels related to each entry

        /*This table will contain the number of stored value for each possible mood category*/

        int moodsCategories[]=new int[Mood.NUMBER_MOODS_CATEGORIES];

        /*Initializes each item in moodsCategories to 0*/

        for(int i=0;i<moodsCategories.length;i++) {
            moodsCategories[i] = 0;
        }

        /*For each item existing in historyMoods, add 1 to the related category in moodsCategories*/

        for(int i=0;i<historyMoods.size();i++) {
            moodsCategories[historyMoods.get(i).getMoodLevel()]++;
        }

        /*This will be used to define the label of each entry*/

        int resIdLabel;                                             //The resource id
        TextView entryLabel=new TextView(this);             //A TextView to turn the resource id into a String

        /*For each item in moodsCategories*/

        for(int i=0;i<moodsCategories.length;i++) {

            /*If the number of stored values for the current category is different than 0*/

            if (moodsCategories[i] != 0) {

                /*Defines the label*/

                resIdLabel=getResources().getIdentifier("text_mood_level_"+i, "string", getPackageName());
                entryLabel.setText(resIdLabel);

                /*Adds a new entry*/

                entries.add(new PieEntry(moodsCategories[i], entryLabel.getText().toString()));
                entriesMoodLevels.add(i);
            }
        }

        /*Main items to generate the pie chart*/

        PieDataSet pieDataSet = new PieDataSet(entries, "");          //The data set, filled with the entries
        ArrayList<Integer> moodsColors=new ArrayList<Integer>();            //The list of colors for the chart
        int resIdColor;                                                     //A resource id used to seek the colors

        /*For each entry, defines the color to be used*/

        for(int i=0;i<entries.size();i++){
            resIdColor=getResources().getIdentifier(Mood.COLORS[entriesMoodLevels.get(i)], "color", getPackageName());
            moodsColors.add(getResources().getColor(resIdColor));
        }

        /*Sets the colors*/

        pieDataSet.setColors(moodsColors);

        /*Then generates the pie chart*/

        PieData data = new PieData(pieDataSet);
        mPieChart.setData(data);
        mPieChart.getDescription().setText("");
        mPieChart.setEntryLabelColor(R.color.colorPrimaryDark);
        mPieChart.invalidate();
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

            saveHistoryItem.layout.setLayoutParams(new ListView.LayoutParams((int)(screenWidth*(itemMood.getMoodLevel()+1)/Mood.NUMBER_MOODS_CATEGORIES), ListView.LayoutParams.MATCH_PARENT));
            saveHistoryItem.layout.setBackgroundResource(resIdColor);
            saveHistoryItem.text.setText(resIdText);
            saveHistoryItem.commentButton.setImageResource(R.drawable.ic_comment_black_48px);
            if(itemMood.getComment().equals("")) {
                saveHistoryItem.commentButton.setVisibility((View.INVISIBLE));
            }

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
