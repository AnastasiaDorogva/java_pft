package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.File;

public class HelperBase {
  protected WebDriver wd;

  public HelperBase(WebDriver wd) {
    this.wd = wd;
  }

  protected void type(String locator, String text) {
    click(By.name(locator));
    if (text != null) {
      String existingText = wd.findElement(By.name(locator)).getAttribute("value");
      if (!text.equals(existingText)) {
        wd.findElement(By.name(locator)).clear();
        wd.findElement(By.name(locator)).sendKeys(text);
      }
    }
  }

  protected void attach(String locator, File file) {
    if (file!= null) {
      wd.findElement(By.name(locator)).sendKeys(file.getAbsolutePath());
      }
    }

  protected void click(By locator) {
    wd.findElement(locator).click();
  }

  public boolean isAlertPresent() {
    try {
      wd.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  protected boolean isElementPresent(By locator) {
    try {
      wd.findElement(locator);
      return true;
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
      return false;
    }
  }

  protected void select (By locator,String text) {
    click (locator);
    if (text!=null) {
      String existingText = wd.findElement(locator).getAttribute("value");
      if (!text.equals(existingText)) {
        new Select(wd.findElement(locator)).selectByVisibleText(text);
      }
    }
    click (locator);
  }
}