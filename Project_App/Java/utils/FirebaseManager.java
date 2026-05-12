package com.example.aquaritual.utils;

import com.example.aquaritual.model.UserProfile;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseManager {

    private static final DatabaseReference rootRef =
            FirebaseDatabase.getInstance().getReference("aquaRitual");


    public static DatabaseReference profileRef() {
        return rootRef.child("profile");
    }

    public static void saveUserProfile(UserProfile profile) {
        profileRef().setValue(profile);
    }


    public static DatabaseReference targetRef() {
        return rootRef.child("target");
    }

    public static void updateDailyTarget(int targetMl) {
        targetRef().child("dailyTarget").setValue(targetMl);
    }


    public static DatabaseReference dailyLogsRef() {
        return rootRef.child("dailyLogs");
    }

    public static DatabaseReference logsByDate(String date) {
        return dailyLogsRef().child(date);
    }
}
