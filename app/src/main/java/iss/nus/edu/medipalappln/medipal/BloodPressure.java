package iss.nus.edu.medipalappln.medipal;

public class BloodPressure extends Measurement {

    private Integer systolic;
    private Integer diastolic;

    // Constructor
    public BloodPressure(Integer ID, Integer systolic, Integer diastolic, String measuredOn) {
        this.ID = ID;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.measuredOn = measuredOn;
    }

    public BloodPressure(Integer systolic, Integer diastolic, String measuredOn) {
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.measuredOn = measuredOn;
    }

    // Methods
    public Integer getID() { return this.ID; }

    public Integer getSystolic() {
        return this.systolic;
    }

    public Integer getDiastolic() {
        return this.diastolic;
    }

    public String getMeasuredOn() {
        return this.measuredOn;
    }

    public String toString() {
        return this.systolic + " | " +
                this.diastolic + " | " +
                this.measuredOn;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        BloodPressure other = (BloodPressure) obj;
        super.equals(other);
        if (this.getID() != other.getID()) {
            return false;
        }
        return true;
    }

    public void setBloodPressure(Integer systolic, Integer diastolic, String measuredOn, Integer ID) {
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.measuredOn = measuredOn;
        this.ID = ID;
    }
}
