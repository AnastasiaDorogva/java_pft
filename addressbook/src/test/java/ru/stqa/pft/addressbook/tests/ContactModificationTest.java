package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

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
    List<ContactData> before = app.getContactHelper().getContactList();
    int index = before.size()-1;
    ContactData contact = new ContactData(before.get(index).getId(),"Kovpak", "Alexander", "wismut", "test",
            "Inetcom", "Nevedoma 4 str2", "89857774943", "test@test.ru", "23", "December",
            "1988",null);

    app.getContactHelper().initToContactModification(index);
    app.getContactHelper().fillContact(contact,false);
    app.getContactHelper().initToUpdateContact();
    app.getNavigationHelper().goToHomePage();

    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());

    before.remove(index);
    before.add(contact);
    Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before,after);
  }
}