package com.hyc.spider.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.hyc.spider.goods.obj.Product;

/**
 * 商品信息表 事务操作
 * 
 * @author candywon - candywon@qq.com
 */

public class ProductsDao extends BaseDao {
	private static final Logger	logger	= Logger.getLogger(ProductsDao.class);

	public static boolean save(Product p) {
      Connection conn = getConnection();
      PreparedStatement ps = null;
      boolean result = true;
      // 入库新抓取的商品信息
      String sql = "insert into bjdg_products(productName,shortName,"
              + "brand,price,contents,smallPic,bigPic,seller,website,product_url,"
              + "pid,cid,keyword,productLikerCount,groupbuyEndTime,groupbuyOnTime,type,"
              + "createTime,updateTime,sellerCount,maxPrice,minPrice,classic,classicCode,mparams,orgPic,"
              + "sellerCode,productBuyerCount)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
              + "ON DUPLICATE KEY UPDATE groupbuyEndTime=VALUES(groupbuyEndTime),price=VALUES(price),"
              + "productLikerCount=VALUES(productLikerCount),updateTime=VALUES(updateTime),sellerCount=VALUES(sellerCount),"
              + "productName=VALUES(productName),maxPrice=VALUES(maxPrice),minPrice=VALUES(minPrice),contents=VALUES(contents),"
              + "classic=VALUES(classic),mparams=VALUES(mparams),orgPic=VALUES(orgPic),type=VALUES(type),groupbuyOnTime=VALUES(groupbuyOnTime),"
              + "smallPic=VALUES(smallPic),bigPic=VALUES(bigPic),brand=VALUES(brand),product_url=VALUES(product_url),sellerCode=VALUES(sellerCode),"
              + "website=VALUES(website),seller=VALUES(seller),productBuyerCount=VALUES(productBuyerCount),"
              + "shortName=VALUES(shortName)";

      try {
          conn.setAutoCommit(false);
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
          ps.setInt(14, p.getProductLikerCount());
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
          ps.setInt(28, p.getProductBuyerCount());
          int i = ps.executeUpdate();
          logger.info(String.format("同步数据1条记录，影响[%s]条记录(update情况下一条记录影响两条记录)", i));
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

}
