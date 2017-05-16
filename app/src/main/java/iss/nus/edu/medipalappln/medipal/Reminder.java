package iss.nus.edu.medipalappln.medipal;

import java.util.Date;

/**
 * Created by rama on 3/22/2017.
 */

public class Reminder {

    private int remindId;
    private String frequency;
    private Date startTime;
    private String interval;

    public Reminder() {
    }

    public Reminder(int remindId, String frequency, Date startTime, String interval) {
        this.remindId = remindId;
        this.frequency = frequency;
        this.startTime = startTime;
        this.interval = interval;
    }

    public Reminder(String frequency, Date startTime, String interval) {
        this.frequency = frequency;
        this.startTime = startTime;
        this.interval = interval;
    }

    public int getRemindId() {
        return remindId;
    }

    public void setRemindId(int remindId) {
        this.remindId = remindId;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }
}
