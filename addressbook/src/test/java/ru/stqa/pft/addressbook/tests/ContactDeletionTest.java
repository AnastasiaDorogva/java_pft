package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTest extends TestBase {

  @Test
  public void testContactDeletionTest(){
    app.getNavigationHelper().goToHomePage();
    app.getContactHelper().selectedContact();
    app.getContactHelper().initDeleteContact();
    app.getContactHelper().acceptAller();
    app.getNavigationHelper().goToHomePage();
  }
}