package iss.nus.edu.medipalappln.medipal;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import iss.nus.edu.medipalappln.asynTask.AddBloodPressureTask;
import iss.nus.edu.medipalappln.asynTask.AddCategory;
import iss.nus.edu.medipalappln.asynTask.AddConsumption;
import iss.nus.edu.medipalappln.asynTask.AddEmergency;
import iss.nus.edu.medipalappln.asynTask.AddMedicine;
import iss.nus.edu.medipalappln.asynTask.AddPersonal;
import iss.nus.edu.medipalappln.asynTask.AddPulseTask;
import iss.nus.edu.medipalappln.asynTask.AddReminder;
import iss.nus.edu.medipalappln.asynTask.AddTemperatureTask;
import iss.nus.edu.medipalappln.asynTask.AddWeightTask;
import iss.nus.edu.medipalappln.asynTask.DeleteBloodPressureTask;
import iss.nus.edu.medipalappln.asynTask.DeleteMedicine;
import iss.nus.edu.medipalappln.asynTask.DeletePulseTask;
import iss.nus.edu.medipalappln.asynTask.DeleteTemperatureTask;
import iss.nus.edu.medipalappln.asynTask.DeleteWeightTask;
import iss.nus.edu.medipalappln.asynTask.GetCategory;
import iss.nus.edu.medipalappln.asynTask.GetMaxReminderId;
import iss.nus.edu.medipalappln.asynTask.GetMedicine;
import iss.nus.edu.medipalappln.asynTask.GetReminder;
import iss.nus.edu.medipalappln.asynTask.ListCategory;
import iss.nus.edu.medipalappln.asynTask.ListConsumption;
import iss.nus.edu.medipalappln.asynTask.ListEmergency;
import iss.nus.edu.medipalappln.asynTask.ListMedicine;
import iss.nus.edu.medipalappln.asynTask.ListPersonal;
import iss.nus.edu.medipalappln.asynTask.UpdateCategory;
import iss.nus.edu.medipalappln.asynTask.UpdateMedicine;
import iss.nus.edu.medipalappln.asynTask.ViewBloodPressureTask;
import iss.nus.edu.medipalappln.asynTask.ViewPulseTask;
import iss.nus.edu.medipalappln.asynTask.ViewTemperatureTask;
import iss.nus.edu.medipalappln.asynTask.ViewWeightTask;
import iss.nus.edu.medipalappln.dao.EmergencyDataBaseAdapter;
import iss.nus.edu.medipalappln.dao.PerBioDataBaseAdapter;

public class User {

    private final static String TAG = "User";

    //private ArrayList<HealthBio> healthBio;
    private PersonalBio personalBio;
    private ArrayList<Measurement> measurements;
    private ArrayList<BloodPressure> bloodPressures;
    private ArrayList<Pulse> pulses;
    private ArrayList<Temperature> temperatures;
    private ArrayList<Weight> weights;
    //private ArrayList<Appointment> appointment;
    private ArrayList<Emergency> emergency;

    private ArrayList<Medicine> medicines;
    private Medicine medicine;

    private ViewBloodPressureTask viewBloodPressureTask;
    private AddBloodPressureTask addBloodPressureTask;
    private DeleteBloodPressureTask deleteBloodPressureTask;

    private ViewPulseTask viewPulseTask;
    private AddPulseTask addPulseTask;
    private DeletePulseTask deletePulseTask;

    private ViewTemperatureTask viewTemperatureTask;
    private AddTemperatureTask addTemperatureTask;
    private DeleteTemperatureTask deleteTemperatureTask;

    private ViewWeightTask viewWeightTask;
    private AddWeightTask addWeightTask;
    private DeleteWeightTask deleteWeightTask;

    private AddEmergency addEmergency;
    private ListEmergency listEmergency;

    private AddPersonal addPersonal;
    private ListPersonal listPersonal;

    private EmergencyDataBaseAdapter emergencyDataBaseAdapter;


    //MEDICINE VALUES
    private ArrayList<Category> facilities;
    private Category category;
    private Reminder reminder;
    private ArrayList<Consumption> consumptions;

    private AddMedicine taskMemberAdd;
    private ListMedicine taskMemberList;
    private AddCategory taskFacilityAdd;
    private ListCategory taskFacilityList;
    private AddConsumption taskBookingAdd;
    private ListConsumption taskBookingList;

    private UpdateCategory taskFacilityUpdate;
    private UpdateMedicine taskUpdateMedicine;
    private GetMedicine taskGetMedicine;

    private DeleteMedicine taskDeleteMedicine;
    private GetCategory taskGetCategory;

    private AddReminder taskAddReminder;
    private GetReminder taskGetReminder;

    private GetMaxReminderId taskGetMaxReminderId;

    public User() {
        personalBio = null;
        //Session session=new Session(this);
        //healthBio = new ArrayList<HealthBio> ();
        //medicine = new ArrayList<Medicine> ();
        //measurements = new ArrayList<Measurement> ();
        bloodPressures = new ArrayList<BloodPressure>();
        pulses = new ArrayList<Pulse>();
        weights = new ArrayList<Weight>();
        temperatures = new ArrayList<Temperature>();
        //consumption = new ArrayList<Consumption> ();
        //appointment = new ArrayList<Appointment> ();
        emergency = new ArrayList<Emergency> ();
    }

    //Personal Bio
    public boolean createPersonalBio(Context context, PersonalBio personalBio) {
        PerBioDataBaseAdapter perBioDataBaseAdapter = new PerBioDataBaseAdapter(context);
        long result = perBioDataBaseAdapter.add(personalBio);

        if (result != -1) {
            this.personalBio = personalBio;
            Log.i(TAG, "User registration successful");
            return true;
        }
        else {
            Log.i(TAG, "User has registered");
            return false;
        }
    }

    public PersonalBio getPersonalBio(Context context) {
        PerBioDataBaseAdapter perBioDataBaseAdapter = new PerBioDataBaseAdapter(context);
        PersonalBio p = perBioDataBaseAdapter.getSingleEntry();

        return p;
    }

    public boolean updatePersonalBio(Context context, PersonalBio personalBio) {
        PerBioDataBaseAdapter perBioDataBaseAdapter = new PerBioDataBaseAdapter(context);

        if(perBioDataBaseAdapter.update(personalBio) != -1)
            return true;
        else
            return false;
    }

    public boolean deletePersonalBio(Context context, String key) {
        PerBioDataBaseAdapter perBioDataBaseAdapter = new PerBioDataBaseAdapter(context);

        if(perBioDataBaseAdapter.delete(key) != -1)
            return true;
        else
            return false;
    }

    //ICE
    public ArrayList<Emergency> getEmergency(Context context) {
        EmergencyDataBaseAdapter emergencyDataBaseAdapter = new EmergencyDataBaseAdapter(context);
        ArrayList<Emergency> emergencies = emergencyDataBaseAdapter.get();
        if (emergencies == null) {
            emergencies = new ArrayList<Emergency>();
        }

        return emergencies;
    }

    public Emergency addEmergency(String Name,
                                  String Phone, String Priority, String Desc) {
        //numMembers++;
        Emergency m = new Emergency(Name, Phone, Priority, Desc);
        emergency.add(m);
        return m;
    }

    public Emergency addEmergency(String Name,
                                  String Phone, String Priority, String Desc, Context context) {
        //numMembers++;
        Emergency em = new Emergency(Name, Phone, Priority, Desc);
        addEmergency = new AddEmergency(context);
        addEmergency.execute(em);
        return em;
    }


    //Measurement: blood pressure
    public BloodPressure getBloodPressure(Integer id) {
        Iterator<BloodPressure> i = bloodPressures.iterator();
        while (i.hasNext()) {
            BloodPressure bp = i.next();
            if (bp.getID() == id) {
                return bp;
            }
        }

        return null;
    }

    //Measurement: latest reading for dashboard
    public String[] getLatestBloodPressure() {
        String bp[] = null;
        Integer lastest = 0, sys = 0, dias = 0;

        if (bloodPressures.isEmpty()) {
            bp[0] = "No systolic record";
            bp[1] = "No diastolic record";
        }
        else {
            lastest = bloodPressures.size() - 1;
            sys = bloodPressures.get(lastest).getSystolic();
            dias = bloodPressures.get(lastest).getDiastolic();

            bp[0] = sys.toString();
            bp[1] = dias.toString();
        }
        return bp;
    }

    public String getLatestPulse() {
        String p = null;
        Integer lastest = 0, pulse = 0;

        if (pulses.isEmpty()) {
            p = "No pulse record";
        }
        else {
            lastest = pulses.size() - 1;
            pulse = pulses.get(lastest).getPulse();

            p = pulse.toString();
        }
        return p;
    }

    public String getLatestTemperature() {
        String t = null;
        Integer lastest = 0;
        Double temp = 0.0;

        if (temperatures.isEmpty()) {
            t = "No temperature record";
        }
        else {
            lastest = temperatures.size() - 1;
            temp = temperatures.get(lastest).getTemperature();

            t = temp.toString();
        }
        return t;
    }

    public String getLatestWeight() {
        String w = null;
        Integer lastest = 0, weight = 0;

        if (weights.isEmpty()) {
            w = "No weight record";
        }
        else {
            lastest = weights.size() - 1;
            weight = weights.get(lastest).getWeight();

            w = weight.toString();
        }
        return w;
    }

    public List<BloodPressure> getBloodPressure (Context context) {
        viewBloodPressureTask = new ViewBloodPressureTask(context);
        viewBloodPressureTask.execute((Void) null);

        try {
            bloodPressures = viewBloodPressureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (bloodPressures == null) {
            bloodPressures = new ArrayList<BloodPressure> ();
        }

        return new ArrayList<BloodPressure>(bloodPressures);
    }

    public void addBloodPressure (Context context, Integer systolic, Integer diastolic, String measuredOn) {
        BloodPressure bp = new BloodPressure(systolic, diastolic, measuredOn);
        addBloodPressureTask = new AddBloodPressureTask(context);
        addBloodPressureTask.execute(bp);
    }

    public void deleteBloodPressure (Context context, Integer id) {
        BloodPressure bp = getBloodPressure(id);
        if (bp != null) {
            bloodPressures.remove(bp);
        }
        deleteBloodPressureTask = new DeleteBloodPressureTask(context);
        deleteBloodPressureTask.execute(bp);
    }

    //Measurement: Pulse
    public Pulse getPulse(Integer id) {
        Iterator<Pulse> i = pulses.iterator();
        while (i.hasNext()) {
            Pulse pulse = i.next();
            if (pulse.getID().equals(id)) {
                return pulse;
            }
        }

        return null;
    }

    public List<Pulse> getPulse (Context context) {
        viewPulseTask = new ViewPulseTask(context);
        viewPulseTask.execute((Void) null);

        try {
            pulses = viewPulseTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (pulses == null) {
            pulses = new ArrayList<Pulse> ();
        }

        return new ArrayList<Pulse>(pulses);
    }

    public void addPulse (Context context, Integer pulse, String measuredOn) {
        Pulse p = new Pulse(pulse, measuredOn);
        addPulseTask = new AddPulseTask(context);
        addPulseTask.execute(p);
    }

    public void deletePulse (Context context, Integer id) {
        Pulse p = getPulse(id);
        if (p != null) {
            pulses.remove(p);
        }
        deletePulseTask = new DeletePulseTask(context);
        deletePulseTask.execute(p);
    }

    //Measurement: Temperature
    public Temperature getTemperature(Integer id) {
        Iterator<Temperature> i = temperatures.iterator();
        while (i.hasNext()) {
            Temperature t = i.next();
            if (t.getID().equals(id)) {
                return t;
            }
        }

        return null;
    }

    public List<Temperature> getTemperature (Context context) {
        viewTemperatureTask = new ViewTemperatureTask(context);
        viewTemperatureTask.execute((Void) null);

        try {
            temperatures = viewTemperatureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (temperatures == null) {
            temperatures = new ArrayList<Temperature> ();
        }

        return new ArrayList<Temperature>(temperatures);
    }

    public void addTemperature (Context context, Double temperature, String measuredOn) {
        Temperature bp = new Temperature(temperature, measuredOn);
        addTemperatureTask = new AddTemperatureTask(context);
        addTemperatureTask.execute(bp);
    }

    public void deleteTemperature (Context context, Integer id) {
        Temperature t = getTemperature(id);
        if (t != null) {
            pulses.remove(t);
        }
        deleteTemperatureTask = new DeleteTemperatureTask(context);
        deleteTemperatureTask.execute(t);
    }


    //Measurement: Weight
    public Weight getWeight(Integer id) {
        Iterator<Weight> i = weights.iterator();
        while (i.hasNext()) {
            Weight w = i.next();
            if (w.getID().equals(id)) {
                return w;
            }
        }

        return null;
    }

    public List<Weight> getWeight (Context context) {
        viewWeightTask = new ViewWeightTask(context);
        viewWeightTask.execute((Void) null);

        try {
            weights = viewWeightTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (weights == null) {
            weights = new ArrayList<Weight> ();
        }

        return new ArrayList<Weight>(weights);
    }

    public void addWeight (Context context, Integer weight, String measuredOn) {
        Weight w = new Weight(weight, measuredOn);
        addWeightTask = new AddWeightTask(context);
        addWeightTask.execute(w);
    }

    public void deleteWeight (Context context, Integer id) {
        Weight w = getWeight(id);
        if (w != null) {
            weights.remove(w);
        }
        deleteWeightTask = new DeleteWeightTask(context);
        deleteWeightTask.execute(w);
    }

    public Emergency getEmergency (String priority,Context context) {
        // Emergency e=new Emergency(context);
        emergencyDataBaseAdapter=new EmergencyDataBaseAdapter(context);

        ArrayList<Emergency>em=emergencyDataBaseAdapter.get();
        Iterator<Emergency> i=em.iterator();

        while(i.hasNext()){
            Emergency e= i.next();
            if(e.getPriority().equals(priority)){
                return e;
            }
        }


        return  null;
        //return new ArrayList<Emergency>(emergency);
    }
    public Emergency addEmergency (int ID, String Name,
                                   String Phone,String Priority,String Desc) {
        //numMembers++;
        //Emergency m = new Emergency (ID,Name,Phone,Priority,Desc);
        Emergency m = new Emergency (Name,Phone,Priority,Desc);
        emergency.add (m);
        return m;
    }
    public Emergency addEmergency (int ID, String Name,
                                   String Phone,String Priority,String Desc,Context context) {
        //numMembers++;
        //Emergency em = new Emergency (ID,Name,Phone,Priority,Desc);
        Emergency em = new Emergency (Name,Phone,Priority,Desc);
        addEmergency=new AddEmergency(context);
        addEmergency.execute(em);
        return em;
    }

    public HashMap<Category,ArrayList<Medicine>> setHashCategoryMedicine(Context context){
        HashMap<Category, ArrayList<Medicine>> hashCM = new HashMap<>();
        ArrayList<Category> tCategories = new ArrayList<>();
        tCategories.addAll(getFacilities(context));
        ArrayList<Medicine> tmedicines = new ArrayList<>();
        tmedicines.addAll(getMedicines(context));

        for (Category c:tCategories){
            ArrayList<Medicine> medHash = new ArrayList<>();
            medHash.clear();
            for(Medicine m:tmedicines){
                if(c.getCatId() == m.getCatId())
                    medHash.add(m);
            }
            hashCM.put(c,medHash);
        }
        return hashCM;
    }

    public HashMap<Medicine,ArrayList<Consumption>> setHashMedicineConsumption(Context context){
        ArrayList<Medicine> tmedicines = new ArrayList<>();
        ArrayList<Consumption> tconsumptions = new ArrayList<>();
        HashMap<Medicine,ArrayList<Consumption>> hashMC = new HashMap<>();
        tmedicines.addAll(getMedicines(context));
        tconsumptions.addAll(getConsumptions(context));

        for(Medicine m:tmedicines){
            ArrayList<Consumption> conHash = new ArrayList<>();
            conHash.clear();
            for(Consumption c:tconsumptions){
                if(m.getMedId() == c.getMedId())
                    conHash.add(c);
            }
            hashMC.put(m,conHash);
        }
        return hashMC;
    }

    public HashMap<Category,ArrayList<Consumption>> setHashCategoryConsumption(Context context){
        ArrayList<Consumption> valueCon = new ArrayList<>();
        ArrayList<Medicine> valueMed = new ArrayList<>();
        HashMap<Category,ArrayList<Consumption>> hashCC = new HashMap<>();
        HashMap<Category,ArrayList<Medicine>> hashCM = setHashCategoryMedicine(context);
        HashMap<Medicine,ArrayList<Consumption>> hashMC = setHashMedicineConsumption(context);
        Iterator iterCM = hashCM.entrySet().iterator();

        while (iterCM.hasNext()){
            valueMed.clear();
            ArrayList<Consumption> consumptionsByCategory = new ArrayList<>();
            Map.Entry entryCM = (Map.Entry)iterCM.next();
            Category keyCat = (Category)entryCM.getKey();
            valueMed = (ArrayList<Medicine>)entryCM.getValue();

            for (Medicine m : valueMed) {
                valueCon.clear();
                Iterator iterMC = hashMC.entrySet().iterator();
                while(iterMC.hasNext()){
                    Map.Entry entryMC = (Map.Entry)iterMC.next();
                    Medicine keyMed = (Medicine)entryMC.getKey();
                    if(keyMed.getMedId() == m.getMedId()){
                        ArrayList<Consumption> valueC = (ArrayList<Consumption>)entryMC.getValue();
                        //valueCon.addAll(valueC);
                        consumptionsByCategory.addAll(valueC);
                    }
                }
            }
            hashCC.put(keyCat, (ArrayList<Consumption>) consumptionsByCategory.clone());
        }
        return hashCC;
    }

    public void updateFacility(int id, String name, String code, String description, String reminder, Context context){
        Category f = new Category(id, name, code, description, reminder);
        taskFacilityUpdate = new UpdateCategory(context);
        taskFacilityUpdate.execute(f);

    }

    public void updateMember(Medicine medicine, Context context){
        taskUpdateMedicine = new UpdateMedicine(context);
        taskUpdateMedicine.execute(medicine);

    }

    public Category getFacility (int facilityNum, Context context) {
        taskGetCategory = new GetCategory(context);
        taskGetCategory.execute(facilityNum);
        try {
            category = taskGetCategory.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (category == null) { category = new Category(); }
        // SQLite - End

        return category;
    }

    public Medicine getMember (int memberNum, Context context) {
        taskGetMedicine = new GetMedicine(context);
        taskGetMedicine.execute(memberNum);
        try {
            medicine = taskGetMedicine.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (medicine == null) { medicine = new Medicine(); }
        // SQLite - End

        return medicine;
    }

    public List<Medicine> getMedicines (Context context) {
        // SQLite - Start
        taskMemberList = new ListMedicine(context);
        taskMemberList.execute((Void) null);
        try {
            medicines = taskMemberList.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (medicines == null) { medicines = new ArrayList<Medicine>(); }
        // SQLite - End

        return new ArrayList<Medicine>(medicines);
    }

    public Medicine addMember(String medName, String medDesc, int catId, int remindID, String reminderFlag,
                              int quantity, int dosage, int threshold, Date dateIssued, int expiryFactor) {
        //numMembers++;
        Log.d("Niv", "inserting medicine");
        Medicine m = new Medicine(medName, medDesc, catId, remindID, reminderFlag, quantity, dosage, threshold, dateIssued, expiryFactor);
        medicines.add (m);
        return m;
    }

    // SQLite
    public Medicine addMember (String medName, String medDesc, int catId, int remindID, String reminderFlag,
                               int quantity, int dosage, int threshold, Date dateIssued, int expiryFactor, Context context) {
        Medicine m = new Medicine(medName, medDesc, catId, remindID,reminderFlag, quantity, dosage, threshold, dateIssued, expiryFactor);

        taskMemberAdd = new AddMedicine(context);
        taskMemberAdd.execute(m);
        return m;
    }

    public void removeMember (Medicine m, Context context) {
/*        Medicine m = getMedicine (memberNum);
        if (m != null) {
            medicines.remove (m);
        }*/
        taskDeleteMedicine = new DeleteMedicine(context);
        taskDeleteMedicine.execute(m);

    }

    public void showMembers () {
        Iterator<Medicine> i = medicines.iterator ();
        while (i.hasNext ()) {
            i.next();
        }
    }

    /*
    public Category getCategory (String name) {

        return facilities.get (name);
    }*/



    public List<Category> getFacilities (Context context) {
        // SQLite - Start
        taskFacilityList = new ListCategory(context);
        taskFacilityList.execute((Void) null);
        try {
            facilities = taskFacilityList.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (facilities == null) { facilities = new ArrayList<Category>(); }
        // SQLite - End
        return (new ArrayList<Category>(facilities));
    }

/*    public void addFacility (String name, String description, String code, String reminder) {
        if (name == null) {
            return;
        }
        Category f = new Category(name, description, code, reminder);
        facilities1.put (name, f);
    }*/

    // SQLite
    public Category addFacility (String name, String description, String code, String reminder, Context context) {
        if (name == null) {
            return null;
        }
        Category f = new Category(name, description, code, reminder);


        taskFacilityAdd = new AddCategory(context);
        taskFacilityAdd.execute(f);
        return f;
    }

    public void removeFacility (String name) {

        facilities.remove (name);
    }

    /*
    public void showFacilities () {
        Iterator<Category> i = getFacilities().iterator ();
        while (i.hasNext ()) {
            i.next().show ();
        }
    }
    */

    public int addReminder(String rfreq, Date rStTime, String rInterval, Context context){
        Reminder r = new Reminder(rfreq, rStTime, rInterval);
        int remId = 0;
        taskAddReminder = new AddReminder(context);
        taskAddReminder.execute(r);
        try {
            remId = taskAddReminder.get().intValue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("Niv_1", String.valueOf(remId));
        return remId;
    }

    public Reminder getReminder(int remId, Context context){
        taskGetReminder = new GetReminder(context);
        taskGetReminder.execute(remId);

        try {
            reminder = taskGetReminder.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (reminder == null) { reminder = new Reminder(); }
        // SQLite - End

        return (reminder);
    }

    public Consumption addConsumption (int memberNumber, int facilityNumber, Date startDate, Context context)
    {
        //bookings1.addBooking (getMedicine (memberNumber), getCategory (facName), startDate, endDate);

        Consumption b = new Consumption(memberNumber, facilityNumber, startDate);
        taskBookingAdd = new AddConsumption(context);
        taskBookingAdd.execute(b);
        return b;
    }


    public void removeConsumption (Consumption consumption) {

        consumptions.remove(consumption);
    }

    public ArrayList<Consumption> getConsumptions (Context context) {
        //return bookings.getBookings (getCategory (facName), startDate, endDate);

        // SQLite - Start
        taskBookingList = new ListConsumption(context);
        taskBookingList.execute((Void) null);
        try {
            consumptions = taskBookingList.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (consumptions == null) { consumptions = new ArrayList<Consumption>(); }
        // SQLite - End
        return (new ArrayList<Consumption>(consumptions));
    }

    public ArrayList<Consumption> getConsumptionsByCategory(Category c,Context context){
        ArrayList<Consumption> consumptions;
        HashMap<Category,ArrayList<Consumption>> hashCC= setHashCategoryConsumption(context);
        if (hashCC.get(c)== null) {
            consumptions = new ArrayList<>();
        }
        else {consumptions = hashCC.get(c);}
        return  consumptions;
    }



    public ArrayList<Consumption> getConsumptionsByMedicine(Medicine m,Context context){
        ArrayList<Consumption> consumptions = new ArrayList<>();
        HashMap<Medicine,ArrayList<Consumption>> hashMC= setHashMedicineConsumption(context);
        Iterator iterMC = hashMC.entrySet().iterator();
        while(iterMC.hasNext()) {
            Map.Entry entryMC = (Map.Entry) iterMC.next();
            Medicine keyMed = (Medicine) entryMC.getKey();
            ArrayList<Consumption> valueCon = (ArrayList<Consumption>) entryMC.getValue();
            if (keyMed.getMedId() == m.getMedId()) {
                consumptions.addAll(valueCon);
                break;
            }
        }
        return  consumptions;
    }

    public void show () {
        System.out.println ("Current Members:");
        showMembers ();
        System.out.println ();
        System.out.println ("Facilities:");
        //showFacilities ();
    }

    public int getMaxReminderId(Context context) {
        int maxRemindId = -1;
        taskGetMaxReminderId = new GetMaxReminderId(context);
        taskGetMaxReminderId.execute((Void) null);
        try {
            maxRemindId = taskGetMaxReminderId.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return maxRemindId;
    }
  
}
