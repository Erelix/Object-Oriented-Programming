package com.example.bamo;

public class Date {
    private String date;
    private boolean attended;

    public Date(String date, boolean attended) {
        this.date = date;
        this.attended = attended;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }
}
