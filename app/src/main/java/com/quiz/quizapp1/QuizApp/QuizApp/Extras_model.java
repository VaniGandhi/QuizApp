package com.quiz.quizapp1.QuizApp.QuizApp;

public class Extras_model {
  int rank;
  int level;
  int xp;
  int star;
  int coin;
  int incr;
  int levelreached=150;
  int count;
  int points;
  int contiwin;
  int sheild;
  public Extras_model() {
    }

    public Extras_model(int rank, int level, int xp, int star, int coin, int iswin, int levelreached, int count, int points, int contiwin, int sheild) {
        this.rank = rank;
        this.contiwin=contiwin;
        this.level = level;
        this.xp = xp;
        this.star = star;
        this.coin=coin;
        this.incr =iswin;
        this.levelreached=levelreached;
        this.count=count;
        this.points=points;
        this .sheild=sheild;
    }

    public int getSheild() {
        return sheild;
    }

    public void setSheild(int sheild) {
        this.sheild = sheild;
    }

    public int getContiwin() {
        return contiwin;
    }

    public void setContiwin(int contiwin) {
        this.contiwin = contiwin;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLevelreached() {
        return levelreached;
    }

    public void setLevelreached(int levelreached) {
        this.levelreached = levelreached;
    }

    public int getIncr() {
        return incr;
    }

    public void setIncr(int incr) {
        this.incr = incr;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }
}
