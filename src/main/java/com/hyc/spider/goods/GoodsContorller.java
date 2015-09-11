package com.hyc.spider.goods;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

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

import com.hyc.spider.goods.obj.FetchObj;
import com.hyc.spider.goods.obj.Product;
import com.hyc.spider.inf.ParserGoodsInf;
import com.hyc.spider.util.ConstParam;
import com.hyc.spider.util.XmlParser;
import com.hyc.spider.woker.Consumer;



@Controller
public class GoodsContorller {
  private static final Logger _log = LoggerFactory.getLogger(GoodsContorller.class);

  @Autowired
  @Qualifier("jdParser")
  ParserGoodsInf jdParser;

  @Autowired
  @Qualifier("dataImpl")
  DataImpl dataImpl;

  @Autowired
  @Qualifier("zParser")
  ParserGoodsInf zParser;

  @Autowired
  @Qualifier("jdListParser")
  ParserGoodsInf jdListParser;

  @RequestMapping(value = "/goods/query")
  public String getProduct(HttpServletRequest request, HttpServletResponse response,
      ModelMap model, String product_url, String sellercode, String format, Integer range,
      String classiccode, String maincat, String secondcat) throws Exception {
    product_url = StringUtils.trimToEmpty(product_url);
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

    Product resp = null;
    if (StringUtils.isEmpty(product_url)) {
      _log.warn("input product_url is empty,try again!");
    } else {
      FetchObj f = new FetchObj();
      f.setMaincat(maincat);
      f.setSecondcat(secondcat);
      f.setClassiccode(classiccode);
      f.setSellercode(sellercode);
      f.setUrl(product_url);
      f.setRange(range);
      resp = dataImpl.datum(f,product_url);
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

  // 批量抓取列表数据
  @RequestMapping(value = "/goods/batch")
  public String getBatch(HttpServletRequest request, HttpServletResponse response, ModelMap model,
      String batchfile) throws Exception {
    // read xml file
    if (StringUtils.isEmpty(batchfile))
      batchfile = this.getClass().getClassLoader().getResource("template.xml").getPath();
    
    List<FetchObj> flist = XmlParser.parseXml(batchfile);
    int flistSize = flist == null ? 0 : flist.size();
    if (flistSize > 0) {
      Map<String, String> paramMap = new HashMap<String, String>();

      List<FetchObj> folist = new ArrayList<FetchObj>();
      BlockingQueue<FetchObj> bq = new ArrayBlockingQueue<FetchObj>(100);
      for (FetchObj fetchObj : flist) {
        String url = fetchObj.getUrl();
        if (StringUtils.isEmpty(url)) continue;
        Document doc = jdListParser.getTargetHtml(url, paramMap);
        List<String> urls = null;
        if (doc != null) {
          urls = jdListParser.parserPage(doc);
        }

        int urlsSize = urls == null ? 0 : urls.size();
        if (0 < urlsSize) {
          fetchObj.setExtUrls(urls);
          bq.add(fetchObj);
        }else{
          System.err.println(url);
        }

      }
      Consumer con = new Consumer(bq,dataImpl);
      Thread t = new Thread(con);
      t.start();
      
      dataImpl.subTimectl();
      
      System.out.println("do flist ok");
      model.addAttribute("flist", flist);

    } else {
      _log.info("no fetch objects!");
    }


    return "/goods/flist_show";
  }


}
