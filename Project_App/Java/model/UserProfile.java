package com.example.aquaritual.model;

public class UserProfile {

    private String gender;
    private int weight;
    private String wakeTime;
    private String sleepTime;
    private int dailyWater;


    public UserProfile() {
    }

    public UserProfile(String gender, int weight, String wakeTime, String sleepTime, int dailyWater) {
        this.gender = gender;
        this.weight = weight;
        this.wakeTime = wakeTime;
        this.sleepTime = sleepTime;
        this.dailyWater = dailyWater;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getWakeTime() {
        return wakeTime;
    }

    public void setWakeTime(String wakeTime) {
        this.wakeTime = wakeTime;
    }

    public String getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(String sleepTime) {
        this.sleepTime = sleepTime;
    }

    public int getDailyWater() {
        return dailyWater;
    }

    public void setDailyWater(int dailyWater) {
        this.dailyWater = dailyWater;
    }
}