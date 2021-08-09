package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;

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

  public void selectedGroups(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
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
    fillGroupForm(group);
    submitToGroupCreation();
    goToGroupPage();
  }

  public void modifyGroup(int index, GroupData group) {
    selectedGroups(index);
    initGroupModification();
    fillGroupForm(group);
    updateGroupModification();
    goToGroupPage();
  }


  public void deleteGroup(int index) {
    selectedGroups(index);
    deleteSelectGroup();
    goToGroupPage();
  }

  public int getGroupCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public List<GroupData> getGroupList() {
    List<GroupData> groups = new ArrayList<>();
    List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));
    for (WebElement element : elements) {
      String name = element.getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      GroupData group = new GroupData(id, name, null, null);
      groups.add(group);
    }
    return groups;
  }
}