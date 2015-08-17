package com.hyc.spider.goods;

import java.util.HashMap;
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

  String JD_SELLER ="1005" ;
  String Z_SELLER = "1011" ;

  
  @RequestMapping(value = "/goods/query")
  public String getProduct(HttpServletRequest request, HttpServletResponse response,
      ModelMap model, String product_url, String sellercode,String classiccode, String format) throws Exception {
    if (StringUtils.isEmpty(format)) format = ConstParam.FORMAT_XML;
    model.addAttribute("format", format);
    model.addAttribute("product_url", product_url);
    model.addAttribute("sellercode", sellercode);
    System.out.println(String.format("product_url:%s,sellercode=%s,format=%s", product_url,
        sellercode, format));
    Map<String, String> paramMap = new HashMap<String, String>();

    String purl = StringUtils.trimToEmpty(product_url);
    Product resp = null ;
    if(StringUtils.isEmpty(purl)){
      _log.warn("input product_url is empty,try again!");
    }else{
      String urls[] = purl.split(",");
      for (String url : urls) {
        if (JD_SELLER.equals(sellercode)) {
          Document doc = jdParser.getTargetHtml(product_url, paramMap);
          if (doc != null) {
            Product p = new Product();
            p.setClassicCode(classiccode);
            p.setSellerCode(JD_SELLER);
            p.setProduct_url(product_url);
            jdParser.parserGoods(doc, p);
            ProductsDao.save(p);
            resp = p ;
          }
        } else if(Z_SELLER.equals(sellercode)) {
          Document doc = zParser.getTargetHtml(product_url, paramMap);
          if (doc != null) {
            Product p = new Product();
            p.setClassicCode(classiccode);
            p.setSellerCode(Z_SELLER);
            p.setProduct_url(product_url);
            zParser.parserGoods(doc, p);
//            ProductsDao.save(p);
            resp = p ;
          }
        }
      }
    }
      
   

    
    model.addAttribute("product", resp);

    return "/goods/list_show";
  }


}
