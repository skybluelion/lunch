package com.lunch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.lunch.dto.Reservation;
import com.lunch.dto.Store;
import com.lunch.exception.Duplicated;
import com.lunch.exception.IsNotContained;

public interface StoreDAO {
	Connection getConnect() throws SQLException;

	void closeAll(Connection conn, PreparedStatement ps) throws SQLException;

	void closeAll(Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException;

	ArrayList<Reservation> getReservation() throws SQLException;

	ArrayList<Reservation> getReservation(int storeId, String date) throws SQLException;

	void deleteReservation(int resId) throws SQLException;

	void addStore(Store store) throws SQLException, IsNotContained, Duplicated;

	void updateStore(int storeId, Store store) throws SQLException;

	void deleteStore(int storeId) throws SQLException, IsNotContained;
}
