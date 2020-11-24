import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;

/**
 * MailHelper class is used for sending Email Notification purpose.
 * <p>
 * A default email account is used:
 * </p>
 * <p>
 * email = cz2002testacc@gmail.com
 * </p>
 *<p>
 * password = thisiscz2002
 *</p>
 */

public class MailHelper
{

    private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    private static String recipientEmail;
    private static String message;
    private static String emailSubject;

    public static String getRecipientEmail() {
        return recipientEmail;
    }

    /**
     *
     * @param recipientEmail set the recipient email address
     */
    public static void setRecipientEmail(String recipientEmail) {
        MailHelper.recipientEmail = recipientEmail;
    }

    public static String getMessage() {
        return message;
    }

    /**
     *
     * @param message set the email body
     */
    public static void setMessage(String message) {
        MailHelper.message = message;
    }

    public static String getEmailSubject() {
        return emailSubject;
    }

    /**
     *
     * @param emailSubject set the subject title of the email
     */
    public static void setEmailSubject(String emailSubject) {
        MailHelper.emailSubject = emailSubject;
    }

    public MailHelper()
    {

    }

    public MailHelper(String recipientEmail, String message) {
        this.recipientEmail = recipientEmail;
        this.message = message;
    }

    /**
     * email host - gmail
     * email port - 465
     * @return return the mail properties setting
     */
    private static Properties init(){
        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        //props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");

        return props;
    }

    /**
     * The final call to trigger the sending action after setRecipientEmail,setMessage,setEmailSubject are done.
     */
     public void SendEmail(){
        //Dummy Email Bot
        String from = "cz2002testacc@gmail.com";
        String emailPW = "thisiscz2002";

        try{
            Session session = Session.getDefaultInstance(init(), new Authenticator(){
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, emailPW);
                }});

            // -- Create a new message --
            Message msg = new MimeMessage(session);

            // -- Set the FROM and fields --
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(getRecipientEmail(),false));

            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = myDateObj.format(myFormatObj);

            msg.setSubject(getEmailSubject());
            msg.setText(getMessage());
            msg.setSentDate(new Date());
            Transport.send(msg);

            //System.out.println(formattedDate + " - Message sent.");
        }catch (MessagingException e){
            System.out.println("Error: " + e);
        }
    }

    public static void main(String [] args){

        MailHelper test = new MailHelper("chunfei96@hotmail.com","This is testing email.");
        test.setEmailSubject("CZ2002");
        test.SendEmail();
    }


}

