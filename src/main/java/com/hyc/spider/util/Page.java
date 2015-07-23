package com.hyc.spider.util;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @ClassName: Page
 * @Description: 分页设置
 * @param <T>
 */
public class Page<T> implements Serializable {


  /**
	 * 
	 */
  private static final long serialVersionUID = -8711269983509153374L;
  private int pageNo = 1; // 第几页
  private int pageSize = 10;// 每页数
  private int totalCount;// 总记录数
  private int totalPage;// 总页数
  private boolean hasNext;
  private List<T> data;
  private int offset;// 位移
  private int sortBy; // 排序方式


  public Page() {

  }

  public List<T> getData() {
    return data;
  }

  public void setData(List<T> data) {
    this.data = data;
  }

  public boolean haveNextPage() {
    return pageNo < totalPage;
  }

  public boolean havePrevPage() {
    return pageNo > 1;
  }

  public int getNextPageNo() {
    return pageNo + 1;
  }

  public int getPrevPageNo() {
    return pageNo - 1 < 1 ? 1 : (pageNo - 1);
  }

  public int getPageNo() {
    return pageNo <= 0 ? 1 : pageNo;
  }

  public void setPageNo(int pageNo) {
    this.pageNo = pageNo;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
    this.totalPage = (totalCount + pageSize - 1) / pageSize;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
    this.totalPage = (totalCount + pageSize - 1) / pageSize;
  }

  public int getTotalPage() {
    return totalPage;
  }

  public int getOffset() {
    this.setOffset(offset = (this.getPageNo() - 1) * pageSize);
    return offset;
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }


  public int getSortBy() {
    return sortBy;
  }

  public void setSortBy(int sortBy) {
    this.sortBy = sortBy;
  }




}
