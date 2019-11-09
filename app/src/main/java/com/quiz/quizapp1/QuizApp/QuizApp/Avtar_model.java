package com.quiz.quizapp1.QuizApp.QuizApp;

public class Avtar_model {

    String url;
    String id;
    int status;

    public Avtar_model() {
    }

    public Avtar_model(String imgurl, String id,int status) {
        this.url = imgurl;
        this.id=id;
        this.status=status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
