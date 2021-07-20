package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTest extends TestBase {

  @Test
  public void testContactModification() {
    app.getNavigationHelper().goToHomePage();
    app.getContactHelper().selectedContact(); //можно и не использвать без енго также будет работать редактирование
    app.getContactHelper().initToContactModification();
    app.getContactHelper().updateContact(new ContactData("Kovpak", "Alexander", "wismut", "test",
            "Inetcom", "Nevedoma 4 str2", "89857774943", "test@test.ru", "23", "December", "1988"));
    app.getContactHelper().initToUpdateContact();
    app.getNavigationHelper().goToHomePage();
  }
}