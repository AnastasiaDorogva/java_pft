package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {
  @Test()
  public void contactCreationTests() {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    app.goTo().contact();
    ContactData contact = new ContactData().withFirstName("Anastasia").withLastName("Dorogova").withNickname("felix").withTitle("test")
            .withCompany("test").withAddress("test test test4").withMobilePhone("89259076566").withEmail("test@gmail.com").withBDay("12")
            .withBMonth("October").withBYear("1991").withGroup("test0");
    app.contact().createContact(contact, true);
    app.goTo().homePage();
    Contacts after = app.contact().all();
    assertThat(after.size(), equalTo(before.size() + 1));
    assertThat(after, equalTo(before
            .withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }
}