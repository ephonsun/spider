package com.hyc.spider.goods.obj;

import java.util.List;



public class FetchObj {

  String pid;
  String url;
  String maincat;
  String secondcat;
  String classiccode;
  String sellercode;
  int range;

  List<String> extUrls;



  public int getRange() {
    return range;
  }

  public void setRange(int range) {
    this.range = range;
  }

  public List<String> getExtUrls() {
    return extUrls;
  }

  public void setExtUrls(List<String> extUrls) {
    this.extUrls = extUrls;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getMaincat() {
    return maincat;
  }

  public void setMaincat(String maincat) {
    this.maincat = maincat;
  }

  public String getSecondcat() {
    return secondcat;
  }

  public void setSecondcat(String secondcat) {
    this.secondcat = secondcat;
  }

  public String getClassiccode() {
    return classiccode;
  }

  public void setClassiccode(String classiccode) {
    this.classiccode = classiccode;
  }

  public String getSellercode() {
    return sellercode;
  }

  public void setSellercode(String sellercode) {
    this.sellercode = sellercode;
  }

  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }


  @Override
  public String toString() {
    return "url=" + url + ",extUrls=" + (extUrls == null ? 0 : extUrls.size()) + ", maincat="
        + maincat + ", secondcat=" + secondcat + ", classiccode=" + classiccode + ", sellercode="
        + sellercode + ", pid=" + pid + ",range=" + range;
  }

}
