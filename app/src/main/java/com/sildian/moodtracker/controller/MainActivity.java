package com.sildian.moodtracker.controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.sildian.moodtracker.R;
import com.sildian.moodtracker.model.Mood;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    /**The keys for saving bundle**/

    private static final String KEY_MOOD_DAY="1001";
    private static final String KEY_MOOD_LEVEL="1002";
    private static final String KEY_MOOD_COMMENT="1003";

    /**Attributes**/

    private Context mContext;                           //Context
    private FrameLayout mLayout;                        //Layout
    private ImageView mSmileyImage;                     //Smiley
    private ImageView mAddCommentButton;                //This button allows to add a comment
    private ImageView mHistoryButton;                   //This button allows to see the history data
    private GestureDetectorCompat mGestureDetector;     //Gesture detector allowing to monitor the swipe
    private SharedPreferences mSharedPreferences;       //Shared preferences to save and load the mood
    private Mood mMood;                                 //The current mood

    /**Callback methods**/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Get the components from the layout*/

        mContext=this;
        mLayout=findViewById(R.id.activity_main_layout);
        mSmileyImage=findViewById(R.id.activity_main_image_smiley);
        mAddCommentButton=findViewById(R.id.activity_main_button_add_comment);
        mHistoryButton=findViewById(R.id.activity_main_button_history);

        /*Creates a detector to catch the gestures on the screen*/

        mGestureDetector=new GestureDetectorCompat(this, new SwipeGestureListener());

        /*Creates the sharedPreferences*/

        mSharedPreferences=getSharedPreferences(Mood.FILE_MOOD_DATA, MODE_PRIVATE);

        /*Creates or loads the mood*/

        if(savedInstanceState==null) {
            mMood = new Mood();
            if(mSharedPreferences!=null){
                mMood.loadMood(mSharedPreferences);
            }
        }
        else
            mMood=new Mood(savedInstanceState.getInt(KEY_MOOD_DAY), savedInstanceState.getInt(KEY_MOOD_LEVEL), savedInstanceState.getString((KEY_MOOD_COMMENT)));

        /*When the user clicks on mAddCommentButton, a dialog is shown to enter a comment*/

        mAddCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentDialog commentDialog = new CommentDialog(mContext, mMood);
                commentDialog.show();
            }
        });

        /*When the user clicks on mHistoryButton, starts HistoryActivity*/

        mHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyActivityIntent=new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(historyActivityIntent);
            }
        });

        /*Refresh the screen*/

        refreshScreen();

        /*Sets an alarm to automatically update the moods history*/

        setAlarmToUpdateHistory();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        refreshScreen();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_MOOD_DAY, mMood.getDay());
        outState.putInt(KEY_MOOD_LEVEL, mMood.getMoodLevel());
        outState.putString(KEY_MOOD_COMMENT, mMood.getComment());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        mMood.saveMood(mSharedPreferences);
        super.onDestroy();
    }

    /**
     * refreshScreen
     * Refresh the background color and the smiley
     */

    private void refreshScreen(){
        int resIdColor=getResources().getIdentifier(Mood.COLORS[mMood.getMoodLevel()], "color", getPackageName());
        int resIdImage=getResources().getIdentifier(Mood.IMAGES[mMood.getMoodLevel()], "drawable", getPackageName());
        mLayout.setBackgroundResource(resIdColor);
        mSmileyImage.setImageResource(resIdImage);
    }

    /**
     * setAlarmToUpdateHistory
     * Sets an alarm to automatically update the moods history
     */

    private void setAlarmToUpdateHistory(){

        /*Creates an intent allowing to start UpdateHistoryService*/

        Intent updateHistoryServiceIntent=new Intent(MainActivity.this, UpdateHistoryService.class);
        PendingIntent updateHistoryServicePendingIntent=PendingIntent.getService(this, 0, updateHistoryServiceIntent, 0);

        /*Creates a Calendar to set the next update time (TEMPORARY 1 MINUTE)*/

        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        int hour=calendar.get(Calendar.HOUR);
        int minute=calendar.get(Calendar.MINUTE)+1;
        calendar.set(year, month, day, hour, minute);

        /*Creates an alarm manager allowing to automatically starts UpdateHistoryService*/

        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1, updateHistoryServicePendingIntent);
    }

    /**
     * SwipeGestureListener
     * Internal class allowing to detect a swipe up or down on the screen
     */

    private class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e2.getY()<e1.getY())
                mMood.increaseMood();
            else if(e2.getY()>e1.getY())
                mMood.decreaseMood();
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}
