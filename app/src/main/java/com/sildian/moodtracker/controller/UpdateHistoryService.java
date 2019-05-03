package com.sildian.moodtracker.controller;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.sildian.moodtracker.model.Mood;

import java.util.ArrayList;

public class UpdateHistoryService extends Service {

    /**Constructor**/

    public UpdateHistoryService() {
    }

    /**Callback methods**/

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        /*Creates an ArrayList to get the different moods*/

        ArrayList<Mood> historyMoods=new ArrayList<Mood>();

        /*Opens the SharedPreferences file containing the data*/

        SharedPreferences sharedPreferences=getSharedPreferences(Mood.FILE_MOOD_DATA, MODE_PRIVATE);

        /*Loads the history moods, then saves them again after changing their days*/

        historyMoods=Mood.loadHistoryMoods(sharedPreferences, 0);
        Mood.saveHistoryMoods(sharedPreferences, historyMoods);

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
