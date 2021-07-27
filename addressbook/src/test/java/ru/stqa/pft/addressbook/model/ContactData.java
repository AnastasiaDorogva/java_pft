package ru.stqa.pft.addressbook.model;

public class ContactData {
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
}
