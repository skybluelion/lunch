package com.lunch.test;

import java.sql.SQLException;
import java.util.ArrayList;

import com.lunch.dao.impl.ReservationDAOImpl;
import com.lunch.dto.Reservation;
import com.lunch.exception.IsNotContained;
import com.lunch.exception.WrongDate;

import config.ServerInfo;

public class ResTest {

	public static void main(String[] args) throws SQLException, IsNotContained, WrongDate {
		ReservationDAOImpl dao = ReservationDAOImpl.getInstance();
	//	dao.makeReservation(new Reservation(1238,1299,"230201",4));
//		System.out.println(dao.getReservation(25));
//		ArrayList<Reservation> rs = dao.getReservation();
//		for(Reservation r : rs) System.out.println(r);
//		dao.makeReservation(new Reservation(1239, 4, "230401", 3));
		dao.makeReservation(new Reservation(1, 5, "230401", 3));
		
	}
	static {
		try {
			Class.forName(ServerInfo.DRIVER_NAME);
			System.out.println("Driver Loading success...");
		}catch(ClassNotFoundException e) {
			System.out.println("Driver Loading failed...");
		}
	}
}
