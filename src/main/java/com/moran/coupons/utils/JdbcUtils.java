//package com.moran.coupons.utils;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.TimeZone;
//
//public class JdbcUtils {
//
//	static {
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static Connection getConnection() throws SQLException {
//		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/coupons?allowMultiQueries=true&serverTimezone="+TimeZone.getDefault().getID(), "root", "1234");
//		return connection;
//	}
//
//	public static void closeResources(Connection connection, PreparedStatement preparedStatement) {
//		try {
//			if (connection != null) {
//				connection.close();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		try {
//			if (preparedStatement != null) {
//				preparedStatement.close();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static void closeResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
//		closeResources(connection, preparedStatement);
//		try {
//			if (resultSet != null) {
//				resultSet.close();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	public static void closeResources(Connection connection, PreparedStatement preparedStatement1,
//			PreparedStatement preparedStatement2) {
//		closeResources(connection, preparedStatement1);
//		try {
//			if (preparedStatement2 != null) {
//				preparedStatement2.close();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//}
