package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

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

  public void selectedGroupsById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
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

  public void create(GroupData group) {
    initToGroupCreation();
    fillGroupForm(group);
    submitToGroupCreation();
    groupsCache = null;
    goToGroupPage();
  }

  public void modify(GroupData group) {
    selectedGroupsById(group.getId());
    initGroupModification();
    fillGroupForm(group);
    updateGroupModification();
    groupsCache = null;
    goToGroupPage();
  }

  public void delete(GroupData group) {
    selectedGroupsById(group.getId());
    deleteSelectGroup();
    groupsCache = null;
    goToGroupPage();
  }

  public int getGroupCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  private Groups groupsCache = null;

  public Groups all() {
    if (groupsCache != null) {
      return new Groups(groupsCache);
    }
    groupsCache = new Groups();
    List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));
    for (WebElement element : elements) {
      String name = element.getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      groupsCache.add(new GroupData().withId(id).withName(name));
    }
    return new Groups(groupsCache);
  }
}