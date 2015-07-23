
package com.hyc.spider;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hyc.spider.exception.BusiException;



public class BaseParser  {
  private static final Logger _log = LoggerFactory.getLogger(BaseParser.class);

  int timeout = 60*3000 ;

  public Document getTargetHtml(String targetUrl, Map paramMap) throws BusiException {
    
    Document doc = null ;
    try {
      doc = Jsoup.connect(targetUrl).data(paramMap).timeout(timeout).get();
    } catch (Exception e) {
      _log.error(e.getMessage(),e);
      String error = String.format("parse targeturl:%s ,params:%s exception:%s", 
        targetUrl,paramMap,e.getMessage());
      throw new BusiException("0100", error);
    } 
    return doc;
  }

  public int getTimeout() {
    return timeout;
  }

  public void setTimeout(int timeout) {
    this.timeout = timeout;
  }

  


}
