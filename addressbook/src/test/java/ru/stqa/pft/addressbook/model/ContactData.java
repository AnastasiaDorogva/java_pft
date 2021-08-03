package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class ContactData {
  private int id;
  private final String lastName;
  private final String firstName;
  private final String nickname;
  private final String title;
  private final String company;
  private final String address;
  private final String mobile;
  private final String email;
  private final String bDay;
  private final String bMonth;
  private final String bYear;
  private final String group;

  public ContactData(String lastName, String firstName, String nickname, String title, String company, String address, String mobile, String email, String bDay, String bMonth, String bYear, String group) {
    this.id = Integer.MAX_VALUE;
    this.lastName = lastName;
    this.firstName = firstName;
    this.nickname = nickname;
    this.title = title;
    this.company = company;
    this.address = address;
    this.mobile = mobile;
    this.email = email;
    this.bDay = bDay;
    this.bMonth = bMonth;
    this.bYear = bYear;
    this.group = group;
  }

  public ContactData(int id,String lastName, String firstName, String nickname, String title, String company, String address, String mobile, String email, String bDay, String bMonth, String bYear, String group) {
    this.id = id;
    this.lastName = lastName;
    this.firstName = firstName;
    this.nickname = nickname;
    this.title = title;
    this.company = company;
    this.address = address;
    this.mobile = mobile;
    this.email = email;
    this.bDay = bDay;
    this.bMonth = bMonth;
    this.bYear = bYear;
    this.group = group;
  }

  public String getLastName() {
    return lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getNickname() {
    return nickname;
  }

  public String getTitle() {
    return title;
  }

  public String getCompany() {
    return company;
  }

  public String getAddress() {
    return address;
  }

  public String getMobile() {
    return mobile;
  }

  public String getEmail() {
    return email;
  }

  public String getbDay() {
    return bDay;
  }

  public String getMonth() {
    return bMonth;
  }

  public String getYear() {
    return bYear;
  }

  public String getGroup() {
    return group;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "lastName='" + lastName + '\'' +
            ", firstName='" + firstName + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContactData that = (ContactData) o;
    return Objects.equals(lastName, that.lastName) &&
            Objects.equals(firstName, that.firstName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lastName, firstName);
  }
}
