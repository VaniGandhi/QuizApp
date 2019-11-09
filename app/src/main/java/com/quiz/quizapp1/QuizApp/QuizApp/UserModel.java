package com.quiz.quizapp1.QuizApp.QuizApp;

import com.google.android.gms.common.internal.Objects;

public class UserModel {

    String id;
    String name;
    String uid;
    String avtar_id;
    String url;


    public UserModel()
    {

    }

    public UserModel(String id, String name, String uid, String avtar_id, String url) {
        this.id = id;
        this.name = name;
        this.uid = uid;
        this.avtar_id=avtar_id;
        this.url=url;
    }

    public String getAvtar_id() {
        return avtar_id;
    }

    public void setAvtar_id(String avtar_id) {
        this.avtar_id = avtar_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
