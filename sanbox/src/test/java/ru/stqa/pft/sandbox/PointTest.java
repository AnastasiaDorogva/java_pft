package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.sbtqa.pft.sandbox.Point;

public class PointTest {

  @Test
  public void distance(){
  Point p1 = new Point(3,7);
  Point p2 = new Point(5,9);
  Assert.assertEquals(p1.distance(p2),2.8284271247461903);
  }
}
