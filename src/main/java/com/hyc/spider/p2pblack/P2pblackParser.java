package com.hyc.spider.p2pblack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hyc.spider.BaseParser;
import com.hyc.spider.exception.BusiException;
import com.hyc.spider.inf.ParserInf;
import com.hyc.spider.model.P2pblack;
import com.hyc.spider.model.UrlPage;


@Component("p2pblackParser")
public class P2pblackParser extends BaseParser implements ParserInf {

  private static final Logger _log = LoggerFactory.getLogger(P2pblackParser.class);

  @Override
  public Document getTargetHtml(String targetUrl, Map paramMap) throws BusiException {
    return super.getTargetHtml(targetUrl, paramMap);
    
  }


  @Override
  public List parserContent(Document doc) throws BusiException {
    Elements elements=doc.select("table.cengter_table>tbody>tr.xiabian");
    List<P2pblack> plist = null ;
    if(elements!=null){
      plist = new ArrayList<P2pblack>();
      for(int i =0;i<elements.size();i++){
        Elements e = elements.get(i).select("td");
        String name = StringUtils.trim(e.get(0).text());// 姓名
        String fee= StringUtils.trim(e.get(1).text()); // 欠款总额
        String penalty= StringUtils.trim(e.get(2).text());// 总罚息
        String sum= StringUtils.trim(e.get(3).text());// 逾期笔数
        String status= StringUtils.trim(e.get(4).text()); // 状态
        String idCard= StringUtils.trim(e.get(5).text());// 身份证号
        String plat= StringUtils.trim(e.get(6).text());// 平台
        String desc= StringUtils.trim(e.get(7).select("a").attr("href"));// 详细
        P2pblack p =new P2pblack(name, fee, penalty, sum, status, idCard, plat, desc);
        plist.add(p);
      }
    }
   
    return plist ;
  }


  @Override
  public List parserPage(Document doc) throws BusiException {
    Elements elements=doc.select("div.s-tz>ul>li");
    List<UrlPage> ulist = null ;
    System.out.println("elements.size()=" +elements.size());
    System.out.println("e=" + elements);
    if(elements!=null){
      ulist = new ArrayList<UrlPage>();
      for(int i =0;i<elements.size();i++){
        Elements e = elements.get(i).select("li");
        String desc = StringUtils.trim(e.text());//分页数字或文字
        String url= StringUtils.trim(e.select("a").attr("href"));// 分页url
        System.out.println(desc +"#" + url);
        UrlPage u =new UrlPage(desc, url);
        ulist.add(u);
      }
    }
   
    return ulist ;
  }

}
