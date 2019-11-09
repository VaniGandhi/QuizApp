package com.quiz.quizapp1.QuizApp.NewProject;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class list_model implements Parcelable, Serializable {

    String name;

    public list_model() {
    }

    public list_model(String name) {
        this.name = name;
    }

    protected list_model(Parcel in) {
        name = in.readString();
    }

    public static final Creator<list_model> CREATOR = new Creator<list_model>() {
        @Override
        public list_model createFromParcel(Parcel in) {
            return new list_model(in);
        }

        @Override
        public list_model[] newArray(int size) {
            return new list_model[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
    }
}
