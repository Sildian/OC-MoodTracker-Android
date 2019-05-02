package com.sildian.moodtracker.model;

import android.content.SharedPreferences;

import java.util.Calendar;

public class Mood {

    /**The different values of mMoodLevel**/

    public static final int SAD=0;
    public static final int DISAPPOINTED=1;
    public static final int NORMAL=2;
    public static final int HAPPY=3;
    public static final int SUPER_HAPPY=4;

    /**The images and colors related to each mMoodLevel**/

    public static final String[] IMAGES={"smiley_sad", "smiley_disappointed", "smiley_normal", "smiley_happy", "smiley_super_happy"};
    public static final String[] COLORS={"faded_red", "warm_grey", "cornflower_blue_65", "light_sage", "banana_yellow"};

    /**The keys for saving and loading data in the SharedPreferences**/

    public static final String FILE_MOOD_DATA="mood_data.xml";
    private static final String KEY_MOOD_DAY="KEY_MOOD_DAY_DM";
    private static final String KEY_MOOD_LEVEL="KEY_MOOD_LEVEL_DM";
    private static final String KEY_MOOD_COMMENT="KEY_MOOD_COMMENT_DM";

    /**Attributes**/

    private int mDay;                               //Number of days elapsed since the mood was registered
    private int mMoodLevel;                         //Mood level
    private String mComment;                        //Comment related to the mood

    /**
     * Constructor
     * Set mDay to 0, mMoodLevel to HAPPY and mComment to ""
     */

    public Mood() {
        mDay=0;
        mMoodLevel=HAPPY;
        mComment="";
    }

    /**
     * Constructor
     * @param day : number of days elapsed since the mood was registered
     * @param moodLevel : the mood level
     * @param comment : the comment
     */

    public Mood(int day, int moodLevel, String comment) {
        mDay=day;
        mMoodLevel=moodLevel;
        mComment=comment;
    }

    /**
     * increaseMood
     * Increases mMoodLevel by 1.
     * As a result mMoodLevel cannot be above SUPER_HAPPY.
     */

    public void increaseMood(){
        mMoodLevel++;
        if(mMoodLevel>SUPER_HAPPY)
            mMoodLevel=SUPER_HAPPY;
    }

    /**
     * decreaseMood
     * Decreases mMoodLevel by 1.
     * As a result mMoodLevel cannot be below SAD.
     */

    public void decreaseMood(){
        mMoodLevel--;
        if(mMoodLevel<SAD)
            mMoodLevel=SAD;
    }

    /**
     * saveMood
     * @param sharedPreferences : the sharedPreferences to be used to save the mood
     */

    public void saveMood(SharedPreferences sharedPreferences){
        sharedPreferences.edit().putInt(KEY_MOOD_DAY+mDay, mDay).apply();
        sharedPreferences.edit().putInt(KEY_MOOD_LEVEL+mDay, mMoodLevel).apply();
        sharedPreferences.edit().putString(KEY_MOOD_COMMENT+mDay, mComment).apply();
    }

    /**
     * loadMood
     * @param sharedPreferences : the sharedPreferences to be used to load the mood
     */

    public void loadMood(SharedPreferences sharedPreferences){
        mDay=sharedPreferences.getInt(KEY_MOOD_DAY+mDay, 0);
        mMoodLevel=sharedPreferences.getInt(KEY_MOOD_LEVEL+mDay, HAPPY);
        mComment=sharedPreferences.getString(KEY_MOOD_COMMENT+mDay, "");
    }

    /**Getters and Setters**/

    public int getDay() {
        return mDay;
    }

    public int getMoodLevel() {
        return mMoodLevel;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }
}
