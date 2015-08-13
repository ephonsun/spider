
package com.hyc.spider.inf;

import org.jsoup.nodes.Document;

import com.hyc.spider.exception.BusiException;
import com.hyc.spider.goods.obj.Product;


public interface ParserGoodsInf extends ParserInf {
    void parserGoods(Document doc,Product p)throws BusiException;
    
}
