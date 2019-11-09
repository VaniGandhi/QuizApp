package com.quiz.quizapp1.QuizApp.QuizApp;

public class Booster_model
{
    String Booster_id;
    long timebuy;

    public long getTimebuy() {
        return timebuy;
    }

    public void setTimebuy(long timebuy) {
        this.timebuy = timebuy;
    }

    public long getTimespan() {
        return timespan;
    }

    public void setTimespan(long timespan) {
        this.timespan = timespan;
    }

    public long getExtended_time() {
        return extended_time;
    }

    public void setExtended_time(long extended_time) {
        this.extended_time = extended_time;
    }

    long timespan;
    long extended_time;

    public Booster_model() {
    }

    public Booster_model(String booster_id, long timebuy, long timespan, long extended_time) {
        Booster_id = booster_id;
        this.timebuy = timebuy;
        this.timespan = timespan;
        this.extended_time = extended_time;
    }

    public String getBooster_id() {
        return Booster_id;
    }

    public void setBooster_id(String booster_id) {
        Booster_id = booster_id;
    }


}
