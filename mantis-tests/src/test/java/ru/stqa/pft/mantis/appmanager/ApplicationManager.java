package ru.stqa.pft.mantis.appmanager;

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

  public WebDriver getDriver() {
    if (browser.equals(BrowserType.CHROME)) {
      System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chrome"));//System.setProperty("webdriver.chrome.driver", "C:\\Users\\Елизавета Криворучка\\Desktop\\world\\chromedriver.exe");
      wd = new ChromeDriver();
    } else if (browser.equals(BrowserType.IE)) {
      System.setProperty("webdriver.ie.driver", properties.getProperty("webdriver.ie"));//System.setProperty("webdriver.ie.driver", "C:\\Users\\Елизавета Криворучка\\Desktop\\world\\IEDriverServer.exe");
      wd = new InternetExplorerDriver();
    } else if (browser.equals(BrowserType.FIREFOX)) {
      System.setProperty("webdriver.gecko.driver", properties.getProperty("webdriver.firefox"));//System.setProperty("webdriver.gecko.driver", "C:\\Users\\Елизавета Криворучка\\Desktop\\world\\geckodriver.exe");
      wd = new FirefoxDriver();
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