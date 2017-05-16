package iss.nus.edu.medipalappln.medipal;

public class Weight extends Measurement {

    private Integer weight;

    // Constructor
    public Weight(Integer ID, Integer weight, String measuredOn) {
        this.ID = ID;
        this.weight = weight;
        this.measuredOn = measuredOn;
    }

    public Weight (Integer weight, String measuredOn) {
        this.weight = weight;
        this.measuredOn = measuredOn;
    }

    // Methods
    public Integer getID() { return this.ID; }

    public Integer getWeight() { return this.weight; }

    public String getMeasuredOn() {
        return this.measuredOn;
    }

    public String toString() {
        return this.weight + " | " +
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
        Weight other = (Weight) obj;
        super.equals(other);
        if (this.getID() != other.getID()) {
            return false;
        }
        return true;
    }

    public void setWeight(Integer weight, String measuredOn, Integer ID) {
        this.weight = weight;
        this.measuredOn = measuredOn;
        this.ID = ID;
    }
}
