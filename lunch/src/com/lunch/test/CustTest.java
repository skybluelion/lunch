package com.lunch.test;

import java.sql.SQLException;
import java.util.ArrayList;

import com.lunch.dao.impl.CustomerDAOImpl;
import com.lunch.dto.Customer;
import com.lunch.dto.Reservation;
import com.lunch.dto.Store;
import com.lunch.exception.Authentication;
import com.lunch.exception.Duplicated;
import com.lunch.exception.IsNotContained;

import config.ServerInfo;

public class CustTest {

	public static void main(String[] args) throws SQLException, Authentication, IsNotContained, Duplicated {
		CustomerDAOImpl cdao = CustomerDAOImpl.getInstance();
//		cdao.addScore(new Reservation(25,1238,1299,"230201",4), 4);
//		ArrayList<Store> ss = cdao.getStoresCat("chinese");
//		for(Store s: ss) System.out.println(ss);
//		System.out.println(cdao.getStore("rest1"));
		ArrayList<Store> scoreStore = cdao.getStoresScore(3);
		for(Store s: scoreStore) System.out.println(s);
		
//		ArrayList<Reservation> reservations = cdao.getReservation(1238);
//		for(Reservation s : reservations) System.out.println(s);
		cdao.addScore(new Reservation(36,1, 5, "230401", 3), 4);
//		System.out.println(cdao.getRecommned("선릉"));
//		cdao.addMember(new Customer("01012123434","형준","선릉"));
//		cdao.addMember(new Customer("01014545234","성준","선릉"));
//		cdao.addMember(new Customer("01078597686","승혜","선릉"));
//		cdao.addMember(new Customer("01092819345","민지","대치"));

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
