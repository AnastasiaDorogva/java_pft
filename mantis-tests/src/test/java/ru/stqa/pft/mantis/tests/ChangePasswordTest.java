package ru.stqa.pft.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class ChangePasswordTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() throws MessagingException {
    if (app.db().users().size() == 1) {
      long now = System.currentTimeMillis();
      String email = String.format("user%s@localhost", now);
      String user = "user" + now;
      String password = "password";
      app.james().createUser(user, password);
      app.registration().start(user, email);
      List<MailMessage> mailMessages = app.james().waitForMail(user, password, 60000);
      String confirmationLink = app.registration().findConfirmationLink(mailMessages, email);
      app.registration().finish(confirmationLink, password);
    }
  }

  @Test
  public void testChangePassword() throws IOException, MessagingException {
    Users dbUsers = app.db().users();
    Iterator<UserData> iterator = dbUsers.iterator();
    UserData selectUser = iterator.next();
    boolean it = iterator.hasNext();
    if (selectUser.getUsername().equals("administrator")) {
      if (it) {
        selectUser = iterator.next();
        changePassword(selectUser.getUsername(), selectUser.getEmail(), selectUser.getPassword());
        Assert.assertTrue(app.newSession().login(selectUser.getUsername(), selectUser.getPassword()));
      }
    }
  }

  private void changePassword(String username, String email, String password) throws MessagingException {
    app.auth().authorization(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
    app.auth().initChangePass(username);
    List<MailMessage> mailMessages = app.james().waitForMail(username,password,70000); // внешний почтовый сервер
    String newConfirmationLink = findConfirmationLink(mailMessages, email);
    app.registration().finish(newConfirmationLink, password);
  }

  private static String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex=VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return  regex.getText(mailMessage.text);
  }
}


