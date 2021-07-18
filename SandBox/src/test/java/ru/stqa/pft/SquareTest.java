package ru.stqa.pft;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.sbtqa.pft.sandbox.Square;

public class SquareTest {

  @Test
  public void testArea() {
    Square s = new Square(5);
    Assert.assertEquals(s.area(), 25);
  }
}
