package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;

public class ContactHelper extends HelperBase {
  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void fillContact(ContactData contactData, boolean creation) {
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
    if (creation) {
      selectDataContact("new_group", contactData.getGroup());
      click(By.xpath("//div[@id='content']/form/input[21]"));
    } else {
      Assert.assertFalse(isElementPresent((By.name("new_group"))));
    }
  }

  private void selectDataContact(String data, String selectData) {
    click(By.name(data));
    new Select(wd.findElement(By.name(data))).selectByVisibleText(selectData);
  }

  private void addAddress(String address, String text) {
    click(By.name(address));
    wd.findElement(By.name(address)).clear();
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

  public void initToContactModification(int id) {
    wd.findElement(By.cssSelector("a[href=\"edit.php?id=" + id + "\"]")).click();
  }

  private void selectedContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }


  public void initToUpdateContact() {
    click(By.xpath("//div[@id='content']/form/input[22]"));
  }

  public void initDeleteContact() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void acceptAller() {
    wd.switchTo().alert().accept();
  }

  public void createContact(ContactData contact, boolean create) {
    initToContactCreation();
    fillContact(contact, create);
  }

  public void delete(ContactData contact) {
    selectedContactById(contact.getId());
    initDeleteContact();
    acceptAller();
  }

  public void modify(ContactData contact) {
    initToContactModification(contact.getId());
    fillContact(contact, false);
    initToUpdateContact();
  }

  public boolean isThereContact() {
    return isElementPresent(By.cssSelector("img[alt=\"Edit\"]"));
  }

  public Contacts all() {
    Contacts contact = new Contacts();
    List<WebElement> elements = wd.findElements(By.cssSelector("tr[name=\"entry\"]"));
    for (WebElement element : elements) {
      String lastName = element.findElement(By.cssSelector("td:nth-child(2)")).getText();
      String firstName = element.findElement(By.cssSelector("td:nth-child(3)")).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      ContactData contactData = new ContactData().withId(id).withLastName(lastName).withFirstName(firstName);
      contact.add(contactData);
    }
    return contact;
  }
}