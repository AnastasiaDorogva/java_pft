package ru.sbtqa.pft.sandbox;

public class Point {
  double a;
  double b;

  public Point(double a, double b) {
    this.a = a;
    this.b = b;
  }

  public double distance(Point p1) {
    return Math.sqrt((this.a - p1.a) * (this.a - p1.a) + (this.b - p1.b) * (this.b - p1.b));
  }
}
