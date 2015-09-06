package com.hyc.spider.goods.obj;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class Product implements Serializable, Cloneable, Comparable<Product> {

  // 每次增删字段应重新生成
  private static final long serialVersionUID = -3278773120532938531L;
  private String id = null; // 序列号

  private String classic = ""; // 产品分类
  private String classicCode = ""; // 产品分类
  private String shortName = ""; // 产品简称
  private String productName = ""; // 产品名称
  private String minMarketPrice = null; // 市场价格 add in
  // 20131023，返回最小的市场价格
  private double dRatio = 0; // 多个价格（价格区间）返回有最小折扣的数据
  private String price = null; // 价格
  private Double maxPrice = null; // 最大价格
  private Double minPrice = null; // 最小价格
  private String contents = ""; // 产品详情
  private String smallPic = ""; // 产品小图
  private String bigPic = ""; // 产品大图
  private String seller = ""; // 卖家
  private int isSeller = 0; // 是否是商家 1是 0否
  private int sellerCount = 0;
  private String website = ""; // 产品商家网站
  private String product_url = ""; // 产品详情地址

  private Date updateTime = null; // 入库时间
  private Date createTime = null; // 更新时间

  // 确定商品唯一性
  private String sellerCode = ""; // 卖家编码
  private String pid = ""; // 产品ID

  private String cid = ""; // 渠道ID
  private String keyword = ""; // 相关性关键词
  private float score = 0.0f;
  private List<Seller> sellerList = null;
  private int productLikerCount = 0; // 想买人数
  private int friendLikerCount = 0;
  private int productBuyerCount = 0; // 已买人数
  private long groupbuyEndTime = 0;
  private int groupbuyOnTime = 0;
  private int type = 0; // 商品分类 0-模糊商品
  // 1-团购商品 2-特价商品
  // 3-条码商品 4-搜索商品
  // 5-特别推荐 6-自有搜索商品等
  private String brand = "";
  private String mparams = ""; // 主要配置参数
  private String orgPic = ""; // 产品原始图片
  private Double decPrice = 0D; // 与想买价的差价 0-表示等于想买价
  // 大于0表示比想买价便宜的差价
  private int hasBuy = -1;

  // add in 2013-11-23 by huangyuchen 活动截止时间,活动价格，而后将dratio做为当前的折扣率对待。
  // 增加dsratio为销售价格的折扣。 disPrice 对应d1ratio， price对应 d2ratio ；需要有所判断
  // 对外只展示dRatio字段，该字段应该按照截止时间是否到期来决定是使用d1ratio,还是d2ratio的值
  // 以及 price是否是使用d1Price,还是d2Price
  private long finalTime = 0;
  private double disPrice = 0;
  private double d1ratio = 0;
  private double d2ratio = 0;
  private int comments = 0;

  public Product() {}

  public Product(String id, String shortName, String productName, String contents, String smallPic,
      String bigPic, String seller, int isSeller, int sellerCount, String website,
      String product_url, String pid, String cid, String keyword, float score,
      List<Seller> sellerList, int productLikerCount, long groupbuyEndTime, int productBuyerCount,
      int groupbuyOnTime, int type, String brand) {
    this.id = id;
    this.shortName = shortName;
    this.productName = productName;
    this.contents = contents;
    this.smallPic = smallPic;
    this.bigPic = bigPic;
    this.seller = seller;
    this.isSeller = isSeller;
    this.sellerCount = sellerCount;
    this.website = website;
    this.product_url = product_url;
    this.pid = pid;
    this.cid = cid;
    this.keyword = keyword;
    this.score = score;
    this.sellerList = sellerList;
    this.productLikerCount = productLikerCount;
    this.groupbuyEndTime = groupbuyEndTime;
    this.productBuyerCount = productBuyerCount;
    this.groupbuyOnTime = groupbuyOnTime;
    this.type = type;
    this.brand = brand;
    computRatio();
  }

  public String getMinMarketPrice() {
    return minMarketPrice;
  }

  public void setMinMarketPrice(String minMarketPrice) {
    this.minMarketPrice = minMarketPrice;
  }

  public double getdRatio() {
    computRatio();
    return dRatio;
  }

  public void setdRatio(double dRatio) {
    this.dRatio = dRatio;
  }

  public int getSellerCount() {
    return sellerCount;
  }

  public void setSellerCount(int sellerCount) {
    this.sellerCount = sellerCount;
  }

  public int getIsSeller() {
    return isSeller;
  }

  public void setIsSeller(int isSeller) {
    this.isSeller = isSeller;
  }

  public int getGroupbuyOnTime() {
    return groupbuyOnTime;
  }

  public void setGroupbuyOnTime(int groupbuyOnTime) {
    this.groupbuyOnTime = groupbuyOnTime;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getContents() {
    return contents;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public void setContents(String contents) {
    this.contents = contents;
  }

  public String getSmallPic() {
    return smallPic;
  }

  public void setSmallPic(String smallPic) {
    this.smallPic = smallPic;
  }

  public String getBigPic() {
    return bigPic;
  }

  public void setBigPic(String bigPic) {
    this.bigPic = bigPic;
  }

  public String getSeller() {
    return seller;
  }

  public void setSeller(String seller) {
    this.seller = seller;
  }

  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }

  public String getProduct_url() {
    return product_url;
  }

  public void setProduct_url(String product_url) {
    this.product_url = product_url;
  }

  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }

  public String getCid() {
    return cid;
  }

  public void setCid(String cid) {
    this.cid = cid;
  }

  public float getScore() {
    return score;
  }

  public void setScore(float score) {
    this.score = score;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public List<Seller> getSellerList() {
    return sellerList;
  }

  public void setSellerList(List<Seller> sellerList) {
    this.sellerList = sellerList;
    computRatio();
  }

  public int getProductLikerCount() {
    return productLikerCount;
  }

  public void setProductLikerCount(int productLikerCount) {
    this.productLikerCount = productLikerCount;
  }

  public long getGroupbuyEndTime() {
    return groupbuyEndTime;
  }

  public void setGroupbuyEndTime(long groupbuyEndTime) {
    this.groupbuyEndTime = groupbuyEndTime;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public int getProductBuyerCount() {
    return productBuyerCount;
  }

  public void setProductBuyerCount(int productBuyerCount) {
    this.productBuyerCount = productBuyerCount;
  }

  public Double getMaxPrice() {
    return maxPrice;
  }

  public void setMaxPrice(Double maxPrice) {
    this.maxPrice = maxPrice;
  }

  public Double getMinPrice() {
    return minPrice;
  }

  public void setMinPrice(Double minPrice) {
    this.minPrice = minPrice;
  }

  public String getClassic() {
    return classic;
  }

  public void setClassic(String classic) {
    this.classic = classic;
  }

  public String getClassicCode() {
    return classicCode;
  }

  public void setClassicCode(String classicCode) {
    this.classicCode = classicCode;
  }

  public int getFriendLikerCount() {
    return friendLikerCount;
  }

  public void setFriendLikerCount(int friendLikerCount) {
    this.friendLikerCount = friendLikerCount;
  }

  public String getMparams() {
    return mparams;
  }

  public void setMparams(String mparams) {
    this.mparams = mparams;
  }

  public String getOrgPic() {
    return orgPic;
  }

  public void setOrgPic(String orgPic) {
    this.orgPic = orgPic;
  }

  public String getSellerCode() {
    return sellerCode;
  }

  public void setSellerCode(String sellerCode) {
    this.sellerCode = sellerCode;
  }

  public Double getDecPrice() {
    return decPrice;
  }

  public void setDecPrice(Double decPrice) {
    this.decPrice = decPrice;
  }

  public int getHasBuy() {
    return hasBuy;
  }

  public void setHasBuy(int hasBuy) {
    this.hasBuy = hasBuy;
  }

  public long getFinalTime() {
    return finalTime;
  }

  public void setFinalTime(long finalTime) {
    this.finalTime = finalTime;
  }

  public int getComments() {
    return comments;
  }

  public void setComments(int comments) {
    this.comments = comments;
  }

  private void computRatio() {
    int sellerListSize = this.sellerList == null ? 0 : this.sellerList.size();
    if (sellerListSize > 1) {// 多商家列表中标注最高原价
      double minMarketPrice = 0;
      double maxCurPrice = 0;
      double minCurPrice = 0;
      double minDratio = 0;
      for (Seller s : sellerList) {
        double curMarketPriceTmp = s.getMarketPrice();
        String priceStr = s.getPrice();
        double disPrice = s.getDisPrice();
        double price = StringUtils.isEmpty(priceStr) ? 0 : Double.parseDouble(priceStr);
        double d1ratio = s.getDratio();
        double d2ratio = s.getDcratio();
        long finalTime = s.getFinalTime();
        long currentTime = System.currentTimeMillis();

        double curPriceTmp = 0;
        double dratioTmp = 0;

        if (disPrice > 0) {
          if (finalTime > currentTime) {
            dratioTmp = d1ratio;
            curPriceTmp = disPrice;
          } else {
            dratioTmp = d2ratio;
            curPriceTmp = price;
          }
        } else {
          dratioTmp = d2ratio;
          curPriceTmp = price;
        }

        if (dratioTmp > 0) {
          minDratio = minDratio > 0 ? minDratio <= dratioTmp ? minDratio : dratioTmp : dratioTmp;
        }
        if (curPriceTmp > 0) {
          minCurPrice =
              minCurPrice > 0
                  ? minCurPrice <= curPriceTmp ? minCurPrice : curPriceTmp
                  : curPriceTmp;
        }
        minMarketPrice = Math.max(curMarketPriceTmp, minMarketPrice);
        maxCurPrice = Math.max(curPriceTmp, maxCurPrice);
      }
      this.dRatio = minDratio;
      this.maxPrice = maxCurPrice;
      this.minPrice = minCurPrice;
      this.minMarketPrice = minMarketPrice + "";
      this.price =
          (maxCurPrice == minCurPrice ? minCurPrice + "" : minCurPrice + "-" + maxCurPrice);
    } else if (sellerListSize == 1) {
      Seller s = sellerList.get(0);
      double curMarketPriceTmp = s.getMarketPrice();
      String priceStr = s.getPrice();
      double price = StringUtils.isEmpty(priceStr) ? 0 : Double.parseDouble(priceStr);
      disPrice = s.getDisPrice();
      d1ratio = s.getDratio();
      d2ratio = s.getDcratio();

      long currentTime = System.currentTimeMillis();
      long finalTimeTmp = s.getFinalTime();

      double curPriceTmp = 0;
      double dratioTmp = 0;
      if (disPrice > 0) {
        if (finalTimeTmp > currentTime) {
          dratioTmp = d1ratio;
          curPriceTmp = disPrice;
          finalTime = finalTimeTmp;
        } else {
          dratioTmp = d2ratio;
          curPriceTmp = price;
        }
      } else {
        dratioTmp = d2ratio;
        curPriceTmp = price;
      }

      this.minMarketPrice = curMarketPriceTmp + "";
      this.dRatio = dratioTmp;
      this.maxPrice = this.minPrice = curPriceTmp;
      this.price = curPriceTmp + "";

    }

  }

  public void freshPrice() {
    if (sellerCount == 1) {
      long curTime = System.currentTimeMillis();
      Seller s = sellerList.get(0);
      String priceStr = s.getPrice();
      double curMarketPriceTmp = s.getMarketPrice();
      double priceDouble = StringUtils.isEmpty(priceStr) ? 0 : Double.parseDouble(priceStr);
      if (disPrice > 0) {
        if (finalTime > curTime) {
          dRatio = d1ratio;
          price = disPrice + "";
          this.maxPrice = this.minPrice = disPrice;
        } else {
          dRatio = d2ratio;
          price = priceDouble + "";
          this.maxPrice = this.minPrice = priceDouble;
        }
      }

      if (curMarketPriceTmp < priceDouble) {
        this.minMarketPrice = "";
        this.dRatio = 0;
      }
    }

  }

  public int compareTo(Product o) {
    String price1 = this.getPrice().split("-")[0];// .replaceAll("\\s", "").replaceAll("\\.00",
    // "").replaceAll("\\.",
    // "").replaceAll("\\,", "").replaceAll("￥",
    // "");
    String price2 = o.getPrice().split("-")[0];// .replaceAll("\\s", "").replaceAll("\\.00",
    // "").replaceAll("\\.", "").replaceAll("\\,",
    // "").replaceAll("￥", "");

    double iprice1 = 0;
    double iprice2 = 0;

    try {
      iprice1 = Double.parseDouble(price1);
      iprice2 = Double.parseDouble(price2);
    } catch (Exception ex) {
      ex.printStackTrace();
      return 0;
    }

    if (iprice2 > iprice1) {
      return -1;
    } else if (iprice1 > iprice2) {
      return 1;
    }

    return 0;
  }

  public double getDisPrice() {
    return disPrice;
  }

  public void setDisPrice(double disPrice) {
    this.disPrice = disPrice;
  }

  public double getD1ratio() {
    return d1ratio;
  }

  public void setD1ratio(double d1ratio) {
    this.d1ratio = d1ratio;
  }

  public double getD2ratio() {
    return d2ratio;
  }

  public void setD2ratio(double d2ratio) {
    this.d2ratio = d2ratio;
  }

}
