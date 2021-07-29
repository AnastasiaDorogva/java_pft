package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupHelper extends HelperBase {

  public GroupHelper(WebDriver wd) {
    super(wd);
  }

  public void fillGroupForm(GroupData groupData) {
    type("group_name", groupData.getName());
    type("group_header", groupData.getHeader());
    type("group_footer", groupData.getFooter());
  }

  public void submitToGroupCreation() {
    click(By.name("submit"));
  }

  public void initToGroupCreation() {
    click(By.name("new"));
  }

  public void goToGroupPage() {
    click(By.linkText("group page"));
  }

  public void deleteSelectGroup() {
    click(By.name("delete"));

  }

  public void selectedGroups() {
    click(By.name("selected[]"));
  }

  public void initGroupModification() {
    click(By.name("edit"));
  }

  public void updateGroupModification() {
    click(By.name("update"));
  }

  public boolean isThereGroup() {
  return isElementPresent(By.name("selected[]"));
  }

  public void createGroup(GroupData group) {
   initToGroupCreation();
   fillGroupForm(new GroupData("test1", null, null));
   submitToGroupCreation();
   goToGroupPage();
  }
}