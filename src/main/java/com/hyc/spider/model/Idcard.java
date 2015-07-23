package com.hyc.spider.model;

import java.io.Serializable;


public class Idcard implements Serializable {
  private static final long serialVersionUID = -257623268044916530L;

  String idcard;
  String address;
  String name;
  String male;

  public Idcard() {
    super();
  }


  public Idcard(String idcard, String address, String name, String male) {
    super();
    this.idcard = idcard;
    this.address = address;
    this.name = name;
    this.male = male;
  }


  public String getIdcard() {
    return idcard;
  }


  public void setIdcard(String idcard) {
    this.idcard = idcard;
  }


  public String getAddress() {
    return address;
  }


  public void setAddress(String address) {
    this.address = address;
  }


  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public String getMale() {
    return male;
  }


  public void setMale(String male) {
    this.male = male;
  }



}
