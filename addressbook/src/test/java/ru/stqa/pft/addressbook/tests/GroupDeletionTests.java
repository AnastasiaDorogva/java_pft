package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;


public class GroupDeletionTests extends TestBase {

  @Test
  public void GroupDeletionTests() {
    app.getNavigationHelper().goToGroupPage();
    if (!app.getGroupHelper().isThereGroup()) {
      app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }
    app.getGroupHelper().selectedGroups();
    app.getGroupHelper().deleteSelectGroup();
    app.getGroupHelper().goToGroupPage();
  }
}
