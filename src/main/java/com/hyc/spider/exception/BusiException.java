package com.hyc.spider.exception;

/**
 * <pre>
 * ******************  类说明  *********************
 * class       :  BusiException
 * @author     :  huangyuchen
 * @version    :  1.0 
 * @copyright  :   
 * @modified   :  2015-04-02
 * description :  业务异常                      
 * ************************************************
 * </pre>
 *
 */
public class BusiException extends Exception{
	private static final long	serialVersionUID	= 682478403376842851L;
	String errorCode ;
	String errorMsg ;
	public BusiException(String errorCode,String errorMsg){
		this(errorCode, errorMsg, null);
		
	}
	public BusiException(String errorCode,String errorMsg,Throwable e){
		super(errorMsg, e);
		this.errorCode = errorCode ;
		this.errorMsg = errorMsg ;
		
	}
	public String getErrorCode() {
		return errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	
	
	
}



