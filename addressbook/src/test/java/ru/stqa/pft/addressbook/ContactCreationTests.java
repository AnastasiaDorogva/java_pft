package ru.stqa.pft.addressbook;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class ContactCreationTests {
  private WebDriver wd;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeMethod(alwaysRun = true)
  public void setUp() {
    wd = new ChromeDriver();
    wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    wd.get("http://localhost/addressbook/group.php");
    login("admin", "secret");
  }

  private void login(String username, String password) {
    wd.findElement(By.name("user")).clear();
    wd.findElement(By.name("user")).sendKeys(username);
    wd.findElement(By.name("pass")).clear();
    wd.findElement(By.name("pass")).sendKeys(password);
    wd.findElement(By.xpath("//input[@value='Login']")).click();
  }

  @Test
  public void testContactCreationTests() {
    goToPage("add new");
    initToContactCreation("firstname");
    fillContact(new ContactData("Dorogova", "Anastasia", "felix", "test", "test", "test test test4", "89259076566", "test@gmail.com", "12", "October", "1991", "test1"));
    goToPage("home page");
    goToPage("Logout");
  }

  private void fillContact(ContactData contactData) {
    wd.findElement(By.name("lastname")).clear();
    wd.findElement(By.name("lastname")).sendKeys(contactData.getLastName());
    wd.findElement(By.name("firstname")).clear();
    wd.findElement(By.name("firstname")).sendKeys(contactData.getFirstName());
    wd.findElement(By.name("nickname")).clear();
    wd.findElement(By.name("nickname")).sendKeys(contactData.getNickname());
    initToContactCreation("title");
    wd.findElement(By.name("title")).clear();
    wd.findElement(By.name("title")).sendKeys(contactData.getTitle());
    initToContactCreation("company");
    wd.findElement(By.name("company")).clear();
    wd.findElement(By.name("company")).sendKeys(contactData.getCompany());
    initToContactCreation("address");
    wd.findElement(By.name("address")).clear();
    wd.findElement(By.name("address")).sendKeys(contactData.getAddress());
    initToContactCreation("home");
    initToContactCreation("mobile");
    wd.findElement(By.name("mobile")).clear();
    wd.findElement(By.name("mobile")).sendKeys(contactData.getMobile());
    initToContactCreation("theform");
    initToContactCreation("email");
    wd.findElement(By.name("email")).clear();
    wd.findElement(By.name("email")).sendKeys(contactData.getEmail());
    initToContactCreation("bday");
    new Select(wd.findElement(By.name("bday"))).selectByVisibleText(contactData.getbDay());
    initToContactCreation("bmonth");
    new Select(wd.findElement(By.name("bmonth"))).selectByVisibleText(contactData.getbMonth());
    initToContactCreation("byear");
    wd.findElement(By.name("byear")).clear();
    wd.findElement(By.name("byear")).sendKeys(contactData.getbYear());
    initToContactCreation("new_group");
    new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGropup());
    initToContactCreation("address2");
    wd.findElement(By.xpath("//div[@id='content']/form/input[21]")).click();
  }

  private void initToContactCreation(String firstname) {
    wd.findElement(By.name(firstname)).click();
  }

  private void goToPage(String s) {
    wd.findElement(By.linkText(s)).click();
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() throws Exception {
    wd.quit();
  }

  private boolean isElementPresent(By by) {
    try {
      wd.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      wd.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = wd.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
