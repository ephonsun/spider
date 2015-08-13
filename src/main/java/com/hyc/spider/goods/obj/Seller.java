package com.hyc.spider.goods.obj;

import java.io.Serializable;

public class Seller implements Serializable {
	// 每次增删字段应重新生成
	private static final long	serialVersionUID	= -7474629816647048244L;
	private String				sellerCode			= null;
	private String				sellerName			= null;
	private String				sellerUrl			= null;
	private String				productName			= null;
	private int					type				= 0;
	private String				oriPid		= null;
	// ====
	private String				price				= null;
	private double				marketPrice			= 0;
	private double				dratio				= 0;
	private double				dcratio				= 0;
	private double				disPrice = 0 ;
	private long				finalTime			= 0;

	public Seller(){
		
	}
	public Seller(String sellerCode, String sellerName, double marketPrice, String sellerUrl,
			String productName, int type, String origProductId, long finalTime, double d1ratio, double d2ratio,
			double disPrice, double salePrice) {
		this.sellerCode = sellerCode;
		this.sellerName = sellerName;
		this.sellerUrl = sellerUrl;
		this.productName = productName;
		this.type = type;
		this.oriPid = origProductId;
		this.finalTime = finalTime;
		this.marketPrice = marketPrice;
		this.price =  salePrice + "" ;
		this.disPrice = disPrice ;
		this.dratio = d1ratio ;
		this.dcratio = d2ratio ;
	}

	public String getOrigProductId() {
		return oriPid;
	}

	public void setOrigProductId(String origProductId) {
		this.oriPid = origProductId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSellerUrl() {
		return sellerUrl;
	}

	public void setSellerUrl(String sellerUrl) {
		this.sellerUrl = sellerUrl;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}


	public String getOriPid() {
		return oriPid;
	}
	public void setOriPid(String oriPid) {
		this.oriPid = oriPid;
	}
	public double getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}
	public double getDratio() {
		return dratio;
	}
	public void setDratio(double dratio) {
		this.dratio = dratio;
	}

	
	public double getDcratio() {
		return dcratio;
	}
	public void setDcratio(double dcratio) {
		this.dcratio = dcratio;
	}
	public double getDisPrice() {
		return disPrice;
	}
	public void setDisPrice(double disPrice) {
		this.disPrice = disPrice;
	}
	public long getFinalTime() {
		return finalTime;
	}

	public void setFinalTime(long finalTime) {
		this.finalTime = finalTime;
	}

	@Override
	public String toString() {
		return "Seller [disPrice=" + disPrice + ", dratio=" + dratio + ", dcratio=" + dcratio + ", finalTime="
				+ finalTime + ", marketPrice=" + marketPrice + ", oriPid=" + oriPid + ", price=" + price
				+ ", productName=" + productName + ", sellerCode=" + sellerCode + ", sellerName=" + sellerName
				+ ", sellerUrl=" + sellerUrl + ", type=" + type + "]";
	}

}
