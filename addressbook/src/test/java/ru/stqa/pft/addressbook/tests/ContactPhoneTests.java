package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTests extends TestBase {

  @Test
  public  void contactPhoneTest() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
    assertThat(contact.getAddress(), equalTo(mergeAddress(contactInfoFromEditForm)));
    assertThat(contact.getAllEmail(), equalTo(mergeEmail(contactInfoFromEditForm)));

  }

  private String mergePhones(ContactData contact) {
   return Stream.of(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone()).filter((s) -> !s.equals(""))
           .map(ContactPhoneTests::cleaned)
           .collect(Collectors.joining("\n"));
  }

  private String mergeAddress (ContactData contact) {
    return Stream.of(contact.getAddress()).filter((s) -> !s.equals(""))
            .map(ContactPhoneTests::cleaned)
            .collect(Collectors.joining("\n"));
  }

  private String mergeEmail (ContactData contact) {
    return Stream.of(contact.getEmail(),contact.getEmail2(),contact.getEmail3()).filter((s) -> !s.equals(""))
            .map(ContactPhoneTests::cleaned)
            .collect(Collectors.joining("\n"));
  }

  public static String cleaned(String phone) {
    return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
  }
}