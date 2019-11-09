package com.quiz.quizapp1.QuizApp.QuizApp;

public class QuestionsPojo {
        String A;
        String B;
        String C;
        String D;
        String ans;
        String q;

    public QuestionsPojo() {
    }

    public QuestionsPojo(String a, String b, String c, String d, String ans, String q) {
        A = a;
        B = b;
        C = c;
        D = d;
        this.ans = ans;
        this.q = q;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }
}