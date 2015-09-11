package com.hyc.spider.woker;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hyc.spider.goods.DataImpl;
import com.hyc.spider.goods.obj.FetchObj;


public class Consumer implements Runnable {
  private static final Logger _log = LoggerFactory.getLogger(Consumer.class);

  DataImpl dataImpl;

  BlockingQueue<FetchObj> queue;

  public Consumer(BlockingQueue<FetchObj> queue, DataImpl dataImpl) {
    this.queue = queue;
    this.dataImpl = dataImpl;
  }

  @Override
  public void run() {
    try {
      while (true) {
        try {
          FetchObj o = queue.take();
          _log.info(String.format("@consumer o:", o));
          List<String> urls = o == null ? null : o.getExtUrls();
          int urlsSize = urls == null ? 0 : urls.size();
          if (urlsSize > 0) {
            for (String url : urls) {
              dataImpl.datum(o, url);
            }
          }

        } catch (InterruptedException e) {
          _log.warn("@consumer queue has no o,blocked!", e);
        }
      }
    } catch (Exception e) {
      _log.warn("@consumer run exception!", e);
    }

  }

}
