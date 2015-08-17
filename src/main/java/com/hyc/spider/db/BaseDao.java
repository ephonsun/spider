package com.hyc.spider.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;



public class BaseDao {
	protected static Connection getConnection() {
		return ConnectionFactory.getInstance().getConnection();
	}

	protected static void close(ResultSet rs,Statement st,Connection con){
		ConnectionFactory.getInstance().closeJDBC(rs, st, con);
	}
	protected static void closeRs(ResultSet rs){
		close(rs, null, null);
	}
	protected static void closePs(Statement st){
		close(null, st, null);
	}
	protected static void closeCon(Connection con){
		close(null, null, con);
	}
	

}



