package iss.nus.edu.medipalappln.medipal;

import java.util.Date;

/**
 * Created by niv on 4/8/2016.
 */
public class Medicine {

    public static final String DATE_FORMAT = "d-MMM-yyyy";

    private int medId;
    private String medName;
    private String medDesc;
    private int catId;
    private int remindId;
    private String remindFlag;
    private int quantity;
    private int dosage;
    private int threshold;
    private Date dateIssued;
    private int expireFactor;
    private Reminder reminder;

    public Medicine() {
    }

    public Reminder getReminder() {
        if(reminder == null){
            reminder = new Reminder();
        }
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        if(reminder!=null){
            setRemindId(reminder.getRemindId());
        }
        this.reminder = reminder;
    }

    public String getRemindFlag() {
        return remindFlag;
    }

    public void setRemindFlag(String remindFlag) {
        this.remindFlag = remindFlag;
    }

    public Medicine(String medName, String medDesc, int catId, int remindId, String remindFlag, int quantity, int dosage, int threshold,
                    Date dateIssued, int expireFactor){
        this.medName = medName;
        this.medDesc = medDesc;
        this.catId = catId;
        this.remindId = remindId;
        this.remindFlag = remindFlag;
        this.quantity = quantity;
        this.dosage = dosage;
        this.threshold = threshold;
        this.dateIssued = dateIssued;
        this.expireFactor = expireFactor;
    }

    public Medicine(int medId, String medName, String medDesc, int catId, int remindId, String remindFlag, int quantity, int dosage, int threshold,
                    Date dateIssued, int expireFactor) {
        this.medId = medId;
        this.medName = medName;
        this.medDesc = medDesc;
        this.catId = catId;
        this.remindId = remindId;
        this.remindFlag = remindFlag;
        this.quantity = quantity;
        this.dosage = dosage;
        this.threshold = threshold;
        this.dateIssued = dateIssued;
        this.expireFactor = expireFactor;
    }

    public int getMedId() {
        return medId;
    }

    public String getMedName() {
        return medName;
    }

    public String getMedDesc() {
        return medDesc;
    }

    public int getCatId() {
        return catId;
    }

    public int getRemindId() {
        return remindId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getDosage() {
        return dosage;
    }

    public int getThreshold() {
        return threshold;
    }

    public Date getDateIssued() {
        return dateIssued;
    }

    public int getExpireFactor() {
        return expireFactor;
    }

    public String toString () {
        return (medId + " - " + super.toString ());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + medId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Medicine other = (Medicine) obj;
        super.equals(other);
        if (medId != other.medId)
            return false;
        return true;
    }

    // Added so that Members can be sorted by membership number
    public int compareTo (Medicine other) {
        return (getMedId() - other.getMedId());
    }

    public void setMedId(int medId) {
        this.medId = medId;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public void setMedDesc(String medDesc) {
        this.medDesc = medDesc;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public void setRemindId(int remindId) {
        this.remindId = remindId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public void setDateIssued(Date dateIssued) {
        this.dateIssued = dateIssued;
    }

    public void setExpireFactor(int expireFactor) {
        this.expireFactor = expireFactor;
    }
}
