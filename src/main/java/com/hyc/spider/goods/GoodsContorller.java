package com.hyc.spider.goods;

import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hyc.spider.db.ProductsDao;
import com.hyc.spider.goods.obj.Product;
import com.hyc.spider.inf.ParserGoodsInf;
import com.hyc.spider.util.ConstParam;



@Controller
public class GoodsContorller {
  private static final Logger _log = LoggerFactory.getLogger(GoodsContorller.class);

  @Autowired
  @Qualifier("jdParser")
  ParserGoodsInf jdParser;

  @Autowired
  @Qualifier("zParser")
  ParserGoodsInf zParser;

  @Autowired
  @Qualifier("jdListParser")
  ParserGoodsInf jdListParser;

  String JD_SELLER = "1005";
  String Z_SELLER = "1011";


  @RequestMapping(value = "/goods/query")
  public String getProduct(HttpServletRequest request, HttpServletResponse response,
      ModelMap model, String product_url, String sellercode, String format, Integer range,
      String classiccode, String maincat, String secondcat) throws Exception {
    String purl = StringUtils.trimToEmpty(product_url);
    if (StringUtils.isEmpty(format)) format = ConstParam.FORMAT_XML;
    model.addAttribute("format", format);
    model.addAttribute("product_url", product_url);
    model.addAttribute("sellercode", sellercode);
    System.out.println(String.format(
        "product_url:%s,sellercode=%s,format=%srange:%s,classiccode:%s,"
            + "maincat:%s,secondcat:%s", product_url, sellercode, format, range, classiccode,
        maincat, secondcat));
    model.addAttribute("classiccode", classiccode);
    model.addAttribute("range", range);
    model.addAttribute("maincat", maincat);
    model.addAttribute("secondcat", secondcat);

    Map<String, String> paramMap = new HashMap<String, String>();

    Product resp = null;
    if (StringUtils.isEmpty(purl)) {
      _log.warn("input product_url is empty,try again!");
    } else {
      Document doc = jdParser.getTargetHtml(product_url, paramMap);
      if (doc != null) {
        Product p = new Product();
        p.setClassicCode(classiccode);
        p.setSellerCode(JD_SELLER);
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
        resp = p;
      }
    }

    model.addAttribute("product", resp);

    return "/goods/list_show";
  }

  @RequestMapping(value = "/goods/list")
  public String getProductList(HttpServletRequest request, HttpServletResponse response,
      ModelMap model, String search_url, String format, Integer range, String classiccode,
      String maincat, String secondcat) throws Exception {
    if (StringUtils.isEmpty(format)) format = ConstParam.FORMAT_XML;
    search_url = StringUtils.trimToEmpty(URLDecoder.decode(search_url, "utf8"));
    System.out.println(String.format(
        "search_url:%s,format=%s,range:%s,classiccode:%s,maincat:%s,secondcat:%s", search_url,
        format, range, classiccode, maincat, secondcat));
    if (StringUtils.isEmpty(search_url)) {
      _log.warn("input search_url is empty,try again!");
      return "/goods/plist_show";
    }
    model.addAttribute("format", format);
    model.addAttribute("search_url", search_url);
    model.addAttribute("classiccode", classiccode);
    model.addAttribute("range", range);
    model.addAttribute("maincat", maincat);
    model.addAttribute("secondcat", secondcat);
    Map<String, String> paramMap = new HashMap<String, String>();

    List<String> plist = null;
    Document doc = jdListParser.getTargetHtml(search_url, paramMap);
    if (doc != null) {
      plist = jdListParser.parserPage(doc);
    }

    model.addAttribute("plist", plist);

    return "/goods/plist_show";
  }


}
