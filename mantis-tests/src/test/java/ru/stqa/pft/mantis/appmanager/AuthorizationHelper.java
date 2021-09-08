package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import ru.stqa.pft.mantis.model.UserData;

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
    click(cssSelector("input[class='width-40 pull-right btn btn-success btn-inverse bigger-110']"));
    wd.get(app.getProperty("web.baseURL")+"manage_user_page.php");
    click(linkText(user));
    click(cssSelector("input[value='Сбросить пароль']"));
  }
}

