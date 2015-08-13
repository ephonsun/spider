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

import com.hyc.spider.goods.obj.Product;
import com.hyc.spider.inf.ParserGoodsInf;
import com.hyc.spider.util.ConstParam;



@Controller
public class GoodsContorller {
  private static final Logger _log = LoggerFactory.getLogger(GoodsContorller.class);

  @Autowired
  @Qualifier("jdParser")
  ParserGoodsInf jdParser;

  @RequestMapping(value = "/goods/query")
  public String getProduct(HttpServletRequest request, HttpServletResponse response,
      ModelMap model, String product_url, String sellercode, String format) throws Exception {
    if (StringUtils.isEmpty(format)) format = ConstParam.FORMAT_XML;
    model.addAttribute("format", format);
    model.addAttribute("product_url", product_url);
    model.addAttribute("sellercode", sellercode);
    System.out.println(String.format("product_url:%s,sellercode=%s,format=%s", product_url,
        sellercode, format));
    Map<String, String> paramMap = new HashMap<String, String>();

    Product p = new Product();
    if ("1005".equals(sellercode)) {
      Document doc = jdParser.getTargetHtml(product_url, paramMap);
      if (doc != null) {

        p.setSellerCode("1005");
        p.setProduct_url(product_url);
        jdParser.parserGoods(doc, p);
      }
    } else {

    }

    model.addAttribute("product", p);

    return "/goods/list_show";
  }


}
