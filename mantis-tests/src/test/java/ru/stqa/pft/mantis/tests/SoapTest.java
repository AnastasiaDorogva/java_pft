package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class SoapTest extends TestBase {

  @Test
  public void testGetProjects() throws RemoteException, ServiceException, MalformedURLException {
    Set<Project> projects = app.soap().getProject();
    System.out.println(projects.size());
    for (Project project : projects) {
      System.out.println(project.getName());
    }
  }

    @Test
    public void testCreateIssue () throws RemoteException, ServiceException, MalformedURLException {
      Set<Project> projects = app.soap().getProject();
      Issue issue = new Issue().withSummary("Test Issue").withDescription("test issue description")
              .withProject(projects.iterator().next());
      Issue createdIssue = app.soap().addIssue(issue);
      assertEquals(issue.getSummary(), createdIssue.getSummary());
    }
  }