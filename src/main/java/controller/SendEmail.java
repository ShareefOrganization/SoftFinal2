package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import oracle.jdbc.pool.OracleDataSource;

import javax.mail.*;
import javax.mail.internet.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class SendEmail {
    Session newSession;
    MimeMessage mimeMessage;

    @FXML
    private TextField CustomerID;
    @FXML
    private Label errorMessageLabel;

    @FXML
    void sendEmailToTheCustomer(ActionEvent event) throws SQLException, MessagingException {
        int flag=-1;
        String customerEmail="";
        String query = "SELECT * FROM Customer WHERE ID = '" + CustomerID.getText() + "'" ;


        OracleDataSource orc = new OracleDataSource();
        orc.setURL("jdbc:oracle:thin:@localhost:1521:orcl");
        orc.setUser("software");
        orc.setPassword("123123");
        Connection conn = orc.getConnection();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(query);
//        JOptionPane.showMessageDialog(null,rs.getString(1)+"");
        while (rs.next()){
            if(rs.getString(1).equals(CustomerID.getText()+"")){
                customerEmail=rs.getString(7);
                flag=1;
            }
            else {
                flag =0;
            }
        }


        if(flag==1){
            SendEmail s =new SendEmail();
            s.setup();
            s.draft(customerEmail);
            s.sendemail();
            errorMessageLabel.setText("The email has been sent successfully");

        }
        else{
            errorMessageLabel.setText("Customer Not Found");
        }



    }
    private void setup() {

        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

        Properties properties = System.getProperties();
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        newSession = Session.getDefaultInstance(properties,null);

    }
    private void sendemail() throws NoSuchProviderException, MessagingException {
        // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        String fromUser = "cleaning.services7890@gmail.com";  //Enter sender email id
        String fromUserPassword = "flhnsjbxlxddizsj";  //Enter sender gmail password , this will be authenticated by gmail smtp server
        String emailHost = "smtp.gmail.com";
        Transport transport = newSession.getTransport("smtp");
        transport.connect(emailHost, fromUser, fromUserPassword);
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
    }
    private MimeMessage draft( String email ) throws AddressException, MessagingException {
        // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        String[] emailReceipients = {email};  //Enter list of email recepients
        String emailSubject = "Your Order has been completed";
        String emailBody = "Your order has been completed successfully. You can collect your product from the showroom";
        mimeMessage = new MimeMessage(newSession);

        for (int i =0 ;i<emailReceipients.length;i++)
        {
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceipients[i]));
        }
        mimeMessage.setSubject(emailSubject);


        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(emailBody,"text/html;charset=utf-8");
        MimeMultipart multiPart = new MimeMultipart();
        multiPart.addBodyPart(bodyPart);
        mimeMessage.setContent(multiPart);
        return mimeMessage;

    }

}
