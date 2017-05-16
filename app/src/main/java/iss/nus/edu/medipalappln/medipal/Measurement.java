package iss.nus.edu.medipalappln.medipal;

public class Measurement {
    String decimalPattern = "#####.##";

    protected Integer ID;
    protected String measuredOn;

    // Constructor

    public Measurement() {
    }

    public Measurement(Integer systolic, Integer diastolic, Integer pulse, Integer temperature,
                       Double weight, String measuredOn) {
        //this.systolic = systolic;
        //this.diastolic = diastolic;
        //this.pulse = pulse;
        //this.temperature = temperature;
        //this.weight = weight;
        this.measuredOn = measuredOn;
    }

    // Methods
    public Integer getID() { return this.ID; }

    /*
    public Integer getSystolic() {
        return this.systolic;
    }

    public Integer getDiastolic() {
        return this.diastolic;
    }

    public Integer getPulse() {
        return this.pulse;
    }

    public Double getWeight() {
        return this.weight;
    }

    public Integer getTemperature() { return this.temperature; }
    */
    public String getMeasuredOn() {
        return this.measuredOn;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Measurement other = (Measurement) obj;
        super.equals(other);
        if (this.getID() != other.getID()) {
            return false;
        }
        return true;
    }

    /*
    public String toString() {
        return this.systolic + " | " +
                this.diastolic + " | " +
                this.pulse + this.temperature + " | " +
                this.weight + this.measuredOn;
    }*/

}