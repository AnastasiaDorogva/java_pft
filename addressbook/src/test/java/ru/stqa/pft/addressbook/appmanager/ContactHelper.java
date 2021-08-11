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
    addOptionalData("mobile", contactData.getMobilePhone());
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
    initToContactModificationById(contact.getId());
    fillContact(contact, false);
    initToUpdateContact();
  }

  public boolean isThereContact() {
    return isElementPresent(By.cssSelector("img[alt=\"Edit\"]"));
  }

  public Contacts all() {
    Contacts contact = new Contacts();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element : elements) {
      List<WebElement> cells = element.findElements(By.tagName("td"));
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      String lastName = cells.get(1).getText();
      String firstName = cells.get(2).getText();
      String address = cells.get(3).getText();
      String allEmail = cells.get(4).getText();
      String allPhones = cells.get(5).getText();
      ContactData contactData = new ContactData().withId(id).withLastName(lastName).withFirstName(firstName).withAddress(address)
              .withAllEmail(allEmail).withAllPhones(allPhones);
      contact.add(contactData);
    }
    return contact;
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initToContactModificationById(contact.getId());
    String firstName = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastName = wd.findElement(By.name("lastname")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    String email1 = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    String homePhone = wd.findElement(By.name("home")).getAttribute("value");
    String mobilePhone = wd.findElement(By.name("mobile")).getAttribute("value");
    String workPhone = wd.findElement(By.name("work")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withFirstName(firstName).withLastName(lastName).withAddress(address).withEmail(email1)
            .withEmail2(email2).withEmail3(email3).withHomePhone(homePhone).withMobilePhone(mobilePhone).withWorkPhone(workPhone);

  }

  private void initToContactModificationById(int id) {
    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
  }
}