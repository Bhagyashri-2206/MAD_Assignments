package com.example.aquaritual.model;

public class DayHistory {

    public String date;
    public int totalMl;


    public DayHistory() {
    }


    public DayHistory(String date, int totalMl) {
        this.date = date;
        this.totalMl = totalMl;
    }

    public String getDate() {
        return date;
    }

    public int getTotalMl() {
        return totalMl;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTotalMl(int totalMl) {
        this.totalMl = totalMl;
    }
}