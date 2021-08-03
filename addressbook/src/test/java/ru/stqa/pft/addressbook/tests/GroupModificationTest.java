package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

public class GroupModificationTest extends TestBase {

  @Test
  public void testGroupModification() {
    app.getNavigationHelper().goToGroupPage();
    if (!app.getGroupHelper().isThereGroup()) {
      app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }
    List<GroupData> before = app.getGroupHelper().getGroupList();
    app.getGroupHelper().selectedGroups(before.size()-1);
    app.getGroupHelper().initGroupModification();
    GroupData group = new GroupData(before.get(before.size()-1).getId(),"test0", "test1", "test2");
    app.getGroupHelper().fillGroupForm(group);
    app.getGroupHelper().updateGroupModification();
    app.getGroupHelper().goToGroupPage();
    List<GroupData> after = app.getGroupHelper().getGroupList();
    Assert.assertEquals(after.size(), before.size());

    before.remove(before.size()-1);
    before.add(group);
    Assert.assertEquals(new HashSet<Object>(before),new HashSet<Object>(after));
  }
}
