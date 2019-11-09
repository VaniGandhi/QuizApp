package com.quiz.quizapp1.QuizApp.QuizApp;

public class Updateavtarmodel {

    String url;
    String avid;
    int status;

    public Updateavtarmodel() {
    }

    public Updateavtarmodel(String url, String avid, int status) {
        this.url = url;
        this.avid = avid;
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAvid() {
        return avid;
    }

    public void setAvid(String avid) {
        this.avid = avid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
