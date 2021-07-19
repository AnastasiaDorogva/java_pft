package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;


public class GroupDeletionTests extends TestBase {

  @Test
  public void GroupDeletionTests() {
    app.getNavigationHelper().goToGroupPage();
    app.getGroupHelper().selectedGroups();
    app.getGroupHelper().deleteSelectGroup();
    app.getGroupHelper().goToGroupPage();
    app.getGroupHelper().goToPageLogout();
  }

}
