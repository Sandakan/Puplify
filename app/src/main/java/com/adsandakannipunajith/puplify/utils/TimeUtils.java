package com.adsandakannipunajith.puplify.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeUtils {

    // Function to convert date string to elapsed time
    public static String getElapsedTime(String createdAt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date past = dateFormat.parse(createdAt);
            Date now = new Date();

            // Calculate time difference in milliseconds
            assert past != null;
            long diff = now.getTime() - past.getTime();

            long days = TimeUnit.MILLISECONDS.toDays(diff);
            long hours = TimeUnit.MILLISECONDS.toHours(diff) % 24;
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff) % 60;

            if (days > 0) {
                return days + " days ago";
            } else if (hours > 0) {
                return hours + " hours ago";
            } else if (minutes > 0) {
                return minutes + " minutes ago";
            } else {
                return "Just now";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
