package com.example.aquaritual.model;

public class WaterLog {

    public String time;
    public int amount;


    public WaterLog() {
    }


    public WaterLog(String time, int amount) {
        this.time = time;
        this.amount = amount;
    }


    public String getTime() {
        return time;
    }

    public int getAmount() {
        return amount;
    }


    public void setTime(String time) {
        this.time = time;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}