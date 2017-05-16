package iss.nus.edu.medipalappln.medipal;

/**
 * Created by root on 23/3/17.
 */

public class Personal {
    private String  ID;
    private String Name;
    private String Dob;
    private String Idno;
    private String Address;
    private String Postcode;
    private String Height;
    private String Bloodtype;
    public String Phone;
    private String Weight;


    //   eid, name, dob,idno,address,postalcode,height,weight,bloodtype,phone)

    public Personal(String id, String name, String dob, String idno, String address, String postalcode, String height, String weight, String bloodtype, String phone) {
        this.ID = id;
        this.Name = name;
        this.Dob = dob;
        this.Idno = idno;
        this.Address = address;
        this.Postcode = postalcode;
        this.Height = height;
        this.Weight=weight;
        this.Bloodtype = bloodtype;
        //this.Phone=phone;
        // eid, name, dob,idno,address,postalcode,height,weight,bloodtype,bloodtype);
    }

    public Personal(int id, String name, String blood, String weight, String height, String phone, String nric, String pincode, String address) {
    }


    public String toString() {
        return this.Name + " | " +
                this.Dob+ "|"+
                this.Idno + "|"+
                this.Address + "|"+
                this.Postcode + "|"+
                this.Height + "|"+
                this.Bloodtype;
    }
    public Personal getPersonal(){
        Personal formData=new Personal(ID,Name,Dob,Idno,Address,Postcode,Height,Weight,Bloodtype,Phone);
        return formData;

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        weight = weight;
    }
    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getDob() {
        return Dob;
    }

    public String getPhone() {
        return Phone;

        //returning 0-preferably returing blood
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public String getIdno() {
        return Idno;
    }

    public void setIdno(String idno) {
        Idno = idno;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPostcode() {
        return Postcode;
    }

    public void setPostcode(String postcode) {
        Postcode = postcode;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public String getBloodtype() {
        return Bloodtype;
    }

    public void setBloodtype(String bloodtype) {
        Bloodtype = bloodtype;
    }
}