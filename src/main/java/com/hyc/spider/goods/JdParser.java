package com.hyc.spider.goods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.hyc.spider.BaseParser;
import com.hyc.spider.exception.BusiException;
import com.hyc.spider.goods.obj.Product;
import com.hyc.spider.inf.ParserGoodsInf;


@Component("jdParser")
public class JdParser extends BaseParser implements ParserGoodsInf {

  private static final Logger _log = LoggerFactory.getLogger(JdParser.class);

  Pattern pattern = Pattern.compile("http\\://item\\.jd\\.com/([0-9]+).html.*?");

  @Override
  public Document getTargetHtml(String targetUrl, Map paramMap) throws BusiException {
    return super.getTargetHtml(targetUrl, paramMap);

  }

  @Override
  public void parserGoods(Document doc, Product p) throws BusiException {
    getProductName(doc, p);
    String skuid = getSkuid(doc, p);
    getPrice(doc, p, skuid);
    getContents(doc, p);
  }

  @Override
  public List parserContent(Document doc) throws BusiException {
    return null;
  }
  
  public void getContents(Document doc,Product p) throws BusiException {
    
    Element e = doc.getElementById("product-detail-1");
    Elements elements = e.select("div.p-parameter");
    int elen = elements.size();
    String text = elements.html();
    StringBuilder sb = new StringBuilder();
    text = text.replaceAll("\\s+", " ");
    text = text.replaceAll("<.*?>", " ");
    text = text.replaceAll("￣| ", " ").trim();//| 
    text = text.replaceAll("\\s{3,}", "  ") + "\n";
    String tagetPhrase = "[\\S]*加载[\\S]*" +
        "|正\\s*[\\S]*加载中[\\S]*" + "|请稍候[\\s\\S]*加载中[\\S]*" + "|纠错";
    text=text.replaceAll(tagetPhrase, "");
    System.out.println(text);
    p.setContents(text);
  }

  
  public String getSkuid(Document doc, Product p) {
    String skuid = null;
    Matcher m = pattern.matcher(p.getProduct_url().toLowerCase());
    if (m.find()) {
      skuid = m.group(1);
    }
    if (StringUtils.isNotEmpty(skuid)) {
      String pid = p.getSellerCode() + StringUtils.trim(skuid);
      p.setPid(pid);
    }

    return skuid;
  }

  private void getPrice(Document doc, Product p, String skuid) {
    String priceUrl =
        "http://p.3.cn/prices/get?skuid=J_" + skuid + "&type=1&callback=changeImgPrice2Num";
    String priceJson = null;
    String resString = null;
    try {
      resString = getTargetHtmlByJson(priceUrl, new HashMap<String, String>());
    } catch (BusiException e) {
      _log.error(e.getMessage(), e);
    }

    if (StringUtils.isNotEmpty(resString)) {
      resString = resString.replaceAll("[\\s]{2,}", " ");
      int beginIndex = resString.indexOf("[") + 1;
      int endIndex = resString.indexOf("]");
      if (beginIndex >= 0 && endIndex >= 0) {
        resString = resString.substring(beginIndex, endIndex);// 得到json字符串
      }
    }
    priceJson = resString ;
    System.out.println(resString);
    String price = "";
    String maxprice = "";
    if (StringUtils.isNotEmpty(priceJson)) {
      JSONObject o = JSONObject.parseObject(priceJson);
      price = o.getString("p");
      maxprice = o.getString("m");
    }
    price = price.replaceAll("[,，]", "");
    maxprice = maxprice.replaceAll("[,，]", "");
    double max, min = 0;
    max = min = StringUtils.isEmpty(price) ? 0 : Double.parseDouble(price);
    max = StringUtils.isEmpty(maxprice) ? max : Double.parseDouble(maxprice);

    p.setPrice(price);
    p.setMaxPrice(max);
    p.setMinPrice(min);
    p.setMinMarketPrice(price);
    System.out
        .println(String.format("price=%s,max=%s,min=%s,minmarket=%s", price, max, min, price));
  }

  private void getProductName(Document doc, Product p) {
    Element e = doc.getElementById("name");
    Elements elements = e.select("h1");
    String productName = elements == null ? "" : StringUtils.trim(elements.text());
    p.setProductName(productName);
    System.out.println("productName=" + productName);
  }

  @Override
  public List parserPage(Document doc) throws BusiException {
    return null;
  }


}
