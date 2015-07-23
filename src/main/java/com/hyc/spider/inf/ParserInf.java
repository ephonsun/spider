package com.hyc.spider.inf;

import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;

import com.hyc.spider.exception.BusiException;

public interface ParserInf {

  Document getTargetHtml(String targetUrl, Map paramMap) throws BusiException;

  List parserContent(Document doc) throws BusiException;

  List parserPage(Document doc) throws BusiException;

}
