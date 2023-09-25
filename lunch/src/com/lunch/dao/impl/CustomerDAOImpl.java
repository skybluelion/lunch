package com.lunch.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import com.lunch.dao.CustomerDAO;
import com.lunch.dto.Customer;
import com.lunch.dto.Reservation;
import com.lunch.dto.Store;
import com.lunch.exception.Authentication;
import com.lunch.exception.Duplicated;
import com.lunch.exception.IsNotContained;

import config.ServerInfo;

public class CustomerDAOImpl implements CustomerDAO {

	//Singleton
    private static CustomerDAOImpl dao = new CustomerDAOImpl();
    private CustomerDAOImpl() {}
    public static CustomerDAOImpl getInstance() {
        return dao;
    }
	@Override
	public Connection getConnect() throws SQLException {
		System.out.println("getConnect..진입..");
		Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASS);
		System.out.println("DB Server Connection......");
		return conn;
	}

	@Override
	public void closeAll(PreparedStatement ps, Connection conn) throws SQLException {
		if(ps!=null) ps.close();
		if(conn !=null) conn.close();
	}

	@Override
	public void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException {
		if(rs!=null) rs.close();
		closeAll(ps,conn);		
	}
	
	private boolean isExist(int custId, Connection conn) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "SELECT cust_id FROM customer WHERE cust_id=?";
		ps = conn.prepareStatement(query);
		ps.setInt(1, custId);
		rs = ps.executeQuery();
		
		return rs.next();
	}
	
	private boolean isExistRes(int resId, Connection conn) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT res_id FROM Reservation WHERE res_id = ?";
        ps = conn.prepareStatement(query);
        ps.setInt(1, resId);
        rs = ps.executeQuery();

        return rs.next();
    }
	
	
	@Override
	public void addMember(Customer customer) throws SQLException, Duplicated {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = getConnect();
			String query = "INSERT INTO CUSTOMER (cust_id,phone,nickname,region) VALUES(cust_seq.nextVal,?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, customer.getPhone());
			ps.setString(2, customer.getNickname());
			ps.setString(3, customer.getRegion());
				
			System.out.println(ps.executeUpdate()+" 개 INSERT 성공!!");
				
		}finally {
			closeAll(ps, conn);
		}	
	}

	@Override
	public Customer getMember(int custId) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Customer customer = null;
		try {
			conn = getConnect();
			if(isExist(custId,conn)) {
				System.out.println("c");
				String query = "SELECT cust_id, phone, nickname, region FROM customer WHERE cust_id=?";
				ps = conn.prepareStatement(query);
				ps.setInt(1, custId);
				rs = ps.executeQuery();
				if(rs.next()) {
					customer = new Customer(custId,
										rs.getString("phone"),
										rs.getString("nickname"),
										rs.getString("region"));
				}
				System.out.println("f");
			} else {
				throw new SQLException("찾으시는 고객님의 정보가 존재하지 않습니다.");
			}
		} finally {
			closeAll(rs, ps, conn);
		}
		return customer;
	}

	@Override
	public void updateMember(Customer dto) throws SQLException, IsNotContained {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
			if(isExist(dto.getCustId(),conn)) {
				String query = "UPDATE Customer SET phone=?, nickname=?, region=? WHERE cust_id=?";
				ps = conn.prepareStatement(query);
				ps.setString(1, dto.getPhone());
				ps.setString(2, dto.getNickname());
				ps.setString(3, dto.getRegion());
				ps.setInt(4, dto.getCustId());
				int row = ps.executeUpdate();
				System.out.println(row+"row UPDATE is OK.");
			}else {
				throw new IsNotContained("업데이트할 고객이 존재하지 않습니다.");
			}
		} finally {
			closeAll(ps, conn);
		}
	}

	@Override
	public void deleteMember(int custId) throws SQLException, IsNotContained {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = getConnect();
			if(isExist(custId,conn)) {
				String query = "DELETE customer WHERE cust_id=?";
				ps = conn.prepareStatement(query);
				ps.setInt(1, custId);
				
				System.out.println(ps.executeUpdate() + " 개 DELETE DONE");
            }  else  {
                throw new IsNotContained("삭제할 대상의 고객이 없습니다!!");
            }
		} finally {
			closeAll(ps, conn);
		}
	}

	@Override
	public ArrayList<Reservation> getReservation(int custId) throws SQLException {
		Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
	    ArrayList<Reservation> reservations = new ArrayList<>();
	    try {
	        conn = getConnect();
	        String query = "SELECT res_id, cust_id, store_id, res_date, cust_num FROM reservation where cust_id = ?";
	        ps = conn.prepareStatement(query);
	        ps.setInt(1, custId);
	        rs = ps.executeQuery();
	        while (rs.next()) {
	        	reservations.add(new Reservation(rs.getInt("res_id"),
	        									rs.getInt("cust_id"),
	        									rs.getInt("store_id"),
	        									rs.getString("res_date"),
	        									rs.getInt("cust_num")));
	        }
	    } finally {
	        closeAll(rs, ps, conn);
	    }
	    return reservations;
	}

	@Override
	public void deleteReservation(int custId) throws SQLException, IsNotContained {
		Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnect();
            if(isExistRes(custId, conn)) {
                String query = "DELETE reservation WHERE res_id = ?";
                ps = conn.prepareStatement(query);
                ps.setInt(1, custId);

                System.out.println(ps.executeUpdate() + " 개 DELETE DONE");
            }  else  {
                throw new IsNotContained("삭제할 대상의 예약이 없습니다!!");
            }
        } finally {
            closeAll(ps, conn);
        }
	}

	@Override
	public void updateRerservation(Reservation dto, int custId) throws SQLException, IsNotContained {
	    Connection conn = null;
	    PreparedStatement ps = null;

	    try {
	        conn = getConnect();
	        if (isExistRes(custId, conn)) {
	            String query = "UPDATE reservation set res_date = ?, cust_num = ? WHERE res_id = ?";
	            ps = conn.prepareStatement(query);
	            ps.setString(1, dto.getDate());
	            ps.setInt(2, dto.getPeople());
	            ps.setInt(3, custId);

	            System.out.println(ps.executeUpdate() + " 개 UPDATE DONE");
	        } else {
	            throw new IsNotContained("수정할 예약이 없습니다!!");
	        }
	    } finally {
	        closeAll(ps, conn);
	    }
	}

	ReservationDAOImpl reservationDao = ReservationDAOImpl.getInstance();

	@Override
	public void addScore(Reservation dto, int score) throws SQLException, Authentication {
	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    String name, category, region;
	    float avgScore;
	    try {
	        conn = getConnect();
	        System.out.println(conn==null);
	        if (reservationDao.resIdExist(dto.getResId(), conn)) {// store - reservation join -> store_name, score,
	                                                                // category, region
	            String joinQuery = "select store.name, store.score, store.category, store.region from store, reservation "
	            		+ "where store.store_id = reservation.store_id and res_id = ?";
	            ps = conn.prepareStatement(joinQuery);
	            ps.setInt(1, dto.getResId());
	            rs = ps.executeQuery();
	            if (rs.next()) {
	                name = rs.getString("name");
	                avgScore = rs.getFloat("score");
	                category = rs.getString("category");
	                region = rs.getString("region");

	                
	                String query = "INSERT INTO visitedrecord(res_id, cust_id, store_name, score, category, region)"
	                        + " VALUES(?,?,?,?,?,?)";
	                
	                ps = conn.prepareStatement(query);

	                ps.setInt(1, dto.getResId());
	                ps.setInt(2, dto.getCustId());
	                ps.setString(3, name); // 조인 가게 이름
	                ps.setFloat(4, score);// 가게 조인 컬럼 추가 후 수정 필요
	                ps.setString(5, category); // 가게조인
	                ps.setString(6, region);//
	                System.out.println(ps.executeUpdate() + " 개 INSERT 성공!!");
	            }
	        } else {
	            throw new Authentication("해당 가게 점수 남길 권한이 없습니다!");
	        }
	    } finally {
	        closeAll(rs, ps, conn);
	    }
	}

	@Override
    public ArrayList<Store> getRecommend(int score) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Store> recommends = new ArrayList<>();

    try {
        conn = getConnect();
        String query = "SELECT name, phone, category, region, table_num, time, menu_price, score FROM store WHERE score >= ?";
        ps = conn.prepareStatement(query);
        ps.setInt(1, score);
        rs = ps.executeQuery();

        while(rs.next()) {
            recommends.add(new Store(rs.getString("name"),
                                    rs.getString("phone"),
                                    rs.getString("category"),
                                    rs.getString("region"),
                                    rs.getInt("table_num"),
                                    rs.getString("time"),
                                    rs.getInt("menu_price"),
                                    rs.getInt("score")));
        }
    } finally {
        closeAll(rs, ps, conn);
    }
    
    return recommends;
}

	@Override
	public Store getRecommend() throws SQLException {
	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    ArrayList<Store> recommends = new ArrayList<>();
	
	    try {
	        conn = getConnect();
	        String query = "SELECT name, phone, category, region, table_num, time, menu_price, score FROM store";
	        ps = conn.prepareStatement(query);
	        rs = ps.executeQuery();
	
	        while(rs.next()) {
	            recommends.add(new Store(rs.getString("name"),
	                                    rs.getString("phone"),
	                                    rs.getString("category"),
	                                    rs.getString("region"),
	                                    rs.getInt("table_num"),
	                                    rs.getString("time"),
	                                    rs.getInt("menu_price"),
	                                    rs.getInt("score")));
	        }
	        
	        }finally {
	            closeAll(rs, ps, conn);
	        }
	        
	        Random rand= new Random();
	        return recommends.get(rand.nextInt(recommends.size()-1));
	}
	
	@Override
	public Store getRecommned(String region) throws SQLException {
	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    ArrayList<Store> recommends = new ArrayList<>();
	
	    try {
	        conn = getConnect();
	        String query = "SELECT name, phone, category, region, table_num, time, menu_price, score FROM store WHERE region = ?";
	        ps = conn.prepareStatement(query);
	        ps.setString(1, region);
	        rs = ps.executeQuery();
	
	        while(rs.next()) {
	            recommends.add(new Store(rs.getString("name"),
	                                    rs.getString("phone"),
	                                    rs.getString("category"),
	                                    rs.getString("region"),
	                                    rs.getInt("table_num"),
	                                    rs.getString("time"),
	                                    rs.getInt("menu_price"),
	                                    rs.getInt("score")));
	        }
	        
	    } finally {
	        closeAll(rs, ps, conn);
	    }
	    
	    Random rand= new Random();
	    return recommends.get(rand.nextInt(recommends.size()-1));
	}

	@Override
	public ArrayList<Store> getStoresRegion(String region) throws SQLException, IsNotContained {
	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    ArrayList<Store> store = new ArrayList<>();
	    try {
	        conn = getConnect();
	        String query = "SELECT store_id, name, phone,category,region,table_num, time,menu_price, score FROM store where region = ?";
	        ps = conn.prepareStatement(query);
	        ps.setString(1, region);
	        rs = ps.executeQuery();
	    
	        while (rs.next()) {
	            store.add(new Store(rs.getString("name"), rs.getString("phone"), rs.getString("category"), rs.getString("region"),
	                    rs.getInt("table_num"), rs.getString("time"), rs.getInt("menu_price"), rs.getFloat("score")));
	        }
	    } finally {
	        closeAll(rs, ps, conn);
	    }
	    return store;
	}

	@Override
	public ArrayList<Store> getStoresCat(String category) throws SQLException, IsNotContained {
	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    ArrayList<Store> store = new ArrayList<>();
	    try {
	        conn = getConnect();
	        String query = "SELECT store_id, name, phone,region,table_num, time,menu_price, score FROM store where category = ?";
	        ps = conn.prepareStatement(query);
	        ps.setString(1, category);
	        rs = ps.executeQuery();
	    
	        while (rs.next()) {
	            store.add(new Store(rs.getString("name"), rs.getString("phone"), category, rs.getString("region"),
	                    rs.getInt("table_num"), rs.getString("time"), rs.getInt("menu_price"), rs.getFloat("score")));
	        }
	    } finally {
	        closeAll(rs, ps, conn);
	    }
	    return store;
	}
	
	@Override
	public ArrayList<Store> getStoresScore(int score) throws SQLException, IsNotContained {
	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    ArrayList<Store> store = new ArrayList<>();
	    try {
	        conn = getConnect();
	        String query = "SELECT store_id, name, phone,category,region,table_num, time,menu_price, score FROM store where score > ? order by score desc";
	        ps = conn.prepareStatement(query);
	        ps.setFloat(1, score);
	        rs = ps.executeQuery();
	    
	        while (rs.next()) {
	            store.add(new Store(rs.getString("name"), rs.getString("phone"), rs.getString("category"), rs.getString("region"),
	                    rs.getInt("table_num"), rs.getString("time"), rs.getInt("menu_price"), rs.getFloat("score")));
	        }
	    } finally {
	        closeAll(rs, ps, conn);
	    }
	    return store;
	}

	@Override
	public Store getStore(String name) throws SQLException {
	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    Store s = null;
	    try {
	        conn = getConnect();
	        String query = "SELECT store_id, name, phone,category,region,table_num, time,menu_price, score FROM store where name = ?";
	        ps = conn.prepareStatement(query);
	        ps.setString(1, name);
	        rs = ps.executeQuery();
	    
	        while (rs.next()) {
	            s = new Store(rs.getString("name"), rs.getString("phone"), rs.getString("category"), rs.getString("region"),
	                    rs.getInt("table_num"), rs.getString("time"), rs.getInt("menu_price"), rs.getFloat("score"));
	        }
	    } finally {
	        closeAll(rs, ps, conn);
	    }
	    return s;
	}



}
