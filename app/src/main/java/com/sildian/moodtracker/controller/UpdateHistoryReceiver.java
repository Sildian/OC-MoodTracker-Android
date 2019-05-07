package com.sildian.moodtracker.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.sildian.moodtracker.model.Mood;

import java.util.ArrayList;

/**
 * UpdateHistoryReceiver
 * When this receiver is called, it updates the history moods.
 */

public class UpdateHistoryReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        /*Creates an ArrayList to get the different moods*/

        ArrayList<Mood> historyMoods=new ArrayList<Mood>();

        /*Opens the SharedPreferences file containing the data*/

        SharedPreferences sharedPreferences=context.getSharedPreferences(Mood.FILE_MOOD_DATA, Context.MODE_PRIVATE);

        /*Loads the history moods, then saves them again after changing their days*/

        historyMoods=Mood.loadHistoryMoods(sharedPreferences, 0);
        Mood.saveHistoryMoods(sharedPreferences, historyMoods);

        /*Creates and saves a new Mood for the new day*/

        Mood newDayMood=new Mood();
        newDayMood.saveMood(sharedPreferences);
    }
}
