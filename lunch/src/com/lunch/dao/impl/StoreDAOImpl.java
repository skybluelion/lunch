package com.lunch.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.lunch.dao.StoreDAO;
import com.lunch.dto.Reservation;
import com.lunch.dto.Store;
import com.lunch.exception.Duplicated;
import com.lunch.exception.IsNotContained;

import config.ServerInfo;

public class StoreDAOImpl implements StoreDAO {
    // Singleton
    private static StoreDAOImpl dao = new StoreDAOImpl();

private StoreDAOImpl() {
}

public static StoreDAOImpl getInstance() {
    return dao;
}

@Override
public Connection getConnect() throws SQLException {
    Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASS);
    System.out.println("DB Server Connected!!");
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

private boolean isExist(int storeId, Connection conn) throws SQLException {
    PreparedStatement ps = null;
    ResultSet rs = null;

    String query = "SELECT store_id FROM Store WHERE store_id = ?";
    ps = conn.prepareStatement(query);
    ps.setInt(1, storeId);
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
public ArrayList<Reservation> getReservation() throws SQLException {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    ArrayList<Reservation> reservations = new ArrayList<>();

    try {
        conn = getConnect();
        String query = "SELECT * FROM reservation ";
        ps = conn.prepareStatement(query);

        rs = ps.executeQuery();
        while (rs.next()) {
            reservations.add(new Reservation(rs.getInt("res_id"), rs.getInt("cust_id"), rs.getInt("store_id"),
                    rs.getString("res_date"), rs.getInt("cust_num")));
        }
    } finally {
        closeAll(conn, ps, rs);
    }

    return reservations;
}

@Override
public ArrayList<Reservation> getReservation(int storeId, String date) throws SQLException {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    ArrayList<Reservation> reservations = new ArrayList<>();

    try {
        conn = getConnect();
        String query = "SELECT * FROM reservation WHERE store_id = ? AND res_date = ?";
        ps = conn.prepareStatement(query);
        ps.setInt(1, storeId);
        ps.setString(2, date);

        rs = ps.executeQuery();
        while (rs.next()) {
            reservations.add(new Reservation(rs.getInt("res_id"), rs.getInt("cust_id"), rs.getInt("store_id"),
                    rs.getString("res_date"), rs.getInt("cust_num")));
        }
    } finally {
        closeAll(conn, ps, rs);
    }

    return reservations;
}

@Override
public void deleteReservation(int resId) throws SQLException {
    Connection conn = null;
    PreparedStatement ps = null;

    try {
        conn = getConnect();
        if (isExistRes(resId, conn)) {
            String query = "DELETE reservation WHERE res_id = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, resId);

            System.out.println(ps.executeUpdate() + " 명 DELETE DONE");
        }
    } finally {
        closeAll(conn, ps);
    }

}

public void addStore(Store store) throws SQLException, IsNotContained, Duplicated {
    Connection conn = null;
    PreparedStatement ps = null;

    try {
        conn = getConnect();
        if (!isExist(store.getStoreId(), conn)) {
            String query = "INSERT INTO store(store_id, name, phone, category, region, table_num, time, menu_price, score) VALUES(store_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?) ";
            ps = conn.prepareStatement(query);
            ps.setString(1, store.getName());
            ps.setString(2, store.getPhone());
            ps.setString(3, store.getCategory());
            ps.setString(4, store.getRegion());
            ps.setInt(5, store.getTableNum());
            ps.setString(6, store.getTime());
            ps.setInt(7, store.getMenuPrice());
            ps.setDouble(8, store.getScore());

            System.out.println(ps.executeUpdate() + " 개 INSERT 성공!!");
            for (int i = 1; i < 5; i++) {
                String query2 = "INSERT INTO seats(store_id, res_date, rest_seat) VALUES (store_seq.CURRVAL,?, ?)";
                ps = conn.prepareStatement(query2);
                ps.setString(1, "23040"+i);
                ps.setInt(2,store.getTableNum());
                System.out.println(ps.executeUpdate() + " 개 INSERT 성공!!");
            }
        } else {
            throw new Duplicated("해당하는 가게가 이미 등록되어 있습니다!!");
        }
    } finally {
        closeAll(conn, ps);
    }
}

@Override
public void updateStore(int storeId, Store store) throws SQLException {
    Connection conn = null;
    PreparedStatement ps = null;

    try {
        conn = getConnect();
        if (isExist(storeId, conn)) {
            String query = "UPDATE store SET name = ?, phone = ?, category = ?, region = ?, table_num = ?, time = ?, menu_price = ?, score = ? WHERE store_id = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, store.getName());
            ps.setString(2, store.getPhone());
            ps.setString(3, store.getCategory());
            ps.setString(4, store.getRegion());
            ps.setInt(5, store.getTableNum());
            ps.setString(6, store.getTime());
            ps.setInt(7, store.getMenuPrice());
            ps.setDouble(8, store.getScore());
            ps.setInt(9, storeId);

            System.out.println(ps.executeUpdate() + " 개 UPDATE 성공!!");
        }

    } finally {
        closeAll(conn, ps);
    }
}

@Override
public void deleteStore(int storeId) throws SQLException, IsNotContained {
    Connection conn = null;
    PreparedStatement ps = null;

    try {
        conn = getConnect();
        if (isExist(storeId, conn)) {
            String query = "DELETE store WHERE store_id = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, storeId);

            System.out.println(ps.executeUpdate() + " 개 DELETE DONE");
        } else {
            throw new IsNotContained("삭제할 대상의 가게가 없습니다!!");
        }
    } finally {
        closeAll(conn, ps);
    }

}
}