package com.hyc.spider.woker;

import java.util.concurrent.BlockingQueue;


public class Producer implements Runnable {

  BlockingQueue queue;

  public Producer(BlockingQueue queue) {
    this.queue = queue;
  }


  @Override
  public void run() {
    while (true) {
    }

  }

}
