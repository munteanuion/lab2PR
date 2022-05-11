package com.tmps;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaMailUtil {
    public static void sendMail(String recepient) throws Exception {
        System.out.println("Pregatirea pentru trimitere mail");
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        String myAccountEmail = "yodel.corporation.delivery@gmail.com";
        String password = "Mesaje65!";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail,password);
            }
        });

        Message message = prepareMessage(session,myAccountEmail,recepient);

        Transport.send(message);
        System.out.println("Mesajul a fost trimis cu success");
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient) {
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(recepient));
            message.setSubject("Lab 2 PR");
            message.setText("Final lab 2 pr");
            return message;
        }catch (Exception ex){
            Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE,null,ex);
        }
        return null;
    }

    public static void GetNumberMessage() throws MessagingException {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        properties.setProperty("mail.store.protocol", "imaps");

        String myAccountEmail = "yodel.corporation.delivery@gmail.com";
        String password = "Mesaje65!";

        Store store = Session.getInstance(properties).getStore("imaps");
        store.connect("smtp.gmail.com",myAccountEmail,password);

        try {
            Folder inbox = store.getFolder("inbox");
            FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            inbox.open(Folder.READ_ONLY);//set access type of Inbox
            Message messages[] = inbox.getMessages();
            String mail,sub;
            Object body;
            int i = 0;
            for(Message message:messages) {
                mail = message.getFrom()[0].toString();
                sub = message.getSubject();
                System.out.println(mail+sub+"\n"+getTextFromMessage(message));
                i++;
                if (i == 20)
                    break;
            }
            System.out.println(inbox.getMessageCount());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }
    private static String getTextFromMimeMultipart(
        MimeMultipart mimeMultipart)  throws MessagingException, IOException{
        StringBuilder result = new StringBuilder();
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result.append("\n").append(bodyPart.getContent());
                break;
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
            }
        }
        return result.toString();
    }
}
