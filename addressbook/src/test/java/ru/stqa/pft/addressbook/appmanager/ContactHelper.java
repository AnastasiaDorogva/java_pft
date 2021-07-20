package ru.stqa.pft.addressbook.appmanager;

import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase {
  public ContactHelper(ChromeDriver wd) {
    super(wd);
  }

  public void fillContact(ContactData contactData) {
    updateContact(new ContactData(contactData.getLastName(), contactData.getFirstName(), contactData.getNickname(), contactData.getTitle(),
            contactData.getCompany(), contactData.getAddress(), contactData.getMobile(), contactData.getEmail(), contactData.getbDay(),
            contactData.getMonth(), contactData.getYear()));
    selectDataContact("new_group", contactData.getGroup());
    click(By.xpath("//div[@id='content']/form/input[21]"));
  }


  public void updateContact(ContactData contactData) {
    contactData("lastname", contactData.getLastName());
    contactData("firstname", contactData.getFirstName());
    contactData("nickname", contactData.getNickname());
    addOptionalData("title", contactData.getTitle());
    addOptionalData("company", contactData.getCompany());
    addAddress("address", contactData.getAddress());
    addOptionalData("mobile", contactData.getMobile());
    addOptionalData("email", contactData.getEmail());
    selectDataContact("bday", contactData.getbDay());
    selectDataContact("bmonth", contactData.getMonth());
    addOptionalData("byear", contactData.getYear());
  }

  private void selectDataContact(String data, String selectData) {
    click(By.name(data));
    new Select(wd.findElement(By.name(data))).selectByVisibleText(selectData);
  }

  private void addAddress(String address, String text) {
    click(By.name(address));
    wd.findElement(By.name(address)).sendKeys(text);
  }

  private void addOptionalData(String title, String data) {
    click(By.name(title));
    contactData(title, data);
  }

  private void contactData(String locator, String text) {
    wd.findElement(By.name(locator)).clear();
    wd.findElement(By.name(locator)).sendKeys(text);
  }

  public void initToContactCreation() {
    click(By.name("firstname"));
  }

  public void initToContactModification() {
    click(By.cssSelector("img[alt=\"Edit\"]"));
  }

  public void selectedContact() {
    click(By.id("5"));
  }

  public void initToUpdateContact() {
    click(By.xpath("//div[@id='content']/form/input[22]"));
  }
}