package ru.sbtqa.pft.sandbox;

public class PointResult {
  public static void main(String[] args) {
    Point p1 = new Point(4, -2);
    Point p2 = new Point(6, 5);

    System.out.println("Расстояние между двумя точками " + " = " + p1.distance(p2));
  }
}