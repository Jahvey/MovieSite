package com.movie.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.movie.domain.Movie;
import com.movie.domain.User;

public class userDAO {
	public final static String TABLE_NAME = "users";
	public final static String ID_COLUMN = "id";
	public final static String NAME_COLUMN = "name";
	public final static String EMAIL_COLUMN = "email";
	
	public static void insertUser(User user){
		
	}
	public static void insertUsers(List<User> users){
		Connection conn = DBUtil.getConnection();
		PreparedStatement ps = null;
		String sql = "insert into "
				+ TABLE_NAME + " ( "
				+ ID_COLUMN + ", "
				+ NAME_COLUMN + ", "
				+ EMAIL_COLUMN 
				+ ") values (?, ?, ?)";
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);

			for (User user : users) {
				ps.setInt(1, user.getId());
				ps.setString(2, user.getName());
				ps.setString(3, user.getEmail());
				ps.addBatch();
			}
			
			ps.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static User getUserByID(String userID){
		
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN + " =  " + userID + " ";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnectionFromDataSource();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				User user = constructUserFromResultSet(rs);
				return user;
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					rs.close();
					pstmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return null;
	}
	//获取所有的用户信息
	public static List<User>getAllUsers(){
		List<User>users = new ArrayList<User>();
		String sql = "SELECT * FROM " + TABLE_NAME;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				User user = constructUserFromResultSet(rs);
				if (user != null) {
					users.add(user);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return users;
	}

	
	public static User getUserByEmail(String email){
		
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + EMAIL_COLUMN + " =  '" + email + "' ";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnectionFromDataSource();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				User user = constructUserFromResultSet(rs);
				return user;
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					rs.close();
					pstmt.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return null;
	}

	private static User constructUserFromResultSet(ResultSet rs) {
		try {
			User user = new User();
			user.setId(rs.getInt(ID_COLUMN));
			user.setName(rs.getString(NAME_COLUMN));
			user.setEmail(rs.getString(EMAIL_COLUMN));
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
