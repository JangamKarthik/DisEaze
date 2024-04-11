package com.example.diseaze;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ScanResult {
    private long timestamp;
    private String imageUrl;
    private String className;
    private double confidenceLevel;
    private String formattedDate;

    public ScanResult() {
        // Default constructor required for calls to DataSnapshot.getValue(ScanResult.class)
    }

    public ScanResult(long timestamp, String className, double confidenceLevel) {
        this.timestamp = timestamp;
        this.className = className;
        this.confidenceLevel = confidenceLevel;
        this.formattedDate = formatDate(timestamp);
    }

    public long getTimestamp() {
        return timestamp;
    }


    public String getClassName() {
        return className;
    }

    public double getConfidenceLevel() {
        return confidenceLevel;
    }

    private String formatDate(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(date);
    }
}
