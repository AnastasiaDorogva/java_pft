package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      app.goTo().homePage();
      app.goTo().contact();
      app.contact().create(new ContactData().withLastName("Dorogova").withFirstName("Anastasia").withNickname("felix").withTitle("test")
              .withTitle("test").withAddress("test test test4").withMobilePhone("89259076566").withEmail("test@gmail.com").withBDay("12")
              .withBMonth("October").withBYear("1991"),false);//withGroup("test0");
      app.goTo().homePage();
    }
  }

  @Test()
  public void testContactModification() {
    app.goTo().homePage();
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact.getId()).withLastName("Kovpak").withFirstName("Alexander")
            .withNickname("wismut").withTitle("test").withCompany("Inetcom").withAddress("Nevedoma 4 str2").withMobilePhone("89857774943")
            .withEmail("test@test.ru").withBDay("23").withBMonth("December").withBYear("1988");
    app.contact().modify(contact);
    app.goTo().homePage();
    Contacts after = app.db().contacts();
    assertThat(after.size(), equalTo((before.size())));
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    verifyListInUi();
  }
}