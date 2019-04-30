package com.sildian.moodtracker.model;

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

    /**Attributes**/

    private Calendar mDate;                         //Date of the mood
    private int mMoodLevel;                         //Mood level
    private String mComment;                        //Comment related to the mood

    /**
     * Constructor
     * Fills mDate with the date of the current day
     * Set mMoodLevel to HAPPY by default.
     */

    public Mood() {
        mDate=Calendar.getInstance();
        mMoodLevel=HAPPY;
        mComment="";
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


    /**Getters**/

    public Calendar getDate() {
        return mDate;
    }

    public int getMoodLevel() {
        return mMoodLevel;
    }

    public String getComment() {
        return mComment;
    }
}
