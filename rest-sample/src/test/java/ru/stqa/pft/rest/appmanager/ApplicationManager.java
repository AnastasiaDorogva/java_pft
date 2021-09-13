package ru.stqa.pft.rest.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {
  private RestHelper restHelper;
  private final String browser;
  private final Properties properties;
  private WebDriver wd;


  public ApplicationManager(String browser) {
    this.browser = browser;
    properties = new Properties();
  }

  public void init() throws IOException {
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }

  public WebDriver getDriver() {
    if (browser.equals(BrowserType.CHROME)) {
      System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chrome"));
      wd = new ChromeDriver();
    } else if (browser.equals(BrowserType.IE)) {
      System.setProperty("webdriver.ie.driver", properties.getProperty("webdriver.ie"));
      wd = new InternetExplorerDriver();
    } else if (browser.equals(BrowserType.FIREFOX)) {
      System.setProperty("webdriver.gecko.driver", properties.getProperty("webdriver.firefox"));
      wd = new FirefoxDriver();
    }
    wd.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    wd.get(properties.getProperty("web.baseUrl"));
    return wd;
  }

  public RestHelper rest(){
    if (restHelper==null){
      restHelper = new RestHelper(this);}
    return restHelper;
  }
}
