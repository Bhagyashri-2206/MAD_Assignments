package com.example.aquaritual.utils;

import android.content.Context;
import android.provider.Settings;

public class DeviceIdUtil {
    public static String getDeviceId(Context context) {
        String id = Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ANDROID_ID
        );

        if (id == null || id.isEmpty()) {
            id = "default_user";
        }

        return id;
    }
}

