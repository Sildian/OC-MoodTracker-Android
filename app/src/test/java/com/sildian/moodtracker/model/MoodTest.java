package com.sildian.moodtracker.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class MoodTest {

    @Test
    public void given_null_when_increaseMood_then_mMoodLevelIsNotAboveSUPER_HAPPY() {
        Mood mood=new Mood();
        mood.increaseMood();
        assertTrue("mMoodLevel cannot be above SUPER_HAPPY", mood.getMoodLevel()<=Mood.SUPER_HAPPY);
    }

    @Test
    public void given_null_when_decreaseMood_then_mMoodLevelIsNotBelow_SAD() {
        Mood mood=new Mood();
        mood.decreaseMood();
        assertTrue("mMoodLevel cannot be below SAD", mood.getMoodLevel()>=Mood.SAD);
    }
}