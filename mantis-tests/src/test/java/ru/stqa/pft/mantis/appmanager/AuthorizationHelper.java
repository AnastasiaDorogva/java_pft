package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;

import static org.openqa.selenium.By.*;

public class AuthorizationHelper extends HelperBase {
  private final WebDriver wd;

  public AuthorizationHelper(ApplicationManager app) {
    super(app);
    wd = app.getDriver();
  }

  public void authorization(String username, String password) {
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    type(name("username"), username);
    click(xpath("//input[2]"));
    type(name("password"), password);
    click(xpath("//input[3]"));
  }
  public void initChangePass(String user) {
    click(cssSelector("#sidebar > ul > li:nth-child(6) > a > span"));
    click(linkText("Управление пользователями"));
    click(linkText(user));
    click(cssSelector("input[value='Сбросить пароль']"));
  }
}

