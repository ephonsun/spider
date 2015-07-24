package com.hyc.spider.idcard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import com.hyc.spider.inf.ParserInf;
import com.hyc.spider.model.Idcard;
import com.hyc.spider.model.P2pblack;
import com.hyc.spider.model.UrlPage;
import com.hyc.spider.util.ConstParam;



@Controller
public class IdcardContorller {
  private static final Logger _log = LoggerFactory.getLogger(IdcardContorller.class);

  @Autowired
  @Qualifier("idcardParser")
  ParserInf idcardParser;

  @RequestMapping(value = "/idcard/query")
  public String getPlaylist(HttpServletRequest request, HttpServletResponse response,
      ModelMap model, String idcard, String format)
      throws Exception {
    if (StringUtils.isEmpty(format)) format = ConstParam.FORMAT_XML;

    model.addAttribute("idcard", idcard);
    model.addAttribute("format", format);

    System.out.println(String.format("idcard:%s,format=%s", idcard, format));

    String targetUrl = "http://idcard.911cha.com/";
    Map<String, String> paramMap = new HashMap<String, String>();
    if (StringUtils.isNotEmpty(idcard)) {
      paramMap.put("q", idcard);
    } else {
    }

    Document doc = idcardParser.getTargetHtml(targetUrl, paramMap);
    
    List<Idcard> c = null;
    if (doc != null) {
      c = idcardParser.parserContent(doc);
    }
    Idcard rc = c == null ?  new Idcard() : c.get(0);
    model.addAttribute("card", rc);
    return "/idcard/list_show";
    
    //test return 
//    response.setCharacterEncoding("UTF-8");
//    response.getWriter().write(doc.html());
  }


}
