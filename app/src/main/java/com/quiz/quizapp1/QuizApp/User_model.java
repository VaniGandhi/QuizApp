package com.quiz.quizapp1.QuizApp;

public class User_model {

    private String name;
    private String password;
    private String email;
    private String imgurl;

    private User_model()

    {

    }

    public User_model(String name, String password, String email, String imgurl) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.imgurl = imgurl;
    }

    public User_model(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
