package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {
  private final Properties properties;
  private WebDriver wd;
  private final String browser;
  private RegistrationHelper registrationHelper;
  private FtpHelper ftpHelper;
  private MailHelper mailHelper;
  private AuthorizationHelper authorizationHelper;
  private JamesHelper jamesHelper;
  private DataBaseHelper dataBaseHelper;
  private SoapHelper soapHelper;

  public ApplicationManager(String browser) {
    this.browser = browser;
    properties = new Properties();
  }

  public void init() throws IOException {
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }

  public void stop() {
    if (wd != null) {
      wd.quit();
    }
  }

  public HttpSession newSession() {
    return new HttpSession(this);
  }

  public String getProperty(String key) {
    return properties.getProperty(key);
  }

  public RegistrationHelper registration() {
    if (registrationHelper == null) {
      registrationHelper = new RegistrationHelper(this);
    }
    return registrationHelper;
  }

  public AuthorizationHelper auth() {
    if (authorizationHelper == null) {
      authorizationHelper = new AuthorizationHelper(this);
    }
    return authorizationHelper;
  }

  public FtpHelper ftp() {
    if (ftpHelper == null) {
      ftpHelper = new FtpHelper(this);
    }
    return ftpHelper;
  }

  public WebDriver getDriver() throws MalformedURLException {
    if ("".equals(properties.getProperty("selenium.server"))) {
      if (browser.equals(BrowserType.FIREFOX)) {
        wd = new FirefoxDriver();
      } else if (browser.equals(BrowserType.CHROME)) {
        wd = new ChromeDriver();
      } else if (browser.equals(BrowserType.IE)) {
        wd = new InternetExplorerDriver();
      } else {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browser);
        wd = new RemoteWebDriver(new URL(properties.getProperty("selenium.server")), capabilities);
      }
    }
    wd.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    wd.get(properties.getProperty("web.baseUrl"));
    return wd;
  }

  public MailHelper mail() {
    if (mailHelper == null) {
      mailHelper = new MailHelper(this);
    }
    return mailHelper;
  }

  public JamesHelper james() {
    if (jamesHelper == null) {
      jamesHelper = new JamesHelper(this);
    }
    return jamesHelper;
  }

  public DataBaseHelper db() {
    if (dataBaseHelper == null) {
      dataBaseHelper = new DataBaseHelper(this);
    }
    return dataBaseHelper;
  }

  public SoapHelper soap() {
    if (soapHelper == null) {
      soapHelper = new SoapHelper(this);
    }
    return soapHelper;
  }
}