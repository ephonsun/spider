package com.hyc.spider.idcard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hyc.spider.BaseParser;
import com.hyc.spider.exception.BusiException;
import com.hyc.spider.inf.ParserInf;
import com.hyc.spider.model.Idcard;


@Component("idcardParser")
public class IdcardParser extends BaseParser implements ParserInf {

  private static final Logger _log = LoggerFactory.getLogger(IdcardParser.class);

  @Override
  public Document getTargetHtml(String targetUrl, Map paramMap) throws BusiException {
    return super.getTargetHtml(targetUrl, paramMap);
    
  }


  @Override
  public List parserContent(Document doc) throws BusiException {
    List<Idcard> plist = null ;
    Elements elements=doc.select("div.leftbox>div.panel>div.noi>p.l200");
    String text = elements ==null ? "" :StringUtils.trim(elements.text());
    // 发证地：甘肃省 临夏回族自治州 康乐县 生　日：1992年11月1日 (22周岁) 性　别：男
    if (StringUtils.isNotEmpty(text)){
      String regex = "(发证地：)(.*)(生　日)" ;
      Pattern p = Pattern.compile(regex);
      Matcher m = p.matcher(text);
      
      if(m.find()){
        if(m.groupCount()>1){
          Idcard c = new Idcard();
          c.setAddress(m.group(2));
          plist = new ArrayList<Idcard>();
          plist.add(c);
        }
      }else{
      }
    }else{
      
    }
    
    return plist ;
  }


  @Override
  public List parserPage(Document doc) throws BusiException {
    return null ;
  }

}
