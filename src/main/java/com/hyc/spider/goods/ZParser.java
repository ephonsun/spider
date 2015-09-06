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
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.NodeList;

import com.alibaba.fastjson.JSONObject;
import com.hyc.spider.BaseParser;
import com.hyc.spider.exception.BusiException;
import com.hyc.spider.goods.obj.Product;
import com.hyc.spider.goods.obj.Seller;
import com.hyc.spider.inf.ParserGoodsInf;


@Component("zParser")
public class ZParser extends BaseParser implements ParserGoodsInf {
  private static final Logger _log = LoggerFactory.getLogger(ZParser.class);
  protected String XMTAG = "[xm99]";
  Pattern pattern = Pattern.compile("(?:^http://www.amazon.cn.*/dp/)([a-zA-Z0-9]+).*?");

  String sellerCode ="1011" ;
  String seller = "亚马逊" ;
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
    getMparams(doc,p);
    getPic(doc, p);
    getSeller(p);
  }

  
  @Override
  public List parserContent(Document doc) throws BusiException {
    return null;
  }
  
  
  public void getSeller(Product p)throws BusiException{
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
    p.setSellerList(sellerList );
  }
  public void getPic(Document doc,Product p) throws BusiException {
    Element e = doc.getElementById("imgTagWrapperId");
    Elements es = e.select("img");
    System.out.println(e);
    int eslen = es.size();
    String pic = "" ;
    if(eslen>0){
      pic = es.attr("src");
    }
    p.setBigPic(pic);
    p.setSmallPic(pic);
    p.setOrgPic(pic);
  }
  public void getContents(Document doc,Product p) throws BusiException {
    StringBuilder sb = new StringBuilder("    ");
    Element ep = doc.getElementById("productDescription");
    Elements div = ep.select("div.aplus");
    Elements et  = ep.select("p");
    if(div !=null){
      Element d = div.get(0);
      sb.append(d.text());
    }else if(et!=null){
      
    }else{
      
    }
    p.setContents(sb.toString());
  }
  
  private static List<String> getNodeList(List<Node> childNodeList){
    List<String> nodeList = new ArrayList<String>();
    String str = "";
    for (int j = 0; j < childNodeList.size(); j++) {
        Node node = childNodeList.get(j);
        if (node != null && !node.nodeName().toLowerCase().equals("script")
                && !node.nodeName().toLowerCase().equals("style") 
              ) {     
            str = node.toString().replaceAll("[　\\s]+"," ");
            str = str.replaceAll("：", ":");
            if (StringUtils.isNotEmpty(str)) {
                nodeList.add(str);
            }
        } else {
            continue;
        }
    }
    return nodeList;
}
  public void getMparams(Document doc,Product p) throws BusiException {
    StringBuilder sb = new StringBuilder("");
    Elements es = doc.getElementById("productDetailsTable").select("tbody>tr>td");
    Elements h2 = es.select("h2");
    String h2txt = h2==null ? "" : StringUtils.trimToEmpty(h2.text());
    if(StringUtils.isNotEmpty(h2txt)){
      sb.append(h2txt).append(XMTAG).append(XMTAG);
    }
    Elements div1 = es.select("div.disclaim");
    Elements div2 = es.select("div.content>ul>li");
    int div2len = div2==null?0: div2.size();
    for(int i=0;i<div2len ; i++){
      Element c = div2.get(i);
      String ctxt = c==null ? "" : StringUtils.trimToEmpty(c.text());
      if(ctxt.contains("用户评分:"))break;
      if(StringUtils.isNotEmpty(ctxt)){
        sb.append(ctxt).append(XMTAG);
      }
      
    }
    
    System.out.println("mparams:" + sb.toString());
    p.setMparams(sb.toString());
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
    System.out.println("skuid=" + skuid);
    return skuid;
  }

  private void getPrice(Document doc, Product p, String skuid) {
    Element e = doc.getElementById("price");
    Elements es = e.select("tbody>tr");
    String maxprice = "";
    
    int eslen = es == null ? 0:es.size();
    if(eslen>1){
        Element t0 = es.get(0);
        Elements tt0 = t0.select("td.a-span12");
        maxprice = StringUtils.trimToEmpty(tt0.text());
        maxprice = maxprice.replaceAll("[,，]", "");
        maxprice = maxprice.replace("￥", "") ;
    }else{
        
    }
    
    Element r1 = doc.getElementById("priceblock_saleprice");
    Element r2 = doc.getElementById("priceblock_ourprice");
    Element r = r1!=null? r1 : r2 ;
    String price = StringUtils.trimToEmpty(r.text());
    price = price.replaceAll("[,，]", "");
    price = price.replace("￥", "") ;
    double max, min = 0;
    max = min = StringUtils.isEmpty(price) ? 0 : Double.parseDouble(price);
    max = StringUtils.isEmpty(maxprice) ? max : Double.parseDouble(maxprice);

    p.setPrice(price);
    p.setMaxPrice(max);
    p.setDisPrice(min);
    p.setMinPrice(min);
    p.setMinMarketPrice(price);
    System.out
        .println(String.format("price=%s,max=%s,min=%s,minmarket=%s", price, max, min, price));
  }

  private void getProductName(Document doc, Product p) {
    Element e = doc.getElementById("productTitle");
    String productName = e == null ? "" : StringUtils.trim(e.text());
    p.setProductName(productName);
    System.out.println("productName=" + productName);
  }

  @Override
  public List parserPage(Document doc) throws BusiException {
    return null;
  }


}
