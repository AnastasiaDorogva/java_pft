package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;
import java.util.Comparator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class DeleteContactGroupTest extends TestBase {

  @BeforeMethod
  public void ensureGroupExistence() {
    app.goTo().groupPage();
    if (app.db().groups().size() == 0) {
      app.group().create(new GroupData().withName("test_1"));
    }
    app.goTo().homePage();
    if (app.db().contacts().size() == 0) {
      File photo = new File("src/test/resources/icon.png");
      app.contact().create(new ContactData().withFirstName("Тэки").withLastName("Томо")
              .withNickname("Dogo").withTitle("NOKIMI").withCompany("DogoCoin")
              .withAddress("Jaanese")
              .withMobilePhone("8-916-060-15-38")
              .withEmail("dogo@coin.com").withPhoto(photo), true);
    }
  }

  @Test
  public void testDropContactFromGroup() {
    Groups groups = app.db().groups();
    GroupData modifiedGroup = groups.iterator().next();
    ContactData modifiedContact = null;
    Contacts before;
    int modifiedGroupId = 0;
    GroupData max = groups.stream().max(Comparator.comparingInt(GroupData::getId)).get();
    for (GroupData group : groups) {
      if (group.getContacts().size() != 0) {
        modifiedGroup = group;
        modifiedGroupId = group.getId();
        modifiedContact = group.getContacts().iterator().next();
        break;
      } else if (group == max) {
        Contacts contactsFromDB = app.db().contacts();
        modifiedContact = contactsFromDB.iterator().next();
        modifiedGroup = group;
        modifiedGroupId = group.getId();
        String groupName = group.getName();
        app.goTo().homePage();
        app.contact().addContactToGroup(modifiedContact, modifiedGroupId, groupName);
      }
    }
    before = modifiedGroup.getContacts();
    String groupName = modifiedGroup.getName();
    app.goTo().homePage();
    assert modifiedContact != null;
    app.contact().deleteContactFromGroup(modifiedContact, groupName, modifiedGroupId);
    Groups newGroups = app.db().groups();
    for (GroupData newGroup : newGroups) {
      if (newGroup.getId() == modifiedGroupId) {
        Contacts after = newGroup.getContacts();
        assertThat(after, equalTo(before.without(modifiedContact)));
      }
    }
  }
}

