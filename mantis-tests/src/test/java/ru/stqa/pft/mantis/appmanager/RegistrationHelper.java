package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationHelper {
  private final ApplicationManager app;
  private WebDriver wd;

  public RegistrationHelper(ApplicationManager app) {
    this.app = app;
    wd = app.getDriver();
  }

  public void start(String name, String email) {
    wd.get(app.getProperty("web.baseUrl")+"/login_page.php");
    wd.findElement(By.xpath("//*[@id=\"login-box\"]/div/div[2]/a")).click();
    wd.findElement(By.name("username")).sendKeys(name);
    wd.findElement(By.name("email")).sendKeys(email);
  }
}
