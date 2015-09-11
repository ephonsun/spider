package com.hyc.spider.goods;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.hyc.spider.db.ProductsDao;
import com.hyc.spider.db.TimeCtlDao;
import com.hyc.spider.exception.BusiException;
import com.hyc.spider.goods.obj.FetchObj;
import com.hyc.spider.goods.obj.Product;
import com.hyc.spider.goods.obj.TimeCtl;
import com.hyc.spider.inf.ParserGoodsInf;

@Component("dataImpl")
public class DataImpl implements DataInf {
  private static final Logger _log = LoggerFactory.getLogger(DataImpl.class);


  @Autowired
  @Qualifier("jdParser")
  ParserGoodsInf jdParser;


  public Product datum(FetchObj o, String product_url) throws BusiException {
    String classiccode = o.getClassiccode();
    String sellercode = o.getSellercode();
    String maincat = o.getMaincat();
    String secondcat = o.getSecondcat();
    int range = o.getRange();

    Map<String, String> paramMap = new HashMap<String, String>();
    Document doc = jdParser.getTargetHtml(product_url, paramMap);
    Product p = null;
    if (doc != null) {
      p = new Product();
      p.setClassicCode(classiccode);
      p.setSellerCode(sellercode);
      p.setProduct_url(product_url);
      jdParser.parserGoods(doc, p);

      ProductsDao.save(p);
      String pid = p.getPid();
      String position = pid;
      double dratio = p.getD1ratio();
      double dcratio = p.getD2ratio();
      long finalTime = p.getFinalTime();
      int totalComment = p.getComments();
      Date createTime = p.getCreateTime();
      Date updateTime = p.getUpdateTime();

      switch (range) {
        case 1: // 首页 -- bjdg_wantbuy----
          String aCatCode = maincat + "#000";

          ProductsDao.savewantbuy(aCatCode, pid, totalComment);
          break;
        case 2: // 享优惠 -- bjdg_onsale ---
          aCatCode = maincat + "#000";

          ProductsDao.saveonsale(aCatCode, pid, finalTime, dratio, dcratio);
          break;
        case 3:// 发现 -- bjdg_shuffle---
          aCatCode = maincat + "#" + secondcat;
          ProductsDao.savefind(aCatCode, pid, pid, dratio, dcratio, finalTime);
          break;
        default:
          _log.warn("input range is not avaliable!");
          break;
      }
    }
    return p;
  }
  
  @Override
  public void subTimectl() throws BusiException {
    String[] df = {"D01", "D02", "D03", "D04", "D05", "D06", "D07", "D08", "D09", "D10"};
    String[] df2 = {"001", "002", "003"};
    String[] dh = {"D02", "D07", "D08", "D09"};
    long shuffleTime = System.currentTimeMillis() + 1000 * 60 * 10;
    int ctlCode = 3;
    List<TimeCtl> tlist = new ArrayList<TimeCtl>();
    for (String d1 : df) {
      // 发现
      for (String d2 : df2) {
        String aCatCode = d1 + "#" + d2;
        TimeCtl t = new TimeCtl(aCatCode, shuffleTime, ctlCode);
        tlist.add(t);
      }
    }
    ctlCode = 1; // 首页
    for (String d1 : dh) {
      String aCatCode = d1 + "#000";
      TimeCtl t = new TimeCtl(aCatCode, shuffleTime, ctlCode);
      tlist.add(t);
    }
    ctlCode = 2; // 享优惠
    for (String d1 : dh) {
      String aCatCode = d1 + "#000";
      TimeCtl t = new TimeCtl(aCatCode, shuffleTime, ctlCode);
      tlist.add(t);
    }

    for (TimeCtl t : tlist) {
      TimeCtlDao.save(t);
    }

  }


}
