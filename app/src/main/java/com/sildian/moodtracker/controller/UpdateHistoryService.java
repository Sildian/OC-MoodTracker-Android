package com.sildian.moodtracker.controller;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.sildian.moodtracker.model.Mood;

import java.util.ArrayList;

public class UpdateHistoryService extends Service {

    /**Constructor**/

    public UpdateHistoryService() {
    }

    /**Callback methods**/

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("CHECK_UPDATE", "Start");

        /*Creates an ArrayList to get the different moods*/

        ArrayList<Mood> moodsHistory=new ArrayList<Mood>();

        /*Opens the SharedPreferences file containing the data*/

        SharedPreferences sharedPreferences=getSharedPreferences(Mood.FILE_MOOD_DATA, MODE_PRIVATE);

        /*If the file already exists*/

        if(sharedPreferences!=null) {

            /*Creates a Mood to be used as a buffer*/

            Mood moodBuffer;

            /*For each Mood in the file from 0 to the maximum number of stored moods, adds the mood to moodsHistory*/

            for (int i = 0; i <= Mood.NUMBER_MOODS_HISTORY; i++) {
                if(sharedPreferences.contains(Mood.KEY_MOOD_DAY+i)) {
                    moodBuffer=new Mood();
                    moodBuffer.loadMood(sharedPreferences);
                    moodsHistory.add(moodBuffer);
                }
            }
        }

        /*For each Mood in moodsHistory, increases the day by 1.
        Then if the resulted day is above the number of moods to be stored, removes the mood. Else saves it.*/

        for(int i=0;i<moodsHistory.size();i++){
            moodsHistory.get(i).increaseDay();
            if(moodsHistory.get(i).getDay()>Mood.NUMBER_MOODS_HISTORY)
                moodsHistory.remove(i);
            else
                moodsHistory.get(i).saveMood(sharedPreferences);
        }

        /*Creates and saves a new Mood for the new day*/

        Mood newDayMood=new Mood();
        newDayMood.saveMood(sharedPreferences);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
