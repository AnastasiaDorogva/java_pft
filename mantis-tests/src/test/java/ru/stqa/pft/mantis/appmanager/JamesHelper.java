package ru.stqa.pft.mantis.appmanager;

import org.apache.commons.net.telnet.TelnetClient;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class JamesHelper {
  private ApplicationManager app;

  private TelnetClient telnet;
  private InputStream in;
  private PrintStream out;

  private Session mailSession;
  private Store store;
  private String mailServer;

  public JamesHelper(ApplicationManager app) {
    this.app = app;
    telnet = new TelnetClient();
    mailSession = Session.getDefaultInstance(System.getProperties());
  }

  public boolean doesUserExist(String name) {
    initTelnetSession();
    write("verify" + name);
    String result = readUntil("exist");
    closeTelnetSession();
    return result.trim().equals("User " + name + " exist");
  }

  public void createUser(String user, String password) {
    initTelnetSession();
    write("addUser" + user + password);
    String result = readUntil("User " + user + " add");
    closeTelnetSession();
  }

  public void deleteUser(String user, String password) {
    initTelnetSession();
    write("delUser" + user + password);
    String result = readUntil("User " + user + " deleted");
    closeTelnetSession();
  }

  private void initTelnetSession() {
    mailServer = app.getProperty("mailserver.host");
    int port = Integer.parseInt(app.getProperty("mailserver.port"));
    String login = app.getProperty("mailserver.admin");
    String password = app.getProperty("mailserver.adminpassword");

    try {
      telnet.connect(mailServer, port);
      in = telnet.getInputStream();
      out = new PrintStream(telnet.getOutputStream());
    } catch (Exception e) {
      e.printStackTrace();
    }

    readUntil("Login id");
    write("");
    readUntil("Password");
    write("");

    readUntil("Login id");
    write("");
    readUntil("Password");
    write("");

    readUntil("Welcome " + login + " HELP for a List of commands");
  }

  private String readUntil(String pattern) {
    try {
      char lastChar = pattern.charAt(pattern.length() - 1);
      StringBuffer sb = new StringBuffer();
      char ch = (char) in.read();
      while (true) {
        System.out.println(ch);
        sb.append(ch);
        if (ch == lastChar) {
          if (sb.toString().endsWith(pattern)) {
            return sb.toString();
          }
        }
        ch = (char) in.read();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private void write(String value) {
    try {
      out.println(value);
      out.flush();
      System.out.println(value);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void closeTelnetSession() {
    write("quit");
  }

  public List<MailMessage> waitForMail(String user, String password, long timeout) throws MessagingException {
    long now = System.currentTimeMillis();
    while (System.currentTimeMillis() < now + timeout) {
      List<MailMessage> allMail = getAllMail(user, password);
      if (allMail.size() > 0) {
        return allMail;
      }
      try {
        sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    throw new Error("No mail :(");
  }


  public List<MailMessage> getAllMail(String user, String password) throws MessagingException {
    Folder inbox = openInbox(user, password);
    List<MailMessage> messages = Arrays.stream(inbox.getMessages()).map(this::toModelMail).collect(Collectors.toList());
    closeFolder(inbox);
    return messages;
  }

  private MailMessage toModelMail(Message m) {
    try {
      return new MailMessage(m.getAllRecipients()[0].toString(), (String) m.getContent());
    } catch (MessagingException | IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  private Folder openInbox(String user, String password) throws MessagingException {
    store = mailSession.getStore("pop3");
    store.connect(mailServer, user, password);
    Folder folder = store.getDefaultFolder().getFolder("INBOX");
    folder.open(Folder.READ_WRITE);
    return folder;
  }

  private void closeFolder(Folder folder) throws MessagingException {
    folder.close(true);
    store.close();
  }
}
