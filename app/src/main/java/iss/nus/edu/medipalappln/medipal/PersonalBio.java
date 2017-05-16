package iss.nus.edu.medipalappln.medipal;

public class PersonalBio {

    private Integer ID;
    private String name;
    private String DOB;
    private String IDNo;
    private String address;
    private String postalCode;
    private Integer height;
    private String bloodType;

    // Constructor

    public PersonalBio() {
    }

    public PersonalBio(String ID, String name, String DOB, String IDNo,
                       String address, String postalCode, Integer height, String bloodType) {
        //this.ID = Integer.parseInt(ID);
        this.name = name;
        this.DOB = IDNo;
        this.address = address;
        this.postalCode = postalCode;
        this.height = height;
        this.bloodType = bloodType;
    }

    // Methods
    public Integer getID() { return this.ID; }

    public String getIDNo() { return this.IDNo; }

    public String getName() {
        return this.name;
    }

    public String getDOB() {
        return this.DOB;
    }

    public String getAddress() {
        return this.address;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public Integer getHeight() {
        return this.height;
    }

    public String getBloodType() {
        return this.bloodType;
    }

    public String toString() {
        return this.ID + this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        PersonalBio other = (PersonalBio) obj;
        super.equals(other);
        if (this.getID() != other.getID()) {
            return false;
        }
        return true;
    }

    public void setIDNo(String IDno) {
        this.IDNo = IDno;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDOB(String dob) {
        this.DOB = dob;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
}
