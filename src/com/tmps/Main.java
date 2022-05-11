package com.tmps;
/*
import javax.mail.Session;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Store;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws IOException, MessagingException {

        FileInputStream fileInputStream = new FileInputStream("config.properties");
        Properties properties = new Properties();
        properties.load(fileInputStream);

        String user = properties.getProperty("mail.user");
        String password = properties.getProperty("mail.password");
        String host = properties.getProperty("mail.host");

        Properties prop = new Properties();
        prop.put("mail.store.protocol","imaps");//SSL

        Store store = Session.getInstance(prop).getStore();
        store.connect(host,user,password);

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        System.out.println(inbox.getMessageCount());
    }


}*/

public class Main {

    public static void main(String[] args) throws Exception {
        JavaMailUtil.sendMail("yodel.corporation.delivery@gmail.com");
        JavaMailUtil.GetNumberMessage();

    }


}