package com.lunch.test;

import java.sql.SQLException;

import com.lunch.dao.impl.StoreDAOImpl;
import com.lunch.dto.Reservation;
import com.lunch.dto.Store;
import com.lunch.exception.Duplicated;
import com.lunch.exception.IsNotContained;

public class StoreDAOImplTest {

public static void main(String[] args) throws SQLException, IsNotContained, Duplicated {
    StoreDAOImpl dao = StoreDAOImpl.getInstance();
    
//    dao.addStore(new Store("마담밍", "021234567", "중식", "선릉", 4, "1130", 9000, 4.5));
//    dao.addStore(new Store("얌샘분", "021234583", "한식", "선릉", 8, "1000", 9000, 5));
//        dao.deleteStore(5);
//        dao.updateStore(4, new Store("버거킹", "123123", "양식", "선릉", 10, "00:00", 10000, 5));
        for(Reservation r : dao.getReservation(5,"230401")) {
        	System.out.println(r);
        };
//        dao.deleteReservation(30);
//        
//    dao.addStore(new Store("백암", "025559603", "한식", "대치", 20, "1130", 10000, 5));
//
//
//    dao.addStore(new Store("북창동", "025559683", "한식", "대치", 15, "1030", 11000, 4));
//
        }
}