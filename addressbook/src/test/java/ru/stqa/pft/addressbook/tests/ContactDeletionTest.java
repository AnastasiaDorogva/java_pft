package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class ContactDeletionTest extends TestBase {

  @Test
  public void testContactDeletionTest(){
    app.getNavigationHelper().goToHomePage();
    if (!app.getContactHelper().isThereContact()){
      app.getNavigationHelper().goToContactPage();
      app.getContactHelper().createContact(new ContactData("Dorogova", "Anastasia", "felix", "test", "test",
              "test test test4", "89259076566", "test@gmail.com", "12", "October", "1991",
              "test0"),true);
      app.getNavigationHelper().goToHomePage();
    }
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().selectedContact(before.size()-1);
    app.getContactHelper().initDeleteContact();
    app.getContactHelper().acceptAller();
    app.getNavigationHelper().goToHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();

    Assert.assertEquals(after.size(), before.size() - 1);
    before.remove(before.size()-1);
    Assert.assertEquals(before, after);
  }
}