import java.util.Date;

/**
 * Created by jlynn on 4/8/2016.
 */
public class Fit {
    public int fitId;
    public Date fitCreationDate;
    public String fitTitle;
    public int fitType;
    public int fitMode;
    public String operatorName;
    public int clientId;
    public String clientLastName;
    public String clientFirstName;
    public int physiology;


    public Fit(int fitId, Date fitCreationDate, String fitTitle, int fitType, int fitMode, String operatorName, int clientId, String clientLastName, String clientFirstName, int physiology) {
        this.fitId = fitId;
        this.fitCreationDate = fitCreationDate;
        this.fitTitle = fitTitle;
        this.fitType = fitType;
        this.fitMode = fitMode;
        this.operatorName = operatorName;
        this.clientId = clientId;
        this.clientLastName = clientLastName;
        this.clientFirstName = clientFirstName;
        this.physiology = physiology;
    }

    public String getOperatorName(){
        return operatorName;
    }

    public Date getFitCreationDate() {
        return fitCreationDate;
    }


}
