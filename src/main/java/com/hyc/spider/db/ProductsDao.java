package com.hyc.spider.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Random;

import org.apache.log4j.Logger;

import com.hyc.spider.goods.obj.Product;

/**
 * 商品信息表 事务操作
 * 
 * @author candywon - candywon@qq.com
 */

public class ProductsDao extends BaseDao {
  private static final Logger logger = Logger.getLogger(ProductsDao.class);
  static int max=200;
  static int min=0;

  static int randomInt(){
    Random random = new Random();
    int s = random.nextInt(max)%(max-min+1) + min;
    return s ;
  }
  public static boolean save(Product p) {
    Connection conn = getConnection();
    PreparedStatement ps = null;
    boolean result = true;
    // 入库新抓取的商品信息
    String sql =
        "insert into bjdg_products(productName,shortName,"
            + "brand,price,contents,smallPic,bigPic,seller,website,product_url,"
            + "pid,cid,keyword,productLikerCount,groupbuyEndTime,groupbuyOnTime,type,"
            + "createTime,updateTime,sellerCount,maxPrice,minPrice,classic,classicCode,mparams,orgPic,"
            + "sellerCode,productBuyerCount,comments,minMarketPrice,disPrice,dratio,dcratio"
            + ")values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
            + "ON DUPLICATE KEY UPDATE groupbuyEndTime=VALUES(groupbuyEndTime),price=VALUES(price),"
            + "productLikerCount=VALUES(productLikerCount),updateTime=VALUES(updateTime),sellerCount=VALUES(sellerCount),"
            + "productName=VALUES(productName),maxPrice=VALUES(maxPrice),minPrice=VALUES(minPrice),contents=VALUES(contents),"
            + "classic=VALUES(classic),mparams=VALUES(mparams),orgPic=VALUES(orgPic),type=VALUES(type),groupbuyOnTime=VALUES(groupbuyOnTime),"
            + "smallPic=VALUES(smallPic),bigPic=VALUES(bigPic),brand=VALUES(brand),product_url=VALUES(product_url),sellerCode=VALUES(sellerCode),"
            + "website=VALUES(website),seller=VALUES(seller),"
            + "productBuyerCount=VALUES(productBuyerCount),comments=VALUES(comments),"
            + "shortName=VALUES(shortName),minMarketPrice=VALUES(minMarketPrice),"
            + "disPrice=VALUES(disPrice),dratio=VALUES(dratio),dcratio=VALUES(dcratio)";

    try {
      conn.setAutoCommit(false);
      int productbuycount = randomInt();
      int productlikecount = randomInt()+randomInt();
      ps = conn.prepareStatement(sql);
      ps.setString(1, p.getProductName());
      ps.setString(2, p.getShortName());
      ps.setString(3, p.getBrand());
      ps.setString(4, p.getPrice());
      ps.setString(5, p.getContents());
      ps.setString(6, p.getSmallPic());
      ps.setString(7, p.getBigPic());
      ps.setString(8, p.getSeller());
      ps.setString(9, p.getWebsite());
      ps.setString(10, p.getProduct_url());
      ps.setString(11, p.getPid());
      ps.setString(12, p.getCid());
      ps.setString(13, p.getKeyword());
      ps.setInt(14, productlikecount);
      ps.setLong(15, p.getGroupbuyEndTime());
      ps.setLong(16, p.getGroupbuyOnTime());
      ps.setInt(17, p.getType());
      ps.setTimestamp(18, new Timestamp(System.currentTimeMillis()));
      ps.setTimestamp(19, new Timestamp(System.currentTimeMillis()));
      ps.setInt(20, p.getSellerCount());
      ps.setDouble(21, p.getMaxPrice());
      ps.setDouble(22, p.getMinPrice());
      ps.setString(23, p.getClassic());
      ps.setString(24, p.getClassicCode());
      ps.setString(25, p.getMparams());
      ps.setString(26, p.getOrgPic());
      ps.setString(27, p.getSellerCode());
      ps.setInt(28, productbuycount);
      ps.setInt(29, p.getComments());
      ps.setString(30, p.getMinMarketPrice());
      ps.setDouble(31, p.getDisPrice());
      ps.setDouble(32, p.getD1ratio());
      ps.setDouble(33, p.getD2ratio());
      System.out.println(p.getDisPrice()+"-"+p.getD1ratio()+"-" + p.getD2ratio());
      int i = ps.executeUpdate();
      logger.info(String.format("bjdg_products同步数据1条记录，影响[%s]条记录(update情况下一条记录影响两条记录)", i));
      conn.commit();
    } catch (SQLException ex) {
      logger.info("SQLException: " + ex.getMessage());
      logger.info("SQLState: " + ex.getSQLState());
      logger.info("Message: " + ex.getMessage());
      logger.info("Vendor error code: " + ex.getErrorCode());
      result = false;
    } finally {
      close(null, ps, conn);
    }
    return result;
  }

  public static void savewantbuy(String aCatCode, String pid, int totalComment) {
    Connection conn = getConnection();
    PreparedStatement ps = null;
    String sql =
        "insert into bjdg_wantbuy(aCatCode,pid,totalComment,createTime,updateTime)values(?,?,?,?,?)"
            + "ON DUPLICATE KEY UPDATE totalComment=VALUES(totalComment),updateTime=VALUES(updateTime)";

    try {
      conn.setAutoCommit(false);
      ps = conn.prepareStatement(sql);
      ps.setString(1, aCatCode);
      ps.setString(2, pid);
      ps.setInt(3, totalComment);
      ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
      ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
      int i = ps.executeUpdate();
      logger.info(String.format("bjdg_wantbuy同步数据1条记录，影响[%s]条记录(update情况下一条记录影响两条记录)", i));
      conn.commit();
    } catch (SQLException ex) {
      logger.info("SQLException: " + ex.getMessage());
      logger.info("SQLState: " + ex.getSQLState());
      logger.info("Message: " + ex.getMessage());
      logger.info("Vendor error code: " + ex.getErrorCode());
    } finally {
      close(null, ps, conn);
    }
  }


  public static void saveonsale(String aCatCode, String pid, long finalTime, double dratio,
      double dcratio) {
    Connection conn = getConnection();
    PreparedStatement ps = null;
    String sql =
        "insert into bjdg_onsale(aCatCode,pid,finalTime,dRatio,dcRatio,createTime,updateTime)values(?,?,?,?,?,?,?)"
            + "ON DUPLICATE KEY UPDATE finalTime=VALUES(finalTime),dRatio=VALUES(dRatio),"
            + "dcRatio=VALUES(dcRatio),updateTime=VALUES(updateTime)";

    try {
      conn.setAutoCommit(false);
      ps = conn.prepareStatement(sql);
      ps.setString(1, aCatCode);
      ps.setString(2, pid);
      ps.setLong(3, finalTime);
      ps.setDouble(4, dratio);
      ps.setDouble(5, dcratio);
      ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
      ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
      int i = ps.executeUpdate();
      logger.info(String.format("bjdg_onsale同步数据1条记录，影响[%s]条记录(update情况下一条记录影响两条记录)", i));
      conn.commit();
    } catch (SQLException ex) {
      logger.info("SQLException: " + ex.getMessage());
      logger.info("SQLState: " + ex.getSQLState());
      logger.info("Message: " + ex.getMessage());
      logger.info("Vendor error code: " + ex.getErrorCode());
    } finally {
      close(null, ps, conn);
    }


  }

  public static void savefind(String aCatCode, String pid, String position, double dratio,
      double dcratio, long finalTime) {
    Connection conn = getConnection();
    PreparedStatement ps = null;
    String sql =
        "insert into bjdg_shuffle(aCatCode,pid,position,finalTime,dratio,dcratio,createTime,updateTime)"
        + "values(?,?,?,?,?,?,?,?)"
            + "ON DUPLICATE KEY UPDATE finalTime=VALUES(finalTime),dratio=VALUES(dratio),"
            + "dcratio=VALUES(dcratio),updateTime=VALUES(updateTime)";

    try {
      conn.setAutoCommit(false);
      ps = conn.prepareStatement(sql);
      ps.setString(1, aCatCode);
      ps.setString(2, pid);
      ps.setString(3, position);
      ps.setLong(4, finalTime);
      ps.setDouble(5, dratio);
      ps.setDouble(6, dcratio);
      ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
      ps.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
      int i = ps.executeUpdate();
      logger.info(String.format("bjdg_shuffle同步数据1条记录，影响[%s]条记录(update情况下一条记录影响两条记录)", i));
      conn.commit();
    } catch (SQLException ex) {
      logger.info("SQLException: " + ex.getMessage());
      logger.info("SQLState: " + ex.getSQLState());
      logger.info("Message: " + ex.getMessage());
      logger.info("Vendor error code: " + ex.getErrorCode());
    } finally {
      close(null, ps, conn);
    }


  }

}
