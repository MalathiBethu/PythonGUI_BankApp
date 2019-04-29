package commonLibs;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendingMailUsingJava {

	
	 public static void sendMail(String resultfilename)
	   {     
		 //get the session object that contains all the information of host like hostname, username and password
		 //we can get the session object by using the getDefaultInstance(Properties) or getInstance(Properties)
		 //Compose the message
		 
		 //Send the message
		 //The javax.mail.Message class provides methods to compose the message. But it is an abstract class so its subclass javax.mail.internet.MimeMessage class is mostly used.
		 //To create the message, you need to pass session object in MimeMessage class constructor
		 
	     
		 String sender="testmailproject9@gmail.com";
		 String recipient="testprodbang@gmail.com";
		 String password="balajigod@9";
		 
		 
		Properties props = new Properties();    
        props.put("mail.smtp.host", "smtp.gmail.com");    
        props.put("mail.smtp.socketFactory.port", "465");    
        props.put("mail.smtp.socketFactory.class",    
                  "javax.net.ssl.SSLSocketFactory");  
        props.put("mail.smtp.socketFactory.fallback", "true");
        props.put("mail.smtp.auth", "true");    
        props.put("mail.smtp.port", "465");    
		 
		
        Session session = Session.getDefaultInstance(props,    
                new javax.mail.Authenticator() {    
                protected PasswordAuthentication getPasswordAuthentication() {    
                return new PasswordAuthentication(sender,password);  
                }    
               });    
	  
	      try 
	      { 
	         // MimeMessage object. 
	         MimeMessage message = new MimeMessage(session); 
	  
	         // Set From Field: adding senders email to from field. 
	         message.setFrom(new InternetAddress(sender)); 
	  
	         // Set To Field: adding recipient's email to from field. 
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient)); 
	  
	         // Set Subject: subject of the email 
	         message.setSubject("This is Suject"); 
	  
	         // set body of the email. 
	         message.setText("This is a test mail"); 
	  
	         
	         // Create the message part
	         BodyPart messageBodyPart = new MimeBodyPart();

	         // Now set the actual message
	         messageBodyPart.setText("This is message body");
	         
	         
	         
	         // Create a multipar message
	         Multipart multipart = new MimeMultipart();

	         // Set text message part
	         multipart.addBodyPart(messageBodyPart);

	         // Part two is attachment
	         messageBodyPart = new MimeBodyPart();
	         String filename = resultfilename;
	         DataSource source = new FileDataSource(filename);
	         messageBodyPart.setDataHandler(new DataHandler(source));
	         messageBodyPart.setFileName(filename);
	         multipart.addBodyPart(messageBodyPart);

	         // Send the complete message parts
	         message.setContent(multipart);

	         
	         
	         
	         
	         
	         // Send email. 
	         Transport.send(message); 
	         System.out.println("Mail successfully sent"); 
	      } 
	      catch (MessagingException mex)  
	      { 
	         mex.printStackTrace(); 
	      } 
	      
	   }
	
	
	
	
}
