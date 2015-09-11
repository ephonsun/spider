package com.hyc.spider.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.hyc.spider.goods.obj.TimeCtl;


public class TimeCtlDao extends BaseDao {
  private static final Logger logger = Logger.getLogger(TimeCtlDao.class);

  public static boolean save(TimeCtl t) {
    Connection conn = getConnection();
    PreparedStatement ps = null;
    boolean result = true;
    // 入库新抓取的商品信息
    String sql =
        "insert into bjdg_timeCtl(aCatCode,shuffleTime,ctlCode,createTime,updateTime"
            + ")values(?,?,?,?,?)";
    try {
      conn.setAutoCommit(false);
      ps = conn.prepareStatement(sql);
      ps.setString(1, t.getaCatCode());
      ps.setLong(2, t.getShuffleTime());
      ps.setInt(3, t.getCtlCode());
      ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
      ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
      int i = ps.executeUpdate();
      logger.info(String.format("bjdg_timeCtl数据%s条记录", i));
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
