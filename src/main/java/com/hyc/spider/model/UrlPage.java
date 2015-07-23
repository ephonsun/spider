package com.hyc.spider.model;

import java.io.Serializable;



public class UrlPage implements Serializable {

  private static final long serialVersionUID = 249370967683926066L;

  String desc; // 分页数字或文字
  String url; // 分页url

  public UrlPage() {
    super();

  }

  public UrlPage(String desc, String url) {
    super();
    this.desc = desc;
    this.url = url;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  
}
