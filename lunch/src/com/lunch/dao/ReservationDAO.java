package com.lunch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.lunch.dto.Reservation;
import com.lunch.exception.IsNotContained;
import com.lunch.exception.WrongDate;

public interface ReservationDAO {
	Connection getConnect() throws SQLException;
	void closeAll(Connection conn, PreparedStatement ps)throws SQLException;
	void closeAll(Connection conn, PreparedStatement ps, ResultSet rs)throws SQLException;
	
	void makeReservation(Reservation dto) throws SQLException, IsNotContained, WrongDate;
	Reservation getReservation(int resId) throws SQLException, IsNotContained;
	ArrayList<Reservation> getReservation() throws SQLException;

}
