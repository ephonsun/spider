package com.hyc.spider.goods.obj;

import java.util.Date;



public class TimeCtl {
  
  private String aCatCode;
  private long shuffleTime; // 散列发现数据通知时间
  private int ctlCode;

  private long intervalTime = 10 * 60 * 1000;// 在通知时间后间隔时间内不更新时间，默认10分钟,毫秒单位
  private int dbLimit = 1000;// 该类从打乱后的数据中记录数下限，默认1000条记录
  private int searchLimit = 1000;// 该类搜索发现数据下限，默认1000条记录
  private long cacheExpireTime = 24 * 60 * 60 * 1000;// 该类数据的缓存失效时间，单位ms，默认24小时
  private int state = 1;
  private Date createTime = new Date();
  private Date updateTime = createTime;
  
  
  
  public TimeCtl(String aCatCode, long shuffleTime, int ctlCode) {
    this();
    this.aCatCode = aCatCode;
    this.shuffleTime = shuffleTime;
    this.ctlCode = ctlCode;
  }
  
  
  public TimeCtl() {
    super();
  }
  public String getaCatCode() {
    return aCatCode;
  }
  public void setaCatCode(String aCatCode) {
    this.aCatCode = aCatCode;
  }
  public long getShuffleTime() {
    return shuffleTime;
  }
  public void setShuffleTime(long shuffleTime) {
    this.shuffleTime = shuffleTime;
  }
  public int getCtlCode() {
    return ctlCode;
  }
  public void setCtlCode(int ctlCode) {
    this.ctlCode = ctlCode;
  }
  public long getIntervalTime() {
    return intervalTime;
  }
  public void setIntervalTime(long intervalTime) {
    this.intervalTime = intervalTime;
  }
  public int getDbLimit() {
    return dbLimit;
  }
  public void setDbLimit(int dbLimit) {
    this.dbLimit = dbLimit;
  }
  public int getSearchLimit() {
    return searchLimit;
  }
  public void setSearchLimit(int searchLimit) {
    this.searchLimit = searchLimit;
  }
  public long getCacheExpireTime() {
    return cacheExpireTime;
  }
  public void setCacheExpireTime(long cacheExpireTime) {
    this.cacheExpireTime = cacheExpireTime;
  }
  public int getState() {
    return state;
  }
  public void setState(int state) {
    this.state = state;
  }
  public Date getCreateTime() {
    return createTime;
  }
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
  public Date getUpdateTime() {
    return updateTime;
  }
  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }
  
  
  
}
