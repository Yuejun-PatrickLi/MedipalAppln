package iss.nus.edu.medipalappln.medipal;

/**
 * Created by Raghu on 21/3/17.
 */

public class Emergency {
    private String phone="";
    private String priority="";
    private String desc="";
    private int ID;
    private String name="";

    public Emergency(String name ,String phone, String desc,String priority) {
        this.ID=ID;
        this.name=name;
        this.phone=phone;

        this.desc=desc;
        this.priority=priority;
        //this.name=name;
    }

    public String getPhone() {
        return phone;
    }

    public Emergency getEmergency() {
        Emergency emergency = new Emergency(priority,phone,name,desc);
        return emergency;
    }
    public void setPhoneNumber(int ID,String name ,String phone, String priority,String desc) {
        this.ID=ID;
        this.name=name;
        this.desc=desc;
        this.phone=phone;
        this.priority = priority;
    }

    public String getPriority() {
        return this.priority;
    }

    public String toString() {
        return this.phone + " | " +
                this.desc + "|"+
                this.name + "|"+
                this.priority;
    }

    public Integer getID() {
        return this.ID;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getName() {

        return this.name;
    }
}