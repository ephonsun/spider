package com.hyc.spider.model;

import java.io.Serializable;


public class P2pblack implements Serializable {
  
  private static final long serialVersionUID = 1224029124838882376L;
  String name; // 姓名
  String fee; // 欠款总额
  String penalty;// 总罚息
  String sum;// 逾期笔数
  String status; // 状态
  String idCard;// 身份证号
  String plat;// 平台
  String desc;// 详细
  
  
  public P2pblack() {
    super();
  }
  public P2pblack(String name, String fee, String penalty, String sum, String status,
      String idCard, String plat, String desc) {
    this.name = name;
    this.fee = fee;
    this.penalty = penalty;
    this.sum = sum;
    this.status = status;
    this.idCard = idCard;
    this.plat = plat;
    this.desc = desc;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getFee() {
    return fee;
  }
  public void setFee(String fee) {
    this.fee = fee;
  }
  public String getPenalty() {
    return penalty;
  }
  public void setPenalty(String penalty) {
    this.penalty = penalty;
  }
  public String getSum() {
    return sum;
  }
  public void setSum(String sum) {
    this.sum = sum;
  }
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  public String getIdCard() {
    return idCard;
  }
  public void setIdCard(String idCard) {
    this.idCard = idCard;
  }
  public String getPlat() {
    return plat;
  }
  public void setPlat(String plat) {
    this.plat = plat;
  }
  public String getDesc() {
    return desc;
  }
  public void setDesc(String desc) {
    this.desc = desc;
  }

}
