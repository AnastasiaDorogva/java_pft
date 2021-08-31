package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;
import java.util.Comparator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddContactInGroupTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test_0"));
    }
    if (app.db().contacts().size() == 0) {
      Groups dbGroups = app.db().groups();
      GroupData group = dbGroups.iterator().next();
      app.goTo().contact();
      app.contact().create(new ContactData().withFirstName("Test").withLastName("Test")
              .withNickname("QA").withTitle("QA AUTO").withCompany("QA_COMP").withAddress("г.Москва Testing 14")
              .withMobilePhone("8-999-478-65-12").withEmail("test@gmail.com")
              .withBDay("14").withBMonth("April").withBYear("1990")
              .withPhoto(new File("src/test/resources/rick.png")).inGroup(group), true);
    }
  }

  @Test
  public void testAddContactInGroupTest() {
    Contacts contactsFromDB = app.db().contacts();
    ContactData modifiedContact = contactsFromDB.iterator().next();
    ContactData max = contactsFromDB.stream().max(Comparator.comparingInt(ContactData::getId)).get();
    for (ContactData contact : contactsFromDB) {
      if (contact.getGroups().size() == 0) {
        modifiedContact = contact;
        break;
      } else if (contact == max) {
        File photo = new File("src/test/resources/icon.png");
        modifiedContact = new ContactData().withFirstName("Тэки").withLastName("Томо")
                .withNickname("Dogo").withTitle("NOKIMO").withCompany("DOGO-COIN")
                .withAddress("Japanese").withMobilePhone("+7 916 060 15 38")
                .withEmail("dogo@japan.com").withPhoto(photo);
        app.contact().create(modifiedContact.withId((contactsFromDB.stream().mapToInt(ContactData::getId).max().getAsInt()) + 1), true);
      }
    }
    Groups before = modifiedContact.getGroups();
    Groups groups = app.db().groups();
    GroupData group = groups.iterator().next();
    Integer groupId = group.getId();
    String groupName = group.getName();
    app.goTo().homePage();
    app.contact().addContactToGroup(modifiedContact, groupId, groupName);
    Groups after = app.db().getContactsFromDB(modifiedContact.getId()).getGroups();
    System.out.println(after);
    assertThat(after, equalTo(before.withAdded(group)));
  }
}


