package com.lunch.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.lunch.dto.Customer;
import com.lunch.dto.Reservation;
import com.lunch.dto.Store;
import com.lunch.exception.Authentication;
import com.lunch.exception.Duplicated;
import com.lunch.exception.IsNotContained;

public interface CustomerDAO {
	Connection getConnect() throws SQLException;
	
	void closeAll(PreparedStatement ps, Connection conn)throws SQLException;
	void closeAll(ResultSet rs, PreparedStatement ps, Connection conn)throws SQLException;
	
	// CRUD
	void addMember(Customer dto) throws SQLException, IsNotContained, Duplicated; 
	Customer getMember(int custId) throws SQLException; 
	void updateMember(Customer dto) throws SQLException, IsNotContained;
	void deleteMember(int custId) throws SQLException, IsNotContained ;
	
	
	ArrayList<Reservation> getReservation(int custId) throws SQLException;
	void deleteReservation(int custId) throws SQLException, IsNotContained;
	void updateRerservation(Reservation dto,int custId) throws SQLException, IsNotContained;
	

	void addScore(Reservation dto,int score) throws SQLException, Authentication;

	
	ArrayList<Store> getRecommend(int score) throws SQLException;
	Store getRecommend() throws SQLException; 
	Store getRecommned(String region) throws SQLException;

	// getstores 겹쳐서 세개로 나눔
	ArrayList<Store> getStoresRegion(String region) throws SQLException, IsNotContained;
	ArrayList<Store> getStoresCat(String category) throws SQLException, IsNotContained; 
	ArrayList<Store> getStoresScore(int score) throws SQLException, IsNotContained ;
	Store getStore(String name) throws SQLException; 


}