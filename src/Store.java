import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by jlynn on 4/8/2016.
 */
public class Store {
    List<Fit> fits = new ArrayList<Fit>();;
    public int storeId;
    public String storeName;
    public String storeAddress;
    public String storeCity;
    public String storeZipcode;
    public String storeState;
    public String storeCountry;
    public String storePhone;
    public String contactEmail;
    public Date storeResyncDate;
    public String storeSoftwareversion;



    public Store(ArrayList fits, int storeId, String storeName, String storeAddress, String storeCity, String storeZipcode, String storeState, String storeCountry, String storePhone, String contactEmail, Date storeResyncDate, String storeSoftwareversion) {
        this.fits = fits;
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.storeCity = storeCity;
        this.storeZipcode = storeZipcode;
        this.storeState = storeState;
        this.storeCountry = storeCountry;
        this.storePhone = storePhone;
        this.contactEmail = contactEmail;
        this.storeResyncDate = storeResyncDate;
        this.storeSoftwareversion = storeSoftwareversion;
    }

    public void addFit(Fit fit) {
        fits.add(fit);
    }

    public String getStoreName(){
        return storeName;
    }

    public int getTotalFits(){
        return fits.size();
    }

    public List getFitsSince(int startDate, int endDate){
        List<Fit> fitsSince = new ArrayList<Fit>();
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DATE, -startDate);
        Date firstDate = c.getTime();
        c.add(Calendar.DATE, -endDate);
        Date lastDate = c.getTime();

        for(int i = 0; i < fits.size(); i++){
            if(fits.get(i).getFitCreationDate().before(firstDate) && fits.get(i).getFitCreationDate().after(lastDate)){
                fitsSince.add(fits.get(i));
            }
        }
        return fitsSince;
    }

    public List getFitterNames() {
        List<String> fitters = new ArrayList<String>();
        for(int i = 0; i < fits.size(); i++ ){
            String fitterName = fits.get(i).getOperatorName();
            if(!fitters.contains(fitterName)){
                fitters.add(fitterName);
            }
        }
        return fitters;
    }

    public int getFitsDay(int day, int fitter, boolean week) {
        int fitCount = 0;
        int fitWeekCount = 0;
        Calendar p = new GregorianCalendar();
        SimpleDateFormat thDateFormat = new SimpleDateFormat("E MM.dd");
        if(day == 0) {
            p.add(Calendar.DATE, -7);
        } else if(day == 1) {
            p.add(Calendar.DATE, -6);
        } else if(day == 2) {
            p.add(Calendar.DATE, -5);
        } else if(day == 3) {
            p.add(Calendar.DATE, -4);
        } else if(day == 4) {
            p.add(Calendar.DATE, -3);
        } else if(day == 5) {
            p.add(Calendar.DATE, -2);
        } else if(day == 6) {
            p.add(Calendar.DATE, -1);
        }
        Date currDate = p.getTime();

        List<Fit> fitsLastWeek = new ArrayList<Fit>();
        fitsLastWeek = getFitsSince(0, 7);

        for(int i = 0; i < fitsLastWeek.size(); i++){
            if(thDateFormat.format(fitsLastWeek.get(i).getFitCreationDate()).compareTo(thDateFormat.format(currDate)) == 0 ){
                fitWeekCount++;
                if(fitsLastWeek.get(i).getOperatorName().toString().equals(getFitterNames().get(fitter).toString())){
                    fitCount++;
                }
            }
        }
        if(week){
            return fitWeekCount;
        } else {
            return fitCount;
        }
    }

    public int getFitsWeek(int fitter){
        int fitCount = 0;
        List<Fit> fitsLastWeek = new ArrayList<Fit>();
        fitsLastWeek = getFitsSince(0, 7);

        for(int i = 0; i < fitsLastWeek.size(); i++){
                if(fitsLastWeek.get(i).getOperatorName().toString().equals(getFitterNames().get(fitter).toString())){
                    fitCount++;
                }
        }


        return fitCount;
    }

    public String getContactEmail(){
        return contactEmail;
    }

}
