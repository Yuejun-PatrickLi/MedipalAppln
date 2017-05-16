package iss.nus.edu.medipalappln.medipal;

public class Pulse extends Measurement {

    protected Integer pulse;

    // Constructor
    public Pulse(Integer ID, Integer pulse, String measuredOn) {
        this.ID = ID;
        this.pulse = pulse;
        this.measuredOn = measuredOn;
    }

    public Pulse (Integer pulse, String measuredOn) {
        this.pulse = pulse;
        this.measuredOn = measuredOn;
    }

    // Methods
    public Integer getID() { return this.ID; }

    public Integer getPulse() { return this.pulse; }

    public String getMeasuredOn() {
        return this.measuredOn;
    }

    public String toString() {
        return this.pulse + " | " +
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
        Pulse other = (Pulse) obj;
        super.equals(other);
        if (this.getID() != other.getID()) {
            return false;
        }
        return true;
    }

    public void setPulse(Integer pulse, String measuredOn, Integer ID) {
        this.pulse = pulse;
        this.measuredOn = measuredOn;
        this.ID = ID;
    }

}
