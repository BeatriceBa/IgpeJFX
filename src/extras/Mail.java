package extras;
import java.util.ArrayList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;    
import javax.mail.internet.*;    

public class Mail{ 
	
    public static void send(String from, String password, String to, String sub, String msg){  
    	//Get properties object, protocol used SSL on port 465 
    	Properties props = new Properties();    
    	props.put("mail.smtp.host", "smtp.gmail.com");    
    	props.put("mail.smtp.socketFactory.port", "465");    
    	props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");    
    	props.put("mail.smtp.auth", "true");    
    	props.put("mail.smtp.port", "465");  
    	
        //Authentication
    	Session session = Session.getDefaultInstance(props,    
    	new javax.mail.Authenticator() { 
    		protected PasswordAuthentication getPasswordAuthentication() {    
    		return new PasswordAuthentication(from,password);  
           	}    
    	});    
        //Composing message (without attachements)    
    	try {    
    		MimeMessage message = new MimeMessage(session);    
    		message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));    
    		message.setSubject(sub);    
    		message.setText(msg);    
    		//send message  
    		Transport.send(message);      		    
    	} catch (MessagingException e) {throw new RuntimeException(e);}         
    }  
    
    public void mailWithAttachment (String from, String password, String to, String sub, String filename) {

    	Properties props = new Properties();    
    	props.put("mail.smtp.host", "smtp.gmail.com");    
        props.put("mail.smtp.socketFactory.port", "465");    
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");    
        props.put("mail.smtp.auth", "true");    
        props.put("mail.smtp.port", "465");    
        
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {    
        	protected PasswordAuthentication getPasswordAuthentication() {    
        		return new PasswordAuthentication(from,password);  
        	}    
        });    
        //Composing the message (with attachment)    
        try {    
	        Message message = new MimeMessage(session);    
	        message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));    
	        message.setSubject(sub); 
	         
	         //Create the message part
	         BodyPart messageBodyPart = new MimeBodyPart();
	
	         // Now set the actual message
	         messageBodyPart.setText("Thank you for your purchase. \n This is an automatic e-mail, do not reply.");
	
	         //Create a multipart message
	         Multipart multipart = new MimeMultipart();
	
	         // Set text message part
	         multipart.addBodyPart(messageBodyPart);

	        // Part two is attachment
	         messageBodyPart = new MimeBodyPart();
	         DataSource source = new FileDataSource(filename);
	         messageBodyPart.setDataHandler(new DataHandler(source));
	         messageBodyPart.setFileName("Receipt.pdf");
	         multipart.addBodyPart(messageBodyPart);
	
	         // Send the complete message parts
	         message.setContent(multipart);
	         
	         //send message  
	         Transport.send(message);    
	         System.out.println("message sent successfully");    
        } catch (MessagingException e) {throw new RuntimeException(e);}    
        
    }
    
    public static void mailWithMultipleAttachment (String from,String password,String to,String sub, ArrayList<String> attachments) {
    	  
        Properties props = new Properties();    
        props.put("mail.smtp.host", "smtp.gmail.com");    
        props.put("mail.smtp.socketFactory.port", "465");    
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");    
        props.put("mail.smtp.auth", "true");    
        props.put("mail.smtp.port", "465");    
       
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {    
        	protected PasswordAuthentication getPasswordAuthentication() {    
        		return new PasswordAuthentication(from,password);  
        	}    
        });    
           
        try {    
	        Message message = new MimeMessage(session);    
	        message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));    
	        message.setSubject(sub); 
	         
         
         BodyPart messageBodyPart = new MimeBodyPart();

         
         messageBodyPart.setText("Thank you for your purchase. \n This is an automatic e-mail, do not reply.");

         
         Multipart multipart = new MimeMultipart();

         
         multipart.addBodyPart(messageBodyPart);
         
         for (int i=0; i<attachments.size(); i++) {
             messageBodyPart = new MimeBodyPart();
        	 DataSource source = new FileDataSource(attachments.get(i));
             messageBodyPart.setDataHandler(new DataHandler(source));
             messageBodyPart.setFileName(attachments.get(i));
             multipart.addBodyPart(messageBodyPart);
         }	 
         

         // Send the complete message parts
         message.setContent(multipart);
         
         //send message  
         Transport.send(message);    
         System.out.println("message sent successfully");    
        } catch (MessagingException e) {throw new RuntimeException(e);}    
        
    }
}  

