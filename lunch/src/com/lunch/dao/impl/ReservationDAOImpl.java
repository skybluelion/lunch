package com.lunch.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.lunch.dao.ReservationDAO;
import com.lunch.dto.Reservation;
import com.lunch.exception.IsNotContained;
import com.lunch.exception.WrongDate;

import config.ServerInfo;

public class ReservationDAOImpl implements ReservationDAO {
	// singleton
	private static ReservationDAOImpl dao = new ReservationDAOImpl();

	private ReservationDAOImpl() {
	}

	public static ReservationDAOImpl getInstance() {
		return dao;
	}

	// connection
	@Override
	public Connection getConnect() throws SQLException {
		Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASS);
		System.out.println("DB Connection...OK");
		return conn;
	}

	@Override
	public void closeAll(Connection conn, PreparedStatement ps) throws SQLException {
		if (ps != null)
			ps.close();
		if (conn != null)
			conn.close();

	}

	@Override
	public void closeAll(Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
		if (rs != null)
			rs.close();
		closeAll(conn, ps);

	}

	public boolean resIdExist(int resId, Connection conn) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		conn = getConnect();
		String query = "SELECT res_id FROM reservation WHERE res_id = ?";
		ps = conn.prepareStatement(query);
		ps.setInt(1, resId);
		rs = ps.executeQuery(); // close 안해줌
		return rs.next();
	}

	@Override
	public void makeReservation(Reservation dto) throws SQLException, IsNotContained, WrongDate {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnect();
			System.out.println(conn != null);
			String query = "INSERT INTO reservation(res_id,cust_id,store_id,res_date,cust_num) values (res_seq.nextval,?,?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setInt(1, dto.getCustId());
			ps.setInt(2, dto.getStoreId());
			ps.setString(3, dto.getDate());
			ps.setInt(4, dto.getPeople());
			ps.executeUpdate();

			String query2 = "SELECT rest_seat from seats where store_id = ? and res_date = ?";
			ps = conn.prepareStatement(query2);
			ps.setInt(1, dto.getStoreId());
			ps.setString(2, dto.getDate());
			rs = ps.executeQuery();
			int restNum = 0;
			if (rs.next()) {
				restNum = rs.getInt("rest_seat");
			}

			if (restNum > 0) {
				String query3 = "UPDATE seats set rest_seat = ? where store_id = ? and res_date = ? ";
				ps = conn.prepareStatement(query3);
				ps.setInt(1, restNum - 1);
				ps.setInt(2, dto.getStoreId());
				ps.setString(3, dto.getDate());
				int row = ps.executeUpdate();
				System.out.println(row + " row 예약 성공");
			} else {
				throw new WrongDate("예약할 수 있는 자리가 존재하지 않습니다");
			}

		} finally {
			closeAll(conn, ps, rs);
		}
	}

	@Override
	public Reservation getReservation(int resId) throws SQLException, IsNotContained {
		Reservation res = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnect();
			String query = "SELECT cust_id, store_id, res_date,cust_num FROM reservation WHERE res_id=?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, resId);
			rs = ps.executeQuery();
			if (rs.next()) {
				res = new Reservation(resId, rs.getInt("cust_id"), rs.getInt("store_id"), rs.getString("res_date"),
						rs.getInt("cust_num"));
			} else {
				throw new IsNotContained("해당 고객은 존재하지 않음");
			}
		} finally {
			closeAll(conn, ps, rs);
		}
		return res;
	}

	@Override
	public ArrayList<Reservation> getReservation() throws SQLException {
		ArrayList<Reservation> ress = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnect();
			String query = "SELECT res_id,cust_id, store_id, res_date,cust_num FROM RESERVATION";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				ress.add(new Reservation(rs.getInt("res_id"), rs.getInt("cust_id"), rs.getInt("store_id"),
						rs.getString("res_date"), rs.getInt("cust_num")));
			}
		} finally {
			closeAll(conn, ps, rs);
		}
		return ress;
	}
}
