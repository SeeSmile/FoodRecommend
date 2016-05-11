package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class BaseDB {
	
	public static final String TYPE_DB_NAME = "com.mysql.jdbc.Driver";
	private String name;
	private String password;
	private String ip;
	private String dbname;
	public static Connection conn = null;
	
	public BaseDB(String ip, String dbname,String name, String password) {
		
		this.ip = ip;
		this.dbname = dbname;
		this.name = name;
		this.password = password;
		
		try {
			Class.forName(TYPE_DB_NAME);
			if(conn != null && !conn.isClosed()) {
				
			} else {
				String uri = "jdbc:mysql://" + ip + "/" + dbname + "?characterEncoding=utf8";
				conn = DriverManager.getConnection(uri, name, password);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("连接数据库失败");
		}
	}
	
	public void connectDB() {
		try {
			Class.forName(TYPE_DB_NAME);
			if(conn != null && !conn.isClosed()) {
				
			} else {
				String uri = "jdbc:mysql://" + ip + "/" + dbname + "?characterEncoding=utf8";
				conn = DriverManager.getConnection(uri, name, password);
			}
		} catch (Exception e) {
			System.out.println("连接数据库失败");
		}
	}
	
	/**
	 * 取得SQL操作对象
	 * @param sql 要执行的SQL语句
	 * @return SQL操作对象
	 * @throws SQLException 执行SQL语句异常
	 */
	public PreparedStatement getPrepared(String sql) throws SQLException {
		connectDB();
		if(conn != null) {
			return conn.prepareStatement(sql);
		}
		return null;
	}
	
	/**
	 * 关闭数据库链接
	 */
	public void close() {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("关闭数据库失败");
		}
	}
	
	public static String createInsertSql(String table, ArrayList<DbParam> list) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ").append(table).append(" (");
		for(int i = 0; i < list.size(); i++) {
			sql.append(list.get(i).getKey());
			if(i != list.size()- 1) {
				sql.append(",");
			}
		}
		sql.append(") VALUES (");
		for(int i = 0; i < list.size(); i++) {
			sql.append("?");
			if(i != list.size() - 1) {
				sql.append(",");
			}
		}
		sql.append(")");
		return sql.toString();
	}
	
	public static void initPst(PreparedStatement pst, ArrayList<DbParam> list) throws SQLException {
		DbParam param;
		for(int i = 0; i < list.size(); i++) {
			param = list.get(i);
			if(param.getValue() instanceof Float) {
				pst.setFloat(i + 1, (Float)param.getValue());
			} else if(param.getValue() instanceof Integer) {
				pst.setInt(i + 1, (Integer) param.getValue());
			} else {
				pst.setString(i + 1, (String) param.getValue());
			}
		}
	}
}
