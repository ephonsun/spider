package com.hyc.spider.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.hyc.spider.goods.obj.FetchObj;


public class XmlParser {
  private static final Logger _log = LoggerFactory.getLogger(XmlParser.class);

  public static List<FetchObj> parseXml(String xmlFile) throws Exception {
    SAXBuilder builder = new SAXBuilder();
    InputStream file = new FileInputStream(xmlFile);
    org.jdom.Document document = builder.build(file);// 获得文档对象
    org.jdom.Element root = document.getRootElement();// 获得根节点
    List<org.jdom.Element> list = root.getChildren();
    List<FetchObj> flist = new ArrayList<FetchObj>();

    for (org.jdom.Element ele : list) {

      String maincat = StringUtils.trimToEmpty(ele.getChildText("maincat"));
      String secondcat = StringUtils.trimToEmpty(ele.getChildText("secondcat"));
      String classiccode = StringUtils.trimToEmpty(ele.getChildText("classiccode"));
      String sellercode = StringUtils.trimToEmpty(ele.getChildText("sellercode"));
      String url = StringUtils.trimToEmpty(ele.getChildText("url"));
      String range = StringUtils.trimToEmpty(ele.getChildText("range"));

      FetchObj f = new FetchObj();
      f.setMaincat(maincat);
      f.setSecondcat(secondcat);
      f.setClassiccode(classiccode);
      f.setSellercode(sellercode);
      f.setRange(Integer.parseInt(range));
      f.setUrl(url);
      flist.add(f);
    }
    return flist;
  }

}
