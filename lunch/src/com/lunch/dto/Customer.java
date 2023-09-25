package com.lunch.dto;
import java.util.ArrayList;

public class Customer {
    private int custId;
    private String phone;
    private String nickname;
    private String region;

    private ArrayList<VisitedRecord> visitedStore;

    public Customer(int custId, String phone, String nickname, String region, ArrayList<VisitedRecord> visitedStore) {
        this.custId = custId;
        this.phone = phone;
        this.nickname = nickname;
        this.region = region;
        this.visitedStore = visitedStore;
    }
    
    public Customer(int custId, String phone, String nickname, String region) {
        this.custId = custId;
        this.phone = phone;
        this.nickname = nickname;
        this.region = region;
    }
    
    public Customer(String phone, String nickname, String region) {
        this.phone = phone;
        this.nickname = nickname;
        this.region = region;
    
    }
    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public ArrayList<VisitedRecord> getVisitedStore() {
        return visitedStore;
    }

    public void setVisitedStore(ArrayList<VisitedRecord> visitedStore) {
        this.visitedStore = visitedStore;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custId=" + custId +
                ", phone='" + phone + '\'' +
                ", nickname='" + nickname + '\'' +
                ", region='" + region + '\'' +
                ", visitedStore=" + visitedStore +
                '}';
    }
}
