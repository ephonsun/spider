package com.hyc.spider.goods;

import com.hyc.spider.exception.BusiException;
import com.hyc.spider.goods.obj.FetchObj;
import com.hyc.spider.goods.obj.Product;


public interface DataInf {
  public Product datum(FetchObj o,String product_url) throws BusiException;

  void subTimectl()throws BusiException;
}
