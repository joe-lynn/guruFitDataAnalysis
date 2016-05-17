import com.sun.xml.internal.org.jvnet.mimepull.MIMEMessage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.*;
//import java.util.Map;
import java.util.Properties;

/**
 * Created by jlynn on 4/29/2016.
 */
public class mailManager {
    //public Map stores;
    public Map<Integer, Store> stores = new HashMap<Integer, Store>();

    public mailManager(){
        importManager imported = new importManager();
        stores = imported.getStores();
        for (Store value : stores.values()) {
            sendMessage(value);
            value.getFitsSince(0, 7);
        }

    }

    public void sendMessage(Store currStore){
        Properties mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");

        Session getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        MimeMessage generateMailMessage = new MimeMessage(getMailSession);

        Date todayDate = new Date();

        try {
            generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(currStore.getContactEmail()));
            generateMailMessage.setSubject("GURU Weekly Report" + todayDate);
            String emailBody = htmlGenerator(currStore);
            generateMailMessage.setContent(emailBody, "text/html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        try {
            Transport transport = getMailSession.getTransport("smtp");
            transport.connect("smtp.gmail.com", "joes.dealz@gmail.com", "Scammer21");
            transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
            transport.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }

    public String htmlGenerator(Store currStore){
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DATE, -7);
        Date currDate = c.getTime();
        SimpleDateFormat thDateFormat = new SimpleDateFormat("E MM.dd");
        List<String> fitters = new ArrayList<String>();
        fitters = currStore.getFitterNames();

        String  messageHTML = "";
        //HEAD
        messageHTML += "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">" + "\n";
        messageHTML += "<html xmlns=\"http://www.w3.org/1999/xhtml\">" + "\n";
        messageHTML += "<head>" + "\n";
        messageHTML += "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />" + "\n";
        messageHTML += "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>" + "\n";
        messageHTML += "<title>GURU Weekly update</title>" + "\n";
        messageHTML += "<style type=\"text/css\">" + "\n";
        messageHTML += "</head>" + "\n";
        messageHTML += "<body>" + "\n";

        //Title and store info
        messageHTML += "<h1 style=\"color: blue text-align: center;\" >" + currStore.getStoreName() + "</h1>" + "<br>" + "\n";

        //TABLE START
        messageHTML += "<table style=\"border: 1px solid black; \">" + "\n";
        //ROW 1
        messageHTML += "<tr>" + "\n";
        messageHTML += "<th></th>" + "\n";
        for(int i = 0; i < 7; i++){
            messageHTML += "<th scope=\"col\" style=\"border: 1px solid black; \">" + thDateFormat.format(currDate) + "</th>" + "\n";
            c.add(Calendar.DATE, 1);
            currDate = c.getTime();
        }
        messageHTML += "<th scope=\"col\" style=\"border: 1px solid black; \">" + "Total" + "</th>" + "\n";
        messageHTML += "</tr>" + "\n";
        //FIRST COLUMN AND ALL COLUMNS
        messageHTML += "<tr>" + "\n";
        messageHTML += "<td></td>" + "\n";
        for(int i = 0; i < fitters.size(); i++){
            messageHTML += "<tr>" + "\n";
            messageHTML += "<th scope=\"row\" style=\"border: 1px solid black;\">" + fitters.get(i) + "</th>" + "\n";
            for(int j = 0; j < 7; j++){
                messageHTML += "<td style=\"border: 1px solid black;\">"+ currStore.getFitsDay(j, i, false) + "</td>" + "\n";
            }
            messageHTML += "<td style=\"border: 1px solid black;\">"+ currStore.getFitsWeek(i) + "</td>" + "\n";
            messageHTML += "</tr>" + "\n";
        }

        //LAST ROW
        messageHTML += "<tr>" + "\n";
        messageHTML += "<td>" + "Total" + "</td>";
        for(int i = 0; i < 7;i++){
            messageHTML += "<td style=\"border: 1px solid black;\">" + currStore.getFitsDay(i, 0, true) +"</td>";
        }
        messageHTML += "<td style=\"border: 1px solid black;\">" + currStore.getTotalFits() + "</td>" + "\n";
        messageHTML += "</tr>" + "\n";
        messageHTML += "</table>" + "\n";





        messageHTML += "</body>" + "\n";
        messageHTML += "</html>";
        return messageHTML;
    }

}
