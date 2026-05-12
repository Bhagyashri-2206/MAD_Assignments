package com.example.aquaritual.utils;

public class WaterCalculator {

    public static int calculate(String gender, int weight, int wakeHour, int sleepHour) {

        // ✅ Safety check
        if (gender == null) gender = "";

        // ✅ Base formula (recommended)
        int base = weight * 35; // 35 ml per kg

        // ✅ Gender adjustment
        if (gender.equalsIgnoreCase("male")) {
            base += 250;
        } else if (gender.equalsIgnoreCase("female")) {
            base += 150;
        }

        // ✅ Fix active hours calculation (handles next-day sleep)
        int activeHours;
        if (sleepHour > wakeHour) {
            activeHours = sleepHour - wakeHour;
        } else {
            activeHours = (24 - wakeHour) + sleepHour;
        }

        // ✅ Add activity hydration
        base += activeHours * 15;

        return base;
    }
}