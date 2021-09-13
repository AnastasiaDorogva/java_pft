package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Request;
import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import ru.stqa.pft.rest.appmanager.ApplicationManager;
import ru.stqa.pft.rest.appmanager.RestHelper;
import ru.stqa.pft.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

public class TestBase {
  protected static ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

  boolean isIssueOpenRest(int issueId) throws IOException {
    String json = RestHelper.getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues/" + issueId + ".json")).returnContent().asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issuesJ = parsed.getAsJsonObject().get("issues");
    Set<Issue> issues = new Gson().fromJson(issuesJ, new TypeToken<Set<Issue>>() {
    }.getType());
    Issue issue = issues.iterator().next();
    System.out.println("Статус задачи: " + issue.getState_name());
    if (issue.getState_name().equals("Resolved") | issue.getState_name().equals("Closed")) {
      return false;
    } else {
      return true;
    }
  }

  public void skipIfNotFixedRest(int issueId) throws IOException {
    if (isIssueOpenRest(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }
}
