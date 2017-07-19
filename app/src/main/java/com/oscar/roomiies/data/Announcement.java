package com.oscar.roomiies.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Oscar on 7/17/2017.
 */

public class Announcement {

    private String announcement;
    private String announcer;
    private String date;

    public Announcement(){}

    public Announcement(String announcement, String announcer){
        this.announcement = announcement;
        this.announcer = announcer;

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        this.date = dateFormat.format(date);
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public String getAnnouncer() {
        return announcer;
    }

    public void setAnnouncer(String announcer) {
        this.announcer = announcer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toString(){
        return announcement + " -" + announcer +" on " + date;
    }

}
