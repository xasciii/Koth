package org.asc.koth.utilities;

public class TimeUtility {
    public static String formatSeconds(long rawSeconds) {
        long hours = rawSeconds / 3600L;
        long minutes = rawSeconds % 3600L / 60L;
        long seconds = rawSeconds % 60L;
        return String.format("%02d:%02d:%02d", new Object[] { Long.valueOf(hours), Long.valueOf(minutes), Long.valueOf(seconds) });
    }
}
