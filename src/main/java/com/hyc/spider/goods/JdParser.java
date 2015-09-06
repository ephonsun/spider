package com.hyc.spider.goods;

import java.util.ArrayList;
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
import com.hyc.spider.goods.obj.Seller;
import com.hyc.spider.inf.ParserGoodsInf;
import com.hyc.spider.util.PriceTool;


@Component("jdParser")
public class JdParser extends BaseParser implements ParserGoodsInf {
  private static final Logger _log = LoggerFactory.getLogger(JdParser.class);
  protected String XMTAG = "[xm99]";
  Pattern pattern = Pattern.compile("http\\://item\\.jd\\.com/([0-9]+).html.*?");

  String sellerCode = "1005";
  String seller = "京东商城";

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
    getMparams(doc, p);
    getPic(doc, p);
    getSeller(p);
    getFinalTime(p);
    getComments(p, skuid);

  }

  private void getFinalTime(Product p) {
    p.setFinalTime(0);
  }

  private void getComments(Product p, String skuid) {
    long ct = System.currentTimeMillis();
    String totalCommentUrl =
        "http://club.jd.com/productpage/p-" + skuid + "-s-0-t-3-p-0.html?callback=jsonp" + ct
            + "&_=" + ct;
    totalCommentUrl = "http://club.jd.com/productpage/p-" + skuid + "-s-0-t-3-p-0.html";
    String resString = null;
    try {
      resString = getTargetHtmlByJson(totalCommentUrl, new HashMap<String, String>());
    } catch (BusiException e) {
      _log.error(e.getMessage(), e);
    }

    int comments = 0;
    String cc = null;
    if (StringUtils.isNotEmpty(resString)) {
      JSONObject o = JSONObject.parseObject(resString);
      String ps = o.getString("productCommentSummary");
      if (StringUtils.isNotEmpty(ps)) {
        JSONObject oo = JSONObject.parseObject(ps);
        cc = oo.getString("commentCount");
      }
    }

    comments = StringUtils.isNotEmpty(cc) ? Integer.parseInt(cc) : 0;
    p.setComments(comments);
    System.out.println(String.format("comments=%s", comments));


  }

  @Override
  public List parserContent(Document doc) throws BusiException {
    return null;
  }


  public void getSeller(Product p) throws BusiException {
    p.setSeller(seller);
    p.setSellerCode(sellerCode);
    p.setSellerCount(1);
    List<Seller> sellerList = new ArrayList<Seller>();
    Seller s = new Seller();
    s.setPrice(p.getPrice());
    s.setProductName(p.getProductName());
    s.setSellerCode(sellerCode);
    s.setSellerName(seller);
    s.setSellerUrl(p.getProduct_url());
    sellerList.add(s);
    p.setSellerList(sellerList);
  }

  public void getPic(Document doc, Product p) throws BusiException {
    Element e = doc.getElementById("spec-n1");
    Elements es = e.select("img");
    int eslen = es.size();
    String pic = "";
    if (eslen > 0) {
      pic = es.get(0).attr("src");
    }
    p.setBigPic(pic);
    p.setSmallPic(pic);
    p.setOrgPic(pic);
  }

  public void getMparams(Document doc, Product p) throws BusiException {
    StringBuilder sb = new StringBuilder();
    Element e1 = doc.getElementById("product-detail-2");
    if (e1 == null) {
      sb.append(" ");
    } else {
      Elements es = e1.select("table.Ptable>tbody>tr");
      int eslen = es.size();
      for (int i = 0; i < eslen; i++) {
        Element e = es.get(i);
        Elements etd = e.select("tr>td");
        Elements eth = e.select("tr>th");
        int etdlen = etd.size();
        int ethlen = eth.size();
        if (etdlen <= 0 && ethlen <= 0) {
          continue;
        }
        if (etdlen < 1) {
          sb.append(e.text()).append(XMTAG).append(XMTAG);
        } else {
          for (int j = 0; j < etdlen; j++) {
            Element etdn = etd.get(j);
            sb.append(etdn.text()).append(XMTAG);
          }
        }

      }
    }
    p.setMparams(sb.toString());
  }

  public void getContents(Document doc, Product p) throws BusiException {
    StringBuilder sb = new StringBuilder("    ");
    Element pe1 = doc.getElementById("parameter1");
    if (null != pe1) {
      Elements pes1 = pe1.select("div.detail>p");
      int pes1len = pes1.size();
      System.out.println("pes1len=" + pes1len);
      for (int i = 0; i < pes1len; i++) {
        Element e = pes1.get(i);
        String text = StringUtils.trim(e.text());
        if (text.length() > 0) {
          sb.append(text).append(XMTAG);
        }
      }
    }

    Element pe2 = doc.getElementById("parameter2");
    if (null != pe2) {
      Elements pes2 = pe2.select("li");
      int pes2len = pes2.size();
      for (int i = 0; i < pes2len; i++) {
        Element e = pes2.get(i);
        String text = StringUtils.trim(e.text());
        if (text.length() > 0) {
          sb.append(text).append(XMTAG);
        }
      }
    }

    String contents = sb.toString();
    p.setContents(contents);
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
    priceJson = resString;
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

    double d1ratioByDouble = 0.00;
    String dicountPrice = price;
    String marketPrice = maxprice;
    double curPrice = StringUtils.isEmpty(dicountPrice) ? 0 : Double.parseDouble(dicountPrice);
    double curMarketPrice = StringUtils.isEmpty(marketPrice) ? 0 : Double.parseDouble(marketPrice);
    if (curPrice > 0 && curMarketPrice > curPrice) {// 只保留小数点后两位
      d1ratioByDouble = curPrice / curMarketPrice;
    }
    d1ratioByDouble = PriceTool.getdRatio(d1ratioByDouble);
    String d1ratio = String.valueOf(d1ratioByDouble);

    double d2ratioByDouble = 1;
    d2ratioByDouble = PriceTool.getdRatio(d2ratioByDouble);
    String d2ratio = String.valueOf(d2ratioByDouble);


    p.setPrice(price);
    p.setMaxPrice(max);
    p.setDisPrice(min);
    p.setMinPrice(min);
    p.setMinMarketPrice(price);
    p.setD1ratio(d1ratioByDouble);
    p.setD2ratio(d2ratioByDouble);
    System.out.println(String.format(
        "price=%s,max=%s,min=%s,minmarket=%s,disprice=%s,d1ratio:%s,d2ratio:%s", price, max, min,
        p.getMinMarketPrice(), p.getDisPrice(), p.getD1ratio(), p.getD2ratio()));
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
