package iss.nus.edu.medipalappln.medipal;

/**
 * Created by swarna on 4/8/2016.
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Consumption {

    public static final String DATE_FORMAT = "d-MMM-yyyy";
    public static final String TIME_FORMAT = "d-MMM-yyyy H:mm";

    private int conId;
    private int medId;
    private int quantity;
    private Date date;
    //private Member   member;
//    private Facility facility;
//    private Date     startDate;
//    private Date     endDate;

    public Consumption(int mid, int fid, Date startDate){
        this.medId = mid;
        this.quantity = fid;
        this.date = startDate;
    }

    public Consumption(int conId, int mid, int quan, Date startDate)
    {

        this.conId = conId;
        this.medId = mid;
        this.quantity = quan;
        this.date = startDate;

    }

    public void setMedId(int mId){ this.medId = mId;}

    public void setQuantity(int quan){this.quantity = quan;}

    public void setConDate(Date dt){this.date = dt;}

    public int getConsumptionID() { return this.conId; }

    public int getMedId() { return medId; }

    public int getQuantity() { return quantity; }

    public Date getConDate(){ return date;}

//    public Date getStartDate () {
//        return startDate;
//    }

//    public Date getEndDate () {
//        return endDate;
//    }

//    public boolean overlaps (Booking other) {
//        boolean status = true;
//        if (this.facility != other.getFacility ()) {
//            status = false;
//        } else if (startDate.getTime() >= other.getEndDate().getTime()) {
//            status = false;
//        } else if (other.getStartDate().getTime() >= endDate.getTime()) {
//            status = false;
//        }
//        return (status);
//    }

    private static DateFormat tf = new SimpleDateFormat(TIME_FORMAT);

//    public String toString () {
//        return ("Booking: " + bookingNumber + " - " + facility.getName()
//                + "\r\n(" + member.getMedName() + " " + member.getMedDesc() + ") " + "\r\nFrom: " + tf.format(startDate)
//                + "\r\nTo: " + tf.format(endDate));
//    }

    public void show () {
        System.out.println (this);
    }
}

