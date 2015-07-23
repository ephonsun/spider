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
      ModelMap model, String idcard, String name, String format, String a, Integer num, Integer page)
      throws Exception {
    if (StringUtils.isEmpty(a)) a = ConstParam.ACTION_QUERY;
    if (StringUtils.isEmpty(format)) format = ConstParam.FORMAT_XML;

    if (page == null || page <= 0) page = 1;

    // 限制翻页数
    if (page > ConstParam.MAX_PAGE_NUM) {
      page = ConstParam.MAX_PAGE_NUM;
      _log.warn("page too large:" + page + "; reset to " + ConstParam.MAX_PAGE_NUM);
    }

    if (num == null || num <= 0) num = 20;

    model.addAttribute("idcard", idcard);
    model.addAttribute("name", name);
    model.addAttribute("num", num);
    model.addAttribute("page", page);

    model.addAttribute("a", a);
    model.addAttribute("format", format);

    System.out.println(String.format("a=%s,name=%s,idcard:%s,format=%s", a, name, idcard, format));

    String targetUrl = "http://www.p2pblack.com/";
    String searchurl = targetUrl;
    Map<String, String> paramMap = new HashMap<String, String>();
    if (StringUtils.isNotEmpty(idcard)) {
      searchurl = searchurl + "?kw=" + idcard + "&tj=" + 2;
      paramMap.put("tj", "2");
      paramMap.put("kw", idcard);
    } else if (StringUtils.isNotEmpty(name)) {
      searchurl = searchurl + "?kw=" + name + "&tj=" + 1;
      paramMap.put("tj", "1");
      paramMap.put("kw", name);
    } else {

    }

    Document doc = idcardParser.getTargetHtml(targetUrl, paramMap);
    List<P2pblack> plist = null;
    List<UrlPage> urlPageList = null;
    if (doc != null) {
      plist = idcardParser.parserContent(doc);
      urlPageList = idcardParser.parserPage(doc);
    }

    if (plist != null) {
      model.addAttribute("code", 1);
      model.addAttribute("message", ConstParam.MSG_SUCCESS);
    } else {
      //
      model.addAttribute("code", -1);
      model.addAttribute("message", ConstParam.MSG_FAIL);
    }

    model.addAttribute("report", plist == null ? new ArrayList<P2pblack>() : plist);
    model.addAttribute("urlPage", urlPageList == null ? new ArrayList<UrlPage>() : urlPageList);
    model.addAttribute("searchurl", searchurl);
    return "/idcard/list_show";
  }


}
