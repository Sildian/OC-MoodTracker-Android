package com.sildian.moodtracker.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class MoodTest {

    @Test
    public void given_null_when_increaseDay_then_mDayIncreasedBy1() {
        Mood mood=new Mood();
        mood.increaseDay();
        assertEquals(1, mood.getDay());
    }

    @Test
    public void given_null_when_increaseMoodTwice_then_mMoodLevelIsNotAboveSUPER_HAPPY() {
        Mood mood=new Mood();
        mood.increaseMood();
        mood.increaseMood();
        assertTrue("mMoodLevel cannot be above SUPER_HAPPY", mood.getMoodLevel()<=Mood.SUPER_HAPPY);
    }

    @Test
    public void given_null_when_decreaseMoodFourTimes_then_mMoodLevelIsNotBelow_SAD() {
        Mood mood=new Mood();
        mood.decreaseMood();
        mood.decreaseMood();
        mood.decreaseMood();
        mood.decreaseMood();
        assertTrue("mMoodLevel cannot be below SAD", mood.getMoodLevel()>=Mood.SAD);
    }
}