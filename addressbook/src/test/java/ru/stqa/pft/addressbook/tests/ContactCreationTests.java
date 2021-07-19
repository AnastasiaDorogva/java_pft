package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {
  @Test
  public void contactCreationTests() {
    app.getNavigationHelper().goToContactPage();
    app.getContactHelper().initToContactCreation();
    app.getContactHelper().fillContact(new ContactData("Dorogova", "Anastasia", "felix", "test", "test", "test test test4", "89259076566", "test@gmail.com", "12", "October", "1991", "test1"));
    app.getNavigationHelper().goToHomePage();
    app.getNavigationHelper().goToLogout();
  }
}
