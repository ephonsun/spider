package com.hyc.spider.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * <pre>
 * 连接池工厂类
 * 数据库连接池调用方法类
 * @author candywon - candywon@qq.com
 * 
 * by huangyuchen修改为c3p0线程池
 * in 2013-11-04
 * 
 * by huangyuchen修改为线程安全单例模式
 * in 2013-11-12
 * </pre>
 */
public class ConnectionFactory {
	private static Logger				log	= Logger.getLogger(ConnectionFactory.class);
	private DataSource							ds;
	private volatile static ConnectionFactory	instance;

	private ConnectionFactory() {
		ds = new ComboPooledDataSource();
	}

	public static ConnectionFactory getInstance() {
		if (instance == null) {
			synchronized (ConnectionFactory.class) {
				if (instance == null) {
					instance = new ConnectionFactory();// instance为volatile，现在没问题了
				}
			}
		}
		return instance;
	}

	public Connection getConnection() {
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			log.error(e);
		}
		return null;
	}
	
	/** 
	 *  用于关闭数据库的关闭 
	 * @param rs ResultSet对象 
	 * @param st Statement对象 
	 * @param con Connection对象 
	 */  
	public void  closeJDBC(ResultSet rs,Statement st,Connection con){  
	    if(rs!=null){  
	        try {  
	            rs.close();  
	        } catch (SQLException e) {  
	        	log.error(e);
	        }  
	    }  
	    if(st!=null){  
	        try {  
	            st.close();  
	        } catch (SQLException e) {  
	        	log.error(e);
	        }  
	    }  
	    if(con!=null){  
	        try {  
	            con.close();  
	        } catch (SQLException e) {  
	        	log.error(e);
	        }  
	    }  
	}  

}