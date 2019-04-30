package com.sildian.moodtracker.controller;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.sildian.moodtracker.R;
import com.sildian.moodtracker.model.Mood;

public class MainActivity extends AppCompatActivity {

    /**Attributes**/

    private FrameLayout mLayout;                    //The layout
    private ImageView mSmileyImage;                 //Smiley
    private ImageView mAddCommentButton;            //This button allows to add a comment
    private ImageView mHistoryButton;               //This button allows to see the history data
    private GestureDetectorCompat mGestureDetector; //Gesture detector allowing to monitor the swipe
    private Mood mMood;                              //The current mood

    /**Callback methods**/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayout=findViewById(R.id.activity_main_layout);
        mSmileyImage=findViewById(R.id.activity_main_image_smiley);
        mAddCommentButton=findViewById(R.id.activity_main_button_add_comment);
        mHistoryButton=findViewById(R.id.activity_main_button_history);

        mGestureDetector=new GestureDetectorCompat(this, new SwipeGestureListener());

        mMood=new Mood();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        refreshScreen();
        return super.onTouchEvent(event);
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
