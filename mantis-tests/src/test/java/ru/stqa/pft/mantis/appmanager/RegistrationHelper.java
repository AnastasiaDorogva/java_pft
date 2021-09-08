package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import java.util.List;

import static org.openqa.selenium.By.*;

public class RegistrationHelper extends HelperBase {
  private final WebDriver wd;

  public RegistrationHelper(ApplicationManager app) {
    super(app);
    wd = app.getDriver();
  }

  public void start(String name, String email) {
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    click(xpath("//*[@id=\"login-box\"]/div/div[2]/a"));
    type(name("username"), name);
    type(name("email"), email);
    click(cssSelector("input[value='Зарегистрироваться']"));
  }

  public void finish(String confirmationLink, String password) {
    wd.get(confirmationLink);
    type(name("password"), password);
    type(name("password_confirm"), password);
    click(By.cssSelector("button[class='width-100 width-40 pull-right btn btn-success btn-inverse bigger-110']"));
  }

  public String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }
}
