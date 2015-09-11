package com.hyc.spider.goods;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hyc.spider.BaseParser;
import com.hyc.spider.exception.BusiException;
import com.hyc.spider.goods.obj.Product;
import com.hyc.spider.inf.ParserGoodsInf;


@Component("jdListParser")
public class JdListParser extends BaseParser implements ParserGoodsInf {
  private static final Logger _log = LoggerFactory.getLogger(JdListParser.class);

  @Override
  public Document getTargetHtml(String targetUrl, Map paramMap) throws BusiException {
    return super.getTargetHtml(targetUrl, paramMap);

  }

  @Override
  public void parserGoods(Document doc, Product p) throws BusiException {}


  @Override
  public List parserContent(Document doc) throws BusiException {
    return null;
  }


  @Override
  public List parserPage(Document doc) throws BusiException {
//    File f = new File("1.txt");
//    if (f.exists())f.delete();
//    FileOutputStream os = null ;
//    try {
//      os = new FileOutputStream(f);
//      os.write(doc.html().getBytes());
//      os.flush();
//    } catch (FileNotFoundException e1) {
//      // TODO Auto-generated catch block
//      
//      e1.printStackTrace();
//    } catch (IOException e1) {
//      // TODO Auto-generated catch block
//      
//      e1.printStackTrace();
//    } finally{
//      if(null != os){
//        try {
//          os.close();
//        } catch (IOException e1) {
//          // TODO Auto-generated catch block
//          
//          e1.printStackTrace();
//        }
//      }
//    }
    
    Element e = doc.getElementById("plist");
    if (e==null) {
      return null ;
    }
//    System.out.println("$$" + e);
    Elements es = e.select("ul>li");
    int eslen = es == null ? 0 : es.size();
    List<String> plist = new ArrayList<String>();
    for(int i=0;i<eslen;i++){
      Element o = es.get(i);
      String sku =StringUtils.trimToEmpty(o.attr("sku"));
      if(StringUtils.isNotEmpty(sku)){
        String url = "http://item.jd.com/" + sku + ".html\n" ;
        plist.add(url);
      }
    }
    
    return plist;
  }


}
