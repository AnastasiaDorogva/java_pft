package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTest extends TestBase {

  @Test
  public void testContactModification() {
    app.getNavigationHelper().goToHomePage();
    if (!app.getContactHelper().isThereContact()){
      app.getNavigationHelper().goToContactPage();
      app.getContactHelper().createContact(new ContactData("Dorogova", "Anastasia", "felix", "test", "test",
              "test test test4", "89259076566", "test@gmail.com", "12", "October", "1991",
              "test0"),true);
      app.getNavigationHelper().goToHomePage();
    }
    app.getContactHelper().selectedContact();
    app.getContactHelper().initToContactModification();
    app.getContactHelper().fillContact(new ContactData("Kovpak", "Alexander", "wismut", "test",
            "Inetcom", "Nevedoma 4 str2", "89857774943", "test@test.ru", "23", "December", "1988",null),false);
    app.getContactHelper().initToUpdateContact();
    app.getNavigationHelper().goToHomePage();
  }
}