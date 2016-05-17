import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by jlynn on 4/18/2016.
 */
public class importManager {
    public List<String> lines;
    public Map<Integer, Store> stores = new HashMap<Integer, Store>();

    public importManager() {
        loadData();
        processData();
    }

    public void loadData() {
        try {
            lines = Files.readAllLines(Paths.get("C:\\Users\\jlynn\\Desktop\\testCrmToday.txt"), StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void processData() {
        String currentLine;
        String[] tempLine = new String[27];
        int tempLineCount = 0;

        for(int i = 1; i < lines.size(); i++) {
            currentLine = lines.get(i);


            currentLine = currentLine.substring(1);


            for (String splitValue : currentLine.split("\",\"")) {
                tempLine[tempLineCount] = splitValue;
                tempLineCount++;
            }
            tempLineCount = 0;
            if(!storeExists(tempLine[10])) {
                createStore(tempLine);
            }
            createFit(tempLine);
        }
    }

    public void createStore(String[] tempHolder) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(tempHolder[21]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayList<Fit> fits = new ArrayList<Fit>();
        Store instance = new Store(fits, Integer.parseInt(tempHolder[10]), tempHolder[11], tempHolder[12], tempHolder[13], tempHolder[14], tempHolder[15], tempHolder[16], tempHolder[17], tempHolder[20], date, tempHolder[22]);
        stores.put(Integer.valueOf(tempHolder[10]), instance);
    }

    public boolean storeExists(String storeId){
        return stores.containsKey(Integer.parseInt(storeId));

    }

    public Map getStores(){
        return stores;
    }

    public void createFit(String[] fitCsv) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(fitCsv[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Fit instance = new Fit(Integer.parseInt(fitCsv[0]), date, fitCsv[2], Integer.parseInt(fitCsv[3]), Integer.parseInt(fitCsv[4]), fitCsv[5], Integer.parseInt(fitCsv[6]), fitCsv[7], fitCsv[8], Integer.parseInt(fitCsv[9]));
        stores.get(Integer.parseInt(fitCsv[10])).addFit(instance);
    }
}