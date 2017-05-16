package iss.nus.edu.medipalappln.medipal;

public class Temperature extends Measurement {

    private Double temperature;

    // Constructor
    public Temperature(Integer ID, Double temperature, String measuredOn) {
        this.ID = ID;
        this.temperature = temperature;
        this.measuredOn = measuredOn;
    }

    public Temperature (Double temperature, String measuredOn) {
        this.temperature = temperature;
        this.measuredOn = measuredOn;
    }

    // Methods
    public Integer getID() { return this.ID; }

    public Double getTemperature() { return this.temperature; }

    public String getMeasuredOn() {
        return this.measuredOn;
    }

    public String toString() {
        return this.temperature + " | " +
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
        Temperature other = (Temperature) obj;
        super.equals(other);
        if (this.getID() != other.getID()) {
            return false;
        }
        return true;
    }


    public void setTemperature(Double temperature, String measuredOn, Integer ID) {
        this.temperature = temperature;
        this.measuredOn = measuredOn;
        this.ID = ID;
    }
}
