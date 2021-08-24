package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTest extends TestBase {

  @BeforeTest
  public void ensurePrecondition(){
    app.goTo().homePage();
    if (app.db().contacts().size()==0){
      app.goTo().contact();
      app.contact().createContact(new ContactData().withLastName("Dorogova").withFirstName( "Anastasia").withNickname( "felix").withTitle( "test")
              .withTitle( "test").withAddress("test test test4").withMobilePhone( "89259076566").withEmail( "test@gmail.com").withBDay( "12")
              .withBMonth("October").withBYear( "1991").withGroup("test0"),true);
      app.goTo().homePage();
    }
  }

  @Test()
  public void testContactDeletionTest(){
    Contacts before = app.db().contacts();
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact);
    app.goTo().homePage();
    Contacts after = app.db().contacts();
    assertThat(after.size(), equalTo(before.size()-1));
    assertThat(after, equalTo(before.without(deletedContact)));
    verifyListInUi();

  }
}